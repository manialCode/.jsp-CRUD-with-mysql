package controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.RutaPagina;
import entidad.Clientes;
import entidad.Movimientos;
import negocio.MovimientoNegocio;
import negocioImpl.MovimientoNegocioImpl;

/**
 * Servlet implementation class MovimientosServlet
 */
@WebServlet("/MovimientosServlet")
public class MovimientosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MovimientoNegocio movNeg = new MovimientoNegocioImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovimientosServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Clientes cli = (Clientes) session.getAttribute("clienteLogueado");
		String accion = request.getParameter("accion");
		if (accion == null || cli == null) {
			response.sendRedirect(RutaPagina.MOVIMIENTOS.getRuta());
			return;
		}

		if (accion.equals("buscar")) {
			String cbu = request.getParameter("cuenta");
			int tipo = Integer.parseInt(request.getParameter("tipoMovimiento"));
			ArrayList<Movimientos> listaActualizada = null;

			if (cbu.equals("xx")) {
				listaActualizada = movNeg.getByClient(cli.getDni_cli());
			} else {
				listaActualizada = movNeg.getByAccount(cbu);
			}

			if (tipo > 0 && listaActualizada != null) {
				listaActualizada.removeIf(mo -> mo.getTipoDeMovimiento_mo() != tipo);
			}

			request.setAttribute("listaMovimientos", listaActualizada);
			request.getRequestDispatcher(RutaPagina.MOVIMIENTOS.getRuta()).forward(request, response);
		}

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
