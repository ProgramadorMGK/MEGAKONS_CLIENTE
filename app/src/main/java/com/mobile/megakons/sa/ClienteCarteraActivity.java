package com.mobile.megakons.sa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adaptadores.AdaptadorClientesCartera;
import conexiones.AJSONServicio;
import entidades.ClienteCartera;
import metodosGenerales.General;

public class ClienteCarteraActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {
    //Servicio WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    //ClienteCartera Lista
    RecyclerView recyclerViewClientesCarteras;
    List<ClienteCartera> clientesCarteras;
    private static String URL_LISTA_CLIENTES_CARTERA = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_LISTA_CLIENTES_CARTERAS();
    AdaptadorClientesCartera adaptadorClientesCartera;

    //Buscador Clientes Carteras
    private androidx.appcompat.widget.SearchView buscadorClientesCarteras;
    private DrawerLayout drawer_layout_clientes_carteras;

    //Loading Clientes Carteras
    ProgressDialog progressDialog;

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
        setContentView(R.layout.activity_cliente_cartera);

        //Inicializacion de variables
        inicializarVariables();
        initListenerListaClientesCarteras();
        extraerListaClientesJSON(oficinaUsuario, codigoUsuario);
    }

    private void inicializarVariables() {

        //Clase General
        metodoGeneral = new General();
        //Determinantes
        recyclerViewClientesCarteras = findViewById(R.id.listaClientesCarterasRV);
        clientesCarteras = new ArrayList<>();

        //DETERMINANTE BUSCADOR PRODUCTOS
        buscadorClientesCarteras = findViewById(R.id.buscadorClienteCartera);
        drawer_layout_clientes_carteras = findViewById(R.id.drawer_layout_clientes_carteras);

        //Menu Toolbar
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //Scroll y cargando por lo tanto esconfo el toolbar y el buscador
        buscadorClientesCarteras.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        //progressProductos = findViewById(R.id.progressProductos);
        progressDialog =  new ProgressDialog(ClienteCarteraActivity.this);

        //Mantener la sesion iniciada con preferences
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        oficinaUsuario = preferences.getString("oficinaUsuario", null);
        codigoUsuario = preferences.getString("codigoUsuario", null);


        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LISTADO DE CLIENTES");
        /*-------------------------- Navigation Drawer Menu -------------------------*/

        /*---------------------- Mostrar y esconder Items -------------------------*/
        //Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_cerrarsesion).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_clientes_carteras, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout_clientes_carteras.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_notaspendientes);
        /*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen - MENU SECUNDARIO ---------------------*/
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        metodoGeneral.reglasMenuSecundario(navigationView, preferences, verificacionUsuario);
    }

    //Inicializar Listener de la lista
    private void initListenerListaClientesCarteras() {
        buscadorClientesCarteras.setOnQueryTextListener(this);
    }

    //drawer_layout_productos
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                metodoGeneral.abrirIntentMenuPrincipal(this, ClienteCarteraActivity.this);
                break;
            case R.id.nav_productos:
                metodoGeneral.abrirIntentMenuLineasProductos(this, ClienteCarteraActivity.this);
                break;
            case R.id.nav_notaspendientes:
                metodoGeneral.abrirIntentNotasPedidos(this, ClienteCarteraActivity.this);
                break;
            case R.id.nav_listaTotalArticulos:
                metodoGeneral.abrirIntentProductosTotal(this, ClienteCarteraActivity.this);
                break;
            case R.id.nav_pagina_web:
                metodoGeneral.abrirIntentPaginaWeb(this, ClienteCarteraActivity.this);
                break;
            case R.id.nav_clientes:
                //metodoGeneral.abrirIntentClientes(this, ClienteCarteraActivity.this);
                break;
            case R.id.nav_cerrarsesion:
                metodoGeneral.cerrarSesion(this, ClienteCarteraActivity.this, preferences);
                break;
        }
        drawer_layout_clientes_carteras.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer_layout_clientes_carteras.isDrawerOpen(GravityCompat.START)){
            drawer_layout_clientes_carteras.closeDrawer(GravityCompat.START);
        }else{
            metodoGeneral.abrirIntentMenuPrincipal(this, ClienteCarteraActivity.this);
            super.onBackPressed();
        }

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptadorClientesCartera.filtradoClientes(newText);
        return false;
    }

    private void extraerListaClientesJSON(String oficina, String codigo) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LISTA_CLIENTES_CARTERA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("CLIENTES");
                            //mostrarCargando();
                            if (jsonArray.length() != 0) {
                                //progressProductos.setVisibility(View.GONE);
                                animacionAparecerBuscador();
                                progressDialog.dismiss();
                                //recyclerView.setVisibility(View.VISIBLE);
                                //drawer_layout_notas.setVisibility(View.VISIBLE);

                                //Scroll y cargando por lo tanto esconfo el toolbar y el buscador
                                toolbar.setVisibility(View.VISIBLE);
                                buscadorClientesCarteras.setVisibility(View.VISIBLE);
                                //Ciclo que obtiene los datos del JSON
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject clienteObject = jsonArray.getJSONObject(i);
                                    ClienteCartera clienteCartera = new ClienteCartera();
                                    clienteCartera.setCliente(clienteObject.getString("CLIENTE").toString());
                                    clienteCartera.setNombre(clienteObject.getString("NOMBRE").toString());
                                    clienteCartera.setMonto(Double.valueOf(clienteObject.getString("MONTO").toString()));
                                    clienteCartera.setDias_Credito(Integer.valueOf(clienteObject.getString("DIAS_CREDITO").replace(',', '.')));
                                    clientesCarteras.add(clienteCartera);
                                }
                            } else {
                                Toast.makeText(ClienteCarteraActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClienteCarteraActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                        ///Asiganr datos a cada item del recycler view
                        recyclerViewClientesCarteras.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adaptadorClientesCartera = new AdaptadorClientesCartera(ClienteCarteraActivity.this, ClienteCarteraActivity.this, clientesCarteras);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        recyclerViewClientesCarteras.setAdapter(adaptadorClientesCartera);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mostrarCargandoSinDatos();
                        Toast.makeText(ClienteCarteraActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", codigo);
                params.put("oficina", oficina);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void animacionAparecerBuscador(){
        //Toast.makeText(NotasPendientesActivity.this, "Cargando artículos...", Toast.LENGTH_SHORT).show();
        //INICIALIZA LA ANIMACION
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        //buscadorProductos.setVisibility(View.VISIBLE);
        //Asignamos la animacion a la lista de items
        buscadorClientesCarteras.setAnimation(animation);
    }

    private void mostrarCargando() {
        progressDialog.show();
        cancelarCargando();
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
        //Toast.makeText(NotasPendientesActivity.this, "Cargando artículos...", Toast.LENGTH_SHORT).show();
    }

    private void mostrarCargandoSinDatos() {
        //Scroll y cargando por lo tanto esconfo el toolbar y el buscador
        toolbar.setVisibility(View.GONE);
        buscadorClientesCarteras.setVisibility(View.GONE);

        //recyclerView.setVisibility(View.GONE);
        //drawer_layout_notas.setVisibility(View.GONE);
        finish();
        /*ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );*/
        //params.setMargins(0,50,0,0);
        //progressProductos.setLayoutParams(params);
        //progressProductos.setPadding(0, 150, 0, 0);
        //progressProductos.setVisibility(View.VISIBLE);
        Toast.makeText(ClienteCarteraActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
    }

    private void cancelarCargando(){
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
    }
}