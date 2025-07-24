package util;

public enum TipoMovimiento {
    ALTA_CUENTA(1, "Alta cuenta"),
    ALTA_PRESTAMO(2, "Alta préstamo"),
    PAGO_PRESTAMO(3, "Pago préstamo"),
    TRANSFERENCIA(4, "Transferencia");

    private final int id;
    private final String descripcion;

    TipoMovimiento(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoMovimiento fromId(int id) {
        for (TipoMovimiento tm : TipoMovimiento.values()) {
            if (tm.id == id) {
                return tm;
            }
        }
        return null;
    }

    public static TipoMovimiento fromString(String text) {
        if (text == null) {
            return null;
        }
        for (TipoMovimiento tm : TipoMovimiento.values()) {
            if (tm.descripcion.equalsIgnoreCase(text)) {
                return tm;
            }
        }
        return null;
    }
}