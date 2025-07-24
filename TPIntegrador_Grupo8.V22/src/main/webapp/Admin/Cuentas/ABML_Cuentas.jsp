<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Cuentas" %>
<%@ page import="entidad.Clientes" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cuentas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans">

<!-- NAVBAR -->
<jsp:include page="../navbarAdmin.jsp">
    <jsp:param name="pagina" value="<%= Pagina.CUENTAS.getValor() %>" />
</jsp:include>


<div class="max-w-7xl mx-auto mt-10 bg-white p-8 rounded-xl shadow-md">
    <div class="text-xl font-bold mb-6">Gestión de Cuentas</div>

    <!-- Botones para elegir acción -->
    <form id="BotonesAccion" class="space-y-4" method="get" action="CuentasServlet">
    <div class="flex flex-col sm:flex-row gap-4 mb-6">
        <button class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-md transition-colors" onclick="mostrarFormulario('crear')" type="button">Crear Cuenta</button>
        <button class="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-6 rounded-md transition-colors" onclick="mostrarFormulario('Vizualizar')" type="submit">Visualizar Cuentas</button>
    </div>
    </form>
    
     <% 
	    String mensaje = (String) request.getAttribute("mensaje");
	    if (mensaje != null) {
		%>
		    <div class="mt-4 px-4 py-3 rounded-md text-green-800 bg-green-100 border border-green-300">
		        <%= mensaje %>
		    </div>
		<% 
		    }
		%>

    <!-- Formulario de Alta -->
    <form id="CrearCuentas" class="hidden space-y-4" method="post" action="CuentasServlet">
        <hr class="my-6 border-t-2 border-blue-500">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
                <label class="block text-gray-700 mb-1">Tipo de cuenta</label>
                <select name="TipoDeCuenta" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
                    <option value="1">Caja de ahorro</option>
                    <option value="2">Cuenta corriente</option>                    
                </select>
            </div>        
            <div>
                <label class="block text-gray-700 mb-1">Cliente a asignar</label>
                <select name="Clientes" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
				    <option value="">-- Seleccione --</option>
				    <%
				        ArrayList<Clientes> listaClientes = (ArrayList<Clientes>) request.getAttribute("listasClientes");
				        if (listaClientes != null) {
				            for (Clientes cli : listaClientes) {
				    %>
				                <option value="<%= cli.getDni_cli() %>">
					               <%= cli.getNombre_cli() %> <%= cli.getApellido_cli() %>
					           </option>
				    <%
				            }
				        }
				    %>
				</select>
            </div>
            <div>
            	<label class="block text-gray-700 mb-1">Número de cuenta</label>
            	<input type="text" 
		           name="NumeroDeCuenta" 
		           required 
		           pattern="^[0-9]{11}$"
		           title="Debe ingresar exactamente 11 dígitos numéricos."
		           maxlength="11"
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
                
            </div>
            <div>
                <label class="block text-gray-700 mb-1">CBU</label>
                <input type="text" 
		           name="cbu" 
		           required 
		           pattern="^[0-9]{20}$"
		           title="Debe ingresar exactamente 20 dígitos numéricos."
		           maxlength="20"
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
	            </div>
            <div>
                <label class="block text-gray-700 mb-1">Saldo Inicial: $10000</label>              
            </div>
        </div> 
        <div class="flex flex-col sm:flex-row gap-2 justify-end pt-2">
            <button type="submit" name="accion" value="alta" class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Crear Cuenta</button>
            <button type="reset" class="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Limpiar</button>
        </div>
    </form>
    
    <!-- Formulario para visualizar cuentas -->
    <form id="VizualizarCuentas" class="space-y-4" method="get" action="CuentasServlet">
    <hr class="my-6 border-t-2 border-orange-500">

    <h3 class="text-lg font-semibold mb-4 text-gray-800">Listado de Cuentas</h3>
    
    <!-- Filtros para buscar cuentas -->
	<div class="bg-orange-50 p-4 rounded-md border border-orange-200 mb-4">
    <h4 class="font-semibold mb-3 text-orange-800">Filtros de búsqueda</h4>
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">

        <!-- Tipo de cuenta -->
        <div>
            <label class="block text-sm mb-1 text-gray-700">Tipo de cuenta</label>
            <select name="filtroTipoCuenta" class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200">
                <option value="">Todos</option>
                <option value="CA">Caja de ahorro</option>
                <option value="CC">Cuenta corriente</option>
            </select>
        </div>

        <!-- Cliente -->
        <div>
            <label class="block text-sm mb-1 text-gray-700">DNI</label>
            <input type="text" name="filtroCliente" placeholder="Dni del cliente..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
        </div>

        <!-- CBU -->
        <div>
            <label class="block text-sm mb-1 text-gray-700">CBU</label>
            <input type="text" name="filtroCBU" placeholder="Ingrese el CBU..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
        </div>

    </div>

    <!-- Botón de búsqueda -->
    <div class="mt-4 flex justify-end">
        <button type="submit" name="accion" value="buscar" class="bg-orange-600 hover:bg-orange-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Buscar</button>
    </div>
</div>
    </form> 

    <!-- Tabla de cuentas -->
    <div id="lista-cuentas" class="overflow-x-auto">
      <jsp:include page="ListaCuentas.jsp"></jsp:include>       
    </div>	
</div>

<script>
    function mostrarFormulario(tipo) {
        document.getElementById("CrearCuentas").classList.add("hidden");
        document.getElementById("VizualizarCuentas").classList.add("hidden");
        document.getElementById("lista-cuentas").classList.add("hidden");
        if (tipo === 'crear') {
            document.getElementById("CrearCuentas").classList.remove("hidden");
        } else if (tipo === 'Vizualizar') {
            document.getElementById("VizualizarCuentas").classList.remove("hidden");
            document.getElementById("lista-cuentas").classList.add("hidden");
        }
    }
</script>

</body>
</html>