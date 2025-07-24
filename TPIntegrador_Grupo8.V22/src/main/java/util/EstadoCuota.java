package util;

public enum EstadoCuota {
    PENDIENTE(0, "Pendiente"), // O VENCIDA, no pagada, etc.
    PAGADA(1, "Pagada");

    private final int id;
    private final String descripcion;

    EstadoCuota(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoCuota fromId(int id) {
        for (EstadoCuota ec : EstadoCuota.values()) {
            if (ec.id == id) {
                return ec;
            }
        }
        return null;
    }
}