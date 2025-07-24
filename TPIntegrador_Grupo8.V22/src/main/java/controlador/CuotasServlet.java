package controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidad.Cuentas;
import entidad.Clientes;
import entidad.Cuotas;
import negocio.CuotaNegocio;
import negocio.CuentaNegocio;
import negocioImpl.CuotaNegocioImpl;
import negocioImpl.CuentasNegocioImpl;


@WebServlet("/CuotasServlet")
public class CuotasServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private CuotaNegocio cuotaNegocio;
	private CuentaNegocio cuentaNegocio = new CuentasNegocioImpl();
   
    public CuotasServlet() {
        super();
        cuotaNegocio = new CuotaNegocioImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] cuotasSeleccionadas = request.getParameterValues("cuotasSeleccionadas");
	    String cbuCuenta = request.getParameter("cuenta");

	    if (cuotasSeleccionadas == null || cuotasSeleccionadas.length == 0 || cbuCuenta == null || cbuCuenta.equals("xx")) {
	        request.setAttribute("error", "Debe seleccionar al menos una cuota y una cuenta.");
	        request.getRequestDispatcher("/Cliente/Prestamos/PrestamosClientes.jsp").forward(request, response);
	        return;
	    }

	    StringBuilder cuotasCSV = new StringBuilder();
	    for (int i = 0; i < cuotasSeleccionadas.length; i++) {
	        cuotasCSV.append(cuotasSeleccionadas[i]);
	        if (i < cuotasSeleccionadas.length - 1) {
	            cuotasCSV.append(",");
	        }
	    }

	    boolean exito = cuotaNegocio.pagarCuotas(cuotasCSV.toString(), cbuCuenta);

	    if (exito) {
	        request.setAttribute("mensajeExito", "Cuotas " + cuotasCSV + " pagadas con éxito.");
	    } else {
	        request.setAttribute("error", "Error al procesar el pago.");
	    }

	    request.getRequestDispatcher("/Cliente/Prestamos/PrestamosClientes.jsp").forward(request, response);
	}

	
    
}
