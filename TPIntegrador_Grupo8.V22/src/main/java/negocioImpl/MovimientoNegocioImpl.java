package negocioImpl;

import java.util.ArrayList;

import dao.MovimientoDAO;
import daoImpl.MovimientoDAOimpl;
import entidad.Movimientos;
import exceptions.MovimientoInvalidoException;
import negocio.ClienteNegocio;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio {
	
	private MovimientoDAO md = new MovimientoDAOimpl();
	private ClienteNegocio cN = new ClienteNegocioImpl();

	public MovimientoNegocioImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean insert(Movimientos movimiento) {
		boolean estado = true;
		try {
			movimiento.validar();
			estado = md.insert(movimiento);
			System.out.println("Estado del insert: " + estado );
		} catch(MovimientoInvalidoException e) {
			System.err.println("Errores al validar el préstamo:");
			for (String error : e.getErrores()) {
				System.err.println("- " + error);
			}
			estado = false;			
		}catch (Exception e) {
			e.getMessage();
			estado = false;
		}
		return estado;
	}

	@Override
	public ArrayList<Movimientos> getAll() {
		return  md.readAll();
	}

	@Override
	public ArrayList<Movimientos> getByAccount(String CBU) {
		return md.readByCBU(CBU);
	}

	@Override
	public ArrayList<Movimientos> getByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Movimientos> getByClient(String DNI) {
		ArrayList<Movimientos> lista = null;
		if(cN.getByDNI(DNI) != null) lista = md.readByClient(DNI);
		return lista;
	}

}
