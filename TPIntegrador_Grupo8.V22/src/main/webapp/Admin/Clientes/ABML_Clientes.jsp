<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Clientes" %>
<%@ page import="entidad.Provincias" %>
<%@ page import="entidad.Localidades" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administrador - ABML Clientes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans">

<jsp:include page="../navbarAdmin.jsp">
    <jsp:param name="pagina" value="<%= Pagina.CLIENTES.getValor() %>" />
</jsp:include>

<div class="max-w-7xl mx-auto mt-10 bg-white p-8 rounded-xl shadow-md">
    <div class="text-xl font-bold mb-6">Gestión de Clientes</div>

    <!-- Botones para elegir acción -->
    <div class="flex flex-col sm:flex-row gap-4 mb-6">
        <button class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-md transition-colors" onclick="mostrarFormulario('crear')" type="submit">Crear Cliente</button>
        <button class="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-6 rounded-md transition-colors" onclick="mostrarFormulario('modificar')" type="submit">Modificar o Eliminar Cliente</button>
    </div>
    
    
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
    <form id="form-crear" 
      class="<%= request.getAttribute("formularioAlta") != null ? "" : "hidden" %> space-y-4" 
      method="post" 
      action="ClienteServlet">
      
        <hr class="my-6 border-t-2 border-blue-500">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
                <label class="block text-gray-700 mb-1">Usuario</label>
                <input type="text" name="usuario" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
           </div> 
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
                <label class="block text-gray-700 mb-1">Contraseña</label>
                <input type="password" name="contrasenia" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
            <div>
            	<label class="block text-gray-700 mb-1">Repetir Contraseña</label>
                <input type="password" name="contrasenia" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
        </div>
        <hr class="my-6 border-t-2 border-blue-500">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
                <label class="block text-gray-700 mb-1">Nombre</label>
                <input type="text" 
		           name="nombre" 
		           required 
		           pattern="^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"
		           title="Solo se permiten letras y espacios."
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
            <div>
                <label class="block text-gray-700 mb-1">Apellido</label>
                <input type="text" 
		           name="apellido" 
		           required 
		           pattern="^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"
		           title="Solo se permiten letras y espacios."
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" /> 
            </div>
            <div>
                <label class="block text-gray-700 mb-1">Sexo</label>
                <select name="sexo" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
                    <option value="">Seleccione</option>
                    <option value="1">Masculino</option>
                    <option value="2">Femenino</option>
                    <option value="3">Otro</option>
                </select>
            </div>
            <div>
                <label class="block text-gray-700 mb-1">DNI</label>
                <input type="text" 
		           name="dni" 
		           required 
		           pattern="^[0-9]{7,8}$"
		           title="Debe ingresar exactamente 7 o 8 dígitos numéricos."
		           maxlength="8"
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" /> 
            </div>
            <div>
                <label class="block text-gray-700 mb-1">CUIL</label>
                <input type="text" 
		           name="cuil" 
		           required 
		           pattern="^[0-9]{9,10,11}$"
		           title="Debe ingresar exactamente de 9 a 11 dígitos numéricos."
		           maxlength="11"
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" /> 
            </div>
            <div>
                <label class="block text-gray-700 mb-1">Correo electrónico</label>
                <input type="email" name="correo" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
            <div>
                <label class="block text-gray-700 mb-1">Fecha de nacimiento</label>
                <input type="date" name="fechaNacimiento" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
            <div>
                <label class="block text-gray-700 mb-1">Dirección</label>
                <input type="text" name="direccion" class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
            <div>
            <div>
    <label class="block text-gray-700 mb-1">Provincia</label>
    <select onchange="MostrarLocalidades()" name="provincia" id="provincia" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
        <option value="">Seleccione</option>
        <%
            ArrayList<Provincias> listaProvincias = (ArrayList<Provincias>) request.getAttribute("listaProvincias");
            if (listaProvincias != null) {
                for (Provincias prov : listaProvincias) {
        %>
            <option value="<%= prov.getId_prov() %>"><%= prov.getDescripcion_prov() %></option>
        <%
                }
            }
        %>
    </select>
