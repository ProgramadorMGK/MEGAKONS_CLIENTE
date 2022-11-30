package entidades;

public class KPIS_VentasPresupuestoGeneral_Y_PorVendedor {

    String vndr_oficina;
    String vndr_codigo;
    String nombre;
    char sexo;
    Double presupuesto;
    Double vent_neta;
    Double cost_neto;
    Double nc_descuento;
    Double rentabilidad;
    Double cumplimiento;

    public KPIS_VentasPresupuestoGeneral_Y_PorVendedor() {
    }

    public KPIS_VentasPresupuestoGeneral_Y_PorVendedor(String vndr_oficina, String vndr_codigo, String nombre, char sexo, Double presupuesto, Double vent_neta, Double cost_neto, Double nc_descuento, Double rentabilidad, Double cumplimiento) {
        this.vndr_oficina = vndr_oficina;
        this.vndr_codigo = vndr_codigo;
        this.nombre = nombre;
        this.sexo = sexo;
        this.presupuesto = presupuesto;
        this.vent_neta = vent_neta;
        this.cost_neto = cost_neto;
        this.nc_descuento = nc_descuento;
        this.rentabilidad = rentabilidad;
        this.cumplimiento = cumplimiento;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Double getVent_neta() {
        return vent_neta;
    }

    public void setVent_neta(Double vent_neta) {
        this.vent_neta = vent_neta;
    }

    public Double getCost_neto() {
        return cost_neto;
    }

    public void setCost_neto(Double cost_neto) {
        this.cost_neto = cost_neto;
    }

    public Double getNc_descuento() {
        return nc_descuento;
    }

    public void setNc_descuento(Double nc_descuento) {
        this.nc_descuento = nc_descuento;
    }

    public Double getRentabilidad() {
        return rentabilidad;
    }

    public void setRentabilidad(Double rentabilidad) {
        this.rentabilidad = rentabilidad;
    }

    public Double getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(Double cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

}
