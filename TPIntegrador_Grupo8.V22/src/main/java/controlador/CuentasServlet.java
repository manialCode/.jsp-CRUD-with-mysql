package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Clientes;
import entidad.Cuentas;
import entidad.TipoCuenta;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentasNegocioImpl;

@WebServlet("/CuentasServlet")
public class CuentasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CuentaNegocio cuentasNegocio = new CuentasNegocioImpl();
	ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
       

    public CuentasServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String accion = request.getParameter("accion");
    	
    	cargarListaClientes(request);
        
        if (accion == null || accion.equals("listar")) {
            List<Cuentas> lista = cuentasNegocio.obtenerTodasLasCuentas();  
            request.setAttribute("listaCuentas", lista);
            request.getRequestDispatcher("Admin/Cuentas/ABML_Cuentas.jsp").forward(request, response);
        } else if (accion.equals("buscar")) {
            String filtroTipoCuenta = request.getParameter("filtroTipoCuenta");
            String filtroCliente = request.getParameter("filtroCliente");
            String filtroCBU = request.getParameter("filtroCBU");
            
            List<Cuentas> listaFiltrada = cuentasNegocio.obtenerCuentasFiltradas(filtroTipoCuenta, filtroCliente, filtroCBU);
            request.setAttribute("listaCuentas", listaFiltrada);
            request.getRequestDispatcher("Admin/Cuentas/ABML_Cuentas.jsp").forward(request, response);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String accion = request.getParameter("accion");
		 String accion2 = request.getParameter("accion2");
		 
		 cargarListaClientes(request);
		 
		 if ("modificar".equals(accion2)) {
             modificarCuentas(request);
             cargarListaCuentas(request);          
             request.getRequestDispatcher("Admin/Cuentas/ABML_Cuentas.jsp").forward(request, response);
             return;
         }

		 	if (accion == null) {
	            doGet(request, response);
	            return;
	        }

		 	switch (accion) {
            case "alta":
                altaCuenta(request);
                break;
            case "baja":
                bajaCuenta(request);
                break;            
            case "cuentaAModificar":
            	String cbuSeleccionado = request.getParameter("cbu");
                Cuentas cuentas = cuentasNegocio.getByCBU(cbuSeleccionado);
                             
                if ("cuentaAModificar".equals(accion)) {                  
                	
                    if (cuentas != null) {
                        request.setAttribute("cuentaAModificar", cuentas);
                        request.getRequestDispatcher("Admin/Cuentas/ModificarCuenta.jsp").forward(request, response);                      
                    } else {
                        request.setAttribute("error", "Cuenta no encontrada");
                        request.getRequestDispatcher("Admin/Cuentas/ABML_Cuentas.jsp").forward(request, response);     
                    }
                    return;                    
                }
                               
            default:
            	
                doGet(request, response);
                break;
	        }		 	
	        cargarListaCuentas(request);	        
	        request.getRequestDispatcher("Admin/Cuentas/ABML_Cuentas.jsp").forward(request, response);
	}
	
	private void cargarListaCuentas(HttpServletRequest request) {
		ArrayList<Cuentas> listaCuentas = cuentasNegocio.obtenerTodasLasCuentas();
		request.setAttribute("listaCuentas", listaCuentas);
	}
	
	private void cargarListaClientes(HttpServletRequest request) {
		ArrayList<Clientes> listaClientes = clienteNegocio.obtenerTodosLosClientesActivos();
		request.setAttribute("listasClientes", listaClientes);
	}
	
	private void altaCuenta(HttpServletRequest request) {
        try {
            Cuentas cuenta = new Cuentas();
            TipoCuenta tipoCuenta = new TipoCuenta();
            String cliente = "";
            
            tipoCuenta.setId_tc(Integer.parseInt(request.getParameter("TipoDeCuenta")));
            cuenta.setCBU_cu(request.getParameter("cbu"));
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setNumero_cu(request.getParameter("NumeroDeCuenta"));
            cliente = request.getParameter("Clientes");
            
            System.out.println("CBU: " + cuenta.getCBU_cu());
            System.out.println("Nombre: " + cuenta.getIdTipo_cu());
            System.out.println("Apellido: " + cuenta.getNumero_cu());
            System.out.println("Dni: " + cuenta.getDni_cu());        
                        

            boolean insertado = cuentasNegocio.insertar(cuenta,cliente);           
            request.setAttribute("mensaje", insertado ? "Cuenta registrada con éxito." : "Error al registrar cuenta.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al procesar los datos de la cuenta.");
        }
    }
	
	private void bajaCuenta(HttpServletRequest request) {
        try {
            String cbu = request.getParameter("cbu");
            Cuentas cuenta = new Cuentas();
            cuenta.setCBU_cu(cbu);

            boolean eliminado = cuentasNegocio.delete(cuenta);
            request.setAttribute("mensaje", eliminado ? "Cuenta eliminada correctamente." : "Error al eliminar cuenta.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminarla cuenta.");
        }
        cargarListaCuentas(request);
    }
	
	
	private void modificarCuentas(HttpServletRequest request) {
        
		try {
            Cuentas cuentas = new Cuentas();

            cuentas.setCBU_cu(request.getParameter("CBU"));
            cuentas.setDni_cu(request.getParameter("dni"));
            cuentas.setIdTipo_cu(Integer.parseInt(request.getParameter("TipoDeCuenta")));
            cuentas.setNumero_cu(request.getParameter("NumeroDeCuenta"));
           
            TipoCuenta tipoCuenta = new TipoCuenta();
            int idTipo = Integer.parseInt(request.getParameter("TipoDeCuenta"));
            tipoCuenta.setId_tc(idTipo);
            tipoCuenta.setDescripcion_tc(
                idTipo == 1 ? "Caja de ahorro" :
                idTipo == 2 ? "Cuenta corriente" :
                "Desconocido"
            );
            cuentas.setTipoCuenta(tipoCuenta);

            boolean modificado = cuentasNegocio.modificar(cuentas);
            request.setAttribute("mensaje", modificado ? "Cuenta modificado correctamente." : "Error al modificar cuenta.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al procesar la modificación del cliente.");
        }
        cargarListaClientes(request);
    }
	
}



