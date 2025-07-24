package entidad;

public class TiposUsuarios {
    private int id_tu;
    private String descripcion_tu;

    public TiposUsuarios() {}
    
    public TiposUsuarios(int id_tu, String descripcion_tu) {
        this.id_tu = id_tu;
        this.descripcion_tu = descripcion_tu;
    }

    public int getId_tu() {
        return id_tu;
    }

    public void setId_tu(int id_tu) {
        this.id_tu = id_tu;
    }

    public String getDescripcion_tu() {
        return descripcion_tu;
    }

    public void setDescripcion_tu(String descripcion_tu) {
        this.descripcion_tu = descripcion_tu;
    }

    @Override
    public String toString() {
        return "TiposUsuarios [id_tu=" + id_tu + ", descripcion_tu=" + descripcion_tu + "]";
    }
}

