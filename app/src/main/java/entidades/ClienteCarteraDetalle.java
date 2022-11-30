package entidades;

import java.util.Date;

public class ClienteCarteraDetalle {
    String tipo;
    Date fecha;
    String serie;
    String documento;
    String plcb_id;
    String estado;
    Date fecha_vencimento;
    int díasFaltantes;
    String id_cliente;
    String nombre;
    double total;
    double abono;
    double saldo;
    double chpf;

    public ClienteCarteraDetalle() {
    }

    public ClienteCarteraDetalle(String tipo, Date fecha, String serie, String documento, String plcb_id, String estado, Date fecha_vencimento, int díasFaltantes, String id_cliente, String nombre, Double total, Double abono, Double saldo, Double chpf) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.serie = serie;
        this.documento = documento;
        this.plcb_id = plcb_id;
        this.estado = estado;
        this.fecha_vencimento = fecha_vencimento;
        this.díasFaltantes = díasFaltantes;
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.total = total;
        this.abono = abono;
        this.saldo = saldo;
        this.chpf = chpf;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPlcb_id() {
        return plcb_id;
    }

    public void setPlcb_id(String plcb_id) {
        this.plcb_id = plcb_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_vencimento() {
        return fecha_vencimento;
    }

    public void setFecha_vencimento(Date fecha_vencimento) {
        this.fecha_vencimento = fecha_vencimento;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getChpf() {
        return chpf;
    }

    public void setChpf(double chpf) {
        this.chpf = chpf;
    }

    public int getDíasFaltantes() {
        return díasFaltantes;
    }

    public void setDíasFaltantes(int díasFaltantes) {
        this.díasFaltantes = díasFaltantes;
    }
}
