package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidad.Clientes;
import entidad.Localidades;
import entidad.Provincias;
import entidad.Usuario;
import dao.ClienteDAO;
import dao.Conexion;

public class ClienteDAOImpl implements ClienteDAO {

	@Override
	public boolean ejecutarSPcrearCliente(Clientes cliente) {
	    if (cliente == null || cliente.getUsuario() == null) return false;

	    boolean estado = false;

	    String sql = "CALL crearCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection cn = Conexion.obtenerConexion();
	         CallableStatement cst = cn.prepareCall(sql)) {

	        cst.setString(1, cliente.getUsuario().getUsuario_us());
	        cst.setString(2, cliente.getUsuario().getContrasena_us());
	        cst.setString(3, cliente.getDni_cli());
	        cst.setString(4, cliente.getNombre_cli());
	        cst.setString(5, cliente.getApellido_cli());
	        cst.setInt(6, cliente.getSexo_cli());
	        cst.setString(7, cliente.getCuil_cli());
	        cst.setString(8, cliente.getCorreo_cli());
	        if (cliente.getFecha_nacimiento_cli() != null) {
	            cst.setDate(9, new java.sql.Date(cliente.getFecha_nacimiento_cli().getTime()));
	        } else {
	            cst.setNull(9, java.sql.Types.DATE);
	        }

	        cst.setString(10, cliente.getDireccion_cli());
	        cst.setInt(11, cliente.getIdLocalidad_cli());
	        cst.setInt(12, cliente.getIdProvincia_cli());
	        cst.setString(13, cliente.getTelefono_cli());

	        cst.execute();
	        estado = true;

	    } catch (Exception e) {
	        System.err.println("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
	    }

	    return estado;
	}

