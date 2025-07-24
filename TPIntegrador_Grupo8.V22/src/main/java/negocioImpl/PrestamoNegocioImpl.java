package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import dao.PrestamoDAO;
import daoImpl.PrestamoDAOImpl;
import entidad.Prestamos;
import exceptions.PrestamoInvalidoException;
import negocio.PrestamoNegocio;
import util.TiposDeUsuario;

public class PrestamoNegocioImpl implements PrestamoNegocio {

	private PrestamoDAO prestamoDAO = new PrestamoDAOImpl();

	public PrestamoNegocioImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean insert(Prestamos prestamo) {
		boolean estado = false;
		try {
			System.out.println(prestamo.toString());
			prestamo.validar();
			estado = prestamoDAO.ejecutarSPcrearPrestamo(prestamo);
			System.out.println("Estado del insert: " + estado );
		} catch (PrestamoInvalidoException e) {
			System.err.println("Errores al validar el préstamo:");
			for (String error : e.getErrores()) {
				System.err.println("- " + error);
			}
			estado = false;
		} catch (Exception e) {
			e.getMessage();
			estado = false;
		}
		return estado;
	}

	@Override
	public boolean modify(Prestamos prestamo) {
		try {
			prestamo.validar();
			prestamoDAO.ejecutarSPmodificarPrestamo(prestamo);
			return false;
		} catch (PrestamoInvalidoException e) {
			System.err.println("Errores al validar el préstamo:");
			for (String error : e.getErrores()) {
				System.err.println("- " + error);
			}
			return false; // Fallo en la validación
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}

	@Override
	public Prestamos getByID(int id) {
		List<Prestamos> prestamos = new ArrayList<>();
		prestamos = prestamoDAO.obtenerTodos();
		Prestamos prestamo = null;
		for (Prestamos pres : prestamos) {
			if (pres.getId_pre() == id)
				prestamo = pres;
		}
		return prestamo;
	}

	@Override
	public ArrayList<Prestamos> getByClient(String dni) {
		ArrayList<Prestamos> prestamos = new ArrayList<>();
		ArrayList<Prestamos> deudasClientes = new ArrayList<>();
		prestamos = prestamoDAO.obtenerTodos();

		for (Prestamos pres : prestamos) {
			if (pres.getDni_pre().equals(dni))
				deudasClientes.add(pres);
		}

		return deudasClientes;
	}

	@Override
	public ArrayList<Prestamos> getAll(int tipoDeUsuario) {
		ArrayList<Prestamos> prestamos = null;
		if(tipoDeUsuario == TiposDeUsuario.USUARIO_BANCO.getTipo()) prestamos = prestamoDAO.obtenerTodos();
		return prestamos;
	}

	@Override
	public boolean aceptarPrestamo(int id, int tipoDeUsuario) {
		boolean estado = false;
		if(tipoDeUsuario == TiposDeUsuario.USUARIO_BANCO.getTipo()) {
			prestamoDAO.ejecutarSPaceptarPrestamo(id);
			estado = true;
		}
		return estado;
	}

	@Override
	public boolean rechazarPrestamo(int id, int tipoDeUsuario) {
		boolean estado = false;
		if(tipoDeUsuario == TiposDeUsuario.USUARIO_BANCO.getTipo()) {
			prestamoDAO.ejecutarSPrechazarPrestamo(id);
			estado = true;
		}
		return estado;	}
	
	
	@Override
	public ArrayList<Prestamos> getByFiltros(String filtroFecha, String filtroApellido, String filtroInteres) {
	    return prestamoDAO.obtenerPorFiltros(filtroFecha, filtroApellido, filtroInteres);
	}
	
	public ArrayList<Prestamos> getByEstado(String estado) {
	    return prestamoDAO.obtenerPorEstado(estado);
	}

}
