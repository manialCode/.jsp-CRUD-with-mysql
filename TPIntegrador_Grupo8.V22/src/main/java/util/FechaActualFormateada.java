package util;
import java.time.LocalDate;
import java.time.ZoneId; // Necesario para especificar la zona horaria en la conversión
import java.util.Date;   // La clase que queremos devolver

public class FechaActualFormateada {

    public static Date obtenerFechaActualComoDate() {
        // 1. Obtener la fecha actual usando la API moderna (LocalDate)
        LocalDate fechaActual = LocalDate.now();

        // 2. Convertir LocalDate a un Instant
        // Para convertir una LocalDate a un Instant (que es un punto en el tiempo),
        // necesitamos especificar una zona horaria.
        // Utilizaremos ZoneId.systemDefault() para la zona horaria por defecto del sistema.
        // atStartOfDay() convierte LocalDate a LocalDateTime al inicio del día (00:00:00).
        // atZone() añade la información de zona horaria para obtener un ZonedDateTime.
        // toInstant() convierte ZonedDateTime a un Instant.
        Date fechaUtilDate = Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return fechaUtilDate;
    }
}