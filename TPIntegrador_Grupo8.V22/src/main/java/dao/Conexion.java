package dao;
import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
    public static Connection obtenerConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TPIntegrador_Grupo8_V4", 
            								  "root", 
            								  "Jmm0817%");
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
        return con;
    }
}
