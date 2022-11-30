package adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.megakons.sa.ProductosDetallesActivity;
import com.mobile.megakons.sa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import conexiones.AJSONServicio;
import entidades.Producto;
import metodosGenerales.General;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolder> {

    //Activities
    Intent intentDetallesProducto;

    //Lista productos
    LayoutInflater inflater;
    List<Producto> productos;
    List<Producto> productosBuscador;
    private Context context;
    private static AJSONServicio ajsonServicio = new AJSONServicio();
    private static String URL_IMAGEN_GIF_PREGUNTA_AQUI = ajsonServicio.getIpGeneral() + ajsonServicio.getURL_IMAGEN_GIF_PREGUNTA_AQUI();
    private General metodoGeneral = new General();
    //Activity ProductosActivity Obtener Línea
    String linea;
    String oficina;

    //Animacion
    int lastPosition = -1;
    Activity activity;


    public AdaptadorProductos(Activity activity, Context ctx, List<Producto> productos, String linea, String oficina) {
        this.inflater = LayoutInflater.from(ctx);
        this.productos = productos;
        this.productosBuscador = new ArrayList<>();
        this.context = ctx;
        this.activity = activity;
        this.linea = linea;
        this.oficina = oficina;
        productosBuscador.addAll(productos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_layout_productos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //ASIGANCION DATOS DESDE BD
        holder.tvCodigo.setText(productos.get(position).getCodigo());
        holder.tvNombre.setText(productos.get(position).getNombre());
        if (productos.get(position).getDias_creado_articulo() <= 30){
            holder.productoInicial.setBackgroundColor(Color.parseColor("#E3E300"));
           // holder.tvNombre.setTextColor(Color.parseColor("#D00000"));
        }else{
            holder.productoInicial.setBackgroundColor(Color.parseColor("#F1F1F1"));
           // holder.tvNombre.setTextColor(Color.parseColor("#000000"));
        }
        //holder.tvPrecio.setText("$ " + productos.get(position).getPvp().toString());
        ////String pvpConvertido = aniadirCeroUnDecimal(productos.get(position).getPvp());
        ////String numericoPuntoPorComaEnPvpConvertido = cambiarPuntoPorComa(pvpConvertido);
        ////holder.tvPrecio.setText("$ " + numericoPuntoPorComaEnPvpConvertido);
        ////if (productos.get(position).getStock() <= 0){
        ////holder.tvStock.setTextColor((ContextCompat.getColor(context, R.color.rojoMGK)));
        ////holder.tvStock.setText(Html.fromHtml("<b>Agotado stock</b>"));
        ////}else{
        ////holder.tvStock.setText(Html.fromHtml("<b>Con stock</b>"));
        ////}
        /*Picasso.get().load(productos.get(position).getImagen()).into(holder.imgProducto);*/
        switch (linea) {
            case "01":
                Picasso.get().load(R.drawable.linea_1).into(holder.imgProducto);
                break;
            case "02":
                Picasso.get().load(R.drawable.linea_2).into(holder.imgProducto);
                break;
            case "03":
                Picasso.get().load(R.drawable.linea_3).into(holder.imgProducto);
                break;
            case "04":
                Picasso.get().load(R.drawable.linea_4).into(holder.imgProducto);
                break;
            case "05":
                Picasso.get().load(R.drawable.linea_5).into(holder.imgProducto);
                break;
            case "06":
                Picasso.get().load(R.drawable.linea_6).into(holder.imgProducto);
                break;
            case "07":
                Picasso.get().load(R.drawable.linea_7).into(holder.imgProducto);
                break;
            case "08":
                Picasso.get().load(R.drawable.linea_8).into(holder.imgProducto);
                break;
            case "09":
                Picasso.get().load(R.drawable.linea_9).into(holder.imgProducto);
                break;
            case "10":
                Picasso.get().load(R.drawable.linea_10).into(holder.imgProducto);
                break;
            case "12":
                Picasso.get().load(R.drawable.l12martillo).into(holder.imgProducto);
                break;
            case "13":
                Picasso.get().load(R.drawable.linea_13).into(holder.imgProducto);
                break;
            case "14":
                Picasso.get().load(R.drawable.linea_14).into(holder.imgProducto);
                break;
            case "15":
                Picasso.get().load(R.drawable.linea_15).into(holder.imgProducto);
                break;
            case "17":
                Picasso.get().load(R.drawable.linea_17).into(holder.imgProducto);
                break;
            case "21":
                Picasso.get().load(R.drawable.linea_21).into(holder.imgProducto);
                break;
            case "22":
                Picasso.get().load(R.drawable.linea_22).into(holder.imgProducto);
                break;
            case "23":
                Picasso.get().load(R.drawable.linea_23).into(holder.imgProducto);
                break;
            case "24":
                Picasso.get().load(R.drawable.l24brocha).into(holder.imgProducto);
                break;

        }
        String auxCodigoAlterno = productos.get(position).getCodigoAlterno();
        if (auxCodigoAlterno.contentEquals("null")) {
            holder.tvCodigoAlterno.setText("COD. ALT: " + "N/A");
        } else {
            holder.tvCodigoAlterno.setText("COD. ALT: " + auxCodigoAlterno);
        }
        //Asignar animacion en cada item
        asignarAnimacion(holder.itemView, position);
    }

    private String aniadirCeroUnDecimal(Double valorAredondear) {
        String numeroString = String.valueOf(valorAredondear);
        //int intNumber = Integer.parseInt(numeroString.substring(0, numeroString.indexOf('.')));
        //float decNumbert = Float.parseFloat(numeroString.substring(numeroString.indexOf('.')));
        //int decNumberInt = Integer.parseInt(numeroString.substring(numeroString.indexOf('.') + 1));
        String decNumberInt = numeroString.substring(numeroString.indexOf('.') + 1);
        if (decNumberInt.length() == 1) {
            return valorAredondear.toString() + "0";
        }
        return valorAredondear.toString();
    }

    //Truncar valores de existencia si es 6.00 => 6
    private String truncarExistencia(Double valorAtruncar) {
        String numeroString = String.valueOf(valorAtruncar);
        String decNumberInt = numeroString.substring(numeroString.indexOf('.') + 1);
        if (decNumberInt.length() == 1 && decNumberInt.equals("0")) {
            return String.valueOf(Math.round(valorAtruncar));
        }
        return valorAtruncar.toString() + "0";
    }

    private String cambiarPuntoPorComa(String valorPuntoComa) {
        valorPuntoComa = valorPuntoComa.replace(".", ",");
        return valorPuntoComa;
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

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void filtradoProductos(String busqueda) {
        if (busqueda.length() == 0) {
            productos.clear();
            productos.addAll(productosBuscador);
        } else {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Producto> collect = productos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
                        .filter(i -> i.getNombre().toUpperCase().contains(busqueda.toUpperCase()))
                        .filter(i -> i.getCodigo().toLowerCase().contains(busqueda.toLowerCase()))
                        .filter(i -> i.getCodigo().toUpperCase().contains(busqueda.toUpperCase()))
                        .collect(Collectors.toList());

                productos.clear();
                productosBuscador.addAll(collect);

            } else {
                *//* productos.clear();
                for (Producto i : productosBuscador) {
                    if (i.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {
                        productos.add(i);
                    }else if (i.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                        productos.add(i);
                    }else if (i.getCodigo().toLowerCase().contains(busqueda.toLowerCase())) {
                        productos.add(i);
                    }else if (i.getCodigo().toUpperCase().contains(busqueda.toUpperCase())) {
                        productos.add(i);
                    }
                }*//*
            }*/
            productos.clear();
            for (Producto i : productosBuscador) {
                if (i.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {
                    productos.add(i);
                } else if (i.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                    productos.add(i);
                } else if (i.getCodigo().toLowerCase().contains(busqueda.toLowerCase())) {
                    productos.add(i);
                } else if (i.getCodigo().toUpperCase().contains(busqueda.toUpperCase())) {
                    productos.add(i);
                } else if (i.getCodigoAlterno().toLowerCase().contains(busqueda.toLowerCase())) {
                    productos.add(i);
                } else if (i.getCodigoAlterno().toUpperCase().contains(busqueda.toUpperCase())) {
                    productos.add(i);
                }else if (busqueda.contentEquals("NUEVOS") || busqueda.contentEquals("nuevos")) {
                    if (i.getNuevo() == 'S') {
                        productos.add(i);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCodigo, tvCodigoAlterno, tvNombre, tvPrecio, tvStock;
        ImageView imgProducto, preguntarArticuloWhatsapp;
        ConstraintLayout productoInicial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            tvCodigoAlterno = itemView.findViewById(R.id.tvCodigoAlterno);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPvp);
            tvStock = itemView.findViewById(R.id.tvStock);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            productoInicial = itemView.findViewById(R.id.productoInicial);
            preguntarArticuloWhatsapp = itemView.findViewById(R.id.preguntarArticuloWhatsapp);
            Glide.with(context).load(URL_IMAGEN_GIF_PREGUNTA_AQUI).into(preguntarArticuloWhatsapp);


            //// Al hacer Click

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Obtener stock de producto solo numerico
                    String numericoExistencia = tvStock.getText().toString().substring(tvStock.getText().toString().indexOf(':') + 1).trim();
                    //Obtener costo de producto solo numerico
                    String numericoCosto = tvPrecio.getText().toString().substring(tvPrecio.getText().toString().indexOf('$') + 1).trim();

                    //Toast.makeText(v.getContext(), "Dato: " + tvCodigo.getText(), Toast.LENGTH_SHORT).show();

                    intentDetallesProducto = new Intent(context, ProductosDetallesActivity.class);
                    intentDetallesProducto.putExtra("codigoArticulo", tvCodigo.getText());
                    intentDetallesProducto.putExtra("nombreArticulo", tvNombre.getText());
                    //intentDetallesProducto.putExtra("existenciaArticulo", tvStock.getText());
                    intentDetallesProducto.putExtra("existenciaArticulo", numericoExistencia);
                    //intentDetallesProducto.putExtra("costoArticulo", tvPrecio.getText());
                    intentDetallesProducto.putExtra("costoArticulo", numericoCosto);
                    intentDetallesProducto.putExtra("lineaArticulo", linea);
                    intentDetallesProducto.putExtra("oficinaArticulo", oficina);
                    context.startActivity(intentDetallesProducto);
                }
            });

            preguntarArticuloWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    metodoGeneral.enviaMensajeWhatsApp("593999404805","Hola MEGAKONS S.A. Quisiera adquirir este producto: " + tvCodigo.getText() + " - " + tvNombre.getText(), context);
                }
            });

        }


    }
}


