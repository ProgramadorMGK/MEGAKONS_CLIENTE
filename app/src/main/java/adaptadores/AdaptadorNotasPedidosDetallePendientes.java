package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.megakons.sa.R;

import java.util.List;

import entidades.NotaPedidoDetalles;
import metodosGenerales.General;

public class AdaptadorNotasPedidosDetallePendientes extends RecyclerView.Adapter<AdaptadorNotasPedidosDetallePendientes.ViewHolder> {

    LayoutInflater inflater;
    List<NotaPedidoDetalles> notasPedidoDetalles;

    //Metodos Clase General
    General metodoGeneral = new General();

    public AdaptadorNotasPedidosDetallePendientes(Context ctx, List<NotaPedidoDetalles> notasPedidosDetalles){
        this.inflater = LayoutInflater.from(ctx);
        this.notasPedidoDetalles = notasPedidosDetalles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_notas_detalle, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Bind the Data
        holder.nppCodigoArticuloNP.setText(notasPedidoDetalles.get(position).getCodigo_articulo());
        holder.nppNombreArticuloNP.setText(notasPedidoDetalles.get(position).getNombre_articulo());
        //Redondear Cantidad Pedido, Perdido
        //holder.nppCantidadNP.setText(notasPedidoDetalles.get(position).getCant_nota_pedido().toString());
        holder.nppCantidadNP.setText(metodoGeneral.truncarNumero(notasPedidoDetalles.get(position).getCant_nota_pedido()).replace('.', ','));
        holder.nppCantidadPerdidaNP.setText(metodoGeneral.truncarNumero(notasPedidoDetalles.get(position).getCant_venta_perdida()).replace('.',','));


        holder.nppTotalVentaPerdidaNP.setText(metodoGeneral.aumentarCeroNumeroUnSoloDecimal(notasPedidoDetalles.get(position).getTotal_venta_perdida()).replace('.',','));
    }

    @Override
    public int getItemCount() {
        return notasPedidoDetalles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nppCodigoArticuloNP, nppNombreArticuloNP, nppCantidadNP, nppCantidadPerdidaNP, nppTotalVentaPerdidaNP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nppCodigoArticuloNP = itemView.findViewById(R.id.nppCodigoArticuloNP);
            nppNombreArticuloNP = itemView.findViewById(R.id.nppNombreArticuloNP);
            nppCantidadNP = itemView.findViewById(R.id.nppCantidadNP);
            nppCantidadPerdidaNP = itemView.findViewById(R.id.nppCantidadPerdidaNP);
            nppTotalVentaPerdidaNP = itemView.findViewById(R.id.nppTotalVentaPerdidaNP);

        }
    }

}
