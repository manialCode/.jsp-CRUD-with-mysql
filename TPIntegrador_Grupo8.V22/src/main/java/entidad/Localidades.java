package entidad;

public class Localidades {
    private int id_loc;
    private int idPro_loc;
    private String descripcion_loc;

    public Localidades() {}
    
    public Localidades(int id_loc, int idPro_loc, String descripcion_loc) {
        this.id_loc = id_loc;
        this.idPro_loc = idPro_loc;
        this.descripcion_loc = descripcion_loc;
    }

    public int getId_loc() {
        return id_loc;
    }

    public void setId_loc(int id_loc) {
        this.id_loc = id_loc;
    }

    public int getIdPro_loc() {
        return idPro_loc;
    }

    public void setIdPro_loc(int idPro_loc) {
        this.idPro_loc = idPro_loc;
    }

    public String getDescripcion_loc() {
        return descripcion_loc;
    }

    public void setDescripcion_loc(String descripcion_loc) {
        this.descripcion_loc = descripcion_loc;
    }

    @Override
    public String toString() {
        return "Localidades [id_loc=" + id_loc + ", idPro_loc=" + idPro_loc + ", descripcion_loc=" + descripcion_loc + "]";
    }
}

