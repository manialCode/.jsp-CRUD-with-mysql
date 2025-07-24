<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administrador - Autorización de Préstamos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans">
<!-- NAVBAR -->
<jsp:include page="../navbarAdmin.jsp">
    <jsp:param name="pagina" value="<%= Pagina.PRESTAMOS.getValor() %>" />
</jsp:include>
<!-- Prestamos -->
<div class="flex justify-center mx-10">
    <div class="bg-white p-8 rounded-xl shadow-md w-auto inline-block">
    	<div class="text-xl font-bold mb-6">Solicitudes de Préstamos</div>
    	
    	        <!-- Filtros para buscar cuentas -->
    	<form action="PrestamoServlet" method="post">
		<div class="bg-orange-50 p-4 rounded-md border border-orange-200 mb-4">
		    <h4 class="font-semibold mb-3 text-orange-800">Filtros de búsqueda</h4>
		    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">       
		
		        <!-- Fecha -->
		        <div>
		            <label class="block text-sm mb-1 text-gray-700">Fecha</label>
		            <input type="date" name="filtroFecha" placeholder="ingrese la fecha..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
		        </div>
		
		        <!-- Nombre y Apellido -->
		        <div>
		            <label class="block text-sm mb-1 text-gray-700">Apellido</label>
		            <input type="text" name="filtroApellido" placeholder="Ingrese el nombre y apellido..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
		        </div>
		        		        
		        
		        <!-- Intereses ganados -->
		        <div>
		            <label class="block text-sm mb-1 text-gray-700">Intereses</label>
		            <input type="text" name="filtroInteres" placeholder="Ingrese los intereses..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
		        </div>
		
		    </div>
		
		    <!-- Botón de búsqueda -->
		    <div class="mt-4 flex justify-end">
		        <button type="submit" name="estadoAccion" value="filtrar" class="bg-orange-600 hover:bg-orange-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">
		            Buscar
		        </button>
		    </div>
		</div>
		</form>
        <jsp:include page="ListaPrestamos.jsp"></jsp:include>
        <jsp:include page="ListaPrestamosAceptados.jsp"></jsp:include>
        <jsp:include page="ListaPrestamosRechazados.jsp"></jsp:include>
        </div>
    </div>
</body>
</html>