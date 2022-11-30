package entidades;

public class NotaPedido {
    String fecha_formato;
    String fecha;
    String nombre;
    String egen_estado;
    String numero;
    Double baseiva0;
    Double baseiva;
    Double total;

    public NotaPedido() {
    }

    public NotaPedido(String fecha_formato, String fecha, String nombre, String egen_estado, String numero, Double baseiva0, Double baseiva, Double total) {
        this.fecha_formato = fecha_formato;
        this.fecha = fecha;
        this.nombre = nombre;
        this.egen_estado = egen_estado;
        this.numero = numero;
        this.baseiva0 = baseiva0;
        this.baseiva = baseiva;
        this.total = total;
    }

    public String getFecha_formato() {
        return fecha_formato;
    }

    public void setFecha_formato(String fecha_formato) {
        this.fecha_formato = fecha_formato;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEgen_estado() {
        return egen_estado;
    }

    public void setEgen_estado(String egen_estado) {
        this.egen_estado = egen_estado;
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
