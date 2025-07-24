package negocioImpl;

import dao.CuentaDAO;
import daoImpl.CuentaDAOIMpl;
import entidad.Cuentas;
import negocio.CuentaNegocio;

import java.util.ArrayList;
import java.util.List;

public class CuentasNegocioImpl implements CuentaNegocio {

	private CuentaDAO cuentaDAO = new CuentaDAOIMpl();
	
	public CuentasNegocioImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean insertar(Cuentas cuenta, String cliente) {
        if (cuenta == null) return false;

        return cuentaDAO.ejecutarSPcrearCuenta(cuenta,cliente);
    }
	
	public ArrayList<Cuentas> obtenerTodasLasCuentas() {
        return cuentaDAO.readAll();
    }
	
	@Override
	public List<Cuentas> obtenerCuentasFiltradas(String tipoCuenta, String cliente, String cbu) {
	    return cuentaDAO.readFiltered(tipoCuenta, cliente, cbu);
	}
	  @Override
	    public boolean delete(Cuentas cuenta) {
	        boolean estado = false;
	        try {
	            // 1. Validar que el CBU para eliminar no sea nulo o vacío.
	            // No llamamos a cuenta.validar() completo aquí, ya que solo necesitamos el CBU para la eliminación.
	            if (cuenta == null || cuenta.getCBU_cu() == null || cuenta.getCBU_cu().trim().isEmpty()) {
	                System.err.println("Error de validación: El CBU de la cuenta a eliminar no puede ser nulo o vacío.");
	                return false;
	            }

	            // Opcional: Validar que la cuenta a eliminar realmente exista.
	            // if (getByCBU(cuenta.getCBU_cu()) == null) {
	            //     System.err.println("Error de negocio: La cuenta con CBU '" + cuenta.getCBU_cu() + "' no existe para eliminar.");
	            //     return false;
	            // }

	            // 2. Si las validaciones pasan, procede con la operación DAO.
	            estado = cuentaDAO.ejecutarSPeliminarCuenta(cuenta);
	            System.out.println("Estado del delete en DAO (Cuenta): " + estado); // Para depuración

	        } catch (Exception e) {
	            System.err.println("Error inesperado en la capa de negocio/DAO al eliminar la cuenta: " + e.getMessage());
	            e.printStackTrace();
	            estado = false;
	        }
	        return estado;
	    }

	 public Cuentas getByCBU(String dni) {
	        List<Cuentas> lista = cuentaDAO.readAll();
	        for (Cuentas c : lista) {
	            if (c.getCBU_cu().equals(dni)) {
	                return c;
	            }
	        }
	        return null;
	    }
	 
	 public boolean modificar(Cuentas cuentas) {
	        if (cuentas == null || cuentas.getCBU_cu() == null || cuentas.getCBU_cu().isEmpty()) return false;
	        return cuentaDAO.ejecutarSPmodificarCuenta(cuentas);
	    }
	 @Override
	 public ArrayList<Cuentas> getByDNI(String dni) {
		 return cuentaDAO.readByClient(dni);
	 }
}
