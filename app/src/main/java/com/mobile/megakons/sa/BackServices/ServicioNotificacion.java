package com.mobile.megakons.sa.BackServices;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.megakons.sa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import conexiones.AJSONServicio;

public class ServicioNotificacion extends Service {
    private Context context = this;
    private Timer timer;
    //    private Handler mHandler;
    private Calendar mCalendar;
    // Constants
    private static final int ID_SERVICE = 101;

    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_NOTIFICACION = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_NOTIFICACION();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        reloj();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager) {
        String channelId = "my_service_channelid";
        String channelName = "My Foreground Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;

    }

    public void reloj() {
        timer = new Timer();// Crear objeto de temporizador
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.v("Timer", "run()...");
                mCalendar = Calendar.getInstance();
                int hour = mCalendar.get(Calendar.HOUR_OF_DAY);// HOUR son 12 horas HOUR_OF_DAY son 24 horas
                int minute = mCalendar.get(Calendar.MINUTE);//minuto
                int second = mCalendar.get(Calendar.SECOND) + 1;//Segundos
                if (second == 60) {
                    minute += 1;
                    second = 0;
                }
                if (minute == 60) {
                    hour += 1;
                    minute = 0;
                }
                if (hour == 12) {
                    hour = 0;
                }
                String time = String.format("%d:%02d:%02d", hour, minute, second);
                mCalendar.set(Calendar.SECOND, second);
                mCalendar.set(Calendar.MINUTE, minute);
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);

                Message message = new Message();
                message.what = 0;
                message.obj = time;
                mHandler.sendMessage(message);

                //Lectura de datos al servidor cada cierto tiempo
                if (time.equals("9:15:20") || time.equals("14:35:20")) {
                    mostrarNotificacion();
                }

                if(time.equals("11:15:30") || time.equals("16:10:20")){
                    stopForeground( false );
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotificationManager.cancelAll();
                }


            }
        }, 0, 1000);
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.v("Timer", "handleMessage()..");
            super.handleMessage(msg);
            String str = (String) msg.obj;
            Log.v("RELOJ: ", str);
        }
    };

    private void mostrarNotificacion() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NOTIFICACION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("notificacion");
                            String dato_notificacion = "";
                            String descripcion = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                dato_notificacion = object.getString("VALORC").trim();
                                descripcion = object.getString("DESCRIPC").trim();
                            }
                            if (dato_notificacion.equals("null")) {
                                Log.v("MENSAJE DE LECTURA NOTIFICACION: ", "SERVICIO DE NOTIFICACION" + null);

                            } else {
                                Log.v("MENSAJE DE LECTURA NOTIFICACION: ", "SERVICIO DE NOTIFICACION" + dato_notificacion);
                                // Create the Foreground Service
                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
                                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
                                // Create the PendingIntent
                                String url = "https://api.whatsapp.com/send/?phone=593999404805&text=Hola%2C+quisiera+contactarme+con+ustedes+y+conocer+más+sobre:+"+dato_notificacion.replace(" ","+")+"&type=phone_number&app_absent=0";
                                Uri uri = Uri.parse(url);
                                Intent notifyIntent = new Intent(Intent.ACTION_VIEW, uri);
                                // Set the Activity to start in a new, empty task
                                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent notifyPendingIntent;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    notifyPendingIntent = PendingIntent.getActivity(
                                            getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE
                                    );
                                }else{
                                    notifyPendingIntent = PendingIntent.getActivity(
                                            getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                                }
                                //
                                Notification notification = notificationBuilder.setAutoCancel(true)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setPriority(PRIORITY_DEFAULT)
                                        .setCategory(NotificationCompat.CATEGORY_SERVICE)
                                        .setContentTitle(descripcion)
                                        .setSubText("Notificación MEGAKONS S.A.")
                                        .setContentText(dato_notificacion)
                                        .setContentIntent(notifyPendingIntent)
                                        .build();
                                startForeground(ID_SERVICE, notification);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}