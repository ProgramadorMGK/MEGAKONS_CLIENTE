package entidades;

import java.util.Date;

public class ClienteCarteraDetalleCheques {
    String numero;
    Date fecha_creacion;
    Date fecha_postfechado;
    double importe;
    String banco;
    String cuenta;

    public ClienteCarteraDetalleCheques(String numero, Date fecha_creacion, Date fecha_postfechado, double importe, String banco, String cuenta) {
        this.numero = numero;
        this.fecha_creacion = fecha_creacion;
        this.fecha_postfechado = fecha_postfechado;
        this.importe = importe;
        this.banco = banco;
        this.cuenta = cuenta;
    }

    public ClienteCarteraDetalleCheques() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_postfechado() {
        return fecha_postfechado;
    }

    public void setFecha_postfechado(Date fecha_postfechado) {
        this.fecha_postfechado = fecha_postfechado;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
