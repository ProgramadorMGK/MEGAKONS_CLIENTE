package com.mobile.megakons.sa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.mobile.megakons.sa.BaseDatos_SQL_LITE.DatosArticulosConexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adaptadores.AdaptadorProductosOffline;
import conexiones.AJSONServicio;
import entidades.ProductoOffline;
import metodosGenerales.General;

public class ProductosOfflineActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {

    //Recycler view
    RecyclerView rv_listaProductosOffline;
    List<ProductoOffline> productoOfflinesLista;
    //Insrtancia Base SQLITE
    DatosArticulosConexion datosArticulosConexionSQLITE;
    //ServiciLo WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_LISTA_PRODUCTOS_OFFLINE = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_LISTA_PRODUCTOS_OFFLINE();
    AdaptadorProductosOffline adaptadorProductosOffline;
    //CustomDialog para actualizar articulos desde la base
    Dialog mensajeConfirmacionActualizarArticulos;
    //Definir buscador
    private androidx.appcompat.widget.SearchView buscadorArticulos;
    private DrawerLayout drawer_layout_productosOffline;
    //Loading Productos
    //ProgressBar progressProductos;
    ProgressDialog progressDialog;

    //Preferencias que vienen desde el Login Activity para mantener iniciada la sesion
    private SharedPreferences preferences;
    String oficinaUsuario;

