package adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.megakons.sa.ClienteCarteraDetalleAbonosCHActivity;
import com.mobile.megakons.sa.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entidades.ClienteCarteraDetalle;
import metodosGenerales.General;

public class AdaptadorClientesCarteraDetalle extends RecyclerView.Adapter<AdaptadorClientesCarteraDetalle.ViewHolder> {
    //Activities
    Intent intentDetalleAbonosCH;
    Intent intentDetalleFactura;
    //Lista Cartera Clientes
    LayoutInflater inflater;
    List<ClienteCarteraDetalle> clientesCarterasDetalles;
    List<ClienteCarteraDetalle> clientesCarterasDetallesBuscador;
    List<String> listaClienteDatosMaestro;
    private Context context;
    // Clase General
    General metodoGeneral = new General();

    public AdaptadorClientesCarteraDetalle(Activity activity, Context context, List<ClienteCarteraDetalle> clientesCarterasDetalles, List<String> listaClienteDatosMaestro){
        this.inflater = LayoutInflater.from(context);
        this.clientesCarterasDetalles = clientesCarterasDetalles;
        this.listaClienteDatosMaestro = listaClienteDatosMaestro;
        this.clientesCarterasDetallesBuscador = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        clientesCarterasDetallesBuscador.addAll(clientesCarterasDetalles);
    }

