package entidad;

public class TiposMovimientos {
    private int id_tm;
    private String descripcion_tm;

    public TiposMovimientos() {}
    
    public TiposMovimientos(int id_tm, String descripcion_tm) {
        this.id_tm = id_tm;
        this.descripcion_tm = descripcion_tm;
    }

    public int getId_tm() {
        return id_tm;
    }

    public void setId_tm(int id_tm) {
        this.id_tm = id_tm;
    }

    public String getDescripcion_tm() {
        return descripcion_tm;
    }

    public void setDescripcion_tm(String descripcion_tm) {
        this.descripcion_tm = descripcion_tm;
    }

    @Override
    public String toString() {
        return "TiposMovimientos [id_tm=" + id_tm + ", descripcion_tm=" + descripcion_tm + "]";
    }
}

