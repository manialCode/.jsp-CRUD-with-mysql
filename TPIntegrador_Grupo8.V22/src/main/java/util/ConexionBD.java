package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

	
	 private static final String URL = "jdbc:mysql://localhost:3306/TPIntegrador_Grupo8_V3";
	    private static final String USUARIO = "root";
	    private static final String CONTRASENA = "root";

	    public static Connection obtenerConexion() {
	        try {
	            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
	        } catch (SQLException e) {
	            System.err.println("Error al conectar a la base de datos");
	            e.printStackTrace();
	            return null;
	        }
	    }	    
}
