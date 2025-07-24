package util;

public enum RutaPagina {
    
    // Admin
    CLIENTES("Admin/Clientes/ABML_Clientes.jsp"),
    CUENTAS("Admin/Cuentas/ABML_Cuentas.jsp"),
    PRESTAMOS_ADMIN("Admin/Prestamos/PrestamosAdministrador.jsp"),
    REPORTES("Admin/Reportes/ReportesEstadisticas.jsp"),

    // Cliente
    INICIO("Cliente/Info/InfoUsuario.jsp"),
    PRESTAMOS_CLIENTE("Cliente/Prestamos/PrestamosClientes.jsp"),
    TRANSFERENCIAS("Cliente/Transferencias/TransferenciasClientes.jsp"),
    MOVIMIENTOS("Cliente/Movimientos/MovimientosClientes.jsp"),

    // General
    LOGIN("Login.jsp");

    private final String ruta;

    RutaPagina(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
