package com.mobile.megakons.sa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragments.ClientesCarteraDetalleAbonosFragment;
import fragments.ClientesCarteraDetalleFacturaFragment;
import fragments.ClientesCarteraDetallesChequesFragment;

public class ClienteCarteraDetalleAbonosCHActivity extends AppCompatActivity {
    //Codigo cliente que se desea ver la cartera y el detalle
    Bundle datoCliente;
    public static String plcbid, numeroFactura, serieFactura;
    //TextViews Maestro
    TextView tvNombreClienteCarteraDetalleACH, tvCodigoClienteCarteraDetalleACH, tvMontoClienteCarteraDetalleACH, tvDiasCreditoClienteCarteraDetalleACH,
            tvTipoDocumentoCCDetalleACH, tvEstadoDocumentoCCDetalleACH, tvFechaEmisionCCDetalleACH,
            tvFechaVencimientoCCDetalleACH, tvDiasFaltantesCCDetalleACH, tvDebitosCCDetalleACH, tvCreditosCCDetalleACH,
            tvSaldosCCDetalleACH, tvChequesPostfechadosCCDetalleACH, tvSaldoCobrarClienteCarteraDetalleACH;
    ImageView imgTipoDocumentoCCDetalleACH, imgRelojAlertaCCDetalleACH, imgCalendarioCCDetalleACH;
    //Acction Toolbar
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_cartera_detalle_abonos_ch);
        //InicializarVariables
        inicilizarVariables();

        //BottomNavigation
        BottomNavigationView btnNav = findViewById(R.id.bottonNavigationViewAbonosCH);
        btnNav.setOnNavigationItemSelectedListener(navListener);
        //Fragment Abonos Como principal
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_AbonosCheques, new ClientesCarteraDetalleAbonosFragment()).commit();

    }

    private void inicilizarVariables() {

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        //TextViews
        tvNombreClienteCarteraDetalleACH = findViewById(R.id.tvNombreClienteCarteraDetalleACH);
        tvCodigoClienteCarteraDetalleACH = findViewById(R.id.tvCodigoClienteCarteraDetalleACH);
        tvMontoClienteCarteraDetalleACH = findViewById(R.id.tvMontoClienteCarteraDetalleACH);
        tvDiasCreditoClienteCarteraDetalleACH = findViewById(R.id.tvDiasCreditoClienteCarteraDetalleACH);
        imgTipoDocumentoCCDetalleACH = findViewById(R.id.imgTipoDocumentoCCDetalleACH);
        tvTipoDocumentoCCDetalleACH = findViewById(R.id.tvTipoDocumentoCCDetalleACH);
        tvEstadoDocumentoCCDetalleACH = findViewById(R.id.tvEstadoDocumentoCCDetalleACH);
        tvFechaEmisionCCDetalleACH = findViewById(R.id.tvFechaEmisionCCDetalleACH);
        imgCalendarioCCDetalleACH = findViewById(R.id.imgCalendarioCCDetalleACH);
        tvFechaVencimientoCCDetalleACH = findViewById(R.id.tvFechaVencimientoCCDetalleACH);
        imgRelojAlertaCCDetalleACH = findViewById(R.id.imgRelojAlertaCCDetalleACH);
        tvDiasFaltantesCCDetalleACH = findViewById(R.id.tvDiasFaltantesCCDetalleACH);
        tvDebitosCCDetalleACH = findViewById(R.id.tvDebitosCCDetalleACH);
        tvCreditosCCDetalleACH = findViewById(R.id.tvCreditosCCDetalleACH);
        tvSaldosCCDetalleACH = findViewById(R.id.tvSaldosCCDetalleACH);
        tvSaldoCobrarClienteCarteraDetalleACH = findViewById(R.id.tvSaldoCobrarClienteCarteraDetalleACH);
        tvChequesPostfechadosCCDetalleACH = findViewById(R.id.tvChequesPostfechadosCCDetalleACH);

        //DETERMINANTE OBTENCION LINEA DEL MENU DE PRODUCTOS
        datoCliente = this.getIntent().getExtras();
        //Obtiene el plcbid
        plcbid = datoCliente.getString("plcbid");
        numeroFactura = datoCliente.getString("numeroDocumento");
        serieFactura = datoCliente.getString("serieDocumento");
        //Cabecera
        rellenoCabecera();

    }


    public void rellenoCabecera(){
        ///Relleno toolbar
        /*-------------------------- Tool Bar --------------------------- */
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(datoCliente.getString("tipoDocumento"));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
        //Relleno datos cliente
        tvNombreClienteCarteraDetalleACH.setText(datoCliente.getString("nombreCliente"));
        tvCodigoClienteCarteraDetalleACH.setText(datoCliente.getString("codigoCliente"));
        tvMontoClienteCarteraDetalleACH.setText(datoCliente.getString("saldoTotal"));
        tvDiasCreditoClienteCarteraDetalleACH.setText(datoCliente.getString("chequesPost"));
        tvTipoDocumentoCCDetalleACH.setText(datoCliente.getString("tipoDocumento"));
        tvEstadoDocumentoCCDetalleACH.setText(datoCliente.getString("estadoDocumento"));
        tvFechaEmisionCCDetalleACH.setText(datoCliente.getString("fechaEmision"));
        tvFechaVencimientoCCDetalleACH.setText(datoCliente.getString("fechaVencimiento"));
        tvDiasFaltantesCCDetalleACH.setText(datoCliente.getString("diasFaltantes"));
        tvDebitosCCDetalleACH.setText(datoCliente.getString("debitos"));
        tvCreditosCCDetalleACH.setText(datoCliente.getString("creditos"));
        tvSaldosCCDetalleACH.setText(datoCliente.getString("saldos"));
        tvSaldoCobrarClienteCarteraDetalleACH.setText(datoCliente.getString("saldoXcobrar"));
        tvChequesPostfechadosCCDetalleACH.setText(datoCliente.getString("chequesPostfechados"));
        //Imagenes
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("imgDocumento"),0,getIntent().getByteArrayExtra("imgDocumento").length);
        imgTipoDocumentoCCDetalleACH.setImageBitmap(bitmap1);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("imgRelojAlerta"),0,getIntent().getByteArrayExtra("imgRelojAlerta").length);
        imgRelojAlertaCCDetalleACH.setImageBitmap(bitmap2);
        Bitmap bitmap3 = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("imgCalendario"),0,getIntent().getByteArrayExtra("imgCalendario").length);
        imgCalendarioCCDetalleACH.setImageBitmap(bitmap3);
    }

    public String gePlbid() {
        return plcbid;
    }

    public String getSerieFactura() {
        return serieFactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }


    //Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;
            switch(item.getItemId()){
                case R.id.itemNavigationViewAbonos:
                    selectedFragment = new ClientesCarteraDetalleAbonosFragment();
                    break;
                case R.id.itemNavigationViewCheques:
                    selectedFragment = new ClientesCarteraDetallesChequesFragment();
                    break;
                case R.id.itemNavigationViewDetalleFactura:
                    selectedFragment = new ClientesCarteraDetalleFacturaFragment();
                    break;
            }
            //Begin Transaction para fragments
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout_AbonosCheques, selectedFragment).commit();
            return true;

        }
    };

}