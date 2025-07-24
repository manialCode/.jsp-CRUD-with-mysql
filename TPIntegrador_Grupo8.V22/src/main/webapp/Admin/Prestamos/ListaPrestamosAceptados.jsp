<%@page import="java.math.RoundingMode"%>
<%@page import="entidad.Prestamos"%>
<%@page import="negocio.ClienteNegocio"%>
<%@page import="negocioImpl.ClienteNegocioImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
List<Prestamos> prestamosAprobados = new ArrayList<>();
if (request.getAttribute("listaPrestamos") != null) {
	prestamosAprobados = (List<Prestamos>) request.getAttribute("listaPrestamos");
}

ClienteNegocio cliNeg = new ClienteNegocioImpl();
String debugMessage = (String) request.getAttribute("debug");
%>

<div class="container mx-auto my-5 p-4 bg-white rounded-lg shadow-sm">
	<%-- Contenedor principal con fondo blanco y sombra sutil --%>

	<h2 class="text-xl font-bold text-gray-800 mb-6 border-b pb-3">Préstamos
		Aprobados</h2>

	<%
	if (prestamosAprobados == null || prestamosAprobados.isEmpty()) {
	%>
	<div id="alert-no-accepted-loans"
		class="flex items-center p-4 mb-4 text-green-700 border border-green-300 bg-green-50 rounded-lg"
		role="alert">
		<svg class="shrink-0 w-5 h-5" aria-hidden="true"
			xmlns="http://www.w3.org/2000/svg" fill="currentColor"
			viewBox="0 0 20 20">
				<path
				d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z" />
			</svg>
		<div class="ml-3 text-base font-medium">
			No hay préstamos Aprobados para mostrar en este momento. Puedes <a
				href="URL_AQUI_PARA_HISTORIAL"
				class="font-semibold underline hover:no-underline text-green-600">
				ver el historial de todas tus solicitudes </a>.
		</div>
		<button type="button"
			class="ml-auto -mx-1.5 -my-1.5 bg-green-50 text-green-500 rounded-lg focus:ring-2 focus:ring-green-400 p-1.5 hover:bg-green-100 inline-flex items-center justify-center h-8 w-8"
			data-dismiss-target="#alert-no-accepted-loans" aria-label="Close">
			<span class="sr-only">Cerrar alerta</span>
			<svg class="w-3 h-3" aria-hidden="true"
				xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
					<path stroke="currentColor" stroke-linecap="round"
					stroke-linejoin="round" stroke-width="2"
					d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
				</svg>
		</button>
	</div>
	<%
	if (debugMessage != null && !debugMessage.isEmpty()) {
	%>
	<p class="text-sm text-gray-500 mt-2 text-center">
		Debug:
		<%=debugMessage%></p>
	<%
	}
	%>
	<%
	} else {
	%>
	<div class="overflow-x-auto shadow-md rounded-lg">
		<table class="w-full text-sm text-left text-gray-700">
			<thead class="text-xs text-gray-700 uppercase bg-green-50">
				<tr>
					<th scope="col" class="px-6 py-3">Fecha</th>
					<th scope="col" class="px-6 py-3">Cliente</th>
					<th scope="col" class="px-6 py-3">Cuenta destino</th>
					<th scope="col" class="px-6 py-3">Monto Solicitado</th>
					<th scope="col" class="px-6 py-3">Cuotas</th>
					<th scope="col" class="px-6 py-3">Interés</th>
					<th scope="col" class="px-6 py-3">Importe a devolver</th>
					<th scope="col" class="px-6 py-3">Importe x mes</th>
					<th scope="col" class="px-6 py-3">Estado</th>
				</tr>
			</thead>
			<tbody>
				<%
				for (Prestamos pre : prestamosAprobados) {
					if (pre.getEstado_pre() != null && pre.getEstado_pre().equalsIgnoreCase("Aprobado")) {

						String nombreCompletoCliente = "N/A";
						if (pre.getDni_pre() != null) {
					entidad.Clientes clienteObj = cliNeg.getByDNI(pre.getDni_pre());
					if (clienteObj != null) {
						nombreCompletoCliente = clienteObj.getNombre_cli() + " " + clienteObj.getApellido_cli();
					}
						}
				%>
				<tr class="bg-white border-b hover:bg-gray-50">
					<td class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap"><%=pre.getFecha_pre()%></td>
					<td class="px-6 py-4"><%=nombreCompletoCliente%></td>
					<td class="px-6 py-4"><%=pre.getCBU_pre()%></td>
					<td class="px-6 py-4 text-green-700 font-semibold">$<%=pre.getImportePedido_pre().setScale(2, RoundingMode.HALF_UP)%></td>
					<td class="px-6 py-4"><%=pre.getCant_Cuotas()%></td>
					<td class="px-6 py-4"><%=pre.getIntereses_pre().setScale(2, RoundingMode.HALF_UP)%>%</td>
					<td class="px-6 py-4 text-blue-700 font-semibold">$<%=pre.getMontoTotal_pre().setScale(2, RoundingMode.HALF_UP)%></td>
					<td class="px-6 py-4 text-blue-600">$<%=pre.getMontoMensual_pre().setScale(2, RoundingMode.HALF_UP)%></td>
					<td class="px-6 py-4"><span
						class="bg-green-100 text-green-800 text-xs font-medium px-2.5 py-0.5 rounded-full">
							<%=pre.getEstado_pre()%>
					</span></td>
				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>
	<%
	}
	%>

</div>