package entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class KPIS_VentasPresupuestoLineasBonosVendedor {
    String datoImagen;
    String vendedor;
    String linea;
    Double presupuesto;
    Double cumplimiento;
    Double venta_total;
    Double bono;
    Bitmap imagen;

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Double getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(Double cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public Double getVenta_total() {
        return venta_total;
    }

    public void setVenta_total(Double venta_total) {
        this.venta_total = venta_total;
    }

    public Double getBono() {
        return bono;
    }

    public void setBono(Double bono) {
        this.bono = bono;
    }

    public String getDatoImagen() {
        return datoImagen;
    }

    public void setDatoImagen(String datoImagen) {
        this.datoImagen = datoImagen;
        try{
            byte[] byteCode = Base64.decode(datoImagen, Base64.DEFAULT);
            this.imagen = BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public KPIS_VentasPresupuestoLineasBonosVendedor(String datoImagen, String vendedor, String linea, Double presupuesto, Double cumplimiento, Double venta_total, Double bono, Bitmap imagen) {
        this.vendedor = vendedor;
        this.linea = linea;
        this.presupuesto = presupuesto;
        this.cumplimiento = cumplimiento;
        this.venta_total = venta_total;
        this.bono = bono;
        this.imagen = imagen;
        this.datoImagen = datoImagen;
    }

    public KPIS_VentasPresupuestoLineasBonosVendedor() {
    }
}
