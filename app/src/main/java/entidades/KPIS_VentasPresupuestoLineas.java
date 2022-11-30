package entidades;

public class KPIS_VentasPresupuestoLineas {
    String vndr_oficina;
    String vndr_codigo;
    String linea_codlinea;
    String descripc;
    Double presup;
    Double venta;
    Double cumplimiento;
    Double rentabilidad;

    public KPIS_VentasPresupuestoLineas() {

    }

    public KPIS_VentasPresupuestoLineas(String vndr_oficina, String vndr_codigo, String linea_codlinea, String descripc, Double presup, Double venta, Double cumplimiento, Double rentabilidad) {
        this.vndr_oficina = vndr_oficina;
        this.vndr_codigo = vndr_codigo;
        this.linea_codlinea = linea_codlinea;
        this.descripc = descripc;
        this.presup = presup;
        this.venta = venta;
        this.cumplimiento = cumplimiento;
        this.rentabilidad = rentabilidad;
    }

    public String getVndr_oficina() {
        return vndr_oficina;
    }

    public void setVndr_oficina(String vndr_oficina) {
        this.vndr_oficina = vndr_oficina;
    }

    public String getVndr_codigo() {
        return vndr_codigo;
    }

    public void setVndr_codigo(String vndr_codigo) {
        this.vndr_codigo = vndr_codigo;
    }

    public String getLinea_codlinea() {
        return linea_codlinea;
    }

    public void setLinea_codlinea(String linea_codlinea) {
        this.linea_codlinea = linea_codlinea;
    }

    public String getDescripc() {
        return descripc;
    }

    public void setDescripc(String descripc) {
        this.descripc = descripc;
    }

    public Double getPresup() {
        return presup;
    }

    public void setPresup(Double presup) {
        this.presup = presup;
    }

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }

    public Double getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(Double cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public Double getRentabilidad() {
        return rentabilidad;
    }

    public void setRentabilidad(Double rentabilidad) {
        this.rentabilidad = rentabilidad;
    }
}
