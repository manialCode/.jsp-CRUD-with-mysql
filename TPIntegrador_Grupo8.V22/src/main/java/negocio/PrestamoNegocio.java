package negocio;

import java.util.ArrayList;
import entidad.Prestamos;

public interface PrestamoNegocio {

	public boolean insert(Prestamos prestamo);
	public boolean modify(Prestamos prestamo);	
	public Prestamos getByID(int id);
	public ArrayList<Prestamos> getByClient(String dni);	
	public ArrayList<Prestamos> getAll(int tipoDeUsuario);
	boolean aceptarPrestamo(int id, int tipoDeUsuario);
	public boolean rechazarPrestamo (int id, int tipoDeUsuario);
	
	ArrayList<Prestamos> getByFiltros(String filtroFecha, String filtroApellido, String filtroInteres);
	ArrayList<Prestamos> getByEstado(String estado);

}
