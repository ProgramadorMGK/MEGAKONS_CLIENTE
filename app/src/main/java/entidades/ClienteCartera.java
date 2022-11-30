package entidades;

public class ClienteCartera {
    String cliente;
    String nombre;
    Double monto;
    int Dias_Credito;

    public ClienteCartera() {
    }

    public ClienteCartera(String cliente, String nombre, Double monto, int dias_Credito) {
        this.cliente = cliente;
        this.nombre = nombre;
        this.monto = monto;
        this.Dias_Credito = dias_Credito;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public int getDias_Credito() {
        return Dias_Credito;
    }

    public void setDias_Credito(int dias_Credito) {
        this.Dias_Credito = dias_Credito;
    }
}
