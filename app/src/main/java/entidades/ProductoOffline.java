package entidades;

public class ProductoOffline {
    String codigo;
    String nombre;
    Double precio;
    Double existencia;
    Double descuento;
    String medida;
    String presentacion;
    String ubicacion;
    String codigoAlterno;

    public ProductoOffline() {
    }

    public ProductoOffline(String codigo, String nombre, Double precio, Double existencia, Double descuento, String medida, String presentacion, String ubicacion, String codigoAlterno) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
        this.descuento = descuento;
        this.medida = medida;
        this.presentacion = presentacion;
        this.ubicacion = ubicacion;
        this.codigoAlterno = codigoAlterno;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getExistencia() {
        return existencia;
    }

    public void setExistencia(Double existencia) {
        this.existencia = existencia;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public void setCodigoAlterno(String codigoAlterno) {
        this.codigoAlterno = codigoAlterno;
    }
}
