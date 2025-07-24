package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import entidad.Clientes;
import entidad.Cuentas;
import entidad.Localidades;
import entidad.Movimientos;
import entidad.Prestamos;
import entidad.Provincias;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.MovimientoNegocio;
import negocio.PrestamoNegocio;
import negocio.ReporteNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentasNegocioImpl;
import negocioImpl.MovimientoNegocioImpl;
import negocioImpl.PrestamoNegocioImpl;
import negocioImpl.ReporteNegocioImpl;
import util.ConsultarEstadoPrestamoStreams;
import util.RutaPagina;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/NavegacionServlet")
public class NavegacionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();;
	private CuentaNegocio negocioCuentas = new CuentasNegocioImpl();;
	private MovimientoNegocio nM = new MovimientoNegocioImpl();
	private PrestamoNegocio prestamoNegocio = new PrestamoNegocioImpl();
	private ReporteNegocio reporteNegocio = new ReporteNegocioImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");

		HttpSession session = request.getSession();
		if (accion == null || session.getAttribute("usuario") == null) {
			response.sendRedirect(RutaPagina.LOGIN.getRuta());
			return;
		}
		
		Usuario usuarioLog = (Usuario)session.getAttribute("usuario");
		int tipoDeUsuario = usuarioLog.getIdTipo_us();
		
		switch (accion) {
		// ADMINISTRADOR
		case "clientes":
			ArrayList<Clientes> clientes = clienteNegocio.getAll();
			ArrayList<Provincias> provincias = clienteNegocio.getProvincias();
			ArrayList<Localidades> localidades = clienteNegocio.getLocalidades();

			request.setAttribute("listaProvincias", provincias);
			request.setAttribute("listaLocalidades", localidades);
			request.setAttribute("listaClientes", clientes);

			request.getRequestDispatcher(RutaPagina.CLIENTES.getRuta()).forward(request, response);
			break;

		case "cuentas":

			ArrayList<Cuentas> cuentas = negocioCuentas.obtenerTodasLasCuentas();
			ArrayList<Clientes> listaClientes = clienteNegocio.obtenerTodosLosClientesActivos();

			request.setAttribute("listaCuentas", cuentas);
			request.setAttribute("listasClientes", listaClientes);

			request.getRequestDispatcher(RutaPagina.CUENTAS.getRuta()).forward(request, response);
			break;

		case "prestamosAdmin":
			ArrayList<Prestamos> prestamos = prestamoNegocio.getAll(tipoDeUsuario);
			boolean hayPendientes = ConsultarEstadoPrestamoStreams.tienePrestamoPendienteStream(prestamos);
			request.setAttribute("hayPendientes", hayPendientes);
			request.setAttribute("listaPrestamos", prestamos);
			request.setAttribute("debug", "La lista fue enviada");
			request.getRequestDispatcher(RutaPagina.PRESTAMOS_ADMIN.getRuta()).forward(request, response);
			break;

		case "reportes":
			//response.sendRedirect(RutaPagina.REPORTES.getRuta());
			 ArrayList<String> resultadoReporte3 = reporteNegocio.ejecutarSPreportes3();
			 ArrayList<String> resultadoReporte4 = reporteNegocio.ejecutarSPreportes4();
			 ArrayList<String> resultadoReporte5 = reporteNegocio.ejecutarSPreportes5();
             System.out.println(resultadoReporte3);
             System.out.println(resultadoReporte4);
             System.out.println(resultadoReporte5);
             request.setAttribute("resultadoReporte3", resultadoReporte3);
             request.setAttribute("resultadoReporte4", resultadoReporte4);
             request.setAttribute("resultadoReporte5", resultadoReporte5);
			request.getRequestDispatcher(RutaPagina.REPORTES.getRuta()).forward(request, response);
			break;

		// CLIENTES
		case "inicio":
			response.sendRedirect(RutaPagina.INICIO.getRuta());
			break;

		case "prestamosCliente":
			response.sendRedirect(RutaPagina.PRESTAMOS_CLIENTE.getRuta());
			break;

		case "transferencias":
			ArrayList<Clientes> clientess = clienteNegocio.getAll();
			Clientes cli = (Clientes)session.getAttribute("clienteLogueado");
			request.setAttribute("listaClientess", clientess);
			
			Clientes clis = (Clientes)session.getAttribute("clienteLogueado");
			ArrayList<Movimientos> listaTransferencia = nM.getByClient(clis.getDni_cli());
		    request.setAttribute("listaTransferencias", listaTransferencia);
			
			request.getRequestDispatcher(RutaPagina.TRANSFERENCIAS.getRuta()).forward(request, response);
			break;

		case "movimientos":
			Clientes cli1 = (Clientes) session.getAttribute("clienteLogueado");
			ArrayList<Movimientos> listaMovimientos = nM.getByClient(cli1.getDni_cli());
			request.setAttribute("listaMovimientos", listaMovimientos);
			request.getRequestDispatcher(RutaPagina.MOVIMIENTOS.getRuta()).forward(request, response);;
			break;
		case "logout":
			request.getSession().invalidate();
			response.sendRedirect(RutaPagina.LOGIN.getRuta());
			break;
		default:
			response.sendRedirect(RutaPagina.LOGIN.getRuta());
			break;
		}
	}
}
