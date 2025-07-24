package util; 

public enum EstadoPrestamo {
    PENDIENTE("Pendiente"),
    APROBADO("Aprobado"),
    PAGADO("Pago"),
    RECHAZADO("Rechazado");
    private final String descripcion;

    EstadoPrestamo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método de utilidad para obtener un EstadoPrestamo a partir de su descripción.
     * Útil cuando lees el estado de la base de datos como String.
     * @param text La descripción del estado (ej. "Pendiente").
     * @return El enum EstadoPrestamo correspondiente o null si no se encuentra.
     */
    public static EstadoPrestamo fromString(String text) {
        if (text == null) {
            return null;
        }
        for (EstadoPrestamo b : EstadoPrestamo.values()) {
            if (b.descripcion.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null; 
    }
}