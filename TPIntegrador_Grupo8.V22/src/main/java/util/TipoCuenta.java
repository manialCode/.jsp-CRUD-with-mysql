package util;

public enum TipoCuenta {
    CAJA_AHORRO(1, "Caja de Ahorro"),
    CUENTA_CORRIENTE(2, "Cuenta Corriente");

    private final int id;
    private final String descripcion;

    TipoCuenta(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoCuenta fromId(int id) {
        for (TipoCuenta tc : TipoCuenta.values()) {
            if (tc.id == id) {
                return tc;
            }
        }
        return null;
    }

    public static TipoCuenta fromString(String text) {
        if (text == null) {
            return null;
        }
        for (TipoCuenta tc : TipoCuenta.values()) {
            if (tc.descripcion.equalsIgnoreCase(text)) {
                return tc;
            }
        }
        return null;
    }
}