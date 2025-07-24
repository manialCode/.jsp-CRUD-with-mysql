
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@page import="entidad.Prestamos"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.RoundingMode"%>
<%
ArrayList<Prestamos> prestamosRep = null;

if(request.getAttribute("resultadoReporte2")!= null){
	prestamosRep = (ArrayList<Prestamos>)request.getAttribute("resultadoReporte2");
	%>
	Me cree
	<%
}

%>

<h2 class="Text-xl font-bold uppercase">Reportes 2</h2>
<table class="min-w-full border border-gray-300 rounded-lg">
 <% 
                if(prestamosRep == null || prestamosRep.isEmpty()){
                	%>
                	<div id="alert-no-clients" class="flex items-center p-4 mb-4 text-yellow-800 border border-yellow-300 bg-yellow-50 rounded-lg" role="alert">
			<svg class="shrink-0 w-5 h-5" aria-hidden="true"
				xmlns="http://www.w3.org/2000/svg" fill="currentColor"
				viewBox="0 0 20 20">
				<path
					d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z" />
			</svg>
			<div class="ml-3 text-base font-medium">
				No se encontraron resultados.
			</div>
			<button type="button"
				class="ml-auto -mx-1.5 -my-1.5 bg-yellow-50 text-yellow-500 rounded-lg focus:ring-2 focus:ring-yellow-400 p-1.5 hover:bg-yellow-100 inline-flex items-center justify-center h-8 w-8"
				data-dismiss-target="#alert-no-pending-loans" aria-label="Close">
				<span class="sr-only">Cerrar alerta</span>
				<svg class="w-3 h-3" aria-hidden="true"
					xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
					<path stroke="currentColor" stroke-linecap="round"
						stroke-linejoin="round" stroke-width="2"
						d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
				</svg>
			</button>
			</div>
			<%}else{
				%>
	<thead>
		<tr>
			<!-- <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Usuario</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">DNI</th> -->

			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">ID</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">CBU</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">DNI</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Fecha</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Importe
				Pedido</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Intereses</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Cant
				de cuotas</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Monto
				total</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Monto
				Mensual</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Estado</th>
		</tr>
	</thead>

	<tbody>
	 	<%
				for(Prestamos pre : prestamosRep){
				%>
				<tr class="bg-white border-b hover:bg-gray-50">
				<!-- ID -->
					<td class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap"><%=pre.getFecha_pre()%></td>
					<!-- CBU -->
					<td class="px-6 py-4"><%=pre.getCBU_pre()%></td>
					<!-- DNI -->
					<td class="px-6 py-4"><%=pre.getDni_pre()%></td>
					<!-- Fecha -->
					<td class="px-6 py-4"><%=pre.getFecha_pre()%></td>
					<!-- Importe -->
					<td class="px-6 py-4 text-green-700 font-semibold">$<%=pre.getImportePedido_pre().setScale(2, RoundingMode.HALF_UP)%></td>
					<!-- Interes -->
					<td class="px-6 py-4"><%=pre.getIntereses_pre().setScale(2, RoundingMode.HALF_UP) %>%</td>
					<!-- Cant Cuotas -->
					<td class="px-6 py-4"><%=pre.getCant_Cuotas()%></td>
					<!-- Monto total -->
					<td class="px-6 py-4 text-blue-700 font-semibold">$<%=pre.getMontoTotal_pre().setScale(2, RoundingMode.HALF_UP)%></td>
					<!-- Monto Mensual-->
					<td class="px-6 py-4 text-blue-600">$<%=pre.getMontoMensual_pre().setScale(2, RoundingMode.HALF_UP)%></td>
					
					<!-- Estado -->
					<td class="px-6 py-4">
						<%
							String estado = pre.getEstado_pre();
							String bgColorClass = "";
							String textColorClass = "";

							if ("Aprobado".equalsIgnoreCase(estado)) {
								bgColorClass = "bg-green-100";
								textColorClass = "text-green-800";
							} else if ("Rechazado".equalsIgnoreCase(estado)) {
								bgColorClass = "bg-red-100";
								textColorClass = "text-red-800";
							} else if ("Pago".equalsIgnoreCase(estado)) { // Asumiendo que "Pago" es un estado final distinto
								bgColorClass = "bg-blue-100"; // O un color que indique finalización, como gris o azul
								textColorClass = "text-blue-800";
							} else {
								// Estado por defecto o desconocido
								bgColorClass = "bg-gray-100";
								textColorClass = "text-gray-800";
							}
						%>
						<span class="text-xs font-medium px-2.5 py-0.5 rounded-full <%= bgColorClass %> <%= textColorClass %>">
							<%= estado %>
						</span>
					</td>
				</tr>
			<%} }%>
	</tbody>
</table>