package daoImpl;
import java.sql.*;
import java.util.ArrayList;
import entidad.Movimientos;
import dao.Conexion;
import dao.MovimientoDAO;

public class MovimientoDAOimpl implements MovimientoDAO {

    private static final String SELECT_ALL = "SELECT * FROM movimientos;";
    private static final String SELECT_BY_CBU = "SELECT * FROM movimientos WHERE CBU_mo = ?";
    private static final String SELECT_BY_CLIENT = "select *\r\n"
    		+ "FROM\r\n"
    		+ "    Movimientos AS M\r\n"
    		+ "INNER JOIN\r\n"
    		+ "    Cuentas AS CU ON M.CBU_mo = CU.CBU_cu\r\n"
    		+ "INNER JOIN\r\n"
    		+ "    Clientes AS C ON CU.dni_cu = C.dni_cli\r\n"
    		+ "where C.dni_cli = ?";
    private static final String INSERT = "INSERT INTO movimientos (CBU_mo, fecha_mo, importe_mo, tipoDeMovimiento_mo, detalle_mo) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE movimientos SET CBU_mo = ?, fecha_mo = ?, importe_mo = ?, tipoDeMovimiento_mo = ?, detalle_mo = ? WHERE idMovimiento_mo = ?";
    private static final String DELETE = "DELETE FROM movimientos WHERE idMovimiento_mo = ?";
    private static final String SELECT_BY_TYPE ="select * from movimientos where tipoDeMovimiento_mo = ?";

    @Override
    public ArrayList<Movimientos> readAll() {
        ArrayList<Movimientos> lista = new ArrayList<>();
        try (
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(SELECT_ALL);
            ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next()) {
                lista.add(getMovimiento(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public ArrayList<Movimientos> readByCBU(String cbu) {
        ArrayList<Movimientos> lista = new ArrayList<>();
        try (
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(SELECT_BY_CBU);
        ) {
            pst.setString(1, cbu);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(getMovimiento(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean insert(Movimientos mov) {
        boolean estado = false;
        try (
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(INSERT);
        ) {
            pst.setString(1, mov.getCBU_mo());
            pst.setDate(2, new java.sql.Date(mov.getFecha_mo().getTime()));
            pst.setBigDecimal(3, mov.getImporte_mo());
            pst.setInt(4, mov.getTipoDeMovimiento_mo());
            pst.setString(5, mov.getDetalle_mo());

            estado = pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }

    @Override
    public boolean update(Movimientos mov) {
        boolean estado = false;
        try (
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(UPDATE);
        ) {
            pst.setString(1, mov.getCBU_mo());
            pst.setDate(2, new java.sql.Date(mov.getFecha_mo().getTime()));
            pst.setBigDecimal(3, mov.getImporte_mo());
            pst.setInt(4, mov.getTipoDeMovimiento_mo());
            pst.setString(5, mov.getDetalle_mo());
            pst.setInt(6, mov.getIdMovimiento_mo());

            estado = pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }

    @Override
    public boolean delete(int id) {
        boolean estado = false;
        try (
            Connection cn = Conexion.obtenerConexion();
            PreparedStatement pst = cn.prepareStatement(DELETE);
        ) {
            pst.setInt(1, id);
            estado = pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }

    private Movimientos getMovimiento(ResultSet rs) throws SQLException {
        Movimientos mov = new Movimientos();
        mov.setIdMovimiento_mo(rs.getInt("idMovimiento_mo"));
        mov.setCBU_mo(rs.getString("CBU_mo"));
        mov.setFecha_mo(rs.getDate("fecha_mo"));
        mov.setImporte_mo(rs.getBigDecimal("importe_mo"));
        mov.setTipoDeMovimiento_mo(rs.getInt("tipoDeMovimiento_mo"));
        mov.setDetalle_mo(rs.getString("detalle_mo"));
        return mov;
    }

	@Override
	public ArrayList<Movimientos> readByClient(String dni) {
		 ArrayList<Movimientos> lista = new ArrayList<>();
	        try (
	            Connection cn = Conexion.obtenerConexion();
	            PreparedStatement pst = cn.prepareStatement(SELECT_BY_CLIENT);
	        ) {
	            pst.setString(1, dni);
	            try (ResultSet rs = pst.executeQuery()) {
	                while (rs.next()) {
	                    lista.add(getMovimiento(rs));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return lista;	
	 }
	
	
	public ArrayList<Movimientos> readByMovementType(int idTipo) {
        ArrayList<Movimientos> lista = new ArrayList<>();
           try (
               Connection cn = Conexion.obtenerConexion();
               PreparedStatement pst = cn.prepareStatement(SELECT_BY_TYPE);
           ) {
               pst.setInt(1, idTipo);
               try (ResultSet rs = pst.executeQuery()) {
                   while (rs.next()) {
                       lista.add(getMovimiento(rs));
                   }
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           return lista;
   }
	
	
}
