package controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Prestamos;
import entidad.Usuario;
import negocio.PrestamoNegocio;
import negocioImpl.PrestamoNegocioImpl;
import util.EstadoPrestamo;
import util.FechaActualFormateada;
import util.RutaPagina;


@WebServlet("/PrestamoServlet")
public class PrestamoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrestamoNegocio preNeg = new PrestamoNegocioImpl();
    
    public PrestamoServlet() {
        super();
        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usu = (Usuario) session.getAttribute("usuario");
		
		String estadoAccion = request.getParameter("estadoAccion");

		if ("Rechazado".equals(estadoAccion)) {
			int idPre = Integer.parseInt(request.getParameter("idPrestamo"));
			preNeg.rechazarPrestamo(idPre, usu.getIdTipo_us());
			request.setAttribute("listaPrestamos", preNeg.getAll(usu.getIdTipo_us()));
		}

		else if ("Aprobado".equals(estadoAccion)) {
			int idPre = Integer.parseInt(request.getParameter("idPrestamo"));
			preNeg.aceptarPrestamo(idPre, usu.getIdTipo_us());
			request.setAttribute("listaPrestamos", preNeg.getAll(usu.getIdTipo_us()));
		}

		else if ("solicitar".equals(estadoAccion)) {
			Prestamos prestamo = new Prestamos();
			try {
				String cuenta = request.getParameter("cuenta");
				String dniCli = request.getParameter("dniCli");
				int cuotas = Integer.parseInt(request.getParameter("cuotas"));
				BigDecimal montoPedido = new BigDecimal(request.getParameter("monto"));
				BigDecimal interesPorcentaje = new BigDecimal(request.getParameter("interes").replace("%", ""));
				BigDecimal interes = montoPedido.multiply(interesPorcentaje).divide(new BigDecimal("100"));
				BigDecimal montoTotal = montoPedido.add(interes);
				BigDecimal montoMensual = montoTotal.divide(new BigDecimal(cuotas), 2, RoundingMode.HALF_UP);							
				
				prestamo.setCBU_pre(cuenta);
				prestamo.setDni_pre(dniCli);
				prestamo.setEstado_pre(EstadoPrestamo.PENDIENTE.getDescripcion());
				prestamo.setFecha_pre(FechaActualFormateada.obtenerFechaActualComoDate());
				prestamo.setImportePedido_pre(montoPedido);
				prestamo.setIntereses_pre(interesPorcentaje);
				prestamo.setMontoTotal_pre(montoTotal);
				prestamo.setMontoMensual_pre(montoMensual);
				prestamo.setCant_Cuotas(cuotas);
				
				System.out.println(prestamo.toString());
				boolean exito = preNeg.insert(prestamo);
				
				if (exito) {
                    request.setAttribute("mensajeExito", "¡Préstamo solicitado correctamente!");
                    request.setAttribute("tipoMensaje", "exito");
                } else {
                    request.setAttribute("mensajeError", "Error al solicitar transferencia");
                    request.setAttribute("tipoMensaje", "error");
                }
				
				//request.setAttribute("mensajeExito", "¡Préstamo solicitado correctamente!" );
			} catch (Exception e) {
				
				e.printStackTrace();
                request.setAttribute("mensajeError", "Error al procesar la transferencia");
                request.setAttribute("tipoMensaje", "error");
				//request.setAttribute("mensajeError", "Hubo un error al solicitar el préstamo.");
			}
						
			request.getRequestDispatcher("/Cliente/Prestamos/PrestamosClientes.jsp").forward(request, response);
			return;
		}

		else if ("filtrar".equals(estadoAccion)) {
			System.out.println("estoy aca");
			String filtroFecha = request.getParameter("filtroFecha");
			String filtroApellido = request.getParameter("filtroApellido");
			String filtroInteres = request.getParameter("filtroInteres"); 

			ArrayList<Prestamos> listaFiltrada = preNeg.getByFiltros(filtroFecha, filtroApellido, filtroInteres);
			request.setAttribute("listaPrestamos", listaFiltrada);
		}

		else {
			
			request.setAttribute("listaPrestamos", preNeg.getAll(usu.getIdTipo_us()));
		}
		
		//request.setAttribute("listaPrestamos", preNeg.getAll(usu.getIdTipo_us()));
		request.getRequestDispatcher(RutaPagina.PRESTAMOS_ADMIN.getRuta()).forward(request, response);
	}
	


}
