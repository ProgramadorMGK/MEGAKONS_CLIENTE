package entidades;

public class Factura {
    String numero;
    Double baseiva0;
    Double iva;
    Double baseiva;
    Double total;

    public Factura() {
    }

    public Factura(String numero, Double baseiva0, Double iva, Double baseiva, Double total) {
        this.numero = numero;
        this.baseiva0 = baseiva0;
        this.iva = iva;
        this.baseiva = baseiva;
        this.total = total;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getBaseiva0() {
        return baseiva0;
    }

    public void setBaseiva0(Double baseiva0) {
        this.baseiva0 = baseiva0;
    }

    public Double getIva() {
        return baseiva0;
    }

    public void setIva(Double baseiva0) {
        this.baseiva0 = baseiva0;
    }

    public Double getBaseiva() {
        return baseiva;
    }

    public void setBaseiva(Double baseiva) {
        this.baseiva = baseiva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
