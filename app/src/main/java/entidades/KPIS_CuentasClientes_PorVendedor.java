package entidades;

public class KPIS_CuentasClientes_PorVendedor {
    Double xvencer;
    Double vencida;
    Double documentada;

    public Double getXvencer() {
        return xvencer;
    }

    public void setXvencer(Double xvencer) {
        this.xvencer = xvencer;
    }

    public Double getVencida() {
        return vencida;
    }

    public void setVencida(Double vencida) {
        this.vencida = vencida;
    }

    public Double getDocumentada() {
        return documentada;
    }

    public void setDocumentada(Double documentada) {
        this.documentada = documentada;
    }

    public KPIS_CuentasClientes_PorVendedor(Double xvencer, Double vencida, Double documentada) {
        this.xvencer = xvencer;
        this.vencida = vencida;
        this.documentada = documentada;
    }

    public KPIS_CuentasClientes_PorVendedor() {
    }
}