    //Animacion
    int lastPosition = -1;
    Activity activity;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_clientes_carteras_detalle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorClientesCarteraDetalle.ViewHolder holder, int position) {
        //Llenar los datos segun los lea del visual code
        holder.tipoDocumento.setText(clientesCarterasDetalles.get(position).getTipo() + "-" + clientesCarterasDetalles.get(position).getSerie()+"-"+clientesCarterasDetalles.get(position).getDocumento());
        if (clientesCarterasDetalles.get(position).getSerie().contentEquals("null")){
            holder.tipoDocumento.setText(clientesCarterasDetalles.get(position).getTipo() + "-" +clientesCarterasDetalles.get(position).getDocumento());
        }
        //        holder.estadoDocumento.setText(Html.fromHtml("<b>Cliente: </b>" + clientesCarteras.get(position).getCliente()));
        String estado = clientesCarterasDetalles.get(position).getEstado();
        if (estado.contentEquals("VG")){holder.estadoDocumento.setText("VIGENTE");}

        holder.diasFaltantes.setText(Html.fromHtml("<b><font color='#007514'> Días Faltantes: </font></b>" + String.valueOf(clientesCarterasDetalles.get(position).getDíasFaltantes())));
        holder.debitos.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetalles.get(position).getTotal()).replace('.',','));
        holder.creditos.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetalles.get(position).getSaldo()).replace('.',','));
        holder.saldos.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetalles.get(position).getAbono()).replace('.',','));
        holder.chequesPostfechados.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetalles.get(position).getChpf()).replace('.',','));

        ///// FECHA
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        holder.fechaEmision.setText(Html.fromHtml("<b>Fecha Emi: </b>" + simpleDateFormat.format(clientesCarterasDetalles.get(position).getFecha()).toUpperCase()));
        holder.fechaVencimiento.setText(Html.fromHtml("<b>Fecha Ven: </b>" + simpleDateFormat.format(clientesCarterasDetalles.get(position).getFecha_vencimento()).toUpperCase()));

        /////// COLOREAR TEXTVIEWS
        if(metodoGeneral.redondearNumero(clientesCarterasDetalles.get(position).getChpf()).contentEquals("0.00")){
            holder.cdd4.setBackgroundColor(Color.parseColor("#E3E300"));
            holder.chequesPostfechados.setTextColor(Color.parseColor("#D00000"));
        }else{
            holder.cdd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.chequesPostfechados.setTextColor(Color.parseColor("#000000"));
        }
        if(metodoGeneral.redondearNumero(clientesCarterasDetalles.get(position).getSaldo()).contentEquals("0.00")){
            holder.cdd3.setBackgroundColor(Color.parseColor("#E3E300"));
            holder.creditos.setTextColor(Color.parseColor("#D00000"));
        }else{
            holder.cdd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.creditos.setTextColor(Color.parseColor("#000000"));
        }
        if(metodoGeneral.redondearNumero(clientesCarterasDetalles.get(position).getAbono()).contentEquals("0.00")){
            holder.cdd2.setBackgroundColor(Color.parseColor("#E3E300"));
            holder.saldos.setTextColor(Color.parseColor("#D00000"));
        }else{
            holder.cdd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.saldos.setTextColor(Color.parseColor("#000000"));
        }
        //////////

        String tipoDocumento = clientesCarterasDetalles.get(position).getTipo();
        if (tipoDocumento.contentEquals("FC")) {
            Picasso.get().load(R.drawable.factura).into(holder.imgDocumento);
            holder.llDetalleCarteraCliente_btnsDetalles.setVisibility(View.VISIBLE);
        }else{
            Picasso.get().load(R.drawable.nota_credito).into(holder.imgDocumento);
            holder.llDetalleCarteraCliente_btnsDetalles.setVisibility(View.GONE);
        }

        int diasFaltantes= clientesCarterasDetalles.get(position).getDíasFaltantes();

        if (diasFaltantes < 0) {
            holder.diasFaltantes.setText(Html.fromHtml("<b> <font color='#910000'> Días Sobrepasados: </font></b>" + String.valueOf(clientesCarterasDetalles.get(position).getDíasFaltantes())));
            Picasso.get().load(R.drawable.reloj_rojo_dinero_derecha).into(holder.imgRelojAlerta);
            Picasso.get().load(R.drawable.calendario_reloj_derecha_rojo).into(holder.imgCalendario);
        }else if (diasFaltantes <= 2) {
            holder.diasFaltantes.setText(Html.fromHtml("<b><font color='#b39b00'> Días Sobrantes: </font></b>" + String.valueOf(clientesCarterasDetalles.get(position).getDíasFaltantes())));
            Picasso.get().load(R.drawable.reloj_rojo_dinero_derecha).into(holder.imgRelojAlerta);
            Picasso.get().load(R.drawable.calendario_reloj_derecha_rojo).into(holder.imgCalendario);
        }else if (diasFaltantes <= 7) {
            Picasso.get().load(R.drawable.reloj_alerta).into(holder.imgRelojAlerta);
            Picasso.get().load(R.drawable.calendario_reloj_derecha).into(holder.imgCalendario);
        }else{
            Picasso.get().load(R.drawable.reloj_correcto).into(holder.imgRelojAlerta);
            Picasso.get().load(R.drawable.calendario_reloj_izquierda).into(holder.imgCalendario);
        }
        //PLCBID
        holder.plcbid.setText(clientesCarterasDetalles.get(position).getPlcb_id());
        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return clientesCarterasDetalles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tipoDocumento, estadoDocumento, fechaEmision, fechaVencimiento, diasFaltantes;
        TextView debitos, creditos, saldos, chequesPostfechados, plcbid;
        ImageView imgDocumento, imgRelojAlerta, imgCalendario;
        RelativeLayout cdd1, cdd2, cdd3, cdd4;
        Button btnDetalleCarteraCliente_DetalleFactura, btnDetalleCarteraCliente_DetallePago;
        LinearLayout llDetalleCarteraCliente_btnsDetalles;

        //Cabecera
        TextView tvNombreClienteCarteraDetalle;

        public ViewHolder(View itemView) {
            super(itemView);
            //Instanciar Datos con los de la lista - list_layout
            plcbid = itemView.findViewById(R.id.plcbid);
            tipoDocumento = itemView.findViewById(R.id.tvTipoDocumentoCCDetalle);
            estadoDocumento = itemView.findViewById(R.id.tvEstadoDocumentoCCDetalle);
            fechaEmision = itemView.findViewById(R.id.tvFechaEmisionCCDetalle);
            fechaVencimiento = itemView.findViewById(R.id.tvFechaVencimientoCCDetalle);
            diasFaltantes = itemView.findViewById(R.id.tvDiasFaltantesCCDetalle);
            debitos = itemView.findViewById(R.id.tvDebitosCCDetalle);
            creditos = itemView.findViewById(R.id.tvCreditosCCDetalle);
            saldos = itemView.findViewById(R.id.tvSaldosCCDetalle);
            chequesPostfechados = itemView.findViewById(R.id.tvChequesPostfechadosCCDetalle);
            imgDocumento = itemView.findViewById(R.id.imgTipoDocumentoCCDetalle);
            imgRelojAlerta = itemView.findViewById(R.id.imgRelojAlertaCCDetalle);
            imgCalendario = itemView.findViewById(R.id.imgCalendarioCCDetalle);
            //Instanciar Relatives
            cdd1 = itemView.findViewById(R.id.cdd1);
            cdd2 = itemView.findViewById(R.id.cdd2);
            cdd3 = itemView.findViewById(R.id.cdd3);
            cdd4 = itemView.findViewById(R.id.cdd4);
            //Instancia button
            llDetalleCarteraCliente_btnsDetalles = itemView.findViewById(R.id.llDetalleCarteraCliente_btnsDetalles);
            btnDetalleCarteraCliente_DetallePago = itemView.findViewById(R.id.btnDetalleCarteraCliente_DetallePago);
            btnDetalleCarteraCliente_DetalleFactura = itemView.findViewById(R.id.btnDetalleCarteraCliente_DetalleFactura);
            //// Al hacer Click

            btnDetalleCarteraCliente_DetallePago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, plcbid.getText().toString(), Toast.LENGTH_SHORT).show();
                    intentDetalleAbonosCH = new Intent(context, ClienteCarteraDetalleAbonosCHActivity.class);
                    intentDetalleAbonosCH.putExtra("codigoCliente", listaClienteDatosMaestro.get(0));
                    intentDetalleAbonosCH.putExtra("nombreCliente", listaClienteDatosMaestro.get(1));
                    intentDetalleAbonosCH.putExtra("chequesPost", listaClienteDatosMaestro.get(2));
                    intentDetalleAbonosCH.putExtra("saldoTotal", listaClienteDatosMaestro.get(3));
                    intentDetalleAbonosCH.putExtra("saldoXcobrar", listaClienteDatosMaestro.get(4));
                    intentDetalleAbonosCH.putExtra("plcbid", plcbid.getText().toString());
                    intentDetalleAbonosCH.putExtra("tipoDocumento", tipoDocumento.getText().toString());
                    intentDetalleAbonosCH.putExtra("serieDocumento", tipoDocumento.getText().toString().substring(3,9));
                    intentDetalleAbonosCH.putExtra("numeroDocumento", tipoDocumento.getText().toString().substring(10,tipoDocumento.getText().toString().length()));
                    intentDetalleAbonosCH.putExtra("estadoDocumento", estadoDocumento.getText().toString());
                    intentDetalleAbonosCH.putExtra("fechaEmision", fechaEmision.getText().toString());
                    intentDetalleAbonosCH.putExtra("fechaVencimiento", fechaVencimiento.getText().toString());
                    intentDetalleAbonosCH.putExtra("diasFaltantes", diasFaltantes.getText().toString());
                    intentDetalleAbonosCH.putExtra("debitos", debitos.getText().toString());
                    intentDetalleAbonosCH.putExtra("creditos", creditos.getText().toString());
                    intentDetalleAbonosCH.putExtra("saldos", saldos.getText().toString());
                    intentDetalleAbonosCH.putExtra("chequesPostfechados", chequesPostfechados.getText().toString());
                    //Imagenes al intent
                    Bitmap bitmap1 = ((BitmapDrawable) imgDocumento.getDrawable()).getBitmap(); // your bitmap
                    ByteArrayOutputStream bs1 = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bs1);
                    intentDetalleAbonosCH.putExtra("imgDocumento", bs1.toByteArray());
                    Bitmap bitmap2 = ((BitmapDrawable) imgRelojAlerta.getDrawable()).getBitmap(); // your bitmap
                    ByteArrayOutputStream bs2 = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bs2);
                    intentDetalleAbonosCH.putExtra("imgRelojAlerta", bs2.toByteArray());
                    Bitmap bitmap3 = ((BitmapDrawable) imgCalendario.getDrawable()).getBitmap(); // your bitmap
                    ByteArrayOutputStream bs3 = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 100, bs3);
                    intentDetalleAbonosCH.putExtra("imgCalendario", bs3.toByteArray());
                    context.startActivity(intentDetalleAbonosCH);
                }
            });
        }
    }

    private void asignarAnimacion(View itemView, int position) {
        //Revisar la condicion
        if (position > lastPosition) {
            //Si la posicion es mayor a la ultima position
            //INICIALIZA LA ANIMACION
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            //Asignamos la animacion a la lista de items
            itemView.setAnimation(animation);
            //Asignar la posicion actual dentro de la ultima posicion
            lastPosition = position;
        }

    }

    public void filtradoClientes(String busqueda) {
        if (busqueda.length() == 0) {
            clientesCarterasDetalles.clear();
            clientesCarterasDetalles.addAll(clientesCarterasDetallesBuscador);
        } else {
            clientesCarterasDetalles.clear();
            for (ClienteCarteraDetalle i : clientesCarterasDetallesBuscador) {
                if (i.getTipo().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarterasDetalles.add(i);
                } else if (i.getTipo().toUpperCase().contains(busqueda.toUpperCase())) {
                    clientesCarterasDetalles.add(i);
                }else if (i.getDocumento().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarterasDetalles.add(i);
                } else if (String.valueOf(i.getFecha()).contains(busqueda.toLowerCase())) {
                    clientesCarterasDetalles.add(i);
                } else if (String.valueOf(i.getFecha_vencimento()).contains(busqueda.toUpperCase())) {
                    clientesCarterasDetalles.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }
}
