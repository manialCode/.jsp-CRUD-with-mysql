package negocioImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAO;
import daoImpl.ClienteDAOImpl;
import daoImpl.ReporteDAOImpl;
import entidad.Clientes;
import entidad.Localidades;
import entidad.Movimientos;
import entidad.Prestamos;
import entidad.Provincias;
import negocio.ClienteNegocio;
import negocio.ReporteNegocio;

public class ReporteNegocioImpl implements ReporteNegocio {

	public ReporteDAOImpl reporteDAO = new ReporteDAOImpl();
	@Override
	public ArrayList<Prestamos> ejecutarSPreportes2(Clientes cliente, String apellido, int idTipo) {
		return reporteDAO.ejecutarSPreportes2(cliente, apellido.toLowerCase(),idTipo);
	}

	@Override
	public ArrayList<Movimientos> ejecutarSPreportes1(Date fechaInicio, Date fechaFin) {
		return reporteDAO.ejecutarSPreportes1(fechaInicio, fechaFin);
	}

	@Override
    public ArrayList<String> ejecutarSPreportes3() {
        return reporteDAO.ejecutarSPreportes3();
    }

	@Override
	public ArrayList<String> ejecutarSPreportes4() {
		return reporteDAO.ejecutarSPreportes4();
	    }
	@Override
	public ArrayList<String> ejecutarSPreportes5() {
		return reporteDAO.ejecutarSPreportes5();
	}

}
