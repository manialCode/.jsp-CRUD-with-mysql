package negocio;

import java.util.ArrayList;

import entidad.Movimientos;

public interface MovimientoNegocio {

	public boolean insert(Movimientos movimiento);

	public ArrayList<Movimientos> getAll();

	public ArrayList<Movimientos> getByClient(String DNI);
	
	public ArrayList<Movimientos> getByAccount(String CBU);

	public ArrayList<Movimientos> getByDate(String date);
}
