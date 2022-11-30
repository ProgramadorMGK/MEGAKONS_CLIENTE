package entidades;

import java.util.Date;

public class Producto {

    String codigo;
    String nombre;
    Double stock;
    Double pvp;
    String imagen;
    String codigoAlterno;
    Date fecha_creacion;
    int dias_creado_articulo;
    char nuevo;

    public Producto(){};

    public Producto(String codigo, String nombre, Double stock, Double pvp, String imagen,String codigoAlterno, Date fecha_creacion, int dias_creado_articulo, char nuevo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.pvp = pvp;
        this.imagen = imagen;
        this.codigoAlterno = codigoAlterno;
        this.fecha_creacion = fecha_creacion;
        this.dias_creado_articulo = dias_creado_articulo;
        this.nuevo = nuevo;
    }

    public char getNuevo() {
        return nuevo;
    }

    public void setNuevo(char nuevo) {
        this.nuevo = nuevo;
    }

    public int getDias_creado_articulo() {
        return dias_creado_articulo;
    }

    public void setDias_creado_articulo(int dias_creado_articulo) {
        this.dias_creado_articulo = dias_creado_articulo;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getPvp() {
        return pvp;
    }

    public void setPvp(Double pvp) {
        this.pvp = pvp;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public void setCodigoAlterno(String codigoAlterno) {
        this.codigoAlterno = codigoAlterno;
    }
}
