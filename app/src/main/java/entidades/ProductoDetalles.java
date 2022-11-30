package entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ProductoDetalles {
    String datoImagen;
    Bitmap imagen;
    String unid;
    String presentacion;
    String bodega;
    String descuento;
    String aliasweb;
    String modelo;
    String marca;
    String linea;
    String codigoAlterno;

    public ProductoDetalles() {
    }

    public ProductoDetalles(String datoImagen, Bitmap imagen, String unid, String presentacion, String bodega, String descuento, String aliasweb, String modelo, String marca, String linea, String codigoAlterno) {
        this.datoImagen = datoImagen;
        this.imagen = imagen;
        this.unid = unid;
        this.presentacion = presentacion;
        this.bodega = bodega;
        this.descuento = descuento;
        this.aliasweb = aliasweb;
        this.modelo = modelo;
        this.marca = marca;
        this.linea = linea;
        this.codigoAlterno = codigoAlterno;
    }

    public String getDatoImagen() {
        return datoImagen;
    }

    public void setDatoImagen(String datoImagen) {
        this.datoImagen = datoImagen;
        try{
            byte[] byteCode = Base64.decode(datoImagen, Base64.DEFAULT);
            this.imagen = BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getAliasweb() {
        return aliasweb;
    }

    public void setAliasweb(String aliasweb) {
        this.aliasweb = aliasweb;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public void setCodigoAlterno(String codigoAlterno) {
        this.codigoAlterno = codigoAlterno;
    }
}
