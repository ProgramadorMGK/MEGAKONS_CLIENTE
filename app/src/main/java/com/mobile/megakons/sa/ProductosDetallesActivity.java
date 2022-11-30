package com.mobile.megakons.sa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.util.HashMap;
import java.util.Map;

import conexiones.AJSONServicio;
import entidades.ProductoDetalles;
import metodosGenerales.General;

public class ProductosDetallesActivity extends AppCompatActivity {
    //Servicio WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_DETALLE_PRODUCTOS = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_DETALLE_PRODUCTOS();

    //Variables Imagenes y textos del layout
    ImageView imagenDetalle;
    TextView nombreProductoDetalle, lineaDetalle, bodegaDetalle, presentacionDetalle, descuentoDetalle, codigoDetalle, unidDetalle, stockDetalle, aliasDetalle, marcaDetalle, modeloDetalle, precioDetalle, alternoDetalle;

    //Obtener línea del menu de Productos
    Bundle datoCodigoArticulo;
    String codigoArticuloDetallesProducto, nombreArticuloDetallesProducto, existenciaArticuloDetallesProducto, costoArticuloDetallesProducto, lineaArticuloDetallesProducto, oficinaArticuloDetallesProducto;

    //Loading Productos
    //ProgressBar progressProductos;
    ProgressDialog progressDialog;
    LinearLayout vistaPrincipalDetalleProductos;

