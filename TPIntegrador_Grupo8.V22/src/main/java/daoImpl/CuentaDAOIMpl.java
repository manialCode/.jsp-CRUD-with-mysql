package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.CuentaDAO;
import entidad.Cuentas;


public class CuentaDAOIMpl implements CuentaDAO{

	public CuentaDAOIMpl() {
		
	}
	private String readByClient = "select * from cuentas where dni_cu =";
	
	@Override
	/*public boolean ejecutarSPcrearCuenta(Cuentas cuenta,String cliente) {
		if(cuenta == null) {return false;}
		boolean estado = false;
		Connection cn = null;
		try 
		{
			cn = Conexion.obtenerConexion();
			CallableStatement cst = cn.prepareCall("CALL crearCuenta (?,?,?,?)");
			cst.setInt(1, cuenta.getTipoCuenta().getId_tc());
			cst.setString(2, cliente);
			cst.setString(3, cuenta.getNumero_cu());
			cst.setString(4, cuenta.getCBU_cu());
			estado = cst.execute();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return estado;
	}*/
	public boolean ejecutarSPcrearCuenta(Cuentas cuenta, String dniCliente) {
	    if (cuenta == null || dniCliente == null || dniCliente.trim().isEmpty()) {
	        return false;
	    }
	    boolean estado = false;
	    Connection cn = null;
	    CallableStatement cst = null;
	    try {
	        cn = Conexion.obtenerConexion();
	        cst = cn.prepareCall("CALL crearCuenta(?, ?, ?, ?)");
	        cst.setInt(1, cuenta.getTipoCuenta().getId_tc()); // idTipo
	        cst.setString(2, dniCliente);                    // dni
	        cst.setString(3, cuenta.getNumero_cu());          // numero
	        cst.setString(4, cuenta.getCBU_cu());             // CBU
	        
	        int filasAfectadas = cst.executeUpdate();
	        estado = (filasAfectadas > 0);
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

	
	public boolean ejecutarSPmodificarCuenta(Cuentas cuenta) {
	    if (cuenta == null) {
	        return false;
	    }
	    boolean estado = false;
	    Connection cn = null;
	    CallableStatement cst = null;
	    try {
	        cn = Conexion.obtenerConexion();
	        cst = cn.prepareCall("CALL modificarCuenta (?,?,?,?);");
	        
	        if (cuenta.getTipoCuenta() != null) {
	            cst.setInt(1, cuenta.getTipoCuenta().getId_tc());
	        } else {
	            cst.setNull(1, java.sql.Types.INTEGER);
	        }
	        cst.setString(2, cuenta.getDni_cu());
	        cst.setString(3, cuenta.getNumero_cu());
	        cst.setString(4, cuenta.getCBU_cu());
	        
	        int filasAfectadas = cst.executeUpdate();
	        estado = (filasAfectadas > 0);
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
	public boolean ejecutarSPeliminarCuenta(Cuentas cuenta) {
		  if (cuenta == null || cuenta.getCBU_cu() == null) return false;

			boolean estado = false;
			Connection cn = null;
			try
			{
				cn = Conexion.obtenerConexion();
				CallableStatement cst = cn.prepareCall("CALL eliminarCuenta(?)");
				cst.setString(1, cuenta.getCBU_cu());
				estado = cst.execute();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return estado;
	}

	@Override
	public ArrayList<Cuentas> readByClient(String dniCli) {
		PreparedStatement statement;
		ResultSet res;
		ArrayList<Cuentas> CuentasList = new ArrayList<Cuentas>();
		
		try {
			statement = Conexion.obtenerConexion().prepareStatement(readByClient + dniCli +";");
			res = statement.executeQuery();
			while(res.next()) {
				CuentasList.add(getCuenta(res));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CuentasList;
	}
	
	@Override
	public ArrayList<Cuentas> readAll() {
        ArrayList<Cuentas> cuentasList = new ArrayList<>();
        String sql = "SELECT * FROM Cuentas WHERE estado_cu = 1"; // Solo cuentas activas

        try (
            Connection conn = Conexion.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Cuentas cuenta = new Cuentas();
                cuenta.setCBU_cu(rs.getString("CBU_cu"));
                cuenta.setDni_cu(rs.getString("dni_cu"));
                cuenta.setFechaCreacion_cu(rs.getDate("fechaCreacion_cu"));
                cuenta.setNumero_cu(rs.getString("numero_cu"));
                cuenta.setIdTipo_cu(rs.getInt("idTipo_cu"));
                cuenta.setSaldo_cu(rs.getBigDecimal("saldo_cu"));
                cuenta.setEstado_cu(rs.getBoolean("estado_cu"));
                cuentasList.add(cuenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cuentasList;
    }
	

	private Cuentas getCuenta(ResultSet resultSet) throws SQLException
	{
		  Cuentas cuenta = new Cuentas();

		    cuenta.setCBU_cu(resultSet.getString("CBU_cu"));
		    cuenta.setDni_cu(resultSet.getString("dni_cu"));
		    cuenta.setFechaCreacion_cu(resultSet.getDate("fechaCreacion_cu"));
		    cuenta.setNumero_cu(resultSet.getString("numero_cu"));
		    cuenta.setIdTipo_cu(resultSet.getInt("idTipo_cu"));
		    cuenta.setSaldo_cu(resultSet.getBigDecimal("saldo_cu"));
		    cuenta.setEstado_cu(resultSet.getBoolean("estado_cu"));

		    return cuenta;
	}
	
	@Override
	public List<Cuentas> readFiltered(String tipoCuenta, String cliente, String cbu) {
		List<Cuentas> cuentasList = new ArrayList<>();
	    StringBuilder sql = new StringBuilder("SELECT * FROM Cuentas WHERE estado_cu = 1");
	    
	    if (tipoCuenta != null && !tipoCuenta.isEmpty()) {
	        sql.append(" AND idTipo_cu = ?");
	    }
	    if (cliente != null && !cliente.isEmpty()) {
	        sql.append(" AND dni_cu LIKE ?");
	    }
	    if (cbu != null && !cbu.isEmpty()) {
	        sql.append(" AND CBU_cu LIKE ?");
	    }
	    
	    try (Connection conn = Conexion.obtenerConexion();
	         PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
	         
	        int paramIndex = 1;
	        if (tipoCuenta != null && !tipoCuenta.isEmpty()) {
	            int tipo = 0;
	            if (tipoCuenta.equalsIgnoreCase("CA")) tipo = 1;
	            else if (tipoCuenta.equalsIgnoreCase("CC")) tipo = 2;
	            stmt.setInt(paramIndex++, tipo);
	        }
	        if (cliente != null && !cliente.isEmpty()) {
	            stmt.setString(paramIndex++, "%" + cliente + "%");
	        }
	        if (cbu != null && !cbu.isEmpty()) {
	            stmt.setString(paramIndex++, "%" + cbu + "%");
	        }
	        
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Cuentas cuenta = new Cuentas();
	            cuenta.setCBU_cu(rs.getString("CBU_cu"));
	            cuenta.setDni_cu(rs.getString("dni_cu"));
	            cuenta.setFechaCreacion_cu(rs.getDate("fechaCreacion_cu"));
	            cuenta.setNumero_cu(rs.getString("numero_cu"));
	            cuenta.setIdTipo_cu(rs.getInt("idTipo_cu"));
	            cuenta.setSaldo_cu(rs.getBigDecimal("saldo_cu"));
	            cuenta.setEstado_cu(rs.getBoolean("estado_cu"));
	            cuentasList.add(cuenta);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return cuentasList;
	}
	
		
}
