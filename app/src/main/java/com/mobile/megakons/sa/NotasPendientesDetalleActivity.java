package com.mobile.megakons.sa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adaptadores.AdaptadorNotasPedidosDetallePendientes;
import conexiones.AJSONServicio;
import entidades.NotaPedidoDetalles;
import metodosGenerales.General;

public class NotasPendientesDetalleActivity extends AppCompatActivity {
    //Servicio WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_DETALLE_NOTAS_PEDIDOS_PENDIENTES = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_DETALLE_NOTAS_PEDIDO();
    //Obtener línea del menu de Notas de Pedido
    Bundle datoNotaPedido;
    String numeroNotaPedidoPendiente, nombreClienteNotaPedidoPendiente, fechaNotaPedidoPendiente, totalNotaPedidoPendiente;
    //nOTAS DE PEDIDO PENDIENTES Lista
    RecyclerView recyclerViewDetalleNotaPendientes;
    List<NotaPedidoDetalles> notasPedidosDetallesPendientes;
    //TextView's Layouts
    TextView nppNombreCliente, nppIdCliente, nppNumeroNP, nppEstadoNP, nppFechaNP, nppTotalNP, nppTotalNPP, nppTotalNPPerdido;
    //Adaptador de filas
    AdaptadorNotasPedidosDetallePendientes adaptadorNotasPedidosDetallePendientes;
    //Linear Layout que contiene toda la pantalla y sus elementos
    LinearLayout contenedorPantalla;
    //Total Perdido
    Double totalNotaPedidoPerdido = 0.00;
    //ProgressBar cargando ROJO;
    ProgressDialog progressDialog;
    //Acction Toolbar
    Toolbar toolbar;
    //Clase General - Obtencion de los Metodos General
    General metodoGeneral;
    //Preferencias que vienen desde el Login Activity para mantener iniciada la sesion
    private SharedPreferences preferences;
    String oficinaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_pendientes_detalle);
        //Inicializar variables();
        inicializarVariables();
        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nota de Pedido " + numeroNotaPedidoPendiente);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
        //Asignacion de datos a la cabecera
        asignarDatosCabecera();
        //OBTENER Datos JSON
        extraerNotasPedidosPendientesDetallesJSON(numeroNotaPedidoPendiente, oficinaUsuario);
        //Asignacion de datos a la cabecera despues de obtener Detalle
    }


    private void extraerNotasPedidosPendientesDetallesJSON(String numero, String oficina) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DETALLE_NOTAS_PEDIDOS_PENDIENTES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("DETALLE_NOTAS");
                            //mostrarCargando();
                            if (jsonArray.length() != 0) {
                                //progressProductos.setVisibility(View.GONE);
                                ////animacionAparecerBuscador();
                                progressDialog.dismiss();
                                //recyclerView.setVisibility(View.VISIBLE);
                                //drawer_layout_productos.setVisibility(View.VISIBLE);
                                toolbar.setVisibility(View.VISIBLE);
                                contenedorPantalla.setVisibility(View.VISIBLE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject notasPedidosPendientesObject = jsonArray.getJSONObject(i);
                                    NotaPedidoDetalles notaPedidoDetalles = new NotaPedidoDetalles();
                                    notaPedidoDetalles.setEstado(notasPedidosPendientesObject.getString("EGEN_ESTADO").toString());
                                    notaPedidoDetalles.setId_cliente(notasPedidosPendientesObject.getString("GRPS_IDCLIENTE").toString());
                                    notaPedidoDetalles.setCodigo_articulo(notasPedidosPendientesObject.getString("ARTL_ARTICULO").toString());
                                    notaPedidoDetalles.setNombre_articulo(notasPedidosPendientesObject.getString("NOMBRE_ARTICULO").toString());
                                    notaPedidoDetalles.setCant_nota_pedido(Double.valueOf(notasPedidosPendientesObject.getString("CANT_NOTA_PEDIDO")));
                                    notaPedidoDetalles.setCant_venta_perdida(Double.valueOf(notasPedidosPendientesObject.getString("CANT_VENTA_PERDIDA").replace(',','.')));
                                    notaPedidoDetalles.setTotal_venta_perdida(Double.valueOf(notasPedidosPendientesObject.getString("TOTAL_VENTA_PERDIDA").replace(',','.')));
                                    //Toast.makeText(NotasPendientesDetalleActivity.this, notaPedidoDetalles.getTotal_venta_perdida().toString(), Toast.LENGTH_LONG).show();
                                    notasPedidosDetallesPendientes.add(notaPedidoDetalles);
                                    //Obtener Datos faltantes de cabecera
                                    ///Negrita setText(Html.fromHtml("<b>Modelo: </b>" + productoDetalles.getModelo()));
                                    //nppIdCliente.setText(notaPedidoDetalles.getId_cliente());
                                    nppIdCliente.setText(Html.fromHtml("<b>CLIENTE: </b>" + notaPedidoDetalles.getId_cliente()));
                                    //Toast.makeText(NotasPendientesDetalleActivity.this, notaPedidoDetalles.getEstado(), Toast.LENGTH_SHORT).show();
                                    if (notaPedidoDetalles.getEstado().contentEquals("VG")){
                                        //nppEstadoNP.setText("VIGENTE");
                                        nppEstadoNP.setText(Html.fromHtml("<b>ESTADO: </b>" + "VIGENTE"));
                                    }else{
                                        //nppEstadoNP.setText("EN PROCESO");
                                        nppEstadoNP.setText(Html.fromHtml("<b>ESTADO: </b>" + "EN PROCESO"));
                                    }
                                    //Sumar total perdido de todos los articulos
                                    //Toast.makeText(NotasPendientesDetalleActivity.this, notaPedidoDetalles.getTotal_venta_perdida().toString(), Toast.LENGTH_LONG).show();
                                    totalNotaPedidoPerdido = totalNotaPedidoPerdido + notaPedidoDetalles.getTotal_venta_perdida();
                                    //nppTotalNPP.setText("$ " + totalNotaPedidoPerdido.toString());
                                    nppTotalNPP.setText("$ " + metodoGeneral.redondearNumero(totalNotaPedidoPerdido).replace('.', ','));
                                    nppTotalNPPerdido.setText(metodoGeneral.redondearNumero(totalNotaPedidoPerdido).replace('.', ','));

                                }
                            } else {
                                Toast.makeText(NotasPendientesDetalleActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NotasPendientesDetalleActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                        ///Asiganr datos a cada item del recycler view
                        recyclerViewDetalleNotaPendientes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adaptadorNotasPedidosDetallePendientes = new AdaptadorNotasPedidosDetallePendientes(NotasPendientesDetalleActivity.this, notasPedidosDetallesPendientes);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        recyclerViewDetalleNotaPendientes.setAdapter(adaptadorNotasPedidosDetallePendientes);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ////mostrarCargandoSinProductos();
                        Toast.makeText(NotasPendientesDetalleActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ProductosActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("numero", numero);
                params.put("oficina", oficina);
                return params;
            }

        };

        //Arreglo en la linea 1 para evitar errores de cuelgue al momento de la peticion de las Notas de pedido
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        5000000,
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

    private void inicializarVariables() {
        //Metodos Clase General
        metodoGeneral = new General();
        //Linear Layout contenedor de pantalla
        contenedorPantalla = findViewById(R.id.contenedorPantalla);
        contenedorPantalla.setVisibility(View.GONE);
        //Scroll y cargando
        //progressProductos = findViewById(R.id.progressProductos);
        progressDialog =  new ProgressDialog(NotasPendientesDetalleActivity.this);
        //Mantener la sesion iniciada con preferences
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        oficinaUsuario = preferences.getString("oficinaUsuario", null);
        //Recycleer View Tabla de articulos
        recyclerViewDetalleNotaPendientes = findViewById(R.id.rv_detalleNotasPedidoPendientes);
        notasPedidosDetallesPendientes = new ArrayList<>();
        //DETERMINANTE OBTENCION datos DEl adaptador del recycler view por item producto
        datoNotaPedido = this.getIntent().getExtras();
        //numeroNotaPedidoPendiente = "131402";
        numeroNotaPedidoPendiente = datoNotaPedido.getString("numeroNP");
        nombreClienteNotaPedidoPendiente = datoNotaPedido.getString("nombreClienteNP");
        fechaNotaPedidoPendiente = datoNotaPedido.getString("fechaNP");
        totalNotaPedidoPendiente = datoNotaPedido.getString("totalNP");
        //Inicializar TEXTVIEWS Cabecera -> nppIdCliente, nppNumeroNP, nppEstadoNP, nppFechaNP, nppTotalNP, nppTotalNPP;
        nppNombreCliente = findViewById(R.id.nppNombreCliente);
        nppIdCliente = findViewById(R.id.nppIdCliente);
        nppNumeroNP = findViewById(R.id.nppNumeroNP);
        nppEstadoNP = findViewById(R.id.nppEstadoNP);
        nppFechaNP = findViewById(R.id.nppFechaNP);
        nppTotalNP = findViewById(R.id.nppTotalNP);
        nppTotalNPP = findViewById(R.id.nppTotalNPP);
        nppTotalNPPerdido = findViewById(R.id.nppTotalNPPerdido);
        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

    }

    private void asignarDatosCabecera(){
        nppNombreCliente.setText(nombreClienteNotaPedidoPendiente);
        //nppIdCliente.setText(notasPedidosDetallesPendientes.lastIndexOf(0));
        nppNumeroNP.setText("NOTA DE PEDIDO " + numeroNotaPedidoPendiente);
        //NEGRITA .setText(Html.fromHtml("<b>ESTADO: </b>" + "EN PROCESO"));
        //nppFechaNP.setText(fechaNotaPedidoPendiente);
        nppFechaNP.setText(Html.fromHtml("<b>FECHA: </b>" + fechaNotaPedidoPendiente));
        nppTotalNP.setText("$ " + totalNotaPedidoPendiente);
    }

}