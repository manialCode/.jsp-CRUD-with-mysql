package negocio;

import java.util.ArrayList;
import java.util.List;

import entidad.Clientes;
import entidad.Localidades;
import entidad.Provincias;

public interface ClienteNegocio {

	public boolean insertar(Clientes cliente);
    public boolean delete(Clientes cliente_a_eliminar);
    public boolean modificar(Clientes cliente);

    public boolean existeCBU(String CBU);
    public boolean existeDni(String dni);
    public ArrayList<Clientes> getAll();
    public ArrayList<Provincias> getProvincias();
    public ArrayList<Localidades> getLocalidades();
    public Clientes getByDNI(String dni);
	public List<Clientes> getClientesFiltrados(String filtroDni, String filtroApellido, String filtroCorreo);
	public List<Clientes> obtenerClientesFiltradas(String dni, String apellido, String correo);
	public Clientes obtenerClientePorIdUsuario(int idUsuario);
	ArrayList<Clientes> obtenerTodosLosClientesActivos();
	
}
