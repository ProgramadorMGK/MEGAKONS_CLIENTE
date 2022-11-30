package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import adaptadores.AdaptadorClientesCarteraDetalleAnticipos;
import conexiones.AJSONServicio;
import entidades.ClienteCarteraDetalleAnticipo;
import metodosGenerales.General;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientesCarteraDetallesAnticiposFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesCarteraDetallesAnticiposFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientesCarteraDetallesAnticiposFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientesCarteraDetallesAnticiposFragment.
     */
    // TODO: Rename and change types and number of parameters
    //Servicio WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_LISTA_CLIENTES_CARTERA_DETALLE_ANTICIPOS = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_LISTA_CLIENTES_CARTERAS_ANTICIPOS();
    //Detalles Lista
    RecyclerView recyclerViewClientesCarterasDetalleAnticipos;
    private List<ClienteCarteraDetalleAnticipo> listaClientesCarterasDetallesAnticipos;
    //Buscador Detalle Cartera de Clientes
    private androidx.appcompat.widget.SearchView buscadorDetalleCarteraClientes;
    //Adaptador Datos
    AdaptadorClientesCarteraDetalleAnticipos adaptadorClientesCarteraDetalleAnticipos;
    //Context
    private static Context context;
    //Metodos Generales - CLase General
    General metodoGeneral;
    //Views
    View view;
    //Codigo cliente que se desea ver la cartera y el detalle
    Bundle datoCliente;
    String codigoCliente;
    //Obtener Datos del activity
    ClienteCarteraDetalleActivity clienteCarteraDetalle;
    //Variables de presentacion sin datos
    ProgressDialog progressDialog;
    ConstraintLayout contenedorDetalleCC;
    RelativeLayout mensajeDatosVacio;
    TextView tv1;
    ImageView img1;
    Fragment fragment_layout_clientes_cartera;
    TextView tvNombreClienteCarteraDetalle;

    public static ClientesCarteraDetallesAnticiposFragment newInstance(String param1, String param2) {
        ClientesCarteraDetallesAnticiposFragment fragment = new ClientesCarteraDetallesAnticiposFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clientes_cartera_detalles_anticipos, container, false);
        inicializarVariables();
        initListenerListaClientesCarterasDetallesAnticipos();
        //DETERMINANTE OBTENCION codigo cliente
        codigoCliente = clienteCarteraDetalle.getCliente();
        //Obtiene los datos del json
        extraerAnticiposDetallesCarterasClientesJSON(codigoCliente);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_clientes_cartera_detalle_abonos, container, false);
        return view;

    }

    private void inicializarVariables() {
        //Obtener Datos del activity
        clienteCarteraDetalle = (ClienteCarteraDetalleActivity) getActivity();
        //Clase General
        metodoGeneral = new General();
        recyclerViewClientesCarterasDetalleAnticipos = view.findViewById(R.id.listaCarteraClientesDetallesAnticipos);
        listaClientesCarterasDetallesAnticipos = new ArrayList<>();

        //Inicializar TextViews
        tvNombreClienteCarteraDetalle = getActivity().findViewById(R.id.tvNombreClienteCarteraDetalle);
        //Inicializar mostrar sin datos
        contenedorDetalleCC = getActivity().findViewById(R.id.contenedorDetalleCC);
        contenedorDetalleCC.setVisibility(View.GONE);
        mensajeDatosVacio = getActivity().findViewById(R.id.mensajeDatosVacio);
        mensajeDatosVacio.setVisibility(View.GONE);
        tv1 = getActivity().findViewById(R.id.tv1);
        tv1.setText("Cliente " + tvNombreClienteCarteraDetalle.getText() + " no cuenta con anticipos");
        //DETERMINANTES
        buscadorDetalleCarteraClientes = getActivity().findViewById(R.id.buscadorClientesCarterasDetalle);
        buscadorDetalleCarteraClientes.setQueryHint("Buscar Anticipo por ID, Fecha o Descripción");
        //buscadorDetalleCarteraClientes.setVisibility(View.GONE);
        progressDialog =  new ProgressDialog(context);
    }

    private void extraerAnticiposDetallesCarterasClientesJSON(String codigoCliente) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LISTA_CLIENTES_CARTERA_DETALLE_ANTICIPOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("ANTICIPOS");
                            contenedorDetalleCC.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                            //mostrarCargando();
                            if (jsonArray.length() != 0) {
                                //progressProductos.setVisibility(View.GONE);
                                //*animacionAparecerBuscador();
                                //*progressDialog.dismiss();
                                //recyclerView.setVisibility(View.VISIBLE);
                                //drawer_layout_notas.setVisibility(View.VISIBLE);

                                //Scroll y cargando por lo tanto esconfo el toolbar y el buscador
                                //*toolbar.setVisibility(View.VISIBLE);
                                buscadorDetalleCarteraClientes.setVisibility(View.VISIBLE);
                                //Ciclo que obtiene los datos del JSON
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject carteraClienteDetalleAnticiposObject = jsonArray.getJSONObject(i);
                                    ClienteCarteraDetalleAnticipo ccDetalleCarteraAnticipo = new ClienteCarteraDetalleAnticipo();
                                    //ccDetalleCartera.setFecha_formato(carteraClienteDetalleObject.getString("FECHA_FORMATO").toString());
                                    ccDetalleCarteraAnticipo.setId(carteraClienteDetalleAnticiposObject.getString("ID").toString());
                                    ccDetalleCarteraAnticipo.setFecha_formato(carteraClienteDetalleAnticiposObject.getString("FECHA_CON").toString());
                                    Date Fecha = metodoGeneral.conversionStringDate(carteraClienteDetalleAnticiposObject.getString("FECHA").toString());
                                    ccDetalleCarteraAnticipo.setFecha(Fecha);
                                    ccDetalleCarteraAnticipo.setDescripcion(carteraClienteDetalleAnticiposObject.getString("DESCRIPCION").toString());
                                    //Lectura e ingreso de Datos Numericos
                                    double anticipo = Double.valueOf(carteraClienteDetalleAnticiposObject.getString("ANTICIPO").replace(',', '.'));
                                    double cruce = Double.valueOf(carteraClienteDetalleAnticiposObject.getString("CRUCE").replace(',', '.'));
                                    ccDetalleCarteraAnticipo.setAnticipo(anticipo);
                                    ccDetalleCarteraAnticipo.setCruce(cruce);
                                    double restante = anticipo - cruce;
                                    ccDetalleCarteraAnticipo.setRestante(Double.valueOf(metodoGeneral.conversionDosDecimalesDoubleString(restante).replace(',', '.')));
                                    listaClientesCarterasDetallesAnticipos.add(ccDetalleCarteraAnticipo);
                                }
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
                        ///Asiganr datos a cada item del recycler view
                        recyclerViewClientesCarterasDetalleAnticipos.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
                        adaptadorClientesCarteraDetalleAnticipos = new AdaptadorClientesCarteraDetalleAnticipos(getActivity(),context, listaClientesCarterasDetallesAnticipos);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        recyclerViewClientesCarterasDetalleAnticipos.setAdapter(adaptadorClientesCarteraDetalleAnticipos);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //*mostrarCargandoSinDatos();
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

    //Inicializar Listener de la lista
    private void initListenerListaClientesCarterasDetallesAnticipos() {
        buscadorDetalleCarteraClientes.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptadorClientesCarteraDetalleAnticipos.filtradoAnticiposCliente(newText);
        return false;
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


}