    //Acction Toolbar
    Toolbar toolbar;
    private General metodoGeneral = new General();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_productos);

        //Action Bar volver

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Inicializar variables
        inicializarVariables();


        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(codigoArticuloDetallesProducto);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        //Lectura a JSON
        extraerProductosJSON(codigoArticuloDetallesProducto, oficinaArticuloDetallesProducto);

        precioDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoGeneral.enviaMensajeWhatsApp("593999404805","Hola MEGAKONS S.A. Quisiera adquirir este producto: " + codigoArticuloDetallesProducto + " - " + nombreArticuloDetallesProducto, ProductosDetallesActivity.this);
            }
        });

    }

    private void inicializarVariables() {
        //Imagen y textos del detalle del producto Seleccionado
        imagenDetalle = findViewById(R.id.imagenDetalle);
        nombreProductoDetalle = findViewById(R.id.nombreProductoDetalle);
        lineaDetalle = findViewById(R.id.lineaDetalle);
        bodegaDetalle = findViewById(R.id.bodegaDetalle);
        presentacionDetalle = findViewById(R.id.presentacionDetalle);
        descuentoDetalle = findViewById(R.id.descuentoDetalle);
        codigoDetalle = findViewById(R.id.codigoDetalle);
        unidDetalle = findViewById(R.id.unidDetalle);
        stockDetalle = findViewById(R.id.stockDetalle);
        aliasDetalle = findViewById(R.id.aliasDetalle);
        marcaDetalle = findViewById(R.id.marcaDetalle);
        modeloDetalle = findViewById(R.id.modeloDetalle);
        precioDetalle = findViewById(R.id.precioDetalle);
        alternoDetalle = findViewById(R.id.alternoDetalle);
        //DETERMINANTE OBTENCION datos DEl adaptador del recycler view por item producto
        datoCodigoArticulo = this.getIntent().getExtras();
        codigoArticuloDetallesProducto = datoCodigoArticulo.getString("codigoArticulo");
        nombreArticuloDetallesProducto = datoCodigoArticulo.getString("nombreArticulo");
        existenciaArticuloDetallesProducto = datoCodigoArticulo.getString("existenciaArticulo");
        costoArticuloDetallesProducto = datoCodigoArticulo.getString("costoArticulo");
        lineaArticuloDetallesProducto = datoCodigoArticulo.getString("lineaArticulo");
        oficinaArticuloDetallesProducto = datoCodigoArticulo.getString("oficinaArticulo");
        //Scroll y cargando
        //progressProductos = findViewById(R.id.progressProductos);
        progressDialog = new ProgressDialog(ProductosDetallesActivity.this);
        vistaPrincipalDetalleProductos = findViewById(R.id.vistaPrincipalDetalleProductos);
        vistaPrincipalDetalleProductos.setVisibility(View.GONE);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
    }

    private void extraerProductosJSON(String articulo, String oficina) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DETALLE_PRODUCTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("PRODUCTO");
                            if (jsonArray.length() != 0) {
                                //ocultar cargando
                                progressDialog.dismiss();
                                vistaPrincipalDetalleProductos.setVisibility(View.VISIBLE);
                                //obtener productos del jason
                                JSONObject productoObject = jsonArray.getJSONObject(0);
                                ProductoDetalles productoDetalles = new ProductoDetalles();
                                productoDetalles.setDatoImagen(productoObject.getString("IMAGEN").toString());
                                productoDetalles.setUnid(productoObject.getString("UNID").toString());
                                productoDetalles.setPresentacion(productoObject.getString("PRESENTACION").toString());
                                productoDetalles.setBodega(productoObject.getString("BODEGA").toString());
                                productoDetalles.setDescuento(productoObject.getString("DESCUENTO").toString());
                                productoDetalles.setAliasweb(productoObject.getString("ALIASWEB").toString());
                                productoDetalles.setModelo(productoObject.getString("MODELO").toString());
                                productoDetalles.setMarca(productoObject.getString("MARCA").toString());
                                productoDetalles.setLinea(productoObject.getString("LINEA").toString());
                                productoDetalles.setCodigoAlterno(productoObject.getString("ALTERNO").toString());
                                ///Asiganr datos a cada item del JSON y del detalle
                                if (productoDetalles.getImagen() != null) {
                                    imagenDetalle.setImageBitmap(productoDetalles.getImagen());
                                } else {
                                    imagenDetalle.setImageResource(R.drawable.mascota);
                                }
                                //lineaDetalle.setText(lineaArticuloDetallesProducto); //eN CASO DE QUE SEA DEL MENU LINEAS
                                //Total en general
                                lineaDetalle.setText(productoDetalles.getLinea());
                                //
                                unidDetalle.setText(productoDetalles.getUnid());
                                if (!productoDetalles.getPresentacion().contentEquals("null")) {
                                    presentacionDetalle.setText(productoDetalles.getPresentacion());
                                } else {
                                    presentacionDetalle.setText("NA");
                                }
                                bodegaDetalle.setText(productoDetalles.getBodega());
                                nombreProductoDetalle.setText(nombreArticuloDetallesProducto);
                                codigoDetalle.setText(codigoArticuloDetallesProducto);
                                descuentoDetalle.setText(Html.fromHtml("<b>Descuento: </b>" + productoDetalles.getDescuento()));
                                aliasDetalle.setText(Html.fromHtml("<b>Alias Web: </b>" + productoDetalles.getAliasweb()));
                                if (!productoDetalles.getModelo().contentEquals("null")) {
                                    modeloDetalle.setText(Html.fromHtml("<b>Modelo: </b>" + productoDetalles.getModelo()));
                                } else {
                                    modeloDetalle.setText(Html.fromHtml("<b>Modelo: </b>" + "No Asignado"));
                                }
                                if (!productoDetalles.getMarca().contentEquals("null")) {
                                    marcaDetalle.setText(Html.fromHtml("<b>Marca: </b>" + productoDetalles.getMarca()));
                                } else {
                                    marcaDetalle.setText(Html.fromHtml("<b>Marca: </b>" + "No Asignado"));
                                }
                                //precioDetalle.setText(Html.fromHtml("<b>PVP: </b> $ " + costoArticuloDetallesProducto));
                                stockDetalle.setText(Html.fromHtml("<b>Existencia: </b>" + existenciaArticuloDetallesProducto));
                                String auxCodigoAlterno = productoDetalles.getCodigoAlterno();
                                if (auxCodigoAlterno.contentEquals("null")) {
                                    alternoDetalle.setText(Html.fromHtml("<b>Código Alterno: </b>" + "No Asignado"));
                                } else {
                                    alternoDetalle.setText(Html.fromHtml("<b>Código Alterno: </b>" + auxCodigoAlterno));
                                }
                            } else {
                                Toast.makeText(ProductosDetallesActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductosDetallesActivity.this, "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mostrarCargandoSinDetalle();
                        Toast.makeText(ProductosDetallesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("articulo", articulo);
                //params.put("oficina", oficina);
                //El dato de oficina va quemado debido a que no se conoce la oficina del usuario de logueo
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    private void mostrarCargandoSinDetalle() {
        vistaPrincipalDetalleProductos.setVisibility(View.GONE);
        finish();
        /*ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );*/
        //params.setMargins(0,50,0,0);
        //progressProductos.setLayoutParams(params);
        //progressProductos.setPadding(0, 150, 0, 0);
        //progressProductos.setVisibility(View.VISIBLE);
        Toast.makeText(ProductosDetallesActivity.this, "Conectándose a la red...", Toast.LENGTH_SHORT).show();
    }
}