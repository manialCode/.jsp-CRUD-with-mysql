package controlador;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Clientes;
import entidad.Cuentas;
import entidad.Movimientos;
import entidad.Prestamos;
import negocio.ReporteNegocio;
import negocioImpl.ReporteNegocioImpl;
import util.RutaPagina;

@WebServlet("/ReportesServlet")
public class ReportesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ReporteNegocio reporteNegocio = new ReporteNegocioImpl();
       
    public ReportesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if (accion == null) {
                response.sendRedirect("Admin/Reportes/ReportesEstadisticas.jsp");
                return;
            }

            switch (accion) {
                case "reporte1":
                    String fechaInicioStr = request.getParameter("fechaInicio");
                    String fechaFinStr = request.getParameter("fechaFin");
                    if (fechaInicioStr != null && fechaFinStr != null) {
                        Date fechaInicio = Date.valueOf(fechaInicioStr);
                        Date fechaFin = Date.valueOf(fechaFinStr);

                        ArrayList<Movimientos> listaReporte1 = reporteNegocio.ejecutarSPreportes1(fechaInicio, fechaFin);
                        System.out.println(listaReporte1);
                        request.setAttribute("listaReporte1", listaReporte1);
                    }
                    break;

                case "reporte2":
                    String dniPre = request.getParameter("dniPre");
                    String apellidoCli = request.getParameter("apellidoCli");
                    String idTipoCuentaStr = request.getParameter("idTipoCuenta");
                    if(dniPre.isEmpty()) {dniPre = " ";}
                    if(apellidoCli.isEmpty()) {apellidoCli = " ";}
                    if(idTipoCuentaStr.isEmpty()) {idTipoCuentaStr = "0";}

                        Clientes cliente = new Clientes();
                        cliente.setDni_cli(dniPre);
                        cliente.setApellido_cli(apellidoCli);
                        
                        ArrayList<Prestamos> exito = reporteNegocio.ejecutarSPreportes2(cliente, apellidoCli,Integer.parseInt(idTipoCuentaStr));
                        System.out.println(exito);
                        request.setAttribute("resultadoReporte2", exito);
                        
                    break;

                case "reporte3":
                    ArrayList<String> resultadoReporte3 = reporteNegocio.ejecutarSPreportes3();
                    System.out.println(resultadoReporte3);
                    request.setAttribute("resultadoReporte3", resultadoReporte3);
                break;


                default:
                    break;
            }

            request.getRequestDispatcher(RutaPagina.REPORTES.getRuta()).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
