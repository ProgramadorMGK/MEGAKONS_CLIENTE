package entidades;

public class ClienteCarteraDetalleAbonos {
    String fecha;
    double valor;
    String referencia;
    String descripcion;

    public ClienteCarteraDetalleAbonos(String fecha, double valor, String referencia, String descripcion) {
        this.fecha = fecha;
        this.valor = valor;
        this.referencia = referencia;
        this.descripcion = descripcion;
    }

    public ClienteCarteraDetalleAbonos() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
