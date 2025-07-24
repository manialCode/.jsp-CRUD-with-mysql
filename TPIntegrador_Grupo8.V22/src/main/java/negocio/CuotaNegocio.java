package negocio;

import java.math.BigDecimal;
import java.util.ArrayList;

import entidad.Cuentas;
import entidad.Cuotas;

public interface CuotaNegocio {
	
	public ArrayList<Cuotas> getAll();
	public ArrayList<Cuotas> getByDNI(String dni);
	
	boolean pagarCuotas(String listaCuotasCSV, String cbuCuenta);
}
