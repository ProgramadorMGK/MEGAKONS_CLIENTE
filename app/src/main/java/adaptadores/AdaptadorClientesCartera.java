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

import com.mobile.megakons.sa.ClienteCarteraDetalleAbonosCHActivity;
import com.mobile.megakons.sa.ClienteCarteraDetalleActivity;
import com.mobile.megakons.sa.ProductosDetallesActivity;
import com.mobile.megakons.sa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import entidades.ClienteCartera;
import entidades.NotaPedido;
import entidades.Producto;
import metodosGenerales.General;

import static com.mobile.megakons.sa.R.drawable.usuario_verde;

public class AdaptadorClientesCartera extends RecyclerView.Adapter<AdaptadorClientesCartera.ViewHolder> {
    //Activities
    Intent intentDetalleCarteraCliente, intentDetalleCarteraClienteACH;
    //Lista Cartera Clientes
    LayoutInflater inflater;
    List<ClienteCartera> clientesCarteras;
    List<ClienteCartera> clientesCarterasBuscador;
    private Context context;
    // Clase General
    General metodoGeneral = new General();

    public AdaptadorClientesCartera(Activity activity, Context context, List<ClienteCartera> clientesCarteras){
        this.inflater = LayoutInflater.from(context);
        this.clientesCarteras = clientesCarteras;
        this.clientesCarterasBuscador = new ArrayList<>();
        this.context = context;
        this.activity = activity;
        clientesCarterasBuscador.addAll(clientesCarteras);
    }

    //Animacion
    int lastPosition = -1;
    Activity activity;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_clientes_carteras,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorClientesCartera.ViewHolder holder, int position) {
        //Llenar los datos segun los lea del visual code
        holder.nombreCliente.setText(clientesCarteras.get(position).getNombre());
        holder.codigoCliente.setText(Html.fromHtml("<b>Cliente: </b>" + clientesCarteras.get(position).getCliente()));
        holder.montoCarteraCliente.setText(clientesCarteras.get(position).getMonto().toString());
        holder.montoCarteraCliente.setText(" $ " + metodoGeneral.redondearNumero(clientesCarteras.get(position).getMonto()).replace('.',','));
        holder.diasCreditoCateraCliente.setText(Integer.valueOf(clientesCarteras.get(position).getDias_Credito()).toString());
        int diasCredito = clientesCarteras.get(position).getDias_Credito();
        if (diasCredito >= 60) {
            Picasso.get().load(R.drawable.usuario_verde).into(holder.imgClienteCartera);
        }else if (diasCredito >= 50) {
                Picasso.get().load(R.drawable.usuario_amarillo).into(holder.imgClienteCartera);
        }else if (diasCredito >= 30) {
            Picasso.get().load(R.drawable.usuario_naranja).into(holder.imgClienteCartera);
        }else{
                Picasso.get().load(R.drawable.usuario_rojo).into(holder.imgClienteCartera);
        }
        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return clientesCarteras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCliente, codigoCliente, montoCarteraCliente, diasCreditoCateraCliente;
        ImageView imgClienteCartera;

        public ViewHolder(View itemView) {
            super(itemView);
            //Instanciar Datos con los de la lista - list_layout
            nombreCliente = itemView.findViewById(R.id.tvNombreClienteCartera);
            codigoCliente = itemView.findViewById(R.id.tvCodigoClienteCartera);
            montoCarteraCliente = itemView.findViewById(R.id.tvMontoClienteCartera);
            diasCreditoCateraCliente = itemView.findViewById(R.id.tvDiasCreditoClienteCartera);
            imgClienteCartera = itemView.findViewById(R.id.imgClienteCartera);
            //// Al hacer Click

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentDetalleCarteraCliente = new Intent(context, ClienteCarteraDetalleActivity.class);
                    intentDetalleCarteraCliente.putExtra("nombreCliente", nombreCliente.getText().toString());
                    intentDetalleCarteraCliente.putExtra("codigoCliente", codigoCliente.getText().toString());
                    intentDetalleCarteraCliente.putExtra("montoCarteraCliente", montoCarteraCliente.getText().toString());
                    intentDetalleCarteraCliente.putExtra("diasCreditoCateraCliente", diasCreditoCateraCliente.getText().toString());
                    context.startActivity(intentDetalleCarteraCliente);
                }
            });
        }
    }

    public void filtradoClientes(String busqueda) {
        if (busqueda.length() == 0) {
            clientesCarteras.clear();
            clientesCarteras.addAll(clientesCarterasBuscador);
        } else {
            clientesCarteras.clear();
            for (ClienteCartera i : clientesCarterasBuscador) {
                if (i.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {
                    clientesCarteras.add(i);
                } else if (i.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                    clientesCarteras.add(i);
                } else if (i.getCliente().contains(busqueda)) {
                    clientesCarteras.add(i);
                }
            }
        }
        notifyDataSetChanged();
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


}