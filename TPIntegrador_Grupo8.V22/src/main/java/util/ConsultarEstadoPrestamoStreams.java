package util;

import java.util.List;

import entidad.Prestamos;

public class ConsultarEstadoPrestamoStreams {

	public static boolean tienePrestamoPendienteStream(List<Prestamos> listaPrestamos) {
		if (listaPrestamos == null || listaPrestamos.isEmpty()) {
			return false;
		}

		return listaPrestamos.stream().anyMatch(
				prestamo -> prestamo.getEstado_pre() != null && prestamo.getEstado_pre().equalsIgnoreCase("PENDIENTE"));
	}
}
