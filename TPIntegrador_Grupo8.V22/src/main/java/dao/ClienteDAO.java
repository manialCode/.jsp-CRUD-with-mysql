package dao;

import java.util.ArrayList;
import java.util.List;

import entidad.Clientes;
import entidad.Localidades;
import entidad.Provincias;

public interface ClienteDAO {
	
	public boolean ejecutarSPcrearCliente (Clientes cliente);
	public boolean ejecutarSPmodificarCliente (Clientes cliente);
	public boolean ejecutarSPeliminarCliente (Clientes cliente);
	public ArrayList<Clientes> readAllClients();
	public ArrayList<Provincias> getProvincias();
	public ArrayList<Localidades> getLocalidades();
	public List<Clientes> readFiltered(String dni, String apellido, String correo);
	public Clientes obtenerClientePorIdUsuario(int idUsuario);	
	public ArrayList<Clientes> cargarClientesDDL();

}
