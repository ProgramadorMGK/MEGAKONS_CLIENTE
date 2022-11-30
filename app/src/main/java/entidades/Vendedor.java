package entidades;

public class Vendedor {
    String codigo;
    String nombre;
    String sexo;
    String oficina;

    public Vendedor(String codigo, String nombre, String sexo, String oficina) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sexo = sexo;
        this.oficina = oficina;
    }

    public Vendedor() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }
}
