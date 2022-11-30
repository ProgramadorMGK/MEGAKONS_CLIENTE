package com.mobile.megakons.sa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import fragments.ClientesCarteraDetallesAnticiposFragment;
import fragments.ClientesCarteraDetallesFragment;
import metodosGenerales.General;


public class ClienteCarteraDetalleActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    //Codigo cliente que se desea ver la cartera y el detalle
    Bundle datoCliente;
    String codigoCliente, nombreCliente;

    private DrawerLayout drawer_layout_detalles_cartera_clientes;
    //Preferencias que vienen desde el Login Activity para mantener iniciada la sesion
    private SharedPreferences preferences;
    String codigoUsuario;
    String oficinaUsuario;
    //Menu toolbar
    NavigationView navigationView;
    Toolbar toolbar;
    //Metodos Generales - CLase General
    General metodoGeneral;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cartera_detalle);
        //Inicializacion de variables
        inicializarVariables();
    }

    private void inicializarVariables() {
        //Clase General
        metodoGeneral = new General();
        datoCliente = this.getIntent().getExtras();
        //Mantener la sesion iniciada con preferences
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
//        codigoCliente = datoCliente.getString("codigoCliente").substring(datoCliente.getString("codigoCliente").indexOf(':')+1).trim();
        if (datoCliente == null) {
            codigoCliente = preferences.getString("idCliente", null);
            nombreCliente = preferences.getString("nombreUsuario", null);
        }else{
            codigoCliente = datoCliente.getString("codigoCliente");
            nombreCliente = datoCliente.getString("nombreCliente");
        }
        drawer_layout_detalles_cartera_clientes = findViewById(R.id.drawer_clientes_carteras_detalle);
        //menu toolbar
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CARTERA DEL CLIENTE " + codigoCliente);
        /*-------------------------- Navigation Drawer Menu -------------------------*/

        /*---------------------- Mostrar y esconder Items -------------------------*/
        //Bottom nav
        BottomNavigationView btnNav = findViewById(R.id.bottonNavigationViewCarteraClientes);
        btnNav.setOnNavigationItemSelectedListener(navListener);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_detalles_cartera_clientes, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout_detalles_cartera_clientes.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_clientes);
        //Setting Fragment Principal
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_clientes_cartera, new ClientesCarteraDetallesFragment()).commit();                       


        /*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen - MENU SECUNDARIO ---------------------*/
//        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
//        metodoGeneral.reglasMenuSecundario(navigationView, preferences, verificacionUsuario);
    }

    @Override
    public void onBackPressed() {

        if (drawer_layout_detalles_cartera_clientes.isDrawerOpen(GravityCompat.START)){
            drawer_layout_detalles_cartera_clientes.closeDrawer(GravityCompat.START);
        }else{
            metodoGeneral.abrirIntentMenuPrincipal(this, ClienteCarteraDetalleActivity.this);
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                metodoGeneral.abrirIntentMenuPrincipal(this, ClienteCarteraDetalleActivity.this);
                break;
            case R.id.nav_productos:
                metodoGeneral.abrirIntentMenuLineasProductos(this, ClienteCarteraDetalleActivity.this);
                break;
            case R.id.nav_notaspendientes:
                metodoGeneral.abrirIntentNotasPedidos(this, ClienteCarteraDetalleActivity.this);
                break;
            case R.id.nav_listaTotalArticulos:
                metodoGeneral.abrirIntentProductosTotal(this, ClienteCarteraDetalleActivity.this);
                break;
            case R.id.nav_pagina_web:
                metodoGeneral.abrirIntentPaginaWeb(this, ClienteCarteraDetalleActivity.this);
                break;
            case R.id.nav_clientes:
                metodoGeneral.abrirIntentClientes(this, ClienteCarteraDetalleActivity.this);
                break;
            case R.id.nav_cerrarsesion:
                metodoGeneral.cerrarSesion(this, ClienteCarteraDetalleActivity.this, preferences);
                break;
        }
        drawer_layout_detalles_cartera_clientes.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getCliente() {
        return codigoCliente;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public String getCodigoCliente() {
        return codigoCliente;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()){
                case R.id.itemNavigationViewDocumentosClientes:
                    selectedFragment = new ClientesCarteraDetallesFragment();
                    break;
                case R.id.itemNavigationViewAnticipos:
                    selectedFragment = new ClientesCarteraDetallesAnticiposFragment();
                    break;
            }
            //Begin Transaction
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_clientes_cartera, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_documentos_revista_mensual_001, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_descargar_revista_mensual:
                metodoGeneral.cerrarSesion(this, ClienteCarteraDetalleActivity.this, preferences);
                return true;
        }
        return false;
    }

}