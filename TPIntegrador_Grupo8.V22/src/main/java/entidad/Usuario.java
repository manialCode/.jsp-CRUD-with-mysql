package entidad;

public class Usuario {
    private int id_us;
    private String usuario_us;
    private String contrasena_us;
    private int idTipo_us;

    public Usuario() {}
    
    public Usuario(int id_us, String usuario_us, String contrasena_us, int idTipo_us) {
        this.id_us = id_us;
        this.usuario_us = usuario_us;
        this.contrasena_us = contrasena_us;
        this.idTipo_us = idTipo_us;
    }	

    public int getId_us() {
        return id_us;
    }

    public void setId_us(int id_us) {
        this.id_us = id_us;
    }

    public String getUsuario_us() {
        return usuario_us;
    }

    public void setUsuario_us(String usuario_us) {
        this.usuario_us = usuario_us;
    }

    public String getContrasena_us() {
        return contrasena_us;
    }

    public void setContrasena_us(String contrasena_us) {
        this.contrasena_us = contrasena_us;
    }

    public int getIdTipo_us() {
        return idTipo_us;
    }

    public void setIdTipo_us(int idTipo_us) {
        this.idTipo_us = idTipo_us;
    }

    @Override
    public String toString() {
        return "Usuario [id_us=" + id_us + ", usuario_us=" + usuario_us + ", contrasena_us=" + contrasena_us + ", idTipo_us=" + idTipo_us + "]";
    }
}

