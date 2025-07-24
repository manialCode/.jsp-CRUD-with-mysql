<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidad.Clientes"%>
<%@page import="entidad.Cuentas"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="negocioImpl.CuentasNegocioImpl"%>
<%@page import="util.TipoMovimiento"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Historial de Movimientos</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

	<!-- NAVBAR -->
	<jsp:include page="../navbarClientes.jsp">
		<jsp:param name="pagina" value="<%=Pagina.MOVIMIENTOS.getValor()%>" />
	</jsp:include>
	<%
	Clientes clienteLogueado = null;
	CuentaNegocio cuentaNegocio = new CuentasNegocioImpl();
	ArrayList<Cuentas> cuentas = null;
	
	if (session.getAttribute("clienteLogueado") != null) {
		clienteLogueado = (Clientes) session.getAttribute("clienteLogueado");
	}
	
	cuentas = cuentaNegocio.getByDNI(clienteLogueado.getDni_cli());
	%>
	<!-- CONTENIDO -->
	<div class="max-w-6xl mx-auto mt-10 bg-white rounded-lg shadow-md p-6">
		<h2 class="text-2xl font-bold mb-6">Historial de Movimientos</h2>

		<!-- Filtros -->
		<form action="MovimientosServlet" method="get"
			class="bg-orange-100 rounded-lg p-4 mb-6">
			<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
				<div>
					<label class="block mb-1 font-medium">Seleccionar cuenta</label> 
					<select
						name="cuenta" required
						class="w-full border border-gray-300 rounded-lg p-2">
						<option value="xx">Seleccione una cuenta</option>
						<% for(Cuentas cuenta : cuentas){
							%>
							<option value="<%=cuenta.getCBU_cu()%>"><%=cuenta.getCBU_cu() %></option>
					<%} %>
					</select>
				</div>
				<div>
					<label class="block mb-1 font-medium">Tipo de movimiento</label> <select
						name="tipoMovimiento"
						class="w-full border border-gray-300 rounded-lg p-2">
						<option value="0">Todos</option>
						<option value="<%=TipoMovimiento.ALTA_CUENTA.getId()%>"><%=TipoMovimiento.ALTA_CUENTA.getDescripcion()%></option>
						<option value="<%=TipoMovimiento.ALTA_PRESTAMO.getId()%>"><%=TipoMovimiento.ALTA_PRESTAMO.getDescripcion()%></option>
						<option value="<%=TipoMovimiento.PAGO_PRESTAMO.getId()%>"><%=TipoMovimiento.PAGO_PRESTAMO.getDescripcion()%></option>
						<option value="<%=TipoMovimiento.TRANSFERENCIA.getId()%>"><%=TipoMovimiento.TRANSFERENCIA.getDescripcion()%></option>
					</select>
				</div>
			</div>
			<div class="text-right mt-4">
				<button type="submit"
						name = "accion"
						value = "buscar"
					class="bg-orange-500 text-white px-6 py-2 rounded-md hover:bg-orange-600 font-semibold">Buscar</button>
			</div>
		</form>

		<!-- Tabla de movimientos -->
		<div class="overflow-x-auto">
			<jsp:include page="ListaMovimientos.jsp" />
		</div>
	</div>

</body>
</html>
