package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.Conexion;
import entidad.Provincias;

public class ProvinciaDAOIml {

	public ArrayList<Provincias> readAllProvinces() {
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
	private Provincias getProvincia(ResultSet resultSet) throws SQLException {
	    Provincias Provincia = new Provincias();

	    Provincia.setId_prov(resultSet.getInt("id_prov"));
	    Provincia.setDescripcion_prov(resultSet.getString("descripcion_prov"));

	
	    return Provincia;
	}
}
