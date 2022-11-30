package com.mobile.megakons.sa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.mobile.megakons.sa.BackServices.ServiceNotificaciones;
import com.mobile.megakons.sa.BackServices.ServicioNotificacion;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.Date;

import adaptadores.AdaptadorSliderMenuPrincipal;
import metodosGenerales.General;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Metodos Generales - CLase General
    General metodoGeneral;
    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RelativeLayout relativeLineasProductos, relativeNotasPedidoPendientes, relativeProductosTotal, relativePaginaWeb, relativeClientes, relativeGestionCobranza, relativeVisitanos, relativeContactanos, relativeConocenos;
    LinearLayout linearLayout2, linearLayout3;
    RelativeLayout relativeKpisVentas, relativeDocumentos;
    CardView cardViewKpisVentas;
    //Activities
    Intent intentMenuProductos;
    Intent intentGestionCobranza;
    Intent intentNotasPendientes;
    Intent intentPaginaWeb;
    Intent intentProductosTotal;
    Intent intentClientes;
    Intent intentKpisVentasMenu;
    Intent intentDocumentos;
    //Variable de slider view
    SliderView slider_view_menu;
    AdaptadorSliderMenuPrincipal sliderMenuPrincipal;

    //Preferencias que vienen desde el Login Activity para mantener iniciada la sesion
    private SharedPreferences preferences;

    //Obtener Hora y Fecha del sistema
    SimpleDateFormat simpleDateFormat;
    String currentDateandTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //HttpsTrustManager.allowAllSSL();

        /*---------------------------DETERMINANTES------------------------------  CTRL+D -*/
        inicializacionVariables();

        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);

        /*------------------------ Mantener la sesion iniciada --------------------------------*/

        String nombreUsuario = preferences.getString("nombreUsuario", null);
        if (nombreUsuario != null) {
            getSupportActionBar().setTitle(Html.fromHtml("¡ <b>" + saludoBienvenida(currentDateandTime).toUpperCase() + "</b> " + nombreUsuario + " !"));
        }

        /*-------------------------- Navigation Drawer Menu OPCIONES -------------------------*/

        relativeLineasProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMenuProductos = new Intent(MenuActivity.this, MenuLineasProductosActivity.class);
                startActivity(intentMenuProductos);
                //Toast.makeText(MenuActivity.this, "Porfavor, seleccione una Línea", Toast.LENGTH_LONG).show();
            }
        });

        relativeGestionCobranza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentGestionCobranza = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intentGestionCobranza);
                //Toast.makeText(MenuActivity.this, "Porfavor, seleccione una Línea", Toast.LENGTH_LONG).show();
            }
        });

        relativeClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentClientes = new Intent(MenuActivity.this, ClienteCarteraActivity.class);
                startActivity(intentClientes);
            }
        });

        relativeNotasPedidoPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentNotasPendientes = new Intent(MenuActivity.this, NotasPendientesActivity.class);
                startActivity(intentNotasPendientes);
            }
        });

        relativeProductosTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentProductosTotal = new Intent(MenuActivity.this, ProductosOfflineActivity.class);
                startActivity(intentProductosTotal);
            }
        });

/*        relativePaginaWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentPaginaWeb = new Intent(MenuActivity.this, PaginaWebActivity.class);
                startActivity(intentPaginaWeb);
            }
        });*/

        relativeContactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String url = "http://api.whatsapp.com/send/?phone=593999404805&text=Hola%2C+quisiera+contactarme+con+ustedes.&type=phone_number&app_absent=0";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
                metodoGeneral.enviaMensajeWhatsApp("593999404805","Hola MEGAKONS S.A. Quisiera contactarme con ustedes.", MenuActivity.this);
            }
        });

        relativeVisitanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.facebook.com/megakons.ec";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        relativeConocenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.instagram.com/megakons_ec/";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //Menú de navegación
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        /*--------------------------- Slider de menu -------------------------------*/
        //asignacionImagenesSlider();
        /*---------------------- Mostrar y esconder Items -------------------------*/
        //Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_cerrarsesion).setVisible(false);

        iniciarServicioNotificaciones();
    }

    //Mostrar y esconder opciones segun reglas de incio de sesión del usuario
