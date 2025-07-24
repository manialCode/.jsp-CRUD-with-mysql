package daoImpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import entidad.Clientes;
import entidad.Cuentas;
import entidad.Cuotas;
import dao.CuotaDAO;

public class CuotaDAOImpl implements CuotaDAO {

    private String readByPrestamo = "SELECT * FROM cuotas WHERE idPrestamo_cuo = ";
    private String readByClient = "SELECT c.* FROM cuotas c JOIN prestamos p ON c.idPrestamo_cuo = p.id_pre WHERE p.dni_pre = ?";

    @Override
    public boolean ejecutarSPcrearCuota(Cuotas cuota) {
        if (cuota == null) return false;
        boolean estado = false;
        Connection cn = null;
        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cst = cn.prepareCall("CALL crearCuota(?,?,?,?,?)");
            cst.setInt(1, cuota.getIdPrestamo_cuo());
            cst.setBigDecimal(2, cuota.getMonto_cuo());
            cst.setDate(3, new java.sql.Date(cuota.getFechaVencimiento_cuo().getTime()));
            cst.setDate(4, cuota.getFechaPago_cuo() != null ? new java.sql.Date(cuota.getFechaPago_cuo().getTime()) : null);
            cst.setString(5, cuota.getCBU_pago_cuo());
            cst.execute();
            estado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }

    @Override
    public boolean ejecutarSPmodificarCuota(Cuotas cuota) {
        if (cuota == null) return false;
        boolean estado = false;
        Connection cn = null;
        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cst = cn.prepareCall("CALL modificarCuota(?,?,?,?,?,?)");
            cst.setInt(1, cuota.getId_cuo());
            cst.setInt(2, cuota.getIdPrestamo_cuo());
            cst.setBigDecimal(3, cuota.getMonto_cuo());
            cst.setDate(4, new java.sql.Date(cuota.getFechaVencimiento_cuo().getTime()));
            cst.setDate(5, cuota.getFechaPago_cuo() != null ? new java.sql.Date(cuota.getFechaPago_cuo().getTime()) : null);
            cst.setString(6, cuota.getCBU_pago_cuo());
            cst.execute();
            estado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }

    @Override
    public boolean ejecutarSPeliminarCuota(int idCuota) {
        boolean estado = false;
        Connection cn = null;
        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cst = cn.prepareCall("CALL eliminarCuota(?)");
            cst.setInt(1, idCuota);
            cst.execute();
            estado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }

    @Override
    public List<Cuotas> readByPrestamo(int idPrestamo) {
        PreparedStatement statement;
        ResultSet res;
        ArrayList<Cuotas> cuotasList = new ArrayList<>();

        try {
            statement = Conexion.obtenerConexion().prepareStatement(readByPrestamo + idPrestamo + ";");
            res = statement.executeQuery();
            while (res.next()) {
                cuotasList.add(getCuota(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cuotasList;
    }

    private Cuotas getCuota(ResultSet rs) throws SQLException {
        Cuotas cuota = new Cuotas();
        cuota.setId_cuo(rs.getInt("id_cuo"));
        cuota.setIdPrestamo_cuo(rs.getInt("idPrestamo_cuo"));
        cuota.setMonto_cuo(rs.getBigDecimal("monto_cuo"));
        cuota.setFechaVencimiento_cuo(rs.getDate("fechaVencimiento_cuo"));
        cuota.setFechaPago_cuo(rs.getDate("fechaPago_cuo"));
        cuota.setCBU_pago_cuo(rs.getString("CBU_pago_cuo"));
        cuota.setEstado_cuo(rs.getBoolean("estado_cuo"));
        return cuota;
    }

	@Override
	public List<Cuotas> readALL() {
		 List<Cuotas> lista = new ArrayList<>();
		    Connection cn = null;
		    PreparedStatement pst = null;
		    ResultSet rs = null;

		    try {
		        cn = Conexion.obtenerConexion();
		        pst = cn.prepareStatement("SELECT * FROM cuotas");
		        rs = pst.executeQuery();

		        while (rs.next()) {
		            lista.add(getCuota(rs));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (pst != null) pst.close();
		            if (cn != null) cn.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }

		    return lista;
	}
	
	
	public ArrayList<Cuotas> readAllCuo() {
		 ArrayList<Cuotas> cuotasList = new ArrayList<>();
		    PreparedStatement statement;
		    ResultSet res;

		    try {
		        Connection conn = Conexion.obtenerConexion();
		        String query = "SELECT * FROM cuotas"; 		        
		        statement = conn.prepareStatement(query);
		        res = statement.executeQuery();

		        while (res.next()) {
		            cuotasList.add(getCuota(res));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return cuotasList;
	}
	
	@Override
	public ArrayList<Cuotas> readByClient(String dniCli) {
	    ArrayList<Cuotas> cuotasList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet res = null;

	    try {
	        conn = Conexion.obtenerConexion();
	        stmt = conn.prepareStatement(readByClient);
	        stmt.setString(1, dniCli); 
	        res = stmt.executeQuery();

	        while (res.next()) {
	            cuotasList.add(getCuota(res));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (res != null) res.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cuotasList;
	}
	
	@Override
	public boolean pagarCuotas(String listaCuotasCSV, String cbuCuenta) {
	    boolean exito = false;
	    Connection cn = null;
	    CallableStatement cst = null;

	    try {
	        cn = Conexion.obtenerConexion();
	        cst = cn.prepareCall("{CALL pagarCuotas(?, ?)}");
	        cst.setString(1, listaCuotasCSV);
	        cst.setString(2, cbuCuenta);
	        cst.execute();
	        exito = true;
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

	    return exito;
	}
	
}
