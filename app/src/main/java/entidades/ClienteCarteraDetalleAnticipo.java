package entidades;

import java.util.Date;

public class ClienteCarteraDetalleAnticipo {
    String id;
    String fecha_formato;
    Date fecha;
    String descripcion;
    double anticipo;
    double cruce;
    double restante;

    public ClienteCarteraDetalleAnticipo() {
    }

    public ClienteCarteraDetalleAnticipo(String id, String fecha_formato, Date fecha, String descripcion, double anticipo, double cruce, double restante) {
        this.id = id;
        this.fecha_formato = fecha_formato;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.anticipo = anticipo;
        this.cruce = cruce;
        this.restante = restante;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_formato() {
        return fecha_formato;
    }

    public void setFecha_formato(String fecha_formato) {
        this.fecha_formato = fecha_formato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public double getCruce() {
        return cruce;
    }

    public void setCruce(double cruce) {
        this.cruce = cruce;
    }

    public double getRestante() {
        return restante;
    }

    public void setRestante(double restante) {
        this.restante = restante;
    }
}