	@Override
	public boolean ejecutarSPmodificarCliente(Clientes cliente) {
		boolean estado = false;
	    Connection cn = null;
	    CallableStatement cst = null;
	    System.out.println("ESTOY EN DAO");
	    try {
	        cn = Conexion.obtenerConexion();
	        cst = cn.prepareCall("{CALL modificarCliente(?,?,?,?,?,?,?,?,?,?)}");
	        
	        cst.setString(1, cliente.getUsuario().getContrasena_us());
	        cst.setString(2, cliente.getDni_cli());
	        cst.setString(3, cliente.getNombre_cli());
	        cst.setString(4, cliente.getApellido_cli());
	        cst.setDate(5, new java.sql.Date(cliente.getFecha_nacimiento_cli().getTime()));
	        cst.setString(6, cliente.getDireccion_cli());
	        cst.setInt(7, cliente.getIdProvincia_cli());
	        cst.setInt(8, cliente.getIdLocalidad_cli());
	        cst.setString(9, cliente.getCorreo_cli());
	        cst.setString(10, cliente.getTelefono_cli());
	        
	        int filas = cst.executeUpdate();
	        estado = (filas > 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (cst != null) cst.close();
	            if (cn != null) cn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return estado;
	}

	@Override
	public boolean ejecutarSPeliminarCliente(Clientes cliente) {
	    if (cliente == null || cliente.getDni_cli() == null || cliente.getDni_cli().isEmpty()) return false;

	    boolean estado = false;
	    Connection cn = null;
	    CallableStatement cst = null;

	    try {
	        cn = Conexion.obtenerConexion();
	        cst = cn.prepareCall("CALL eliminarCliente(?)");
	        cst.setString(1, cliente.getDni_cli());

	        int filasAfectadas = cst.executeUpdate();

	        if (filasAfectadas > 0) {
	            estado = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (cst != null) cst.close();
	            if (cn != null) cn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return estado;
	}


	@Override
	public ArrayList<Provincias> getProvincias() {
		 ArrayList<Provincias> ProvinciasList = new ArrayList<>();
		    PreparedStatement statement;
		    ResultSet res;

		    try {
		        Connection conn = Conexion.obtenerConexion();
		        String query = "SELECT * FROM provincias"; 		        
		        statement = conn.prepareStatement(query);
		        res = statement.executeQuery();

		        while (res.next()) {
		        	ProvinciasList.add(getProvincia(res));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return ProvinciasList;
	}
	
	public ArrayList<Localidades> getLocalidades() {
		 ArrayList<Localidades> LocalidadesList = new ArrayList<>();
		    PreparedStatement statement;
		    ResultSet res;

		    try {
		        Connection conn = Conexion.obtenerConexion();
		        String query = "SELECT * FROM localidades"; 		        
		        statement = conn.prepareStatement(query);
		        res = statement.executeQuery();

		        while (res.next()) {
		        	LocalidadesList.add(getLocalidad(res));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return LocalidadesList;
	}
	
	public ArrayList<Clientes> readAllClients() {
		 ArrayList<Clientes> clientesList = new ArrayList<>();
		    PreparedStatement statement;
		    ResultSet res;

		    try {
		        Connection conn = Conexion.obtenerConexion();
		        String query = "SELECT c.*, u.usuario_us, u.contrasena_us FROM clientes c JOIN usuario u ON c.idUsuario_cli = u.id_us WHERE estado_cli = 1"; 		        
		        statement = conn.prepareStatement(query);
		        res = statement.executeQuery();

		        while (res.next()) {
		            clientesList.add(getCliente(res));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return clientesList;
	}
	
	
	private Clientes getCliente(ResultSet resultSet) throws SQLException {
	    Clientes cliente = new Clientes();

	    cliente.setDni_cli(resultSet.getString("dni_cli"));
	    cliente.setCuil_cli(resultSet.getString("cuil_cli"));
	    cliente.setNombre_cli(resultSet.getString("nombre_cli"));
	    cliente.setApellido_cli(resultSet.getString("apellido_cli"));
	    cliente.setSexo_cli(resultSet.getInt("sexo_cli"));
	    cliente.setFecha_nacimiento_cli(resultSet.getDate("fecha_nacimiento_cli"));
	    cliente.setDireccion_cli(resultSet.getString("direccion_cli"));
	    cliente.setIdProvincia_cli(resultSet.getInt("idProvincia_cli"));
	    cliente.setIdLocalidad_cli(resultSet.getInt("idLocalidad_cli"));
	    cliente.setCorreo_cli(resultSet.getString("correo_cli"));
	    cliente.setTelefono_cli(resultSet.getString("telefono_cli"));
	    cliente.setIdUsuario_cli(resultSet.getInt("idUsuario_cli"));
	    //cliente.setEstado_cli(resultSet.getBoolean("estado_cli"));

	    // Si querés cargar el usuario, deberías hacer un JOIN o una consulta adicional
	    // Aquí simplemente dejamos un objeto vacío o podrías recuperar los datos si están en el resultSet
	    Usuario usuario = new Usuario();
	    usuario.setUsuario_us(resultSet.getString("usuario_us")); // solo si hiciste JOIN con tabla usuarios
	    usuario.setContrasena_us(resultSet.getString("contrasena_us"));
	    cliente.setUsuario(usuario);

	    return cliente;
	}
	
	private Provincias getProvincia(ResultSet resultSet) throws SQLException {
	    Provincias provincia = new Provincias();

	    provincia.setId_prov(resultSet.getInt("id_prov"));
	    provincia.setDescripcion_prov(resultSet.getString("descripcion_prov"));

	    return provincia;
	}
	
	private Localidades getLocalidad(ResultSet resultSet) throws SQLException {
		Localidades localidad = new Localidades();

	    localidad.setId_loc(resultSet.getInt("id_loc"));
	    localidad.setIdPro_loc(resultSet.getInt("idPro_loc"));
	    localidad.setDescripcion_loc(resultSet.getString("descripcion_loc"));
	    return localidad;
	}
	
	
	@Override
	public List<Clientes> readFiltered(String dni, String apellido, String correo) {
		List<Clientes> clientesList = new ArrayList<>();
	    StringBuilder sql = new StringBuilder("SELECT * FROM Clientes WHERE estado_cli = 1");
	    
	    if (dni != null && !dni.isEmpty()) {
	        sql.append(" AND dni_cli LIKE ?");
	    }
	    if (apellido != null && !apellido.isEmpty()) {
	        sql.append(" AND apellido_cli LIKE ?");
	    }
	    if (correo != null && !correo.isEmpty()) {
	        sql.append(" AND correo_cli LIKE ?");
	    }
	    
	    try (Connection conn = Conexion.obtenerConexion();
	         PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
	         
	    	int paramIndex = 1;
	    	if (dni != null && !dni.isEmpty()) {	
	            stmt.setString(paramIndex++, "%" + dni + "%");
	    	}
	        if (apellido != null && !apellido.isEmpty()) {
	            stmt.setString(paramIndex++, "%" + apellido + "%");
	        }
	        if (correo != null && !correo.isEmpty()) {
	            stmt.setString(paramIndex++, "%" + correo + "%");
	        }
	        
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        	Clientes clientes = new Clientes();
	        	clientes.setDni_cli(rs.getString("dni_cli"));
	        	clientes.setCuil_cli(rs.getString("cuil_cli"));
	        	clientes.setNombre_cli(rs.getString("nombre_cli"));
	        	clientes.setApellido_cli(rs.getString("apellido_cli"));
	        	clientes.setSexo_cli(rs.getInt("sexo_cli"));
	        	clientes.setCorreo_cli(rs.getString("correo_cli"));
	        	clientes.setFecha_nacimiento_cli(rs.getDate("fecha_nacimiento_cli"));
	        	clientes.setDireccion_cli(rs.getString("direccion_cli"));
	        	clientes.setIdProvincia_cli(rs.getInt("idProvincia_cli"));
	        	clientes.setIdLocalidad_cli(rs.getInt("idLocalidad_cli"));
	        	clientes.setTelefono_cli(rs.getString("telefono_cli"));
	            clientesList.add(clientes);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return clientesList;
	}
	
	public Clientes obtenerClientePorIdUsuario(int idUsuario) {
	    Clientes cliente = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = Conexion.obtenerConexion();

	        String sql = "SELECT * FROM Clientes WHERE idUsuario_cli = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, idUsuario);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            cliente = new Clientes();
	            cliente.setDni_cli(rs.getString("dni_cli"));
	            cliente.setCuil_cli(rs.getString("cuil_cli"));
	            cliente.setNombre_cli(rs.getString("nombre_cli"));
	            cliente.setApellido_cli(rs.getString("apellido_cli"));
	            cliente.setSexo_cli(rs.getInt("sexo_cli"));
	            cliente.setFecha_nacimiento_cli(rs.getDate("fecha_nacimiento_cli"));
	            cliente.setDireccion_cli(rs.getString("direccion_cli"));
	            cliente.setIdProvincia_cli(rs.getInt("idProvincia_cli"));
	            cliente.setIdLocalidad_cli(rs.getInt("idLocalidad_cli"));
	            cliente.setCorreo_cli(rs.getString("correo_cli"));
	            cliente.setTelefono_cli(rs.getString("telefono_cli"));
	            cliente.setIdUsuario_cli(rs.getInt("idUsuario_cli"));
	            cliente.setEstado_cli(rs.getBoolean("estado_cli"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    return cliente;
	}
	
	public ArrayList<Clientes> cargarClientesDDL() {
	    ArrayList<Clientes> lista = new ArrayList<>();
	    String query = "SELECT * FROM clientes WHERE estado_cli = 1";

	    try (Connection cn = Conexion.obtenerConexion();
	         PreparedStatement ps = cn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Clientes c = new Clientes();
	            c.setDni_cli(rs.getString("dni_cli"));
	            c.setNombre_cli(rs.getString("nombre_cli"));
	            c.setApellido_cli(rs.getString("apellido_cli"));
	            lista.add(c);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

}
