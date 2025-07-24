package dao;
import entidad.Usuario;

public interface UsuarioDAO {

	public boolean login(String email, String password);
	public boolean register(Usuario u);
	public boolean modify(Usuario u);
	public Usuario validarUsuario(String usuario, String contrasena);
	
}
