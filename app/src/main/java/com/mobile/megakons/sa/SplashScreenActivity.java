package com.mobile.megakons.sa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN;

    //VARIABLES
    Animation topAnimation, bottonAnimation;
    ImageView image;
    TextView frase;

    //Mantener la sesion iniciada
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        inicializacionVariables();


        image.setAnimation(topAnimation);
        frase.setAnimation(bottonAnimation);
        irMenuPrincipalsinLoguearse();
    }

    private void inicializacionVariables() {
        //Preferencias sesion iniciada
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Mantener la sesion iniciada con preferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("codigoUsuario", "AL");
        editor.putString("oficinaUsuario", "001");
        editor.commit();

        SPLASH_SCREEN = 5000;

        //Animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottonAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView_mgk);
        frase = findViewById(R.id.txt_frase);

    }

    //Validar la sesion iniciada
    private boolean validarSesion() {

        boolean irAlogin = true;

        /*------------------------ Mantener la sesion iniciada --------------------------------*/
        String nombreUsaurio = preferences.getString("nombreUsuario", null);
        if (nombreUsaurio != null) {
            irAlogin = false;
            return irAlogin;
        } else {
            return irAlogin;
        }
    }

    /* ---------------------------- Mantiene la sesion iniciada y nos dirige al menu principal -------------------*/
    private void irMenuPrincipalsinLoguearse() {
        Intent intent = new Intent(SplashScreenActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

}