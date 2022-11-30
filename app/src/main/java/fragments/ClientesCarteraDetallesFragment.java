package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.megakons.sa.ClienteCarteraDetalleAbonosCHActivity;
import com.mobile.megakons.sa.ClienteCarteraDetalleActivity;
import com.mobile.megakons.sa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adaptadores.AdaptadorClientesCarteraDetalle;
import adaptadores.AdaptadorClientesCarteraDetalleAbonos;
import conexiones.AJSONServicio;
import entidades.ClienteCarteraDetalle;
import entidades.ClienteCarteraDetalleAbonos;
import metodosGenerales.General;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientesCarteraDetallesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ClientesCarteraDetallesFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
/*    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientesCarteraDetallesFragment.
     */
    // TODO: Rename and change types and number of parameters
    //-------------------------------------------------------------------------------------
    //Servicio WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_LISTA_CLIENTES_CARTERA_DETALLE = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_LISTA_CLIENTES_CARTERAS_DETALLE();
    private static String URL_ENTIDAD_PRINCIPAL = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_ENTIDAD_PRINCIPAL();
    //Fecha Servidor
    private static Date fechaServer;
    //Detalles Lista
    RecyclerView recyclerViewClientesCarterasDetalle;
    private List<ClienteCarteraDetalle> listaClientesCarterasDetalles;
    List<String> listaClienteDatosMaestro;
    //Buscador Detalle Cartera de Clientes
    private androidx.appcompat.widget.SearchView buscadorDetalleCarteraClientes;
    //Adaptador Datos
    AdaptadorClientesCarteraDetalle adaptadorClientesCarteraDetalle;
    //Context
    private static Context context;
    //Metodos Generales - CLase General
    General metodoGeneral;
    //Views
    View view;
    //Codigo cliente que se desea ver la cartera y el detalle
    Bundle datoCliente;
    String codigoCliente;
    //Saldo x cobrar y Totales
    private static double diferenciaCHSA = 0;
    private static double saldoTotal = 0;
    private static double chPostTotal = 0;
    //TextViews Maestro
    TextView tvNombreClienteCarteraDetalle, tvCodigoClienteCarteraDetalle, tvChPostTotalClienteCarteraDetalle, tvSaldoTotalClienteCarteraDetalle;
    TextView tvSaldoXcobrar;
    //Obtener Datos del activity
    ClienteCarteraDetalleActivity clienteCarteraDetalle;
    private DrawerLayout drawer_layout_detalles_cartera_clientes;
    //Loading
    ProgressDialog progressDialog;

    //Variables de presentacion sin datos
    ConstraintLayout contenedorDetalleCC;
    RelativeLayout mensajeDatosVacio;
    TextView tv1;
    ImageView img1;
    Fragment fragment_layout_clientes_cartera;


    public static ClientesCarteraDetallesFragment newInstance(String param1, String param2) {
        ClientesCarteraDetallesFragment fragment = new ClientesCarteraDetallesFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public ClientesCarteraDetallesFragment() {
        // Required empty public constructor
    }
    @Override

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clientes_cartera_detalles, container, false);
        inicializarVariables();
        initListenerListaClientesCarterasDetalles();
        //DETERMINANTE OBTENCION codigo cliente
        codigoCliente = clienteCarteraDetalle.getCliente();
        //Obtiene los datos del json
        extraerDetallesCarterasClientesJSON(codigoCliente);
        //Asignar saldo por cobrar
        totalesSaldosCH();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_clientes_cartera_detalle_abonos, container, false);
        return view;
    }

    private void totalesSaldosCH(){
        diferenciaCHSA = 0;
        saldoTotal = 0;
        chPostTotal = 0;
    }

    private void inicializarVariables() {
        //Obtener Datos del activity
        clienteCarteraDetalle = (ClienteCarteraDetalleActivity) getActivity();
        //obtener fecha
        extraerFechaServidorJSON();
        //Clase General
        metodoGeneral = new General();
        recyclerViewClientesCarterasDetalle = view.findViewById(R.id.listaCarteraClientesDetalles);
        listaClientesCarterasDetalles = new ArrayList<>();
        listaClienteDatosMaestro = new ArrayList<>();
        //Inicializar TextViews
        tvNombreClienteCarteraDetalle = getActivity().findViewById(R.id.tvNombreClienteCarteraDetalle);
        tvCodigoClienteCarteraDetalle = getActivity().findViewById(R.id.tvCodigoClienteCarteraDetalle);
        tvChPostTotalClienteCarteraDetalle = getActivity().findViewById(R.id.tvChPostTotalClienteCarteraDetalle);
        tvSaldoTotalClienteCarteraDetalle = getActivity().findViewById(R.id.tvSaldoTotalClienteCarteraDetalle);
        tvSaldoXcobrar = getActivity().findViewById(R.id.tvSaldoCobrarClienteCarteraDetalle);
        rellenoCabecera();
        //Inicializar mostrar sin datos
        contenedorDetalleCC = getActivity().findViewById(R.id.contenedorDetalleCC);
        contenedorDetalleCC.setVisibility(View.GONE);
        mensajeDatosVacio = getActivity().findViewById(R.id.mensajeDatosVacio);
        mensajeDatosVacio.setVisibility(View.GONE);
        tv1 = getActivity().findViewById(R.id.tv1);
        tv1.setText("Cliente " + tvNombreClienteCarteraDetalle.getText() + " no contiene datos en cartera");
        //DETERMINANTES
        buscadorDetalleCarteraClientes = getActivity().findViewById(R.id.buscadorClientesCarterasDetalle);
        buscadorDetalleCarteraClientes.setQueryHint("Buscar Factura o Nota de Débito");
        //buscadorDetalleCarteraClientes.setVisibility(View.GONE);
        progressDialog =  new ProgressDialog(context);
    }

    public void rellenoCabecera(){
        tvNombreClienteCarteraDetalle.setText(clienteCarteraDetalle.getNombreCliente());
        tvCodigoClienteCarteraDetalle.setText(clienteCarteraDetalle.getCodigoCliente());
        //tvChPostTotalClienteCarteraDetalle.setText(clienteCarteraDetalle.getCodigoCliente());
        //tvSaldoTotalClienteCarteraDetalle.setText(datoCliente.getString("diasCreditoCateraCliente"));

    }

    public void rellenoDatosClienteCabecera() {
        listaClienteDatosMaestro.add(0,tvCodigoClienteCarteraDetalle.getText().toString());
        listaClienteDatosMaestro.add(1,tvNombreClienteCarteraDetalle.getText().toString());
        listaClienteDatosMaestro.add(2,tvChPostTotalClienteCarteraDetalle.getText().toString());
        listaClienteDatosMaestro.add(3,tvSaldoTotalClienteCarteraDetalle.getText().toString());
        listaClienteDatosMaestro.add(4,tvSaldoXcobrar.getText().toString());
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
                                Toast.makeText(getActivity(), "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ////*mostrarCargandoSinDatos();
                        getActivity().finish();
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void extraerDetallesCarterasClientesJSON(String codigoCliente) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LISTA_CLIENTES_CARTERA_DETALLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("FCND");
                            //mostrarCargando();
                            contenedorDetalleCC.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                            if (jsonArray.length() != 0) {
                                //progressProductos.setVisibility(View.GONE);
                                //*animacionAparecerBuscador();
                                //progressDialog.dismiss();
                                //recyclerView.setVisibility(View.VISIBLE);
                                //drawer_layout_notas.setVisibility(View.VISIBLE);

                                //Scroll y cargando por lo tanto esconfo el toolbar y el buscador
                                //*toolbar.setVisibility(View.VISIBLE);
                                buscadorDetalleCarteraClientes.setVisibility(View.VISIBLE);

                                //Ciclo que obtiene los datos del JSON
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject carteraClienteDetalleObject = jsonArray.getJSONObject(i);
                                    ClienteCarteraDetalle ccDetalleCartera = new ClienteCarteraDetalle();
                                    //ccDetalleCartera.setFecha_formato(carteraClienteDetalleObject.getString("FECHA_FORMATO").toString());
                                    ccDetalleCartera.setTipo(carteraClienteDetalleObject.getString("TIPO").toString());
                                    ccDetalleCartera.setSerie(carteraClienteDetalleObject.getString("SERIE").toString());
                                    ccDetalleCartera.setDocumento(carteraClienteDetalleObject.getString("DOCUMENTO").toString());
                                    ccDetalleCartera.setPlcb_id(carteraClienteDetalleObject.getString("PLCB_ID").toString());
                                    ccDetalleCartera.setEstado(carteraClienteDetalleObject.getString("EGEN_ESTADO").toString());
                                    ccDetalleCartera.setId_cliente(carteraClienteDetalleObject.getString("GRPS_IDCLIENTE").toString());
                                    ccDetalleCartera.setNombre(carteraClienteDetalleObject.getString("NOMBRE").toString());
                                    //Lectura e ingreso de Fechas
                                    Date FechaCreacion = metodoGeneral.conversionStringDate(carteraClienteDetalleObject.getString("FECHA").toString());
                                    ccDetalleCartera.setFecha(FechaCreacion);
                                    Date FechaExpiracion = metodoGeneral.conversionStringDate(carteraClienteDetalleObject.getString("FECHVCTO").toString());
                                    ccDetalleCartera.setFecha_vencimento(FechaExpiracion);
                                    //Calcular diferencia de fechas
                                    int diasFaltantes = (int) ((FechaExpiracion.getTime()-fechaServer.getTime())/86400000);
                                    ccDetalleCartera.setDíasFaltantes(diasFaltantes);
                                    //Lectura e ingreso de Datos Numericos
                                    double debitos = Double.valueOf(carteraClienteDetalleObject.getString("TOTAL").replace(',', '.'));
                                    double saldo = Double.valueOf(carteraClienteDetalleObject.getString("SALDO").replace(',', '.'));
                                    ccDetalleCartera.setTotal(debitos);
                                    ccDetalleCartera.setSaldo(saldo);
                                    double chpf = Double.valueOf(carteraClienteDetalleObject.getString("CHPF").replace(',', '.'));
                                    ccDetalleCartera.setChpf(chpf);
                                    //Calcular Abonos
                                    double creditos = debitos - saldo;
                                    ccDetalleCartera.setAbono(Double.valueOf(metodoGeneral.conversionDosDecimalesDoubleString(creditos).replace(',', '.')));
                                    //Calculo del saldo x cobrar
                                    diferenciaCHSA = diferenciaCHSA + (saldo - chpf);
                                    //calculo saldo y chPost totales
                                    saldoTotal = saldoTotal + saldo;
                                    chPostTotal = chPostTotal + chpf;
                                    //Añadire objeto a la lista
                                    listaClientesCarterasDetalles.add(ccDetalleCartera);
                                }
                                tvChPostTotalClienteCarteraDetalle.setText("$ " + metodoGeneral.conversionDosDecimalesDoubleString(chPostTotal).replace('.', ',').toString());
                                tvSaldoXcobrar.setText("$ " + metodoGeneral.conversionDosDecimalesDoubleString(diferenciaCHSA).replace('.', ',').toString());
                                tvSaldoTotalClienteCarteraDetalle.setText("$ " + metodoGeneral.conversionDosDecimalesDoubleString(saldoTotal).replace('.', ',').toString());
                            } else {
                                //*getActivity().finish();
                                buscadorDetalleCarteraClientes.setVisibility(View.GONE);
                                mensajeDatosVacio.setVisibility(View.VISIBLE);
                                //Toast.makeText(getActivity(), "Error con los datos...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Conectándose a red MGK...", Toast.LENGTH_SHORT).show();
                        }
                        //Obtener datos entidad cliente
                        rellenoDatosClienteCabecera();
                        ///Asiganr datos a cada item del recycler view
                        recyclerViewClientesCarterasDetalle.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
                        adaptadorClientesCarteraDetalle = new AdaptadorClientesCarteraDetalle(getActivity(),context, listaClientesCarterasDetalles, listaClienteDatosMaestro);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        recyclerViewClientesCarterasDetalle.setAdapter(adaptadorClientesCarteraDetalle);

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
                params.put("cliente", codigoCliente);
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

    //Inicializar Listener de la lista
    private void initListenerListaClientesCarterasDetalles() {
        buscadorDetalleCarteraClientes.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptadorClientesCarteraDetalle.filtradoClientes(newText);
        return false;
    }
}