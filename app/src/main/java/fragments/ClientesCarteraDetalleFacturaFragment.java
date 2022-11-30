package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.megakons.sa.ClienteCarteraDetalleAbonosCHActivity;
import com.mobile.megakons.sa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adaptadores.AdaptadorClientesCarteraDetalleFactura;
import conexiones.AJSONServicio;
import entidades.FacturaDetalles;
import metodosGenerales.General;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientesCarteraDetalleFacturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesCarteraDetalleFacturaFragment extends Fragment  implements androidx.appcompat.widget.SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Servicio WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_LISTA_CLIENTES_CARTERAS_DETALLE_CHEQUES = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_LISTA_CLIENTES_CARTERAS_DETALLE_CHEQUES();
    private static String URL_FACTURA_MAESTRO_CLIENTE  = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_FACTURA_MAESTRO_CLIENTE();
    private static String URL_FACTURA_DETALLE_CLIENTE = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_FACTURA_DETALLE_CLIENTE();
    //Abonos Cheques Lista
    RecyclerView recyclerViewClientesCarterasDetalleABCH;
    private List<FacturaDetalles> listaFacturaDetalles;
    //Buscador Detalle Cartera de Clientes
    private androidx.appcompat.widget.SearchView buscadorClientesCarterasDetalleABCH;
    //Adaptador Datos
    AdaptadorClientesCarteraDetalleFactura adaptadorClientesCarteraDetalleCheques;
    //Context
    private static Context context;
    //Metodos Generales - CLase General
    General metodoGeneral;
    //Views
    View view;
    //Obtener plcbId
    private static String numeroFactura, serieFactura;
    //Loading
    ProgressDialog progressDialog;

    //Variables de presentacion sin datos
    ConstraintLayout contenedorDetalleCC;
    RelativeLayout mensajeDatosVacio;
    TextView tv1;
    ImageView img1;
    Fragment fragment_clientes_cartera_detalle_abonos;
    TextView tvNombreClienteCarteraDetalleACH;

    //Datos maestro factura
    TextView baseIva0ClientesCarterasDetallesFacturasTV, baseIva12ClientesCarterasDetallesFacturasTV, ivaClientesCarterasDetallesFacturasTV, valorPagarClientesCarterasDetallesFacturasTV;

    public ClientesCarteraDetalleFacturaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientesCarteraDetallesChequesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientesCarteraDetalleFacturaFragment newInstance(String param1, String param2) {
        ClientesCarteraDetalleFacturaFragment fragment = new ClientesCarteraDetalleFacturaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clientes_cartera_detalles_facturas, container, false);
        inicializarVariables();
        initListenerListaClientesCarterasDetallesAbonosCheques();
        //Obtener Datos del activity
        ClienteCarteraDetalleAbonosCHActivity clienteCarteraDetalleAbonosCHActivity = (ClienteCarteraDetalleAbonosCHActivity) getActivity();
        numeroFactura = clienteCarteraDetalleAbonosCHActivity.getNumeroFactura();
        serieFactura = clienteCarteraDetalleAbonosCHActivity.getSerieFactura();
        //Obtiene los datos del json
        extraerDetalleFacturaCarterasClientesJSON(numeroFactura, serieFactura);
        extraerDatosPagarFacturaCarterasClientesJSON(numeroFactura, serieFactura);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_clientes_cartera_detalle_abonos, container, false);
        return view;
    }

    private void inicializarVariables() {
        //Clase General
        metodoGeneral = new General();
        //DETERMINANTES
        recyclerViewClientesCarterasDetalleABCH = view.findViewById(R.id.listaClientesCarterasDetallesFacturasRV);
        listaFacturaDetalles = new ArrayList<>();
        //DETERMINANTES
        buscadorClientesCarterasDetalleABCH = getActivity().findViewById(R.id.buscadorClientesCarterasDetalleABCH);
        //Inicializar mostrar sin datos
        tvNombreClienteCarteraDetalleACH = getActivity().findViewById(R.id.tvNombreClienteCarteraDetalleACH);
        contenedorDetalleCC = getActivity().findViewById(R.id.contenedorDetalleCC);
        contenedorDetalleCC.setVisibility(View.GONE);
        mensajeDatosVacio = getActivity().findViewById(R.id.mensajeDatosVacio);
        mensajeDatosVacio.setVisibility(View.GONE);
        tv1 = getActivity().findViewById(R.id.tv1);
        tv1.setText("Cliente " + tvNombreClienteCarteraDetalleACH.getText() + " tiene 0 cheques posfechados");
        //DETERMINANTES
        //buscadorClientesCarterasDetalleABCH.setVisibility(View.GONE);
        progressDialog =  new ProgressDialog(context);

        //Inicializar datos de maetsro de factura
        baseIva0ClientesCarterasDetallesFacturasTV = view.findViewById(R.id.baseIva0ClientesCarterasDetallesFacturasTV);
        baseIva12ClientesCarterasDetallesFacturasTV = view.findViewById(R.id.baseIva12ClientesCarterasDetallesFacturasTV);
        ivaClientesCarterasDetallesFacturasTV = view.findViewById(R.id.ivaClientesCarterasDetallesFacturasTV);
        valorPagarClientesCarterasDetallesFacturasTV = view.findViewById(R.id.valorPagarClientesCarterasDetallesFacturasTV);
    }
    //Inicializar Listener de la lista
    private void initListenerListaClientesCarterasDetallesAbonosCheques() {
        buscadorClientesCarterasDetalleABCH.setOnQueryTextListener(this);
    }

    private void extraerDatosPagarFacturaCarterasClientesJSON(String numeroFactura, String serieFactura) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FACTURA_MAESTRO_CLIENTE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("FACTURA");
                            contenedorDetalleCC.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                            if (jsonArray.length() != 0) {
                                //Ciclo que obtiene los datos del JSON
                                ////buscadorClientesCarterasDetalleABCH.setVisibility(View.VISIBLE);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject carteraClienteMaestroFacturaObject = jsonArray.getJSONObject(i);
                                    FacturaDetalles facturaDetalle= new FacturaDetalles();
                                    //Lectura e ingreso de Datos Numericos
                                    double BASEIVA0 = Double.valueOf(carteraClienteMaestroFacturaObject.getString("BASEIVA0").replace(',', '.'));
                                    baseIva0ClientesCarterasDetallesFacturasTV.setText(Html.fromHtml("Base IVA 0%:  <b>" + metodoGeneral.string_Moneda(BASEIVA0) + "</b>"));
                                    double BASEIVA = Double.valueOf(carteraClienteMaestroFacturaObject.getString("BASEIVA").replace(',', '.'));
                                    baseIva12ClientesCarterasDetallesFacturasTV.setText(Html.fromHtml("Base IVA 12%:  <b>" + metodoGeneral.string_Moneda(BASEIVA) + "</b>"));
                                     double IVA = Double.valueOf(carteraClienteMaestroFacturaObject.getString("IVA").replace(',', '.'));
                                    ivaClientesCarterasDetallesFacturasTV.setText(Html.fromHtml("IVA:  <b>" + metodoGeneral.string_Moneda(IVA) + "</b>"));
                                    double TOTAL = Double.valueOf(carteraClienteMaestroFacturaObject.getString("TOTAL").replace(',', '.'));
                                    valorPagarClientesCarterasDetallesFacturasTV.setText(Html.fromHtml("Valor a pagar: <b>" + metodoGeneral.string_Moneda(TOTAL) + "</b>"));
                                }
                            } else {
                                buscadorClientesCarterasDetalleABCH.setVisibility(View.GONE);
                                mensajeDatosVacio.setVisibility(View.VISIBLE);
                                //Toast.makeText(getActivity(), "Error con los datos...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getActivity().finish();
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("numero", numeroFactura);
                params.put("serie", serieFactura);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void extraerDetalleFacturaCarterasClientesJSON(String numeroFactura, String serieFactura) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FACTURA_DETALLE_CLIENTE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("FACTURA");
                            contenedorDetalleCC.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                            if (jsonArray.length() != 0) {
                                //Ciclo que obtiene los datos del JSON
                                ////buscadorClientesCarterasDetalleABCH.setVisibility(View.VISIBLE);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject carteraClienteDetalleFacturaObject = jsonArray.getJSONObject(i);
                                    FacturaDetalles facturaDetalle= new FacturaDetalles();
                                    facturaDetalle.setCodigo_articulo(carteraClienteDetalleFacturaObject.getString("ARTL_ARTICULO").toString());
                                    facturaDetalle.setNombre_articulo(carteraClienteDetalleFacturaObject.getString("NOMBRE_ARTICULO").toString());
                                    //Lectura e ingreso de Datos Numericos
                                    double cantidad = Double.valueOf(carteraClienteDetalleFacturaObject.getString("CANTIDAD").replace(',', '.'));
                                    facturaDetalle.setCant_articulo(cantidad);
                                    double precioUnitario = Double.valueOf(carteraClienteDetalleFacturaObject.getString("PRECUNIT").replace(',', '.'));
                                    facturaDetalle.setPrec_articulo(precioUnitario);
                                    double porcentajeDescuento = Double.valueOf(carteraClienteDetalleFacturaObject.getString("PORCDESC").replace(',', '.'));
                                    facturaDetalle.setPorcd_articulo(porcentajeDescuento);
                                    double subtotal = Double.valueOf(carteraClienteDetalleFacturaObject.getString("VALOR").replace(',', '.'));
                                    facturaDetalle.setSubtotal(subtotal);
                                    //Añadire objeto a la lista
                                    listaFacturaDetalles.add(facturaDetalle);
                                }
                            } else {
                                buscadorClientesCarterasDetalleABCH.setVisibility(View.GONE);
                                mensajeDatosVacio.setVisibility(View.VISIBLE);
                                //Toast.makeText(getActivity(), "Error con los datos...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                        ///Asiganr datos a cada item del recycler view
                        recyclerViewClientesCarterasDetalleABCH.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
                        adaptadorClientesCarteraDetalleCheques = new AdaptadorClientesCarteraDetalleFactura(getActivity(),context, listaFacturaDetalles);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        recyclerViewClientesCarterasDetalleABCH.setAdapter(adaptadorClientesCarteraDetalleCheques);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getActivity().finish();
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("numero", numeroFactura);
                params.put("serie", serieFactura);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void mostrarCargando() {
        progressDialog.show();
        cancelarCargando();
        progressDialog.setContentView(R.layout.progress_dialog_cargando);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
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
                getActivity().finish();
            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptadorClientesCarteraDetalleCheques.filtradoAbonosCheques(newText);
        return false;
    }
}