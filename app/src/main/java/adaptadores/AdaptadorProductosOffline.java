package adaptadores;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.megakons.sa.NotasPendientesDetalleActivity;
import com.mobile.megakons.sa.ProductosDetallesActivity;
import com.mobile.megakons.sa.R;

import java.util.ArrayList;
import java.util.List;

import entidades.Producto;
import entidades.ProductoOffline;
import metodosGenerales.General;

public class AdaptadorProductosOffline extends RecyclerView.Adapter<AdaptadorProductosOffline.ViewHolder> {
    //Activities
    Intent intentDetallesProducto;
    //Lista
    LayoutInflater inflater;
    List<ProductoOffline> productosOfflineLista;
    //Buscador Lista
    List<ProductoOffline> productosOfflineListaBuscador;
    //CONTEXT
    private Context context;

    //Llamado a la clase de Metodos Generales
    General metodoGeneral;
    //Oficina usuario
    String oficina;

    public AdaptadorProductosOffline(Context context, List<ProductoOffline> productosOfflineLista, String oficina){
        this.inflater = LayoutInflater.from(context);
        this.productosOfflineLista = productosOfflineLista;
        //Inicializar Context
        this.context = context;
        //Oficina Usuario
        this.oficina = oficina;
        //Inbicializar buscador y asignar una nueva lista
        this.productosOfflineListaBuscador = new ArrayList<>();
        productosOfflineListaBuscador.addAll(productosOfflineLista);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_productos_offline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Instancias clase General
        metodoGeneral = new General();
        //bind the data
        holder.tvNombreO.setText(productosOfflineLista.get(position).getNombre());
        holder.tvCodigoO.setText(productosOfflineLista.get(position).getCodigo());
        //holder.tvExistenciaO.setText(productosOfflineLista.get(position).getExistencia().toString());
        holder.tvExistenciaO.setText(General.truncarNumero(productosOfflineLista.get(position).getExistencia()).replace('.', ','));
        holder.tvDescuentoO.setText(Html.fromHtml("<b>Descuento: </b>" + General.truncarNumero(productosOfflineLista.get(position).getDescuento()).replace('.', ',')));
        holder.tvMedidaO.setText(productosOfflineLista.get(position).getMedida());
        holder.tvPresentacionO.setText(productosOfflineLista.get(position).getPresentacion());
        holder.tvUbicacionO.setText(productosOfflineLista.get(position).getUbicacion());

        //Precio a setText Control Decimales
        String valorControl_PVP = "$ " + General.truncarNumero(productosOfflineLista.get(position).getPrecio()).replace('.', ',');

        if (valorControl_PVP.substring(valorControl_PVP.indexOf(',') + 1).length() > 4){
            String numericoCosto = valorControl_PVP.substring(valorControl_PVP.indexOf('$')+1).trim();
            Double nuevoValor_PVP = Double.valueOf(numericoCosto.replace(',', '.'));
            valorControl_PVP = "$ " + metodoGeneral.redondearNumeroA3Decimales(nuevoValor_PVP);
        }
        holder.tvPrecioO.setText(valorControl_PVP);
        String auxCodigoAlterno = productosOfflineLista.get(position).getCodigoAlterno();
        if (auxCodigoAlterno.contentEquals("null")) {
            holder.tvCodigoAlternoO.setText(Html.fromHtml("<b>COD A: </b>" + "N/A"));
        } else {
            holder.tvCodigoAlternoO.setText(Html.fromHtml("<b>COD A: </b>" + auxCodigoAlterno));
        }

    }

    @Override
    public int getItemCount() {
        return productosOfflineLista.size();
    }

    public void filter(String busqueda){
        if (busqueda.length() == 0){
            productosOfflineLista.clear();
            productosOfflineLista.addAll(productosOfflineListaBuscador);
        }else{
            productosOfflineLista.clear();
            for (ProductoOffline i : productosOfflineListaBuscador) {
                if (i.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {
                    productosOfflineLista.add(i);
                }else if (i.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                    productosOfflineLista.add(i);
                }else if (i.getCodigo().toLowerCase().contains(busqueda.toLowerCase())) {
                    productosOfflineLista.add(i);
                }else if (i.getCodigo().toUpperCase().contains(busqueda.toUpperCase())) {
                    productosOfflineLista.add(i);
                }else if (i.getCodigoAlterno().toLowerCase().contains(busqueda.toLowerCase())) {
                    productosOfflineLista.add(i);
                }else if (i.getCodigoAlterno().toUpperCase().contains(busqueda.toUpperCase())) {
                    productosOfflineLista.add(i);
                }
            }

        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombreO, tvCodigoO, tvExistenciaO, tvDescuentoO, tvMedidaO, tvPresentacionO, tvUbicacionO, tvPrecioO, tvCodigoAlternoO;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombreO = itemView.findViewById(R.id.tvNombreO);
            tvCodigoAlternoO = itemView.findViewById(R.id.tvCodigoAlternoO);
            tvCodigoO = itemView.findViewById(R.id.tvCodigoO);
            tvExistenciaO = itemView.findViewById(R.id.tvExistenciaO);
            tvDescuentoO = itemView.findViewById(R.id.tvDescuentoO);
            tvMedidaO = itemView.findViewById(R.id.tvMedidaO);
            tvPresentacionO = itemView.findViewById(R.id.tvPresentacionO);
            tvUbicacionO = itemView.findViewById(R.id.tvUbicacionO);
            tvPrecioO = itemView.findViewById(R.id.tvPrecioO);
//// Al hacer Click

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Obtener stock de producto solo numerico
                    String numericoExistencia = tvExistenciaO.getText().toString().substring(tvExistenciaO.getText().toString().indexOf(':')+1).trim();
                    //Obtener costo de producto solo numerico
                    String numericoCosto = tvPrecioO.getText().toString().substring(tvPrecioO.getText().toString().indexOf('$')+1).trim();

                    //Toast.makeText(v.getContext(), "Dato: " + tvCodigo.getText(), Toast.LENGTH_SHORT).show();

                    intentDetallesProducto = new Intent(context, ProductosDetallesActivity.class);
                    intentDetallesProducto.putExtra("codigoArticulo", tvCodigoO.getText());
                    intentDetallesProducto.putExtra("nombreArticulo", tvNombreO.getText());
                    //intentDetallesProducto.putExtra("existenciaArticulo", tvStock.getText());
                    intentDetallesProducto.putExtra("existenciaArticulo", numericoExistencia);
                    //intentDetallesProducto.putExtra("costoArticulo", tvPrecio.getText());
                    intentDetallesProducto.putExtra("costoArticulo", numericoCosto);
                    intentDetallesProducto.putExtra("lineaArticulo", "NA");
                    intentDetallesProducto.putExtra("oficinaArticulo", oficina);
                    context.startActivity(intentDetallesProducto);
                }
            });
        }
    }
}
