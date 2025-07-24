package entidad;

public class TipoCuenta {
    private int id_tc;
    private String descripcion_tc;

    public TipoCuenta() {}
    
    public TipoCuenta(int id_tc, String descripcion_tc) {
        this.id_tc = id_tc;
        this.descripcion_tc = descripcion_tc;
    }

    public int getId_tc() {
        return id_tc;
    }

    public void setId_tc(int id_tc) {
        this.id_tc = id_tc;
    }

    public String getDescripcion_tc() {
        return descripcion_tc;
    }

    public void setDescripcion_tc(String descripcion_tc) {
        this.descripcion_tc = descripcion_tc;
    }

    @Override
    public String toString() {
        return "TipoCuenta [id_tc=" + id_tc + ", descripcion_tc=" + descripcion_tc + "]";
    }
}
