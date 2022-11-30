package entidades;

public class FacturaDetalles {
    String codigo_articulo;
    String nombre_articulo;
    Double cant_articulo;
    Double prec_articulo;
    Double porcd_articulo;
    Double subtotal;

    public FacturaDetalles() {

    }

    public FacturaDetalles(String codigo_articulo, String nombre_articulo, Double cant_articulo, Double prec_articulo, Double porcd_articulo, Double subtotal) {
        this.codigo_articulo = codigo_articulo;
        this.nombre_articulo = nombre_articulo;
        this.cant_articulo = cant_articulo;
        this.prec_articulo = prec_articulo;
        this.porcd_articulo = porcd_articulo;
        this.subtotal = subtotal;
    }

    public String getCodigo_articulo() {
        return codigo_articulo;
    }

    public void setCodigo_articulo(String codigo_articulo) {
        this.codigo_articulo = codigo_articulo;
    }

    public String getNombre_articulo() {
        return nombre_articulo;
    }

    public void setNombre_articulo(String nombre_articulo) {
        this.nombre_articulo = nombre_articulo;
    }

    public Double getCant_articulo() {
        return cant_articulo;
    }

    public void setCant_articulo(Double cant_articulo) {
        this.cant_articulo = cant_articulo;
    }

    public Double getPrec_articulo() {
        return prec_articulo;
    }

    public void setPrec_articulo(Double prec_articulo) {
        this.prec_articulo = prec_articulo;
    }

    public Double getPorcd_articulo() {
        return porcd_articulo;
    }

    public void setPorcd_articulo(Double porcd_articulo) {
        this.porcd_articulo = porcd_articulo;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

}
