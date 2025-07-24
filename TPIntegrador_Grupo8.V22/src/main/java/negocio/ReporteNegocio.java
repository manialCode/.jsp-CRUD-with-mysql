package negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import entidad.Clientes;
import entidad.Localidades;
import entidad.Movimientos;
import entidad.Prestamos;
import entidad.Provincias;

public interface ReporteNegocio {
	ArrayList<String> ejecutarSPreportes5();
	ArrayList<String> ejecutarSPreportes4();
	ArrayList<String> ejecutarSPreportes3();
	ArrayList<Prestamos> ejecutarSPreportes2(Clientes cliente, String apellido, int idTipo);
	ArrayList<Movimientos> ejecutarSPreportes1(Date fechaInicio, Date fechaFin);
}
