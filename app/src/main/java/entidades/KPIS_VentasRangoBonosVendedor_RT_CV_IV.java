package entidades;

public class KPIS_VentasRangoBonosVendedor_RT_CV_IV {
    String referencia;
    Double valor_r;
    String descripcion;
    Double valor_d;
    Double valor_hasta;
    Double bono;

    public KPIS_VentasRangoBonosVendedor_RT_CV_IV(String referencia, Double valor_r, String descripcion, Double valor_d, Double valor_hasta, Double bono) {
        this.referencia = referencia;
        this.valor_r = valor_r;
        this.descripcion = descripcion;
        this.valor_d = valor_d;
        this.valor_hasta = valor_hasta;
        this.bono = bono;
    }

    public KPIS_VentasRangoBonosVendedor_RT_CV_IV() {
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getValor_r() {
        return valor_r;
    }

    public void setValor_r(Double valor_r) {
        this.valor_r = valor_r;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValor_hasta() {
        return valor_hasta;
    }

    public void setValor_hasta(Double valor_hasta) {
        this.valor_hasta = valor_hasta;
    }

    public Double getValor_d() {
        return valor_d;
    }

    public void setValor_d(Double valor_d) {
        this.valor_d = valor_d;
    }

    public Double getBono() {
        return bono;
    }

    public void setBono(Double bono) {
        this.bono = bono;
    }
}
