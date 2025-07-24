package negocioImpl;

import dao.UsuarioDAO;
import daoImpl.UsuarioDAOImpl;
import entidad.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    public Usuario validarUsuario(String usuario, String contrasena) {
        return usuarioDAO.validarUsuario(usuario, contrasena);
        
    }
    
}
