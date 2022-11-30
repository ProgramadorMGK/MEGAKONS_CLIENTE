package com.mobile.megakons.sa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import conexiones.AJSONServicio;
import metodosGenerales.General;

public class LoginActivity extends AppCompatActivity {
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private TextInputEditText usuario, password;
    private Button btn_login;
    private ProgressBar loading;
    private static String URL_LOGIN = ajsonServicio.getIpGeneral()+ ajsonServicio.getURL_LOGIN();
    //Datos de cuenta
    TextView tv_crearCuenta, tv_olvidoContrasenia;

    //Mantener la sesion iniciada
    private SharedPreferences preferences;

    //Clase de metodos generales
    General metodoGeneral = new General();

    //Acction Toolbar
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        inicializacionVariables();

        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inicio de sesión");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        validarSesion();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, URL_LOGIN, Toast.LENGTH_SHORT).show();
                String nUsuario = usuario.getText().toString().trim();
                String nPass = password.getText().toString().trim();

                if (!nUsuario.isEmpty() || !nPass.isEmpty()){
                    Login(nUsuario,nPass);
                }else{
                    usuario.setError("Porfavor, ingrese el usuario");
                    password.setError("Porfavor, ingrese la contraseña");
                }
            }
        });
/*
        String value = "<html>Visit my blog <a href=\"http://www.maxartists.com\">mysite</a> View <a href=\"sherif-activity://myactivity?author=sherif&nick=king\">myactivity</a> callback</html>";
*/
        String crearCuenta = "<html><a href=\"http://192.168.1.49:85/registro-cliente\">Registrarse y crear una cuenta</a></html>";
        String olvidoContrasenia = "<html><a href=\"http://192.168.1.49:85/recuperar-contrase%C3%B1a\">¿Olvidaste tu contraseña?</a></html>";
        tv_crearCuenta.setText(Html.fromHtml(crearCuenta));
        tv_crearCuenta.setMovementMethod(LinkMovementMethod.getInstance());
        tv_olvidoContrasenia.setText(Html.fromHtml(olvidoContrasenia));
        tv_olvidoContrasenia.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        //finishAffinity();
        metodoGeneral.abrirIntentMenuPrincipal(this, LoginActivity.this);
        super.onBackPressed();
    }

    private void inicializacionVariables(){
        loading = findViewById(R.id.loading);
        usuario = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        //Datos cuenta
        tv_crearCuenta = findViewById(R.id.tv_crearCuenta);
        tv_olvidoContrasenia = findViewById(R.id.tv_olvidoContrasenia);

        //Preferencias sesion iniciada
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        //Toolbar
        toolbar = findViewById(R.id.toolbar);
    }

    private void Login(String usuario, String password) {
    loading.setVisibility(View.VISIBLE);
    btn_login.setVisibility(View.GONE);
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")){
                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String usuario = object.getString("USUARIO").trim();
                                String nombre = object.getString("NOMBRE").trim();
                                String idCliente = object.getString("IDCLIENTE").trim();
                                String monto = object.getString("MONTO").trim();
                                String diasCredito = object.getString("DIAS_CREDITO").trim();
                                Toast.makeText(LoginActivity.this, "Bienvenido: " + usuario, Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.GONE);

                                //Mantener la sesión iniciada
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("codigoUsuario", usuario);
                                editor.putString("nombreUsuario", nombre);
                                editor.putString("idCliente", idCliente);
                                editor.commit(); //Almacena la informacion inmediatammente
                                //editor.apply();  //Guarda mientras el programa sigue en ejecucion es decir en segundo plano, utilizalo en modo de muchas variables

                                //Entrada al activity de la cartera

                                Intent intentDetalleCarteraCliente;
                                intentDetalleCarteraCliente = new Intent(LoginActivity.this, ClienteCarteraDetalleActivity.class);
                                intentDetalleCarteraCliente.putExtra("nombreCliente", nombre);
                                intentDetalleCarteraCliente.putExtra("codigoCliente", idCliente);
                                intentDetalleCarteraCliente.putExtra("montoCarteraCliente", monto);
                                intentDetalleCarteraCliente.putExtra("diasCreditoCateraCliente", diasCredito);
                                LoginActivity.this.startActivity(intentDetalleCarteraCliente);
                                //Intent intent = new Intent(LoginActivity.this, ClienteCarteraDetalleActivity.class);
                                //startActivity(intent);
                                finish();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        //Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                    //Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "Error de conexión",Toast.LENGTH_SHORT).show();
                }
            })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<>();
            params.put("usuario", usuario);
            params.put("password",password);
            return params;
        }
    };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //Validar la sesion iniciada
    private void validarSesion(){
        /*------------------------ Mantener la sesion iniciada --------------------------------*/
        String nombreUsuario = preferences.getString("nombreUsuario", null);
        if (nombreUsuario != null){
            //Entrada al menu principal
            irDetalleCarteraClienteSinLoguearse();
        }
    }

    /* ---------------------------- Mantiene la sesion iniciada y nos dirige al menu principal -------------------*/
    private void irDetalleCarteraClienteSinLoguearse(){
        Intent intent = new Intent(LoginActivity.this, ClienteCarteraDetalleActivity.class);
        startActivity(intent);
    }

}