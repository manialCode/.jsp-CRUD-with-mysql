package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.servlet.RequestDispatcher;

import negocio.ClienteNegocio;
import negocioImpl.ClienteNegocioImpl;
import entidad.Clientes;

/*@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public LoginServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        
        if (usuario.equals("admin1") && contrasena.equals("1234")) {
            // Usuario administrador
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            sesion.setAttribute("tipo", "admin");
            response.sendRedirect("ABML_Cuentas.jsp");

        } else if (usuario.equals("juan1") && contrasena.equals("8888")) {
            // Usuario cliente
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            sesion.setAttribute("tipo", "cliente");
            response.sendRedirect("InfoUsuario.jsp");

        } else {
            // Usuario o contraseña incorrectos
            request.setAttribute("errorLogin", "Usuario o contraseña incorrecto");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
        }
    }
}*/

//import java.io.IOException;

//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;

import entidad.Usuario;
import negocio.UsuarioNegocio;
import negocioImpl.CuentasNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
import util.RutaPagina;
import negocio.CuentaNegocio;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();
    private CuentaNegocio cuentasNegocio = new CuentasNegocioImpl();
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();    
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println(">> Entré al LoginServlet");

        String user = request.getParameter("usuario");
        String pass = request.getParameter("contrasena");
               
        System.out.println("Llamando a negocio para validar usuario...");
        Usuario usu = usuarioNegocio.validarUsuario(user, pass);

        if (usu != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usu);

            if (usu.getIdTipo_us() == 1) {
                session.setAttribute("tipo", "admin");
                response.sendRedirect("NavegacionServlet?accion=cuentas");
            } else {
                session.setAttribute("tipo", "cliente");
                Clientes cliente = clienteNegocio.obtenerClientePorIdUsuario(usu.getId_us());
                if (cliente != null) {
                    session.setAttribute("clienteLogueado", cliente);
                }
                response.sendRedirect(RutaPagina.INICIO.getRuta());
                //response.sendRedirect("NavegacionServlet?accion=clientes");
            }
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
        System.out.println("Usuario recibido: " + user);
		System.out.println("Contraseña recibida: " + pass);
	}
        
}
