package controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Clientes;
import entidad.Cuentas;
import entidad.Movimientos;
import negocio.TransferenciaNegocio;
import negocioImpl.TransferenciaNegocioImpl;
import negocio.MovimientoNegocio;
import negocioImpl.MovimientoNegocioImpl;
import negocio.ClienteNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocio.CuentaNegocio;
import negocioImpl.CuentasNegocioImpl;

@WebServlet("/TransferenciaServlet")
public class TransferenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
	private CuentaNegocio cuentasNegocio = new CuentasNegocioImpl();
	private MovimientoNegocio movimientoNegocio = new MovimientoNegocioImpl();
    
    public TransferenciaServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		    
		cargarListaClientes(request);
		cargarListaCuentas(request);
		request.getRequestDispatcher("Cliente/Transferencias/TransferenciasClientes.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 System.out.println("ESTOY EN EL POST");
		 String accion = request.getParameter("accion");

		    cargarListaClientes(request); 

		    String dniSeleccionado = request.getParameter("dniSeleccionado");

		    if (dniSeleccionado != null && !dniSeleccionado.isEmpty()) {
		        ArrayList<Cuentas> listaCuentas = cuentasNegocio.getByDNI(dniSeleccionado);
		        request.setAttribute("listaCuentasFiltradas", listaCuentas);
		        request.setAttribute("dniSeleccionado", dniSeleccionado);
		    }
		    
		    if ("enviar".equals(accion)) {
		    	System.out.println("ESTOY EN ENVIAR");
		        String cbuOrigen = request.getParameter("cuenta");
		        String cbuDestino = request.getParameter("cbu");
		        String detalle = request.getParameter("detalle");
		        String montoStr = request.getParameter("monto");

		        if (cbuOrigen != null && cbuDestino != null && montoStr != null) {
		            try {
		                java.math.BigDecimal monto = new java.math.BigDecimal(montoStr);

		                Movimientos movimiento = new Movimientos();
		                movimiento.setCBU_mo(cbuOrigen);
		                movimiento.setDetalle_mo(detalle);
		                movimiento.setImporte_mo(monto);

		                TransferenciaNegocio transferenciaNegocio = new TransferenciaNegocioImpl();
		                boolean exito = transferenciaNegocio.enviarTransferencia(movimiento, cbuDestino);

		                if (exito) {
		                    request.setAttribute("mensaje", "Transferencia exitosa");
		                    request.setAttribute("tipoMensaje", "exito");
		                } else {
		                    request.setAttribute("mensaje", "Fondos insuficientes");
		                    request.setAttribute("tipoMensaje", "error");
		                }

		            } catch (Exception e) {
		            	e.printStackTrace();
		                request.setAttribute("mensaje", "Error al procesar la transferencia");
		                request.setAttribute("tipoMensaje", "error");
		            }
		        }
		    }
		request.getRequestDispatcher("Cliente/Transferencias/TransferenciasClientes.jsp").forward(request, response);
		//doGet(request, response);
	}

	
	private void cargarListaClientes(HttpServletRequest request) {
        ArrayList<Clientes> listaClientes = clienteNegocio.getAll();
        request.setAttribute("listaClientess", listaClientes);
    }
	
	private void cargarListaCuentas(HttpServletRequest request) {
		ArrayList<Cuentas> listaCuentas = cuentasNegocio.obtenerTodasLasCuentas();
		request.setAttribute("listaCuentass", listaCuentas);
	}

}
