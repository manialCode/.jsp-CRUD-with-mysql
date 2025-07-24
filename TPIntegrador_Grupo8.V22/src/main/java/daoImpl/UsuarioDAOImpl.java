package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.Conexion;
import dao.UsuarioDAO;
import entidad.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario validarUsuario(String usuario, String contrasena) {
        Usuario usu = null;
        System.out.println(">> Entré a UsuarioDAOImpl.validarUsuario()");

        try {      	
        	  
            Connection conn = Conexion.obtenerConexion();
            System.out.println("Voy a ejecutar la consulta para: " + usuario + " - " + contrasena); //PARA PROBAR
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT u.*, t.descripcion_tu FROM Usuario u " +
                "INNER JOIN TiposUsuarios t ON u.idTipo_us = t.id_tu " +
                "WHERE u.usuario_us = ? AND u.contrasena_us = ?"
            );
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {           	
            	System.out.println("Se encontró el usuario");
            	
                usu = new Usuario();
                usu.setId_us(rs.getInt("id_us"));
                usu.setUsuario_us(rs.getString("usuario_us"));
                usu.setContrasena_us(rs.getString("contrasena_us"));
                usu.setIdTipo_us(rs.getInt("idTipo_us"));

                // Solo para ver la descripción del tipo (opcional)
                System.out.println("Tipo usuario: " + rs.getString("descripcion_tu"));
            }
         else {
            System.out.println("⚠️ No se encontró ningún usuario con esas credenciales.");
        }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usu;
    }

	@Override
	public boolean login(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(Usuario u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modify(Usuario u) {
		// TODO Auto-generated method stub
		return false;
	}
}
