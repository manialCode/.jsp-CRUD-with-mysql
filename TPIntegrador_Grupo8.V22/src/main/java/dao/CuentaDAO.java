package dao;

import java.util.ArrayList;
import java.util.List;

import entidad.Cuentas;

public interface CuentaDAO {
	public boolean ejecutarSPcrearCuenta (Cuentas cuenta, String cliente);
	public boolean ejecutarSPmodificarCuenta (Cuentas cuenta);
	public boolean ejecutarSPeliminarCuenta (Cuentas cuenta);
	public ArrayList<Cuentas> readByClient(String dniCli);
	public ArrayList<Cuentas> readAll();
	List<Cuentas> readFiltered(String tipoCuenta, String cliente, String cbu);
}
