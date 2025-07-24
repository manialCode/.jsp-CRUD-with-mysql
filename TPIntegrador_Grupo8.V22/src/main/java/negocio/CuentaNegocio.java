package negocio;

import entidad.Cuentas;

import java.util.ArrayList;
import java.util.List;

public interface CuentaNegocio {
	///public boolean generarCuotasParaPrestamo(int idPrestamo);
	//public boolean obtenerCuotasPorPrestamo(int idPrestamo);
	//public boolean actualizarCuotaPagada(int cuotaId);
	
	public boolean insertar(Cuentas cuenta, String cliente);
	
	ArrayList<Cuentas> obtenerTodasLasCuentas();
	List<Cuentas> obtenerCuentasFiltradas(String tipoCuenta, String cliente, String cbu);
	public boolean delete(Cuentas cuenta_a_eliminar);
	
	public Cuentas getByCBU(String cbu);
	public ArrayList<Cuentas> getByDNI(String dni);
	
	public boolean modificar(Cuentas cuentas); 
}
