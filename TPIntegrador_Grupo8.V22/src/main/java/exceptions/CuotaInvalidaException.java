package exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuotaInvalidaException extends Exception {
    private static final long serialVersionUID = 1L;
    private final List<String> errores;

    /**
     * Constructor para cuando hay múltiples errores de validación.
     * @param errores Lista de strings con los mensajes de error específicos.
     */
    public CuotaInvalidaException(List<String> errores) {
        super("Uno o más campos de la cuota son inválidos.");
        this.errores = Collections.unmodifiableList(new ArrayList<>(errores));
    }

    /**
     * Constructor para un único mensaje de error.
     * @param mensaje El mensaje de error específico.
     */
    public CuotaInvalidaException(String mensaje) {
        super(mensaje);
        this.errores = Collections.unmodifiableList(Collections.singletonList(mensaje));
    }

    /**
     * Obtiene la lista de errores detallados.
     * @return Una lista inmutable de mensajes de error.
     */
    public List<String> getErrores() {
        return errores;
    }
}