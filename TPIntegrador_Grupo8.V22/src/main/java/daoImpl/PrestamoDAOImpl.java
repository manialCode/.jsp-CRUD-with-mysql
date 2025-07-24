package daoImpl;

import java.sql.*;
import java.util.ArrayList;
import dao.Conexion;
import dao.PrestamoDAO;
import entidad.Prestamos;

public class PrestamoDAOImpl implements PrestamoDAO {

    @Override
    public boolean ejecutarSPcrearPrestamo(Prestamos prestamo) {
        boolean estado = false;
        Connection cn = null;

        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cs = cn.prepareCall("CALL crearPrestamo(?,?,?,?,?,?,?,?)");
            cs.setString(1, prestamo.getDni_pre());                              
            cs.setString(2, prestamo.getCBU_pre());                              
            cs.setDate(3, new java.sql.Date(prestamo.getFecha_pre().getTime())); 
            cs.setBigDecimal(4, prestamo.getImportePedido_pre());                
            cs.setBigDecimal(5, prestamo.getIntereses_pre());                    
            cs.setInt(6, prestamo.getCant_Cuotas());
            cs.setBigDecimal(7, prestamo.getMontoTotal_pre());                   
            cs.setBigDecimal(8, prestamo.getMontoMensual_pre());

            cs.execute();
            estado = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estado;
    }

    @Override
    public boolean ejecutarSPmodificarPrestamo(Prestamos prestamo) {
        boolean estado = false;
        Connection cn = null;

        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cs = cn.prepareCall("CALL modificarPrestamo(?,?,?,?,?,?,?,?,?,?)");
            cs.setInt(1, prestamo.getId_pre());
            cs.setString(2, prestamo.getCBU_pre());
            cs.setString(3, prestamo.getDni_pre());
            cs.setDate(4, new java.sql.Date(prestamo.getFecha_pre().getTime()));
            cs.setBigDecimal(5, prestamo.getImportePedido_pre());
            cs.setBigDecimal(6, prestamo.getIntereses_pre());
            cs.setBigDecimal(7, prestamo.getMontoTotal_pre());
            cs.setBigDecimal(8, prestamo.getMontoMensual_pre());
            cs.setString(9, prestamo.getEstado_pre());
            cs.setInt(10, prestamo.getCant_Cuotas());

            cs.execute();
            estado = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estado;
    }

    @Override
    public boolean ejecutarSPeliminarPrestamo(int idPrestamo) {
        boolean estado = false;
        Connection cn = null;

        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cs = cn.prepareCall("CALL eliminarPrestamo(?)");
            cs.setInt(1, idPrestamo);

            cs.execute();
            estado = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estado;
    }
    
    public boolean ejecutarSPaceptarPrestamo(int idPrestamo) {
        boolean estado = false;
        Connection cn = null;

        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cs = cn.prepareCall("CALL aceptarPrestamo(?)");
            cs.setInt(1, idPrestamo);

            cs.execute();
            estado = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estado;
    }
    
    public boolean ejecutarSPrechazarPrestamo(int idPrestamo) {
        boolean estado = false;
        Connection cn = null;

        try {
            cn = Conexion.obtenerConexion();
            CallableStatement cs = cn.prepareCall("CALL rechazarPrestamo(?)");
            cs.setInt(1, idPrestamo);

            cs.execute();
            estado = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return estado;
    }

    @Override
    public ArrayList<Prestamos> obtenerPrestamosPorDni(String dni) {
        ArrayList<Prestamos> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection cn = Conexion.obtenerConexion();
            ps = cn.prepareStatement("SELECT * FROM prestamos WHERE dni_pre = ?");
            ps.setString(1, dni);
            rs = ps.executeQuery();

            while (rs.next()) {
                Prestamos p =setPrestamo(rs);

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


@Override
public ArrayList<Prestamos> obtenerTodos() {
    ArrayList<Prestamos> lista = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        Connection cn = Conexion.obtenerConexion();
        ps = cn.prepareStatement("SELECT * FROM prestamos");
        rs = ps.executeQuery();

        while (rs.next()) {
            Prestamos p = setPrestamo(rs);

            lista.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

public ArrayList<Prestamos> obtenerPorFiltros(String filtroFecha, String filtroApellido, String filtroInteres) {
    ArrayList<Prestamos> lista = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;
    System.out.println("estoy en dao");
    try {
        Connection cn = Conexion.obtenerConexion();

        StringBuilder query = new StringBuilder("SELECT * FROM prestamos WHERE 1=1 ");

        if (filtroFecha != null && !filtroFecha.trim().isEmpty()) {
            query.append(" AND fecha_pre = ? ");
        }
        if (filtroApellido != null && !filtroApellido.trim().isEmpty()) {
            query.append(" AND dni_pre IN (SELECT dni_cli FROM clientes WHERE apellido_cli LIKE ?) ");
        }
        if (filtroInteres != null && !filtroInteres.trim().isEmpty()) {
            query.append(" AND intereses_pre = ? ");
        }

        ps = cn.prepareStatement(query.toString());

        int paramIndex = 1;
        if (filtroFecha != null && !filtroFecha.trim().isEmpty()) {
            ps.setDate(paramIndex++, java.sql.Date.valueOf(filtroFecha));
        }
        if (filtroApellido != null && !filtroApellido.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + filtroApellido + "%");
        }
        if (filtroInteres != null && !filtroInteres.trim().isEmpty()) {
            ps.setBigDecimal(paramIndex++, new java.math.BigDecimal(filtroInteres));
        }

        rs = ps.executeQuery();

        while (rs.next()) {
            Prestamos p = setPrestamo(rs);
            lista.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

public ArrayList<Prestamos> obtenerPorEstado(String estado) {
    ArrayList<Prestamos> lista = new ArrayList<>();
    try (Connection cn = Conexion.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement("SELECT * FROM prestamos WHERE estado_pre = ?")) {
        ps.setString(1, estado);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prestamos p = setPrestamo(rs);
                lista.add(p);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

private Prestamos setPrestamo(ResultSet rs) throws SQLException {
	Prestamos p = new Prestamos();
	p.setId_pre(rs.getInt("id_pre"));
    p.setCBU_pre(rs.getString("CBU_pre"));
    p.setDni_pre(rs.getString("dni_pre"));
    p.setFecha_pre(rs.getDate("fecha_pre"));
    p.setCant_Cuotas(rs.getInt("cantCuotas_pre"));
    p.setImportePedido_pre(rs.getBigDecimal("importePedido_pre"));
    p.setIntereses_pre(rs.getBigDecimal("intereses_pre"));
    p.setMontoTotal_pre(rs.getBigDecimal("montoTotal_pre"));
    p.setMontoMensual_pre(rs.getBigDecimal("montoMensual_pre"));
    p.setEstado_pre(rs.getString("estado_pre"));
	return p;
}
}
