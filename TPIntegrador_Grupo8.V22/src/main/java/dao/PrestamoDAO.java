package dao;

import java.util.ArrayList;
import entidad.Prestamos;

public interface PrestamoDAO {

	public boolean ejecutarSPcrearPrestamo(Prestamos prestamo);
	public boolean ejecutarSPmodificarPrestamo(Prestamos prestamo);
	public boolean ejecutarSPeliminarPrestamo(int idPrestamo);
	public boolean ejecutarSPaceptarPrestamo(int idPrestamo);
	public boolean ejecutarSPrechazarPrestamo(int idPrestamo);
	public ArrayList<Prestamos> obtenerPrestamosPorDni(String dni);
	public ArrayList<Prestamos> obtenerTodos();
	
	
	ArrayList<Prestamos> obtenerPorFiltros(String filtroFecha, String filtroApellido, String filtroInteres);
	ArrayList<Prestamos> obtenerPorEstado(String estado);

}
