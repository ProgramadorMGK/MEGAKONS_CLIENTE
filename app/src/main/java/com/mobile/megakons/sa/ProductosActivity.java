package com.mobile.megakons.sa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import adaptadores.AdaptadorProductos;
import conexiones.AJSONServicio;
import entidades.Producto;
import metodosGenerales.General;

public class ProductosActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener,
        NavigationView.OnNavigationItemSelectedListener {
    //ServiciLo WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();

    //Productos Lista
    RecyclerView recyclerView;
    List<Producto> productos;
    private static String URL_LISTA_PRODUCTOS = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_LISTA_PRODUCTOS();
    private static String URL_ENTIDAD_PRINCIPAL = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_ENTIDAD_PRINCIPAL();
    //Fecha Servidor
    private static Date fechaServer;
    //Adaptador Productos
    AdaptadorProductos adaptadorProductos;

    //Obtener línea del menu de Productos
    Bundle datoLinea;
    String lineaMenuProductos;

    //Buscador Productos
    private androidx.appcompat.widget.SearchView buscadorProductos;
    private DrawerLayout drawer_layout_productos;

    //Loading Productos
    //ProgressBar progressProductos;
    ProgressDialog progressDialog;


    //Preferencias que vienen desde el Login Activity para mantener iniciada la sesion
    private SharedPreferences preferences;
    String oficinaUsuario;

    //Linea seleccionada de Shared Preferences
    String ultimaLineaSeleccionada;

    //Menu toolbar
    NavigationView navigationView;
    Toolbar toolbar;

    //Numero de Articulos - Numero de items de una Lista
    private static int numeroArticulosLinea;
    //Metodos Generales - CLase General
    General metodoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);


        //Inicializacion de variables
        inicializarVariables();
        initListenerListaProductos();
        //Metodo que se utiliza para saber si la ultima linea seleccionada regresio de detalle producto o salió de menu lineas productos
        if (lineaMenuProductos == null){
            extraerProductosJSON(ultimaLineaSeleccionada, oficinaUsuario);
        }else{
            extraerProductosJSON(lineaMenuProductos, oficinaUsuario);
        }

    }

    private void inicializarVariables() {
        //Clase General
        metodoGeneral = new General();
        //obtener fecha
        extraerFechaServidorJSON();
        //DETERMINANTES
        recyclerView = findViewById(R.id.listaProductosRV);
        productos = new ArrayList<>();

        //DETERMINANTE OBTENCION LINEA DEL MENU DE PRODUCTOS
        datoLinea = this.getIntent().getExtras();
        lineaMenuProductos = datoLinea.getString("linea");

        //DETERMINANTE BUSCADOR PRODUCTOS
        buscadorProductos = findViewById(R.id.buscadorProductos);
        buscadorProductos.setVisibility(View.GONE);
        drawer_layout_productos = findViewById(R.id.drawer_layout);
        //drawer_layout_productos.setVisibility(View.GONE);

        //Scroll y cargando
        //progressProductos = findViewById(R.id.progressProductos);
        progressDialog =  new ProgressDialog(ProductosActivity.this);

        //Mantener la sesion iniciada con preferences
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        oficinaUsuario = preferences.getString("oficinaUsuario", null);

        //Obtener ultima linea seleccionada de Shared Preferences
        ultimaLineaSeleccionada = preferences.getString("ultimaLineaSeleccionada", null);

        //menu toolbar
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*-------------------------- Tool Bar --------------------------- */
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);


        //getSupportActionBar().setTitle("LÍNEA " + lineaMenuProductos);
        /*-------------------------- Navigation Drawer Menu -------------------------*/

        /*---------------------- Mostrar y esconder Items -------------------------*/
        //Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_cerrarsesion).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_productos, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout_productos.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_productos);
        /*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen - MENU SECUNDARIO ---------------------*/
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        /////metodoGeneral.reglasMenuSecundario(navigationView, preferences, verificacionUsuario);
    }

    private void extraerFechaServidorJSON() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ENTIDAD_PRINCIPAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("SERVER");
                            //mostrarCargando();
                            if (jsonArray.length() != 0) {
                                //Scroll y cargando por lo tanto esconfo el toolbar y el buscador
                                ////*toolbar.setVisibility(View.VISIBLE);
                                ////*buscadorDetalleCarteraClientes.setVisibility(View.VISIBLE);
                                //Ciclo que obtiene los datos del JSON
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject entidadObject = jsonArray.getJSONObject(i);
                                    fechaServer = metodoGeneral.conversionStringDate(entidadObject.getString("FECHA").toString());
                                }
                            } else {
                                Toast.makeText(ProductosActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductosActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ////*mostrarCargandoSinDatos();
                        finish();
                        Toast.makeText(ProductosActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void definirTituloActionBar(){
        if (lineaMenuProductos == null){
            //getSupportActionBar().setTitle("LÍNEA " + ultimaLineaSeleccionada + " - " + numeroArticulosLinea + " ARTÍCULOS");
            getSupportActionBar().setTitle("CANTIDAD - " + numeroArticulosLinea + " ARTÍCULOS");
        }else{
            //getSupportActionBar().setTitle("LÍNEA " + lineaMenuProductos + " - " + numeroArticulosLinea + " ARTÍCULOS");
            getSupportActionBar().setTitle("CANTIDAD - " + numeroArticulosLinea + " ARTÍCULOS");
        }
    }

    @Override
    public void onBackPressed() {

        if (drawer_layout_productos.isDrawerOpen(GravityCompat.START)){
            drawer_layout_productos.closeDrawer(GravityCompat.START);
            //finish();
            //Toast.makeText(ProductosActivity.this, "56456456465 a red MGK...", Toast.LENGTH_SHORT).show();

        }else{
            super.onBackPressed();
        }

    }

    //drawer_layout_productos
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                metodoGeneral.abrirIntentMenuPrincipal(this, ProductosActivity.this);
                break;
            case R.id.nav_productos:
                //metodoGeneral.abrirIntentMenuLineasProductos(this, ProductosActivity.this);
                break;
            case R.id.nav_notaspendientes:
                metodoGeneral.abrirIntentNotasPedidos(this, ProductosActivity.this);
                break;
            case R.id.nav_listaTotalArticulos:
                metodoGeneral.abrirIntentProductosTotal(this, ProductosActivity.this);
                break;
            case R.id.nav_pagina_web:
                metodoGeneral.abrirIntentPaginaWeb(this, ProductosActivity.this);
                break;
            case R.id.nav_clientes:
                metodoGeneral.abrirIntentClientes(this, ProductosActivity.this);
                break;
            case R.id.nav_cerrarsesion:
                metodoGeneral.cerrarSesion(this, ProductosActivity.this, preferences);
                break;
        }
        drawer_layout_productos.closeDrawer(GravityCompat.START);
        return true;
    }

/*    private void cerrarSesion(){
        //preferences.edit().clear().commit();
        preferences.edit().clear().apply();
        Intent intent = new Intent(ProductosActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        //System.exit(0);
    }*/

    private void animacionAparecerBuscador(){
        //Toast.makeText(ProductosActivity.this, "Cargando artículos...", Toast.LENGTH_SHORT).show();
        //INICIALIZA LA ANIMACION
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        //buscadorProductos.setVisibility(View.VISIBLE);
        //Asignamos la animacion a la lista de items
        buscadorProductos.setAnimation(animation);
    }

    HurlStack hurlStack = new HurlStack() {
        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    httpsURLConnection.setSSLSocketFactory(VolleySingleton.getInstance(getApplicationContext()).getGlobalSSlFactory());
                }
                httpsURLConnection.setHostnameVerifier(VolleySingleton.getInstance(getApplicationContext()).getHostnameVerifier());
                Log.i("SSL","SUCCESS");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return httpsURLConnection;
        }
    };

    private void extraerProductosJSON(String linea, String oficina) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LISTA_PRODUCTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("PRODUCTOS");
                            //mostrarCargando();
                            if (jsonArray.length() != 0) {
                                //progressProductos.setVisibility(View.GONE);
                                animacionAparecerBuscador();
                                progressDialog.dismiss();
                                //recyclerView.setVisibility(View.VISIBLE);
                                //drawer_layout_productos.setVisibility(View.VISIBLE);
                                buscadorProductos.setVisibility(View.VISIBLE);
                                toolbar.setVisibility(View.VISIBLE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject productoObject = jsonArray.getJSONObject(i);
                                    Producto producto = new Producto();
                                    producto.setCodigo(productoObject.getString("CODIGO").toString());
                                    producto.setCodigoAlterno(productoObject.getString("ALTERNO").toString());
                                    producto.setNombre(productoObject.getString("NOMBRE").toString());
                                    //producto.setPvp(Double.valueOf(productoObject.getString("PVP").replace(',', '.')));
                                    ////producto.setPvp(Double.valueOf(productoObject.getString("PVP").replace(',', '.')));
                                    ////producto.setStock(Double.valueOf(productoObject.getString("STOCK").replace(',', '.')));
                                    producto.setImagen(productoObject.getString("IMAGEN").toString());
                                    //Lectura e ingreso de Fechas
                                    Date FechaCreacion = metodoGeneral.conversionStringDate(productoObject.getString("FECHA_CREACION").toString());
                                    producto.setFecha_creacion(FechaCreacion);
                                    //Calcular diferencia de fechas
                                    int diasCreacion = (int) ((fechaServer.getTime()-FechaCreacion.getTime())/86400000);
                                    producto.setDias_creado_articulo(diasCreacion);
                                    if(diasCreacion <= 30){
                                        producto.setNuevo('S');
                                    }else{
                                        producto.setNuevo('N');
                                    }
                                    productos.add(producto);
                                }
                            } else {
                                Toast.makeText(ProductosActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductosActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                        ///Asiganr datos a cada item del recycler view
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adaptadorProductos = new AdaptadorProductos(ProductosActivity.this,ProductosActivity.this, productos, linea, oficina);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        recyclerView.setAdapter(adaptadorProductos);
                        //Obtiene el numero de datos o articulos de la linea por la lista productos
                        numeroArticulosLinea = productos.size();
                        definirTituloActionBar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mostrarCargandoSinProductos();
                        Toast.makeText(ProductosActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ProductosActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("linea", linea);
                params.put("oficina", "001");
                return params;
            }
        };

        //Arreglo en la linea 1 para evitar errores de cuelgue al momento de la peticion de los articulos
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this, hurlStack);
        requestQueue.add(stringRequest);
    }



    //Inicializar Listener de la lista
    private void initListenerListaProductos() {
        buscadorProductos.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptadorProductos.filtradoProductos(newText);
        return false;
    }


    private void mostrarCargandoSinProductos() {
        //recyclerView.setVisibility(View.GONE);

        buscadorProductos.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        //drawer_layout_productos.setVisibility(View.GONE);
        finish();
        /*ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );*/
        //params.setMargins(0,50,0,0);
        //progressProductos.setLayoutParams(params);
        //progressProductos.setPadding(0, 150, 0, 0);
        //progressProductos.setVisibility(View.VISIBLE);
        Toast.makeText(ProductosActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(ProductosActivity.this, "Cargando artículos...", Toast.LENGTH_SHORT).show();
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

