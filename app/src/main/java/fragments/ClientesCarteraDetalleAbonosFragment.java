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
import com.mobile.megakons.sa.ClienteCarteraDetalleAbonosCHActivity;
import com.mobile.megakons.sa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adaptadores.AdaptadorClientesCarteraDetalleAbonos;
import conexiones.AJSONServicio;
import entidades.ClienteCarteraDetalleAbonos;
import metodosGenerales.General;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientesCarteraDetalleAbonosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesCarteraDetalleAbonosFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Servicio WEB
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_LISTA_CLIENTES_CARTERA_DETALLE_ABCH = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_LISTA_CLIENTES_CARTERAS_DETALLE_ABCH();
    //Abonos Cheques Lista
    RecyclerView recyclerViewClientesCarterasDetalleABCH;
    private List<ClienteCarteraDetalleAbonos> listaClientesCarterasDetallesABCH;
    //Buscador Detalle Cartera de Clientes
    private androidx.appcompat.widget.SearchView buscadorClientesCarterasDetalleABCH;
    //Adaptador Datos
    AdaptadorClientesCarteraDetalleAbonos adaptadorClientesCarteraDetalleAbonos;
    //Context
    private static Context context;
    //Metodos Generales - CLase General
    General metodoGeneral;
    //Views
    View view;
    //Obtener plcbId
    private static String plcbid;
    //Loading
    ProgressDialog progressDialog;

    //Variables de presentacion sin datos
    ConstraintLayout contenedorDetalleCC;
    RelativeLayout mensajeDatosVacio;
    TextView tv1;
    ImageView img1;
    Fragment fragment_clientes_cartera_detalle_abonos;
    TextView tvNombreClienteCarteraDetalleACH;

    public ClientesCarteraDetalleAbonosFragment() {
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
     * @return A new instance of fragment ClientesCarteraDetalleAbonosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientesCarteraDetalleAbonosFragment newInstance(String param1, String param2) {
        ClientesCarteraDetalleAbonosFragment fragment = new ClientesCarteraDetalleAbonosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        view = inflater.inflate(R.layout.fragment_clientes_cartera_detalle_abonos, container, false);
        inicializarVariables();
        initListenerListaClientesCarterasDetallesAbonosCheques();
        //Obtener Datos del activity
        ClienteCarteraDetalleAbonosCHActivity clienteCarteraDetalleAbonosCHActivity = (ClienteCarteraDetalleAbonosCHActivity) getActivity();
        plcbid = clienteCarteraDetalleAbonosCHActivity.gePlbid();
        //Obtiene los datos del json
        extraerAbonosChequesDetallesCarterasClientesJSON(plcbid);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_clientes_cartera_detalle_abonos, container, false);
        return view;
    }

    private void inicializarVariables() {
        //Clase General
        metodoGeneral = new General();
        recyclerViewClientesCarterasDetalleABCH = view.findViewById(R.id.listaClientesCarterasDetallesAbonosRV);
        listaClientesCarterasDetallesABCH = new ArrayList<>();
        //DETERMINANTES
        buscadorClientesCarterasDetalleABCH = getActivity().findViewById(R.id.buscadorClientesCarterasDetalleABCH);
        //Inicializar mostrar sin datos
        tvNombreClienteCarteraDetalleACH = getActivity().findViewById(R.id.tvNombreClienteCarteraDetalleACH);
        contenedorDetalleCC = getActivity().findViewById(R.id.contenedorDetalleCC);
        contenedorDetalleCC.setVisibility(View.GONE);
        mensajeDatosVacio = getActivity().findViewById(R.id.mensajeDatosVacio);
        mensajeDatosVacio.setVisibility(View.GONE);
        tv1 = getActivity().findViewById(R.id.tv1);
        tv1.setText("Cliente " + tvNombreClienteCarteraDetalleACH.getText() + " tiene 0 abonos");
        //DETERMINANTES
        //buscadorClientesCarterasDetalleABCH.setVisibility(View.GONE);
        progressDialog =  new ProgressDialog(context);
    }

    //Inicializar Listener de la lista
    private void initListenerListaClientesCarterasDetallesAbonosCheques() {
        buscadorClientesCarterasDetalleABCH.setOnQueryTextListener(this);
    }

    private void extraerAbonosChequesDetallesCarterasClientesJSON(String plcbid) {
        mostrarCargando();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LISTA_CLIENTES_CARTERA_DETALLE_ABCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("ABONOSCH");
                            contenedorDetalleCC.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                            if (jsonArray.length() != 0) {
                                ////buscadorClientesCarterasDetalleABCH.setVisibility(View.VISIBLE);
                                //Ciclo que obtiene los datos del JSON
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject carteraClienteDetalleABCHObject = jsonArray.getJSONObject(i);

                                        ClienteCarteraDetalleAbonos ccDetalleCarteraABCH = new ClienteCarteraDetalleAbonos();
                                        ccDetalleCarteraABCH.setFecha(carteraClienteDetalleABCHObject.getString("FECHA").toString());
                                        ccDetalleCarteraABCH.setDescripcion(carteraClienteDetalleABCHObject.getString("DESCRIPC").toString());
                                        ccDetalleCarteraABCH.setReferencia(carteraClienteDetalleABCHObject.getString("REFER").toString());
                                        //Lectura e ingreso de Datos Numericos
                                        double saldo = Double.valueOf(carteraClienteDetalleABCHObject.getString("VALOR").replace(',', '.'));
                                        ccDetalleCarteraABCH.setValor(saldo);
                                        //Añadire objeto a la lista
                                        listaClientesCarterasDetallesABCH.add(ccDetalleCarteraABCH);

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
                        adaptadorClientesCarteraDetalleAbonos = new AdaptadorClientesCarteraDetalleAbonos(getActivity(),context, listaClientesCarterasDetallesABCH);
                        //adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
                        recyclerViewClientesCarterasDetalleABCH.setAdapter(adaptadorClientesCarteraDetalleAbonos);

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
                params.put("plcbid", plcbid);
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
        adaptadorClientesCarteraDetalleAbonos.filtradoAbonosCheques(newText);
        return false;
    }

}