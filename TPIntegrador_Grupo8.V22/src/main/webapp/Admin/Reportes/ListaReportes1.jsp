<%@page import="java.util.ArrayList"%>
<%@page import="entidad.Movimientos"%>
<%@page import="java.math.BigDecimal"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
ArrayList<Movimientos> listaMovimientos = new ArrayList<>();
if(request.getAttribute("listaReporte1") != null){
	listaMovimientos = (ArrayList<Movimientos>)request.getAttribute("listaReporte1");	
} 
request.setAttribute("listaMovimientos", listaMovimientos);
%>


<table class="min-w-full border border-gray-300 rounded-lg">
<%
		if (listaMovimientos == null || listaMovimientos.isEmpty())
		{
			%>
			<div id="alert" class="flex items-center p-4 mb-4 text-yellow-800 border border-yellow-300 bg-yellow-50 rounded-lg" role="alert">
			<svg class="shrink-0 w-5 h-5" aria-hidden="true"
				xmlns="http://www.w3.org/2000/svg" fill="currentColor"
				viewBox="0 0 20 20">
				<path
					d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z" />
			</svg>
			<div class="ml-3 text-base font-medium">
				No hay Movimientos registrados.
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
			<%
		}
		else{
			%>
	<thead>
		<tr>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">CBU</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Fecha</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Importe</th>
			<th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Tipo</th>
		</tr>
	</thead>

	<tbody>
		
			<%
			for (Movimientos mov : listaMovimientos) {
			boolean isPositive = mov.getImporte_mo().compareTo(java.math.BigDecimal.ZERO) > 0;

		%>
		<tr>
			<td class="px-4 py-2 border-b border-gray-200"><%=mov.getCBU_mo()%></td>
			<td class="px-4 py-2 border-b border-gray-200"><%=mov.getFecha_mo()%></td>
			<td class="px-4 py-2 border-b border-gray-200 font-bold <%=isPositive ? "text-green-400" : "text-red-400" %>">$<%=mov.getImporte_mo()%></td>
			<td class="px-4 py-2 border-b border-gray-200"><%=mov.getTipoDeMovimiento_mo()%></td>
		</tr>
		<%
		}
		}
		%>
	</tbody>
</table>

