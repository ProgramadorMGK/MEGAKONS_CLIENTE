package com.mobile.megakons.sa.BackServices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * La clase ServiceNotificaciones - Permite ejecutar en segundo plano acciones de notificacion
 */

public class ServiceNotificaciones extends Service {

    private Context context = this;
    private Timer timer;
    //    private Handler mHandler;
    private Calendar mCalendar;
    
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int idProcess) {
        Toast.makeText(context, "PREBA", Toast.LENGTH_SHORT).show();
        reloj();
        //Identificar el proceso que se esta iniciando
       return START_STICKY_COMPATIBILITY;
    }

    public void fecha(){
        /*creamos un objeto de una clase y llamamo al metodo de la clase*/
        Time today=new Time(Time.getCurrentTimezone());
        /*Optenemos la fecha actual */
        today.setToNow();
        /*Creamos variables int y llamamos a los atributos de la clase time*/
        int dia=today.monthDay;
        int mes=today.month;
        int ano=today.year;
        int hora = today.hour;
        /*El mes lo sumamos mas 1 por que la clase time me da un mes atrasado */
        mes=mes+1;
        //vt.setText("Fecha   Dia : " + dia + " Mes : "+mes+" Año : "+ano);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(timer!=null){
            timer.cancel();// Cerrar temporizador
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void reloj(){
        timer = new Timer();// Crear objeto de temporizador
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.v("Timer","run()...");
                mCalendar = Calendar.getInstance();
                int hour = mCalendar.get(Calendar.HOUR_OF_DAY);// HOUR son 12 horas HOUR_OF_DAY son 24 horas
                int minute = mCalendar.get(Calendar.MINUTE);//minuto
                int second = mCalendar.get(Calendar.SECOND) + 1;//Segundos
                if (second == 60) {
                    minute += 1;
                    second = 0;
                }
                if (minute == 60){
                    hour += 1;
                    minute = 0;
                }
                if (hour == 12){
                    hour = 0;
                }
                String time = String.format("%d:%02d:%02d", hour, minute, second);
                mCalendar.set(Calendar.SECOND, second);
                mCalendar.set(Calendar.MINUTE, minute);
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);

                Message message=new Message();
                message.what=0;
                message.obj=time;
                mHandler.sendMessage(message);

            }
        },0,1000);
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.v("Timer","handleMessage()..");
            super.handleMessage(msg);
            String str=(String)msg.obj;
            Log.v("RELOJ: ", str);
        }
    };
    
    
}
