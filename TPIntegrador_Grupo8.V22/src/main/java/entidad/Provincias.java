package entidad;

public class Provincias {
    private int id_prov;
    private String descripcion_prov;

    public Provincias() {}
    
    public Provincias(int id_prov, String descripcion_prov) {
        this.id_prov = id_prov;
        this.descripcion_prov = descripcion_prov;
    }
    	
    public int getId_prov() {
        return id_prov;
    }

    public void setId_prov(int id_prov) {
        this.id_prov = id_prov;
    }

    public String getDescripcion_prov() {
        return descripcion_prov;
    }

    public void setDescripcion_prov(String descripcion_prov) {
        this.descripcion_prov = descripcion_prov;
    }

    @Override
    public String toString() {
        return "Provincias [id_prov=" + id_prov + ", descripcion_prov=" + descripcion_prov + "]";
    }
}

