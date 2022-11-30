package com.mobile.megakons.sa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import metodosGenerales.General;

public class MenuLineasProductosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RelativeLayout rvlinea1, rvlinea2, rvlinea3, rvlinea4, rvlinea5, rvlinea6, rvlinea7, rvlinea8, rvlinea9, rvlinea10, rvlinea12, rvlinea13, rvlinea14, rvlinea15, rvlinea17, rvlinea21, rvlinea22, rvlinea23, rvlinea24;
    Intent intentLinea;

    //Metodos Generales - CLase General
    General metodoGeneral;

    //Mantener la sesion iniciada
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lineas_productos);

        //Inicializar Variables
        inicializarVariables();

        /*---------------------------DETERMINANTES------------------------------  CTRL+D -*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        rvlinea1 = findViewById(R.id.rvLinea1);
        rvlinea2 = findViewById(R.id.rvLinea2);
        rvlinea3 = findViewById(R.id.rvLinea3);
        rvlinea4 = findViewById(R.id.rvLinea4);
        rvlinea5 = findViewById(R.id.rvLinea5);
        rvlinea6 = findViewById(R.id.rvLinea6);
        rvlinea7 = findViewById(R.id.rvLinea7);
        rvlinea8 = findViewById(R.id.rvLinea8);
        rvlinea9 = findViewById(R.id.rvLinea9);
        rvlinea10 = findViewById(R.id.rvLinea10);
        rvlinea12 = findViewById(R.id.rvLinea12);
        rvlinea13 = findViewById(R.id.rvLinea13);
        rvlinea14 = findViewById(R.id.rvLinea14);
        rvlinea15 = findViewById(R.id.rvLinea15);
        rvlinea17 = findViewById(R.id.rvLinea17);
        rvlinea21 = findViewById(R.id.rvLinea21);
        rvlinea22 = findViewById(R.id.rvLinea22);
        rvlinea23 = findViewById(R.id.rvLinea23);
        rvlinea24 = findViewById(R.id.rvLinea24);

        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LINEAS DE ARTÍCULOS");
        /*-------------------------- Navigation Drawer Menu -------------------------*/

        /*---------------------- Mostrar y esconder Items -------------------------*/
        //Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_cerrarsesion).setVisible(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_productos);

        //Preferencias sesion iniciada
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        /*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen - MENU SECUNDARIO ---------------------*/
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        ////metodoGeneral.reglasMenuSecundario(navigationView, preferences, verificacionUsuario);


        //Seleccion de lineas
        clickOpcionesLineas();
    }

    private void inicializarVariables() {
        //Importar metodos de la clase general
        metodoGeneral = new General();
    }

    private void guardarLineaSeleccionada(SharedPreferences.Editor editor, String linea){
        //almacena en memoria la linea seleccionada que funcionara al dar click en retroseder en el detalle de un articulo dependiendo la linea

        editor.putString("ultimaLineaSeleccionada", linea);
        editor.commit(); //Almacena la informacion inmediatammente
    }

    private void clickOpcionesLineas() {
        //Mantener la edicion iniciada
        SharedPreferences.Editor editor = preferences.edit();
        //Dar click segun linea
        rvlinea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "01");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "01");
                startActivity(intentLinea);

            }
        });
        //Dar click segun linea
        rvlinea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "02");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "02");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "03");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "03");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "04");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "04");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "05");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "05");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "06");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "06");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "07");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "07");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "08");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "08");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "09");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "09");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "10");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "10");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "12");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "12");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "13");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "13");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "14");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "14");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "15");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "15");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "17");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "17");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "21");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "21");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "22");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "22");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "23");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "23");
                startActivity(intentLinea);
            }
        });
        //Dar click segun linea
        rvlinea24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //linea seleccionada
                guardarLineaSeleccionada(editor, "24");
                //abre intent
                intentLinea = new Intent(MenuLineasProductosActivity.this, ProductosActivity.class);
                intentLinea.putExtra("linea", "24");
                startActivity(intentLinea);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            metodoGeneral.abrirIntentMenuPrincipal(this, MenuLineasProductosActivity.this);
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                metodoGeneral.abrirIntentMenuPrincipal(this, MenuLineasProductosActivity.this);
                break;
            case R.id.nav_productos:
                metodoGeneral.abrirIntentMenuLineasProductos(this, MenuLineasProductosActivity.this);
                break;
            case R.id.nav_notaspendientes:
                metodoGeneral.abrirIntentNotasPedidos(this, MenuLineasProductosActivity.this);
                break;
            case R.id.nav_listaTotalArticulos:
                metodoGeneral.abrirIntentProductosTotal(this, MenuLineasProductosActivity.this);
                break;
            case R.id.nav_clientes:
                metodoGeneral.abrirIntentClientes(this, MenuLineasProductosActivity.this);
                break;
            case R.id.nav_pagina_web:
                metodoGeneral.abrirIntentPaginaWeb(this, MenuLineasProductosActivity.this);
                break;
            case R.id.nav_cerrarsesion:
                metodoGeneral.cerrarSesion(this, MenuLineasProductosActivity.this, preferences);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}