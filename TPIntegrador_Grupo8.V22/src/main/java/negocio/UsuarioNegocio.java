package negocio;

import entidad.Usuario;

public interface UsuarioNegocio {

//	public boolean login(String email, String password);
//	public boolean register(Usuario u);
//	public boolean modify(Usuario u);
	public Usuario validarUsuario(String user, String pass);
	

}
