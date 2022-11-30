package com.mobile.megakons.sa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import conexiones.AJSONServicio;
import metodosGenerales.General;

public class PaginaWebActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Clases
    AJSONServicio ajsonServicio;
    //WebView en donde se mostrara el rutero
    WebView webViewRutero;
    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //Loading Productos
    //ProgressBar progressProductos;
    ProgressDialog progressDialog;

    //Acction Toolbar
    Toolbar toolbar;

    //SwipeRefresh del rutero
    SwipeRefreshLayout swipeRefreshRutero;

    //ProgressBar cargar pagina Rutero
    RelativeLayout relativeCargarProgressBar;
    ProgressBar progressBarRutero;

    //Activities
    Intent intentMenuProductos;
    Intent intentNotasPendientes;
    Intent intentDespachosPendientes;
    Intent intentRutero;

    //Metodos Generales - CLase General
    General metodoGeneral;
    //Preferencias que vienen desde el Login Activity para mantener iniciada la sesion
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutero);
        //Inicializar Variables /*---------------------------DETERMINANTES------------------------------  CTRL+D -*/
        inicializacionVariables();
        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MEGAKONS S.A.");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        //PROGRESSBAR CARGA - asignar Tamaño de recorrido
        progressBarRutero.setMax(100);

        //CREAR PAGINA WEB RUTERO
        webViewRutero.getSettings().setJavaScriptEnabled(true); //Permite que la pagina funcione correctamente
        webViewRutero.getSettings().setBuiltInZoomControls(true); //Permite que el zoom sea responsive
        webViewRutero.loadUrl(ajsonServicio.getURL_PaginaWeb());

        //ROGRESSBAR CARGA - Cargar pagina mientras recorre el progressbar junto a Web View Rutero metodo Chrome
        progressBarRutero.setProgress(0);
        webViewRutero.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                //Mostrar Cargando
                mostrarCargando();
                //Asignar progreso a la barra
                progressBarRutero.setProgress(newProgress);
                if (newProgress == 100){
                    //ocultar cargando
                    progressDialog.dismiss();
                    //Ocultar ProgressBar
                    progressBarRutero.setVisibility(View.INVISIBLE);
                    relativeCargarProgressBar.setVisibility(View.GONE);
                }else{
                    //Mostrar Barra de progreso
                    relativeCargarProgressBar.setVisibility(View.VISIBLE);
                    progressBarRutero.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });


        //Navegar en el Web view sin abrir el Navegador
        webViewRutero.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false; //Permite refrescar la pagina en el webview
            }
        });


        //Refrescar Rutero
        swipeRefreshRutero.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webViewRutero.reload();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshRutero.setRefreshing(false);
                    }
                }, 4*1000);
            }
        });

        /*---------------------- Mostrar y esconder Items -------------------------*/
        //Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_cerrarsesion).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_pagina_web);

    }

    @Override
    public void onBackPressed() {
        //Opciones del menu de navegacion
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //Opciones de navegacion pagina web
            if (webViewRutero.canGoBack()){
                webViewRutero.goBack();
            }else{
                super.onBackPressed();
                finish();
            }
            //super.onBackPressed();
            //finishAffinity();
            ////finish();
            ////System.exit(0);
            //Toast.makeText(MenuActivity.this, "Dio atras", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                Intent intentMenuPrincipal = new Intent(PaginaWebActivity.this, MenuActivity.class);
                startActivity(intentMenuPrincipal);
                finish();
                break;
            case R.id.nav_productos:
                intentMenuProductos = new Intent(PaginaWebActivity.this, MenuLineasProductosActivity.class);
                startActivity(intentMenuProductos);
                //Toast.makeText(MenuActivity.this, "Porfavor, seleccione una Línea", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.nav_clientes:
                metodoGeneral.abrirIntentClientes(this, PaginaWebActivity.this);
                break;
            case R.id.nav_notaspendientes:
                Intent intentNotas = new Intent(PaginaWebActivity.this, NotasPendientesActivity.class);
                startActivity(intentNotas);
                finish();
                break;
            case R.id.nav_pagina_web:
                break;
            case R.id.nav_cerrarsesion:
                cerrarSesion();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void mostrarCargando() {

        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel(); //se cancela el asyntask
                //volverAmenuLineas();
                //finish();
                finish();
            }
        });
        progressDialog.setContentView(R.layout.progress_dialog_cargando);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        /*ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );*/
        //params.setMargins(0,50,0,0);
        //progressProductos.setLayoutParams(params);
        //progressProductos.setPadding(0, 150, 0, 0);
        //progressProductos.setVisibility(View.VISIBLE);
        //CARGANDO DATOS MENSAJE
        //Toast.makeText(ProductosDetallesActivity.this, "Cargando datos...", Toast.LENGTH_SHORT).show();
    }

    private  void inicializacionVariables(){
        metodoGeneral = new General();
        //Conexion
        ajsonServicio = new AJSONServicio();
        //Determinantes Menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //WenView en donde se mostrara ell rutero es decir la pagina web
        webViewRutero = (WebView) findViewById(R.id.webviewRutero);
        //ProgressBar Cargar Pagina Rutero
        relativeCargarProgressBar = findViewById(R.id.relativeCargarProgressBar);
        progressBarRutero = findViewById(R.id.progressBarRutero);
        //SwipeRefresh rutero
        swipeRefreshRutero = findViewById(R.id.refrescarRutero);
        //Scroll y cargando
        //progressProductos = findViewById(R.id.progressProductos);
        progressDialog =  new ProgressDialog(PaginaWebActivity.this);
        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        //Mantener la sesion iniciada con preferences
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        /*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen - MENU SECUNDARIO ---------------------*/
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        metodoGeneral.reglasMenuSecundario(navigationView, preferences, verificacionUsuario);
    }

    private void cerrarSesion(){
        //preferences.edit().clear().commit();
        preferences.edit().clear().apply();
        Intent intent = new Intent(PaginaWebActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        //System.exit(0);
    }



}