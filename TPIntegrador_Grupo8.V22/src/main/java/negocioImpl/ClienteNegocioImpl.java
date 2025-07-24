package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAO;
import daoImpl.ClienteDAOImpl;
import entidad.Clientes;
import entidad.Localidades;
import entidad.Provincias;
import negocio.ClienteNegocio;

public class ClienteNegocioImpl implements ClienteNegocio {

    private ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    public boolean insertar(Clientes cliente) {
        if (cliente == null || cliente.getDni_cli() == null || cliente.getDni_cli().isEmpty()) return false;

        if (existeDni(cliente.getDni_cli())) {
            return false;
        }

        return clienteDAO.ejecutarSPcrearCliente(cliente);
    }

    @Override
    public boolean modificar(Clientes cliente) {
        if (cliente == null || cliente.getDni_cli() == null || cliente.getDni_cli().isEmpty()) return false;

        return clienteDAO.ejecutarSPmodificarCliente(cliente);
    }

    @Override
    public boolean delete(Clientes cliente) {
        if (cliente == null || cliente.getDni_cli() == null || cliente.getDni_cli().isEmpty()) return false;

        return clienteDAO.ejecutarSPeliminarCliente(cliente);
    }
    
    @Override
    public ArrayList<Clientes> getAll() {
        return clienteDAO.readAllClients();
    }
    
    public ArrayList<Provincias> getProvincias() {
        return clienteDAO.getProvincias();
    }
    
    public ArrayList<Localidades> getLocalidades() {
        return clienteDAO.getLocalidades();
    }

    @Override
    public boolean existeDni(String dni) {
        ArrayList<Clientes> lista = clienteDAO.readAllClients();
        for (Clientes c : lista) {
            if (c.getDni_cli().equals(dni)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existeCBU(String cbu) {
        // Lógica para verificar existencia de CBU (opcional según diseño)
        return false;
    }


    @Override
    public Clientes getByDNI(String dni) {
        List<Clientes> lista = clienteDAO.readAllClients();
        for (Clientes c : lista) {
            if (c.getDni_cli().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Clientes> getClientesFiltrados(String dni, String apellido, String correo) {
        List<Clientes> todos = clienteDAO.readAllClients();
        List<Clientes> filtrados = new ArrayList<>();

        for (Clientes c : todos) {
            boolean coincide = true;

            if (dni != null && !dni.isEmpty() && !c.getDni_cli().toLowerCase().contains(dni.toLowerCase())) coincide = false;
            if (apellido != null && !apellido.isEmpty() && !c.getApellido_cli().toLowerCase().contains(apellido.toLowerCase())) coincide = false;
            if (correo != null && !correo.isEmpty() && !c.getCorreo_cli().toLowerCase().contains(correo.toLowerCase())) coincide = false;

            if (coincide) filtrados.add(c);
        }

        return filtrados;
    }

	@Override
	public List<Clientes> obtenerClientesFiltradas(String dni, String apellido, String correo) {
		return clienteDAO.readFiltered(dni, apellido, correo);
	}
	
	@Override
    public ArrayList<Clientes> obtenerTodosLosClientesActivos() {
        return clienteDAO.cargarClientesDDL();
    }
	
	public Clientes obtenerClientePorIdUsuario(int idUsuario) {
	    return clienteDAO.obtenerClientePorIdUsuario(idUsuario);
	}
    
    


}
