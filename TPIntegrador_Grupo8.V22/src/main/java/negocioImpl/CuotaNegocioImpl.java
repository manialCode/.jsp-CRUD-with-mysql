package negocioImpl;

import java.math.BigDecimal;
import java.util.ArrayList;

import negocio.CuotaNegocio;
import entidad.Cuentas;
import entidad.Cuotas;
import dao.CuotaDAO;
import daoImpl.CuotaDAOImpl;

public class CuotaNegocioImpl implements CuotaNegocio {

	private CuotaDAO cuotaDAO = new CuotaDAOImpl();
	
	public ArrayList<Cuotas> getAll() {
        return cuotaDAO.readAllCuo();
    }
	
	@Override
	 public ArrayList<Cuotas> getByDNI(String dni) {
		 return cuotaDAO.readByClient(dni);
	 }
	
	@Override
	public boolean pagarCuotas(String listaCuotasCSV, String cbuCuenta) {
	    return cuotaDAO.pagarCuotas(listaCuotasCSV, cbuCuenta);
	}
	
}