</div>
<div>
    <label class="block text-gray-700 mb-1">Localidad</label>
    <select name="localidad" id="localidad" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
        <option value="">Seleccione una provincia primero</option>
        <%
        ArrayList<Localidades> listaLocalidades = (ArrayList<Localidades>) request.getAttribute("listaLocalidades");
        if (listaLocalidades != null) {
            for (Localidades localidad : listaLocalidades){
        %>
        <option disabled value="<%= localidad.getId_loc() %>" class = "sinUsar provincia<%= localidad.getIdPro_loc()%>"> <%= localidad.getDescripcion_loc() %> </option>
        <%
            }
        }
        %>
    </select>
</div>
</div>
            <div>
                <label class="block text-gray-700 mb-1">Teléfonos</label>
                <input type="text" 
		           name="telefonos" 
		           required 
		           pattern="^[0-9]{6,15}$"
		           title="Debe ingresar entre 6 a 15 dígitos numéricos."
		           maxlength="11"
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            </div>
        </div>
        <div class="flex flex-col sm:flex-row gap-2 justify-end pt-2">
            <button type="submit" name="accion" value="alta" class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Guardar Cliente</button>
            <button type="reset" class="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Limpiar</button>
        </div>
    </form>

    <!-- Formulario de Modificación / Baja -->
<form id="form-modificar" method="get" action="ClienteServlet">
    <div>
        <hr class="my-6 border-t-2 border-orange-400">
        
        <!-- Filtros para buscar cuentas -->

<div class="bg-orange-50 p-4 rounded-md border border-orange-200 mb-4">
    <h4 class="font-semibold mb-3 text-orange-800">Filtros de búsqueda</h4>
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">       

        <!-- Dni -->
        <div>
            <label class="block text-sm mb-1 text-gray-700">Dni</label>            
            <input type="text" name="filtroDni" placeholder="DNI del cliente..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
        </div>

        <!-- Apellido -->
        <div>
            <label class="block text-sm mb-1 text-gray-700">Apellido</label>
            <input type="text" name="filtroApellido" placeholder="Apellido del cliente..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
        </div>
        
        <!-- Correo -->
        <div>
            <label class="block text-sm mb-1 text-gray-700">Correo</label>
            <input type="text" name="filtroCorreo" placeholder="Correo del cliente..." class="w-full px-3 py-2 border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-orange-200" />
        </div>

    </div>
    
    <!-- Botón de búsqueda -->
    <div class="mt-4 flex justify-end">
        <button type="submit" name="accion" value="buscar" class="bg-orange-600 hover:bg-orange-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Buscar</button>
    </div>
	</div>
    </div>
   </form>
        <div id="lista-clientes" class="overflow-x-auto">
   		<jsp:include page="ListaClientes.jsp" />
        </div>
   </div>
   <script>
   function mostrarFormulario(tipo) {
	    document.getElementById("form-crear").classList.add("hidden");
	    document.getElementById("form-modificar").classList.add("hidden");
	    document.getElementById("lista-clientes").classList.add("hidden");

	    if (tipo === 'crear') {
	        document.getElementById("form-crear").classList.remove("hidden");
	    } else if (tipo === 'modificar') {
	        document.getElementById("form-modificar").classList.remove("hidden");
	        document.getElementById("lista-clientes").classList.remove("hidden");
	    }
    }
    
    function MostrarLocalidades(){
    	var provincia = document.getElementById("provincia");
    	var id = provincia.options[provincia.options.selectedIndex].value;
    	var sinUsar = document.getElementsByClassName("sinUsar");
    	for(let localidad of sinUsar){
    		localidad.setAttribute("disabled",true)
    	}
    	var provincia = "provincia" + id;
    	var usando = document.getElementsByClassName(provincia);
    	for(let localidad of usando){
    		localidad.removeAttribute("disabled");
    	}
    	var select = document.getElementById("localidad");
		select.options.selectedIndex = 0;
    }
    
</script>

</body>
</html>