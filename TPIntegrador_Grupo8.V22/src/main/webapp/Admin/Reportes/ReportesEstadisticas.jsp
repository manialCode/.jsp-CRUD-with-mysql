<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reportes y Estadísticas</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans">

<!-- NAVBAR -->
<jsp:include page="../navbarAdmin.jsp">
    <jsp:param name="pagina" value="<%= Pagina.REPORTES.getValor() %>" />
</jsp:include>
<div class="max-w-7xl mx-auto mt-10 p-6 bg-white shadow-md rounded-xl">

  <h1 class="text-2xl font-bold mb-6">Reportes y Estadísticas</h1>

  <!-- REPORTE: ingresos/egresos -->
<div class="mb-8 border p-4 rounded-lg shadow-sm">
  <h2 class="text-xl font-semibold mb-4">Total de ingresos y egresos</h2>
  <form action="ReportesServlet" method="get" class="flex flex-wrap gap-4 items-end">
    <input type="hidden" name="accion" value="reporte1" />
    <div>
      <label class="block text-sm font-medium">Fecha inicio:</label>
      <input type="date" name="fechaInicio" required class="mt-1 block w-full border border-gray-300 rounded p-2" />
    </div>
    <div>
      <label class="block text-sm font-medium">Fecha fin:</label>
      <input type="date" name="fechaFin" required class="mt-1 block w-full border border-gray-300 rounded p-2" />
    </div>
    <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded-md mt-4">Generar reporte</button>
  </form>
	<jsp:include page="ListaReportes1.jsp"></jsp:include>
</div>

<!-- REPORTE: préstamos por cliente o tipo de cuenta -->
<div class="mb-8 border p-4 rounded-lg shadow-sm">
  <h2 class="text-xl font-semibold mb-4">Préstamos</h2>
  <form action="ReportesServlet" method="get" class="flex flex-wrap gap-4 items-end">
    <input type="hidden" name="accion" value="reporte2" />
    <div>
      <label class="block text-sm font-medium">DNI Cliente:</label>
      <input type="text" name="dniPre" placeholder="Ingrese DNI" class="mt-1 border border-gray-300 rounded p-2 w-48" />
    </div>
    <div>
      <label class="block text-sm font-medium">Apellido Cliente:</label>
      <input type="text" name="apellidoCli" placeholder="Ingrese apellido" class="mt-1 border border-gray-300 rounded p-2 w-48" />
    </div>
    <div>
      <label class="block text-sm font-medium">Tipo de cuenta:</label>
      <select name="idTipoCuenta" class="mt-1 border border-gray-300 rounded p-2 w-48">
        <option value="">Seleccione...</option>
        <option value="1">Caja de ahorro</option>
        <option value="2">Cuenta corriente</option>
      </select>
    </div>
    <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded-md mt-4">Generar reporte</button>
  </form>
	<jsp:include page="ListaReportes2.jsp"></jsp:include>
</div>

<!-- ESTADÍSTICA: cantidad de clientes por tipo de cuenta -->
<div class="border p-4 rounded-lg shadow-sm">
	<div class="grid grid-cols-2 place-content-center">
	<jsp:include page="ListaReportes3.jsp"></jsp:include>	
	<jsp:include page="EstadisticaPrestamos.jsp"></jsp:include>
	<jsp:include page="EstadisticaMovimientos.jsp"></jsp:include>
	</div>
</div>

</div>

</body>

</html>