/*    public void reglasMenu(){
        //Menu
        Menu menu = navigationView.getMenu();
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        if(verificacionUsuario.contentEquals("2")){
            //Ocultar la informacion para almacen
            linearLayout2.setVisibility(View.GONE);
            menu.findItem(R.id.nav_notaspendientes).setVisible(false);
            menu.findItem(R.id.nav_clientes).setVisible(false);
            cardViewKpisVentas.setVisibility(View.GONE);
            menu.findItem(R.id.nav_Kpis).setVisible(false);
        }else if(verificacionUsuario.contentEquals("1")){
            //Ocultar la informacion para SUPERVISORES DE VENTAS
            linearLayout2.setVisibility(View.GONE);
            menu.findItem(R.id.nav_notaspendientes).setVisible(false);
            menu.findItem(R.id.nav_clientes).setVisible(false);
        }else if(verificacionUsuario.contentEquals("0")){
            //Ocultar la informacion para vendedores
            cardViewKpisVentas.setVisibility(View.GONE);
            menu.findItem(R.id.nav_Kpis).setVisible(false);
        }
    }*/

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            //Toast.makeText(MenuActivity.this, "Dio atras", Toast.LENGTH_SHORT).show();
            Intent logout_intent = new Intent(MenuActivity.this, MenuActivity.class);
            logout_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            logout_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            logout_intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(logout_intent);

            MenuActivity.this.finish();
            finish();
            //super.onBackPressed();
            //finishAffinity();
            //System.exit(0);
            //finish();
            System.exit(0);
            //Toast.makeText(MenuActivity.this, "Dio atras", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_productos:
                intentMenuProductos = new Intent(MenuActivity.this, MenuLineasProductosActivity.class);
                startActivity(intentMenuProductos);
                finish();
                //Toast.makeText(MenuActivity.this, "Porfavor, seleccione una Línea", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_gestion_cobranza:
                intentGestionCobranza = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intentGestionCobranza);
                finish();
                //Toast.makeText(MenuActivity.this, "Porfavor, seleccione una Línea", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_notaspendientes:
                Intent intentNotas = new Intent(MenuActivity.this, NotasPendientesActivity.class);
                startActivity(intentNotas);
                finish();
                break;
            case R.id.nav_listaTotalArticulos:
                Intent intentListaTotalArticulos = new Intent(MenuActivity.this, ProductosOfflineActivity.class);
                startActivity(intentListaTotalArticulos);
                finish();
                break;
            case R.id.nav_pagina_web:
                Intent intentPaginaWeb = new Intent(MenuActivity.this, PaginaWebActivity.class);
                startActivity(intentPaginaWeb);
                finish();
                break;
            case R.id.nav_clientes:
                Intent intentClientes = new Intent(MenuActivity.this, ClienteCarteraActivity.class);
                startActivity(intentClientes);
                finish();
                break;
            case R.id.nav_cerrarsesion:
                metodoGeneral.cerrarSesion(this, MenuActivity.this, preferences);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void inicializacionVariables() {
        //Importar metodos de la clase general
        metodoGeneral = new General();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Determinantes Menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout3 = findViewById(R.id.linearLayout3);
        cardViewKpisVentas = findViewById(R.id.cardViewKpisVentas);
        relativeLineasProductos = findViewById(R.id.relative1);
        relativeGestionCobranza = findViewById(R.id.relative11);
        relativeNotasPedidoPendientes = findViewById(R.id.relative2);
        relativeProductosTotal = findViewById(R.id.relative3);
        ////relativePaginaWeb =  findViewById(R.id.relative4);
        relativeClientes = findViewById(R.id.relative5);
        relativeKpisVentas = findViewById(R.id.relativeKpisVentas);
        relativeDocumentos = findViewById(R.id.relativeDocumentos);
        relativeVisitanos = findViewById(R.id.relative7);
        relativeContactanos = findViewById(R.id.relative8);
        relativeConocenos = findViewById(R.id.relative10);
        //Determinantes slider
        slider_view_menu = findViewById(R.id.slider_view_menu);
        asignacionImagenesSlider();
        //Hora y fecha del sistema
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //currentDateandTime = simpleDateFormat.format(new Date()); //Hora y fecha
        //currentDateandTime = simpleDateFormat.format(new Date().getTime()); //Solo Hora Minutos Segundos
        currentDateandTime = simpleDateFormat.format(new Date().getTime()); //Solo Hora
        //Verificar usuario para diferencir opcioones de menu segun el rol del usuario
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        /*
         *//*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen  ---------------------*//*
        //metodoGeneral.reglasMenuPrincipal(preferences, linearLayout2, linearLayout3);
        *//*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen - MENU SECUNDARIO ---------------------*//*
        metodoGeneral.reglasMenuSecundario(navigationView, preferences, verificacionUsuario);
        */
    }

    private void asignacionImagenesSlider() {

        int[] imageids = new int[]{
                R.drawable.megakons,
                R.drawable.marcas_importaciones,
                R.drawable.img_slider_001
        };

        sliderMenuPrincipal = new AdaptadorSliderMenuPrincipal(imageids);
        slider_view_menu.setSliderAdapter(sliderMenuPrincipal);
        slider_view_menu.setIndicatorAnimation(IndicatorAnimationType.WORM);
        slider_view_menu.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        slider_view_menu.startAutoCycle();
    }

    private String saludoBienvenida(String currentDateandTime) {
        String recorteHora = currentDateandTime.substring(11, 13);
        //Toast.makeText(MenuActivity.this, recorteHora, Toast.LENGTH_SHORT).show();
        //04-01-2021 12
        int recorteHoraFormato = Integer.valueOf(recorteHora);
        String saludo = "Buenas Noches";
        if (recorteHora.contains("04") || recorteHora.contains("05") || recorteHora.contains("06") || recorteHora.contains("07") || recorteHora.contains("08") || recorteHora.contains("09") || recorteHora.contains("10") || recorteHora.contains("11")) {
            saludo = "Buenos Días";
        } else if (recorteHoraFormato >= 18) {
            saludo = "Buenas Noches";
        } else if (recorteHoraFormato >= 12) {
            saludo = "Buenas Tardes";
        }
        return saludo;
    }

    private void iniciarServicioNotificacion() {
        startService(new Intent(MenuActivity.this, ServiceNotificaciones.class));
        //stopService(new Intent(MenuActivity.this, ServiceNotificaciones.class));
    }

    private void iniciarServicioNotificaciones() {
        Intent intent = new Intent(MenuActivity.this, ServicioNotificacion.class);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(MenuActivity.this, intent);
        } else {
            startService(intent);
        }*/
        startService(intent);
    }

}