package adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.megakons.sa.NotasPendientesDetalleActivity;
import com.mobile.megakons.sa.R;

import java.util.ArrayList;
import java.util.List;

import entidades.NotaPedido;
import metodosGenerales.General;

public class AdaptadorNotasPedidosPendientes extends RecyclerView.Adapter<AdaptadorNotasPedidosPendientes.ViewHolder> {

    //Datos entre Activities
    Intent intentDetallesNota;

    //Lista notas
    LayoutInflater inflater;
    List<NotaPedido> notaPedidos;
    List<NotaPedido> notaPedidosBuscador;
    private Context context;

    //Activity NotasActivity Obtener codigo y oficina
    String codigo;
    String oficina;

    // Clase General
    General metodoGeneral = new General();
    //Animacion
    int lastPosition = -1;
    Activity activity;

    public AdaptadorNotasPedidosPendientes(Activity activity, Context ctx, List<NotaPedido> notaPedidos, String codigo, String oficina) {
        this.inflater = LayoutInflater.from(ctx);
        this.notaPedidos = notaPedidos;
        this.notaPedidosBuscador = new ArrayList<>();
        this.context = ctx;
        this.activity = activity;
        this.codigo = codigo;
        this.oficina = oficina;
        notaPedidosBuscador.addAll(notaPedidos);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_notas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //ASIGANCION DATOS DESDE BD
        holder.tvFecha.setText(Html.fromHtml("<b>Fecha: </b>" + notaPedidos.get(position).getFecha_formato()));
        holder.tvNombreCliente.setText(Html.fromHtml("<b>Cliente: </b>" + notaPedidos.get(position).getNombre()));
        //holder.tvPrecio.setText("$ " + productos.get(position).getPvp().toString());
        holder.tvNumero.setText(Html.fromHtml("<b>Número NP: </b>" + notaPedidos.get(position).getNumero()));
        String totalConvertido = aniadirCeroUnDecimal(notaPedidos.get(position).getTotal());
        holder.tvTotal.setText(Html.fromHtml("<b>Total: </b>" + "$ " + totalConvertido.replace('.', ',')));
        /*Picasso.get().load(productos.get(position).getImagen()).into(holder.imgProducto);*/
        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);
    }

    private String aniadirCeroUnDecimal(Double valorAredondear) {
        String numeroString = String.valueOf(valorAredondear);
        //int intNumber = Integer.parseInt(numeroString.substring(0, numeroString.indexOf('.')));
        //float decNumbert = Float.parseFloat(numeroString.substring(numeroString.indexOf('.')));
        //int decNumberInt = Integer.parseInt(numeroString.substring(numeroString.indexOf('.') + 1));
        String decNumberInt = numeroString.substring(numeroString.indexOf('.') + 1);
        if (decNumberInt.length() == 1){
            return valorAredondear.toString() + "0";
        }
        return  valorAredondear.toString();
    }

    private void asignarAnimacion(View itemView, int position) {
        //Revisar la condicion
        if (position > lastPosition){
            //Si la posicion es mayor a la ultima position
            //INICIALIZA LA ANIMACION
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            //Asignamos la animacion a la lista de items
            itemView.setAnimation(animation);
            //Asignar la posicion actual dentro de la ultima posicion
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return notaPedidos.size();
    }

    public void filtradoProductos(String busqueda) {
        if (busqueda.length() == 0) {
            notaPedidos.clear();
            notaPedidos.addAll(notaPedidosBuscador);
        } else {
                notaPedidos.clear();
                for (NotaPedido i : notaPedidosBuscador) {
                    if (i.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {
                        notaPedidos.add(i);
                    }else if (i.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                        notaPedidos.add(i);
                    }else if (i.getFecha_formato().toLowerCase().contains(busqueda.toLowerCase())) {
                        notaPedidos.add(i);
                    }else if (i.getFecha_formato().toUpperCase().contains(busqueda.toUpperCase())) {
                        notaPedidos.add(i);
                    }else if (i.getNumero().toLowerCase().contains(busqueda.toLowerCase())) {
                        notaPedidos.add(i);
                    }else if (i.getNumero().toUpperCase().contains(busqueda.toUpperCase())) {
                        notaPedidos.add(i);
                    }
                }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFecha, tvNombreCliente, tvNumero, tvTotal;
        ImageView imgNota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvNumero = itemView.findViewById(R.id.tvNumero);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            imgNota = itemView.findViewById(R.id.imgNota);


            //// Al hacer Click

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intentDetallesNota = new Intent(context, NotasPendientesDetalleActivity.class);
                    intentDetallesNota.putExtra("fechaNP", tvFecha.getText().toString().substring(tvFecha.getText().toString().indexOf(':')+1).trim());
                    intentDetallesNota.putExtra("nombreClienteNP", tvNombreCliente.getText().toString().substring(tvNombreCliente.getText().toString().indexOf(':')+1).trim());
                    intentDetallesNota.putExtra("totalNP", tvTotal.getText().toString().substring(tvTotal.getText().toString().indexOf('$')+1).trim());
                    intentDetallesNota.putExtra("numeroNP", tvNumero.getText().toString().substring(tvNumero.getText().toString().indexOf(':')+1).trim());
                    context.startActivity(intentDetallesNota);
                }
            });
        }


    }
}


