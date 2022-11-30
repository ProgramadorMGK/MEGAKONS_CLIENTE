package adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.megakons.sa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entidades.FacturaDetalles;
import metodosGenerales.General;

public class AdaptadorClientesCarteraDetalleFactura extends RecyclerView.Adapter<AdaptadorClientesCarteraDetalleFactura.ViewHolder>  {

    //Lista Cartera Clientes
    LayoutInflater inflater;
    List<FacturaDetalles> clientesCarterasDetallesAbonosCheques;
    List<FacturaDetalles> clientesCarterasDetallesBuscadorAbonosCheques;
    private Context context;
    // Clase General
    General metodoGeneral = new General();
    //Animacion
    int lastPosition = -1;
    Activity activity;

    public AdaptadorClientesCarteraDetalleFactura(Activity activity, Context context, List<FacturaDetalles> clientesCarterasDetallesAbonosCheques){
        this.inflater = LayoutInflater.from(context);
        this.clientesCarterasDetallesAbonosCheques = clientesCarterasDetallesAbonosCheques;
        this.clientesCarterasDetallesBuscadorAbonosCheques = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        clientesCarterasDetallesBuscadorAbonosCheques.addAll(clientesCarterasDetallesAbonosCheques);
    }

    @Override
    public AdaptadorClientesCarteraDetalleFactura.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_clientes_cartera_detalles_facturas, parent,false);
        return new AdaptadorClientesCarteraDetalleFactura.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorClientesCarteraDetalleFactura.ViewHolder holder, int position) {
        ///// FECHA
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        //PLCBID
        holder.tvNombre.setText(clientesCarterasDetallesAbonosCheques.get(position).getNombre_articulo());
        holder.tvCodigo.setText(clientesCarterasDetallesAbonosCheques.get(position).getCodigo_articulo());
        holder.tvCantidad.setText(String.valueOf(Math.round(clientesCarterasDetallesAbonosCheques.get(position).getCant_articulo())));
        holder.tvPrecioUnitario.setText(metodoGeneral.string_Moneda(clientesCarterasDetallesAbonosCheques.get(position).getPrec_articulo()));
        holder.tvSubtotal.setText(metodoGeneral.string_Moneda(clientesCarterasDetallesAbonosCheques.get(position).getCant_articulo()*clientesCarterasDetallesAbonosCheques.get(position).getPrec_articulo()));
        holder.tvPorcentajeDescuento.setText(metodoGeneral.string_Porcentaje(clientesCarterasDetallesAbonosCheques.get(position).getPorcd_articulo()/100));
        holder.tvTotal.setText(metodoGeneral.string_Moneda(clientesCarterasDetallesAbonosCheques.get(position).getSubtotal()));
        holder.tvDescuentoValor.setText(Html.fromHtml("<b>Descuento: </b>" + metodoGeneral.string_Moneda(clientesCarterasDetallesAbonosCheques.get(position).getCant_articulo()*clientesCarterasDetallesAbonosCheques.get(position).getPrec_articulo() * (clientesCarterasDetallesAbonosCheques.get(position).getPorcd_articulo()/100))));
        //Picasso.get().load(R.drawable.cheque_verde).into(holder.imgTipoAbonoChequeABCH);
        //}
        //Asignacion de visualizacion
        holder.tvSubtotal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (clientesCarterasDetallesAbonosCheques.get(position).getPorcd_articulo() <= 0){
            holder.tvSubtotal.setVisibility(View.GONE);
            holder.tvDescuentoValor.setVisibility(View.GONE);
        }else{
            holder.tvSubtotal.setVisibility(View.VISIBLE);
            holder.tvDescuentoValor.setVisibility(View.VISIBLE);
        }
        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);

    }
    @Override
    public int getItemCount() {
        return clientesCarterasDetallesAbonosCheques.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCodigo, tvCantidad, tvPrecioUnitario, tvSubtotal, tvPorcentajeDescuento, tvTotal, tvDescuentoValor;

        public ViewHolder(View itemView) {
            super(itemView);
            //Instanciar Datos con los de la lista - list_layout
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecioUnitario = itemView.findViewById(R.id.tvPrecioUnitario);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
            tvPorcentajeDescuento = itemView.findViewById(R.id.tvPorcentajeDescuento);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvDescuentoValor = itemView.findViewById(R.id.tvDescuentoValor);
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
            for (FacturaDetalles i : clientesCarterasDetallesBuscadorAbonosCheques) {
                if (i.getCodigo_articulo().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarterasDetallesAbonosCheques.add(i);
                }else if(i.getNombre_articulo().toLowerCase().contains(busqueda.toLowerCase())){
                    clientesCarterasDetallesAbonosCheques.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }



}

