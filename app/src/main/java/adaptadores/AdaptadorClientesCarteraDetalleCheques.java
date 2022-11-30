package adaptadores;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.megakons.sa.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entidades.ClienteCarteraDetalleAbonos;
import entidades.ClienteCarteraDetalleCheques;
import metodosGenerales.General;

public class AdaptadorClientesCarteraDetalleCheques  extends RecyclerView.Adapter<AdaptadorClientesCarteraDetalleCheques.ViewHolder>  {

    //Lista Cartera Clientes
    LayoutInflater inflater;
    List<ClienteCarteraDetalleCheques> clientesCarterasDetallesAbonosCheques;
    List<ClienteCarteraDetalleCheques> clientesCarterasDetallesBuscadorAbonosCheques;
    private Context context;
    // Clase General
    General metodoGeneral = new General();
    //Animacion
    int lastPosition = -1;
    Activity activity;

    public AdaptadorClientesCarteraDetalleCheques(Activity activity, Context context, List<ClienteCarteraDetalleCheques> clientesCarterasDetallesAbonosCheques){
        this.inflater = LayoutInflater.from(context);
        this.clientesCarterasDetallesAbonosCheques = clientesCarterasDetallesAbonosCheques;
        this.clientesCarterasDetallesBuscadorAbonosCheques = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        clientesCarterasDetallesBuscadorAbonosCheques.addAll(clientesCarterasDetallesAbonosCheques);
    }

    @Override
    public AdaptadorClientesCarteraDetalleCheques.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_clientes_cartera_detalles_cheques, parent,false);
        return new AdaptadorClientesCarteraDetalleCheques.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorClientesCarteraDetalleCheques.ViewHolder holder, int position) {
        ///// FECHA
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        //PLCBID
        holder.tvFechaCH.setText(Html.fromHtml("<b>Creado: </b>" + simpleDateFormat.format(clientesCarterasDetallesAbonosCheques.get(position).getFecha_creacion()).toUpperCase()));
        holder.tvPostfechadoCH.setText(Html.fromHtml("<b>Posfechado: </b>" + simpleDateFormat.format(clientesCarterasDetallesAbonosCheques.get(position).getFecha_postfechado()).toUpperCase()));
        holder.tvNumeroCH.setText(Html.fromHtml("<b>Cheque Nº: </b>" + clientesCarterasDetallesAbonosCheques.get(position).getNumero()));
        holder.tvCuentaCH.setText(Html.fromHtml("<b>Cuenta: </b>" + clientesCarterasDetallesAbonosCheques.get(position).getCuenta()+ " - " + clientesCarterasDetallesAbonosCheques.get(position).getBanco()));
        holder.tvImporteCH.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetallesAbonosCheques.get(position).getImporte()).replace('.',','));
        Picasso.get().load(R.drawable.cheque_verde).into(holder.imgTipoAbonoChequeABCH);
        //}

        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);

    }
    @Override
    public int getItemCount() {
        return clientesCarterasDetallesAbonosCheques.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumeroCH, tvCuentaCH, tvFechaCH, tvPostfechadoCH, tvImporteCH;
        ImageView imgTipoAbonoChequeABCH;

        public ViewHolder(View itemView) {
            super(itemView);
            //Instanciar Datos con los de la lista - list_layout
            tvNumeroCH = itemView.findViewById(R.id.tvNumeroCH);
            tvCuentaCH = itemView.findViewById(R.id.tvCuentaCH);
            tvFechaCH = itemView.findViewById(R.id.tvFechaCH);
            tvPostfechadoCH = itemView.findViewById(R.id.tvPostfechadoCH);
            tvImporteCH = itemView.findViewById(R.id.tvImporteCH);
            imgTipoAbonoChequeABCH = itemView.findViewById(R.id.imgTipoAbonoChequeABCH);

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

    public void filtradoAbonosCheques(String busqueda) {
        if (busqueda.length() == 0) {
            clientesCarterasDetallesAbonosCheques.clear();
            clientesCarterasDetallesAbonosCheques.addAll(clientesCarterasDetallesBuscadorAbonosCheques);
        } else {
            clientesCarterasDetallesAbonosCheques.clear();
            for (ClienteCarteraDetalleCheques i : clientesCarterasDetallesBuscadorAbonosCheques) {
                if (i.getNumero().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarterasDetallesAbonosCheques.add(i);
                }else if(i.getBanco().toLowerCase().contains(busqueda.toLowerCase())){
                    clientesCarterasDetallesAbonosCheques.add(i);
                }else if(i.getBanco().toLowerCase().contains(busqueda.toUpperCase())){
                    clientesCarterasDetallesAbonosCheques.add(i);
                }else if (i.getCuenta().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarterasDetallesAbonosCheques.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }



}

