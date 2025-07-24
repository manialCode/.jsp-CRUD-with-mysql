package exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuentaInvalidaException extends Exception {
    private static final long serialVersionUID = 1L;
    private final List<String> errores;

    public CuentaInvalidaException(List<String> errores) {
        super("Uno o más campos de la cuenta son inválidos.");
        this.errores = Collections.unmodifiableList(new ArrayList<>(errores));
    }

    public CuentaInvalidaException(String mensaje) {
        super(mensaje);
        this.errores = Collections.unmodifiableList(Collections.singletonList(mensaje));
    }

    public List<String> getErrores() {
        return errores;
    }
}