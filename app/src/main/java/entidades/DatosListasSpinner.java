package entidades;

public class DatosListasSpinner {
    //Esta clase concatena datos de varias entidades para poder aplicar un item incial de selección en los spinners
    String datos0;
    String datos1;
    String datos2;

    public DatosListasSpinner() {
    }

    public DatosListasSpinner(String datos0, String datos1, String datos2) {
        this.datos0 = datos0;
        this.datos1 = datos1;
        this.datos2 = datos2;
    }

    public String getDatos0() {
        return datos0;
    }

    public void setDatos0(String datos0) {
        this.datos0 = datos0;
    }

    public String getDatos1() {
        return datos1;
    }

    public void setDatos1(String datos1) {
        this.datos1 = datos1;
    }

    public String getDatos2() {
        return datos2;
    }

    public void setDatos2(String datos2) {
        this.datos2 = datos2;
    }
}
