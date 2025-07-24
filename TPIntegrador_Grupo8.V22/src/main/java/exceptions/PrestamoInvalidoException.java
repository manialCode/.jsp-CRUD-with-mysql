package exceptions;

import java.util.ArrayList;
import java.util.Collections; // Para Collections.unmodifiableList
import java.util.List;

public class PrestamoInvalidoException extends Exception {
    private static final long serialVersionUID = 1L; 
    private final List<String> errores;

    /**
     * Constructor para cuando hay múltiples errores de validación.
     * @param errores Lista de strings con los mensajes de error específicos.
     */
    public PrestamoInvalidoException(List<String> errores) {
        // Llama al constructor de la clase padre (Exception)
        // Puedes pasar un mensaje general aquí o dejarlo en null
        super("Uno o más campos del préstamo son inválidos."); 
        this.errores = Collections.unmodifiableList(new ArrayList<>(errores)); // Hace la lista inmutable
    }

    /**
     * Constructor para un único mensaje de error.
     * @param mensaje El mensaje de error específico.
     */
    public PrestamoInvalidoException(String mensaje) {
        super(mensaje);
        this.errores = Collections.unmodifiableList(Collections.singletonList(mensaje)); // Lista inmutable con un solo elemento
    }

    /**
     * Obtiene la lista de errores detallados.
     * @return Una lista inmutable de mensajes de error.
     */
    public List<String> getErrores() {
        return errores;
    }
}