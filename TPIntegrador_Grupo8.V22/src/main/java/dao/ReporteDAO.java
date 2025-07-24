package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import entidad.Clientes;
import entidad.Cuentas;
import entidad.Movimientos;
import entidad.Prestamos;

public interface ReporteDAO {
	public ArrayList<String> ejecutarSPreportes5();
	public ArrayList<String> ejecutarSPreportes4();
	public ArrayList<String> ejecutarSPreportes3();
	public ArrayList<Prestamos> ejecutarSPreportes2(Clientes cliente, String apellido, int idTipo);
	public ArrayList<Movimientos> ejecutarSPreportes1(Date fechaInicio,Date fechaFin);
}
