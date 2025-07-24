<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@page import="entidad.Cuotas"%>
<%@page import="entidad.Cuentas"%>
<%@page import="entidad.Clientes"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="negocioImpl.CuentasNegocioImpl"%>
<%@page import="negocio.CuotaNegocio"%>
<%@page import="negocioImpl.CuotaNegocioImpl"%>
            
        <%
		Clientes clienteLogueado = null;
		CuentaNegocio cuentaNegocio = new CuentasNegocioImpl();
		ArrayList<Cuentas> cuentas = null;
		CuotaNegocio cuotaNegocio = new CuotaNegocioImpl();
		ArrayList<Cuotas> cuotas = null;
		
		if (session.getAttribute("clienteLogueado") != null) {
			clienteLogueado = (Clientes) session.getAttribute("clienteLogueado");
		}
		
		cuentas = cuentaNegocio.getByDNI(clienteLogueado.getDni_cli());
		cuotas = cuotaNegocio.getByDNI(clienteLogueado.getDni_cli());
		%>


 <form id="PagosPrestamos" class="hidden space-y-4" method="post" action="<%= request.getContextPath() %>/CuotasServlet">
    	<hr class="my-6 border-t-2 border-orange-400">
    <h2 class="text-lg font-semibold mb-4 text-black-600">Cuotas Pendientes</h2>
    
	    <% if (request.getAttribute("mensajeExito") != null) { %>
	    <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative my-4">
	        <%= request.getAttribute("mensajeExito") %>
	    </div>
		<% } else if (request.getAttribute("error") != null) { %>
		    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative my-4">
		        <%= request.getAttribute("error") %>
		    </div>
		<% } %>

    <!-- Tabla de cuotas -->
    <table class="w-full border-collapse border border-gray-200 text-left text-sm mb-4">
    <thead class="bg-orange-100 text-orange-800">
        <tr>
            <th class="p-2 border border-gray-200">Seleccionar</th>
            <th class="p-2 border border-gray-200">Número Cuota</th>
            <th class="p-2 border border-gray-200">Importe</th>
            <th class="p-2 border border-gray-200">Vencimiento</th>
            <th class="p-2 border border-gray-200">Estado</th>
        </tr>
    </thead>
    <tbody>
     		
    
        <!-- Cuota pendiente -->
        <% for(Cuotas cuota : cuotas){ 
        if (!cuota.isEstado_cuo()) { %> 
        <tr class="cuotas-row">
        	<td class="p-2 border border-gray-200 text-center">
                <input type="checkbox" name="cuotasSeleccionadas" value="<%= cuota.getId_cuo() %>" />
            </td>
            <td class="px-4 py-2 border-b border-gray-200"><%=cuota.getId_cuo() %></td>
			<td class="px-4 py-2 border-b border-gray-200"><%=cuota.getMonto_cuo() %></td>
			<td class="px-4 py-2 border-b border-gray-200"><%=cuota.getFechaVencimiento_cuo() %></td>
			<td class="px-4 py-2 border-b border-gray-200"><%=cuota.isEstado_cuo() ? "Pagado" : "Pendiente" %></td>					        			        			
        </tr>      
		<%} 
        }
		%>

    </tbody>
</table>

    <!-- Seleccionar cuenta para debitar -->
    <div>
        <label class="block text-gray-700 mb-1">Cuenta de donde se debitará:</label>
        <select
				name="cuenta" required
				class="w-full border border-gray-300 rounded-lg p-2">
				<option value="xx">Seleccione una cuenta</option>
				<% for(Cuentas cuenta : cuentas){ %>
					<option value="<%=cuenta.getCBU_cu()%>"><%=cuenta.getCBU_cu() %></option>
			<%} %>
		</select>
    </div>

    <!-- Botón confirmar pago -->
    <button type="submit" class="w-full bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-6 rounded-md transition-colors mt-4">
        Pagar Cuotas Seleccionadas
    </button>
    </form>