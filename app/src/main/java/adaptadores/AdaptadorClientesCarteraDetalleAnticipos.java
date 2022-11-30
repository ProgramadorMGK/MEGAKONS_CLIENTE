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

import java.util.ArrayList;
import java.util.List;

import entidades.ClienteCarteraDetalleAbonos;
import entidades.ClienteCarteraDetalleAnticipo;
import entidades.ClienteCarteraDetalleCheques;
import metodosGenerales.General;

public class AdaptadorClientesCarteraDetalleAnticipos extends RecyclerView.Adapter<AdaptadorClientesCarteraDetalleAnticipos.ViewHolder>{
    //Lista Cartera Clientes
    LayoutInflater inflater;
    List<ClienteCarteraDetalleAnticipo> clientesCarterasDetallesAnticipos;
    List<ClienteCarteraDetalleAnticipo> clientesCarterasDetallesBuscadorAnticipos;
    private Context context;
    // Clase General
    General metodoGeneral = new General();
    //Animacion
    int lastPosition = -1;
    Activity activity;

    public AdaptadorClientesCarteraDetalleAnticipos(Activity activity, Context context, List<ClienteCarteraDetalleAnticipo> clientesCarterasDetallesAnticipos){
        this.inflater = LayoutInflater.from(context);
        this.clientesCarterasDetallesAnticipos = clientesCarterasDetallesAnticipos;
        this.clientesCarterasDetallesBuscadorAnticipos = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        clientesCarterasDetallesBuscadorAnticipos.addAll(clientesCarterasDetallesAnticipos);
    }
    @Override
    public AdaptadorClientesCarteraDetalleAnticipos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_clientes_cartera_detalles_anticipos,parent,false);
        return new AdaptadorClientesCarteraDetalleAnticipos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorClientesCarteraDetalleAnticipos.ViewHolder holder, int position) {
        //PLCBID
        holder.tvCCDescripcionAnticipo.setText(clientesCarterasDetallesAnticipos.get(position).getDescripcion());
        holder.tvCCFechaAnticipo.setText(Html.fromHtml("<b>Fecha: </b>" + clientesCarterasDetallesAnticipos.get(position).getFecha_formato()));
        holder.tvCCIdAnticipo.setText(Html.fromHtml("<b>ID: </b>" +clientesCarterasDetallesAnticipos.get(position).getId()));
        holder.tvCCValorAnticipo.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetallesAnticipos.get(position).getAnticipo()).replace('.',','));
        holder.tvCCCruceAnticipo.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetallesAnticipos.get(position).getCruce()).replace('.',','));
        holder.tvCCRestanteAnticipo.setText(" $ " + metodoGeneral.redondearNumero(clientesCarterasDetallesAnticipos.get(position).getRestante()).replace('.',','));
        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return clientesCarterasDetallesAnticipos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCCDescripcionAnticipo, tvCCFechaAnticipo, tvCCIdAnticipo, tvCCValorAnticipo, tvCCCruceAnticipo, tvCCRestanteAnticipo;

        public ViewHolder(View itemView) {
            super(itemView);
            //Instanciar Datos con los de la lista - list_layout
            tvCCDescripcionAnticipo = itemView.findViewById(R.id.tvCCDescripcionAnticipo);
            tvCCFechaAnticipo = itemView.findViewById(R.id.tvCCFechaAnticipo);
            tvCCIdAnticipo = itemView.findViewById(R.id.tvCCIdAnticipo);
            tvCCValorAnticipo = itemView.findViewById(R.id.tvCCValorAnticipo);
            tvCCCruceAnticipo = itemView.findViewById(R.id.tvCCCruceAnticipo);
            tvCCRestanteAnticipo = itemView.findViewById(R.id.tvCCRestanteAnticipo);

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

    public void filtradoAnticiposCliente(String busqueda) {
        if (busqueda.length() == 0) {
            clientesCarterasDetallesAnticipos.clear();
            clientesCarterasDetallesAnticipos.addAll(clientesCarterasDetallesBuscadorAnticipos);
        } else {
            clientesCarterasDetallesAnticipos.clear();
            for (ClienteCarteraDetalleAnticipo i : clientesCarterasDetallesBuscadorAnticipos) {
                if (i.getFecha_formato().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarterasDetallesAnticipos.add(i);
                }else if(i.getDescripcion().toLowerCase().contains(busqueda.toLowerCase())){
                    clientesCarterasDetallesAnticipos.add(i);
                }else if(i.getDescripcion().toLowerCase().contains(busqueda.toUpperCase())){
                    clientesCarterasDetallesAnticipos.add(i);
                }else if(i.getId().toLowerCase().contains(busqueda.toUpperCase())){
                    clientesCarterasDetallesAnticipos.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }
}