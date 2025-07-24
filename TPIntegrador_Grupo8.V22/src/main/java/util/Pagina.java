package util;

public enum Pagina {
    INICIO("inicio"),
    PRESTAMOS("prestamos"),
    TRANSFERENCIAS("transferencias"),
    MOVIMIENTOS("movimientos"),
    CLIENTES("clientes"),
    CUENTAS("cuentas"),
    REPORTES("reportes");

    private final String valor;

    Pagina(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static Pagina from(String valor) {
        for (Pagina p : Pagina.values()) {
            if (p.getValor().equalsIgnoreCase(valor)) {
                return p;
            }
        }
        return null;
    }
}
