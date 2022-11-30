package entidades;

public class NotaPedidoDetalles {
    String estado;
    String id_cliente;
    String codigo_articulo;
    String nombre_articulo;
    Double cant_nota_pedido;
    Double prec_nota_pedido;
    Double porcd_nota_pedido;
    Double iva_nota_pedido;
    Double cant_facturada;
    Double costo_prom;
    Double cant_venta_perdida;
    Double total_venta_perdida;

    public NotaPedidoDetalles() {

    }

    public NotaPedidoDetalles(String estado, String id_cliente, String codigo_articulo, String nombre_articulo, Double cant_nota_pedido, Double prec_nota_pedido, Double porcd_nota_pedido, Double iva_nota_pedido, Double cant_facturada, Double costo_prom, Double cant_venta_perdida, Double total_venta_perdida) {
        this.estado = estado;
        this.id_cliente = id_cliente;
        this.codigo_articulo = codigo_articulo;
        this.nombre_articulo = nombre_articulo;
        this.cant_nota_pedido = cant_nota_pedido;
        this.prec_nota_pedido = prec_nota_pedido;
        this.porcd_nota_pedido = porcd_nota_pedido;
        this.iva_nota_pedido = iva_nota_pedido;
        this.cant_facturada = cant_facturada;
        this.costo_prom = costo_prom;
        this.cant_venta_perdida = cant_venta_perdida;
        this.total_venta_perdida = total_venta_perdida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
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

    public Double getCant_nota_pedido() {
        return cant_nota_pedido;
    }

    public void setCant_nota_pedido(Double cant_nota_pedido) {
        this.cant_nota_pedido = cant_nota_pedido;
    }

    public Double getPrec_nota_pedido() {
        return prec_nota_pedido;
    }

    public void setPrec_nota_pedido(Double prec_nota_pedido) {
        this.prec_nota_pedido = prec_nota_pedido;
    }

    public Double getPorcd_nota_pedido() {
        return porcd_nota_pedido;
    }

    public void setPorcd_nota_pedido(Double porcd_nota_pedido) {
        this.porcd_nota_pedido = porcd_nota_pedido;
    }

    public Double getIva_nota_pedido() {
        return iva_nota_pedido;
    }

    public void setIva_nota_pedido(Double iva_nota_pedido) {
        this.iva_nota_pedido = iva_nota_pedido;
    }

    public Double getCant_facturada() {
        return cant_facturada;
    }

    public void setCant_facturada(Double cant_facturada) {
        this.cant_facturada = cant_facturada;
    }

    public Double getCosto_prom() {
        return costo_prom;
    }

    public void setCosto_prom(Double costo_prom) {
        this.costo_prom = costo_prom;
    }

    public Double getCant_venta_perdida() {
        return cant_venta_perdida;
    }

    public void setCant_venta_perdida(Double cant_venta_perdida) {
        this.cant_venta_perdida = cant_venta_perdida;
    }

    public Double getTotal_venta_perdida() {
        return total_venta_perdida;
    }

    public void setTotal_venta_perdida(Double total_venta_perdida) {
        this.total_venta_perdida = total_venta_perdida;
    }
}
