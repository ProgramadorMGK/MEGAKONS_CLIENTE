package adaptadores;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import entidades.ClienteCarteraDetalleAbonos;
import metodosGenerales.General;

public class AdaptadorClientesCarteraDetalleAbonos extends RecyclerView.Adapter<AdaptadorClientesCarteraDetalleAbonos.ViewHolder>  {
    //Lista Cartera Clientes
    LayoutInflater inflater;
    List<ClienteCarteraDetalleAbonos> clientesCarterasDetallesAbonosCheques;
    List<ClienteCarteraDetalleAbonos> clientesCarterasDetallesBuscadorAbonosCheques;
    private Context context;
    // Clase General
    General metodoGeneral = new General();
    //Animacion
    int lastPosition = -1;
    Activity activity;

    public AdaptadorClientesCarteraDetalleAbonos(Activity activity, Context context, List<ClienteCarteraDetalleAbonos> clientesCarterasDetallesAbonosCheques){
        this.inflater = LayoutInflater.from(context);
        this.clientesCarterasDetallesAbonosCheques = clientesCarterasDetallesAbonosCheques;
        this.clientesCarterasDetallesBuscadorAbonosCheques = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        clientesCarterasDetallesBuscadorAbonosCheques.addAll(clientesCarterasDetallesAbonosCheques);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_clientes_cartera_detalles_abonos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorClientesCarteraDetalleAbonos.ViewHolder holder, int position) {
        //PLCBID
        holder.tvDescripcionABCH.setText(clientesCarterasDetallesAbonosCheques.get(position).getDescripcion());
        holder.tvReferABCH.setText(clientesCarterasDetallesAbonosCheques.get(position).getReferencia());
        holder.tvValorABCH.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetallesAbonosCheques.get(position).getValor()).replace('.',','));
        holder.tvFechaABCH.setText(clientesCarterasDetallesAbonosCheques.get(position).getFecha());
        //if (clientesCarterasDetallesAbonosCheques.get(position).getReferencia().substring(0,2).contentEquals("CH")){
            //Picasso.get().load(R.drawable.cheque_verde).into(holder.imgTipoAbonoChequeABCH);
        //}else{
            Picasso.get().load(R.drawable.abono_factura).into(holder.imgTipoAbonoChequeABCH);
        //}

        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return clientesCarterasDetallesAbonosCheques.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescripcionABCH, tvReferABCH, tvFechaABCH, tvValorABCH;
        ImageView imgTipoAbonoChequeABCH;

        public ViewHolder(View itemView) {
            super(itemView);
            //Instanciar Datos con los de la lista - list_layout
            tvDescripcionABCH = itemView.findViewById(R.id.tvDescripcionABCH);
            tvReferABCH = itemView.findViewById(R.id.tvReferABCH);
            tvFechaABCH = itemView.findViewById(R.id.tvFechaABCH);
            tvValorABCH = itemView.findViewById(R.id.tvValorABCH);
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
            for (ClienteCarteraDetalleAbonos i : clientesCarterasDetallesBuscadorAbonosCheques) {
                if (i.getFecha().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarterasDetallesAbonosCheques.add(i);
                }else if(i.getReferencia().toLowerCase().contains(busqueda.toLowerCase())){
                    clientesCarterasDetallesAbonosCheques.add(i);
                }else if(i.getReferencia().toLowerCase().contains(busqueda.toUpperCase())){
                    clientesCarterasDetallesAbonosCheques.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }
}
