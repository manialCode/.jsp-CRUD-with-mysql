package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProbarConexion {

	public static void main(String[] args) {
        Connection conn = Conexion.obtenerConexion();

        if (conn != null) {
            System.out.println("✅ Conexión exitosa a la base: " + conn);

            try {
                String sql = "SELECT * FROM Usuario";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                System.out.println("📋 Resultados de la tabla Usuario:");
                while (rs.next()) {
                    int id = rs.getInt("id_us");
                    String usuario = rs.getString("usuario_us");
                    String contrasena = rs.getString("contrasena_us");
                    int idTipo = rs.getInt("idTipo_us");

                    System.out.println("ID: " + id + ", Usuario: " + usuario + ", Contraseña: " + contrasena + ", Tipo: " + idTipo);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al ejecutar la consulta.");
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ No se pudo conectar a la base de datos.");
        }
    }
}
