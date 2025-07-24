package negocio;

import java.util.List;

import entidad.Cuentas;

public interface CuentasNegocio {
	
	public boolean insert(Cuentas cuenta );
	public boolean modificar(Cuentas cuenta);
	public boolean existeCBU(String CBU);
	public boolean delete();
	public List<Cuentas> getAll();
	public Cuentas getByCBU(String CBU);

}
