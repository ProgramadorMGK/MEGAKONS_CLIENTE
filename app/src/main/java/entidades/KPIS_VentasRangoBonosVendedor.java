package entidades;

public class KPIS_VentasRangoBonosVendedor {
    String referencia;
    Double venta;
    Double bono;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }

    public Double getBono() {
        return bono;
    }

    public void setBono(Double bono) {
        this.bono = bono;
    }

    public KPIS_VentasRangoBonosVendedor() {
    }

    public KPIS_VentasRangoBonosVendedor(String referencia, Double venta, Double bono) {
        this.referencia = referencia;
        this.venta = venta;
        this.bono = bono;
    }
}