    //Menu toolbar
    NavigationView navigationView;
    Toolbar toolbar;
    //Metodos Generales - CLase General
    General metodoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_offline);
        //Inicializar Variables de utilizacion para la clase
        inicializarVariables();
        //mostrarMensajeConfirmacionActualizarArticulos
        mostrarMensajeConfirmacionActualizarArticulos();

    }

    private void cargarDatosArticulosEnRecyclerView() {
        datosArticulosConexionSQLITE.open(); //Abre la Conexion con SQLITE
        if (datosArticulosConexionSQLITE.listarArticulos()) {
            productoOfflinesLista = datosArticulosConexionSQLITE.getProductosOfflineLista(); //la lista del select realizado de los articulos se rellena en la lista de la clase de conexion de los articulos
            //Inicializa el adaptador y el recyclerview
            rv_listaProductosOffline.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adaptadorProductosOffline = new AdaptadorProductosOffline(this, productoOfflinesLista, oficinaUsuario);
            rv_listaProductosOffline.setAdapter(adaptadorProductosOffline);
        }
        datosArticulosConexionSQLITE.close(); //Cierra la conexion

    }

    private void inicializarVariables() {
        //Clase General
        metodoGeneral = new General();
        //Recylcer view donde se mostraran los datos
        rv_listaProductosOffline = findViewById(R.id.rv_listaProductosOffline);
        rv_listaProductosOffline.setVisibility(View.GONE);
        productoOfflinesLista = new ArrayList<>();
        //Instancia a la base de datos SQLITE Articulos
        datosArticulosConexionSQLITE = new DatosArticulosConexion(ProductosOfflineActivity.this);
        //inicializa el Cuadro de dialogo para la confirmacion de actualizacion de los articulos
        mensajeConfirmacionActualizarArticulos = new Dialog(ProductosOfflineActivity.this);        //Scroll y cargando
        //progressProductos = findViewById(R.id.progressProductos);
        progressDialog = new ProgressDialog(ProductosOfflineActivity.this);
        //Inicializar buscador
        buscadorArticulos = findViewById(R.id.buscadorProductos);
        buscadorArticulos.setVisibility(View.GONE);
        //Metodo que escucha el buscador de articulos
        initListener();
        //Drawer Layout
        drawer_layout_productosOffline = findViewById(R.id.drawer_layout_productosOffline);
        //menu toolbar
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*-------------------------- Tool Bar --------------------------- */
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LISTA DE ARTÍCULOS");

        //getSupportActionBar().setTitle("LÍNEA " + lineaMenuProductos);
        /*-------------------------- Navigation Drawer Menu -------------------------*/

        /*---------------------- Mostrar y esconder Items -------------------------*/
        //Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_cerrarsesion).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_productosOffline, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout_productosOffline.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_listaTotalArticulos);
        //Mantener la sesion iniciada con preferences
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        oficinaUsuario = preferences.getString("oficinaUsuario", null);
        /*-------------------------- Condicion - Reglas segun el supervisor, vendedor, almacen - MENU SECUNDARIO ---------------------*/
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        metodoGeneral.reglasMenuSecundario(navigationView, preferences, verificacionUsuario);
    }

    private void mostrarMensajeConfirmacionActualizarArticulos() {
        mensajeConfirmacionActualizarArticulos.setContentView(R.layout.custom_dialog_cuadro_de_confirmacion);
        mensajeConfirmacionActualizarArticulos.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //instancia datos del cuadro de confirmacion
        TextView tituloCuadroConfirmacion, detalleCuadroConfirmacion;
        ImageView imagenCuadroConfirmacion, cerrarCuadroConfirmacion;
        //Los botones de confirmacion
        Button btnAceptarConfirmacionActualizarArticulos, btnCancelarConfirmacionActualizarArticulos;
        tituloCuadroConfirmacion = mensajeConfirmacionActualizarArticulos.findViewById(R.id.tituloCustomDialogConfirmacion);
        detalleCuadroConfirmacion = mensajeConfirmacionActualizarArticulos.findViewById(R.id.detalleCustomDialogConfirmacion);
        imagenCuadroConfirmacion = mensajeConfirmacionActualizarArticulos.findViewById(R.id.imagenCustomDialogConfirmacion);
        cerrarCuadroConfirmacion = mensajeConfirmacionActualizarArticulos.findViewById(R.id.cerrarCustomDialogConfirmacion);
        btnAceptarConfirmacionActualizarArticulos = mensajeConfirmacionActualizarArticulos.findViewById(R.id.buttonAceptarCustomDialog);
        btnCancelarConfirmacionActualizarArticulos = mensajeConfirmacionActualizarArticulos.findViewById(R.id.buttonCancelarCustomDialog);
        //Asignar Datos al cuadro de confirmacion
        asignarDatosCuadroConfirmacion(tituloCuadroConfirmacion, detalleCuadroConfirmacion, imagenCuadroConfirmacion);
        //Mostrar Cuadro de confirmacion
        mensajeConfirmacionActualizarArticulos.show();
        //Metodo que surge al dar click en los botones del custom dialog
        botonesMensajesConfirmacion(btnAceptarConfirmacionActualizarArticulos, btnCancelarConfirmacionActualizarArticulos, cerrarCuadroConfirmacion);

        //Evitar Cancelacion del cuadro de diálogo
        cancelarCuadroConfirmacion();
    }

    private void animacionAparecerBuscador() {
        //Toast.makeText(ProductosActivity.this, "Cargando artículos...", Toast.LENGTH_SHORT).show();
        //INICIALIZA LA ANIMACION
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        //buscadorProductos.setVisibility(View.VISIBLE);
        //Asignamos la animacion a la lista de items
        buscadorArticulos.setAnimation(animation);
    }

    private void botonesMensajesConfirmacion(Button btnAceptarConfirmacionActualizarArticulos, Button btnCancelarConfirmacionActualizarArticulos, ImageView btnCerrarConfirmacionActualizarArticulos) {

        btnAceptarConfirmacionActualizarArticulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeConfirmacionActualizarArticulos.dismiss();
                insertarDatosProductosOfflineDesdeJSONaSQLITE(oficinaUsuario);
            }
        });

        btnCancelarConfirmacionActualizarArticulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeConfirmacionActualizarArticulos.dismiss();
                //Cargar dartos de Base Al recycler view
                cargarDatosArticulosEnRecyclerView();
                ocultarCargando();
            }
        });

        btnCerrarConfirmacionActualizarArticulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajeConfirmacionActualizarArticulos.dismiss();
                //Cargar dartos de Base Al recycler view
                cargarDatosArticulosEnRecyclerView();
                ocultarCargando();
            }
        });
    }

    private void ocultarCargando() {
        progressDialog.dismiss();
        animacionAparecerBuscador();
        toolbar.setVisibility(View.VISIBLE);
        buscadorArticulos.setVisibility(View.VISIBLE);
        rv_listaProductosOffline.setVisibility(View.VISIBLE);
    }

    private void cancelarCuadroConfirmacion() {
        mensajeConfirmacionActualizarArticulos.setCancelable(true);
        mensajeConfirmacionActualizarArticulos.setCanceledOnTouchOutside(false);
        mensajeConfirmacionActualizarArticulos.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel(); //se cancela el asyntask
                //volverAmenuLineas();
                //finish();
                finish();
            }
        });
    }

    private void asignarDatosCuadroConfirmacion(TextView titulo, TextView detalle, ImageView imagenRepresentativa) {
        titulo.setText("Artículos");
        detalle.setText("¿Actualizar la lista de artículos y precios?");
        imagenRepresentativa.setImageDrawable(getDrawable(R.drawable.costos_herramientas));
    }

    private void insertarDatosProductosOfflineDesdeJSONaSQLITE(String oficina) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LISTA_PRODUCTOS_OFFLINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("PRODUCTOS");
                            if (jsonArray.length() != 0) {
                                //Almacenamiento de valores de forma secuencial con un ContentValue
                                ContentValues contentValues = new ContentValues();
                                datosArticulosConexionSQLITE.open(); //Abre la conexion
                                datosArticulosConexionSQLITE.eliminaCrea_TB_ARTICULOS();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject productoObject = jsonArray.getJSONObject(i);
                                    contentValues.put("CODIGO", productoObject.getString("CODIGO"));
                                    contentValues.put("NOMBRE", productoObject.getString("NOMBRE"));
                                    contentValues.put("PRECIO", Double.valueOf(productoObject.getString("PRECIO").replace(',','.')));

                                    String existenciaValor = productoObject.getString("EXISTENCIA");
                                    if (existenciaValor.contentEquals("null")) {
                                        contentValues.put("EXISTENCIA", Double.valueOf(0));
                                    } else {
                                        contentValues.put("EXISTENCIA", Double.valueOf(existenciaValor.replace(',','.')));
                                    }
                                    contentValues.put("MEDIDA", productoObject.getString("MEDIDA"));
                                    contentValues.put("ALTERNO", productoObject.getString("ALTERNO"));
                                    //contentValues.put("PRESENTACION", productoObject.getString("PRESENTACION"));
                                    if (productoObject.getString("PRESENTACION").contentEquals("null")) {
                                        contentValues.put("PRESENTACION", "N/A");
                                    } else {
                                        contentValues.put("PRESENTACION", productoObject.getString("PRESENTACION"));
                                    }
                                    contentValues.put("UBICACION", productoObject.getString("UBICACION"));
                                    //Insertar Datos en la tabla TB_ARTICULOS de la base de datos SQLITE
                                    datosArticulosConexionSQLITE.insertarDatosTablaArticulos(contentValues);
                                }
                                datosArticulosConexionSQLITE.close(); //Cerrar la conexion a la base de datos
                                Toast.makeText(ProductosOfflineActivity.this, "Datos Actualizados correctamente", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                animacionAparecerBuscador();
                                toolbar.setVisibility(View.VISIBLE);
                                buscadorArticulos.setVisibility(View.VISIBLE);
                                rv_listaProductosOffline.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(ProductosOfflineActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductosOfflineActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                        //Cargar dartos de Base Al recycler view
                        cargarDatosArticulosEnRecyclerView();
                        ///Asiganr datos a cada item del recycler view
                        ////recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        /////adaptadorProductos = new AdaptadorProductos(ProductosActivity.this,ProductosActivity.this, productos, linea, oficina);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        ////recyclerView.setAdapter(adaptadorProductos);
                        //Obtiene el numero de datos o articulos de la linea por la lista productos
                        ////numeroArticulosLinea = productos.size();
                        ////definirTituloActionBar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mostrarCargandoSinProductos();
                        Toast.makeText(ProductosOfflineActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(ProductosActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                ////params.put("linea", linea);
                params.put("oficina", oficina);
                return params;
            }
        };

        //Arreglo para evitar errores de cuelgue al momento de la peticion de los articulos
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    private void cancelarCargando() {
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                metodoGeneral.abrirIntentMenuPrincipal(this, ProductosOfflineActivity.this);
                break;
            case R.id.nav_productos:
                metodoGeneral.abrirIntentMenuLineasProductos(this, ProductosOfflineActivity.this);
                break;
            case R.id.nav_notaspendientes:
                metodoGeneral.abrirIntentNotasPedidos(this, ProductosOfflineActivity.this);
                break;
            case R.id.nav_listaTotalArticulos:
                break;
            case R.id.nav_pagina_web:
                metodoGeneral.abrirIntentPaginaWeb(this, ProductosOfflineActivity.this);
                break;
            case R.id.nav_clientes:
                metodoGeneral.abrirIntentClientes(this, ProductosOfflineActivity.this);
                break;
            case R.id.nav_cerrarsesion:
                metodoGeneral.cerrarSesion(this, ProductosOfflineActivity.this, preferences);
                break;
        }
        drawer_layout_productosOffline.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        if (drawer_layout_productosOffline.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_productosOffline.closeDrawer(GravityCompat.START);
            //finish();
            //Toast.makeText(ProductosActivity.this, "56456456465 a red MGK...", Toast.LENGTH_SHORT).show();

        } else {
            super.onBackPressed();
        }

    }

    private void cerrarSesion() {
        //preferences.edit().clear().commit();
        preferences.edit().clear().apply();
        Intent intent = new Intent(ProductosOfflineActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        //System.exit(0);
    }

    private void mostrarCargandoSinProductos() {
        //recyclerView.setVisibility(View.GONE);

        buscadorArticulos.setVisibility(View.GONE);
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
        Toast.makeText(ProductosOfflineActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptadorProductosOffline.filter(newText);
        return false;
    }

    private void initListener() {
        buscadorArticulos.setOnQueryTextListener(this);
    }
}