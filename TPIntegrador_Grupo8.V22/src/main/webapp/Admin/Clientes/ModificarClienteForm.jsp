<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
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
<div class="max-w-4xl mx-auto mt-10 bg-white p-8 rounded-xl shadow-md">
    <div class="text-xl font-bold mb-6">Gestión de Clientes</div>

    
    <div class="flex flex-col sm:flex-row gap-4 mb-6">
    <button onclick="window.location.href='NavegacionServlet?accion=clientes'" class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-md transition-colors" type="button"> Volver </button>
    <button class="bg-orange-600 hover:bg-orange-700 text-white font-semibold py-2 px-6 rounded-md transition-colors" type="button"> Modificar Cliente </button>             
    </div>

<%
	Clientes cliente = (Clientes) request.getAttribute("clienteAModificar");
%>

<script type="text/javascript">
    var idProvinciaCliente = <%= cliente != null ? cliente.getIdProvincia_cli() : "null" %>;
    var idLocalidadCliente = <%= cliente != null ? cliente.getIdLocalidad_cli() : "null" %>;
</script>

    <!-- Formulario -->
   <form id="form-crear" class="space-y-4" method="post" action="ClienteServlet">
    <hr class="my-6 border-t-2 border-orange-500">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
            <label class="block text-gray-700 mb-1">Usuario</label>
            <input disabled type="text" name="usuario" 
                placeholder="<%= cliente != null ? cliente.getUsuario().getUsuario_us() : "" %>" 
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            <input type="hidden" name="idUsuario" value="<%= cliente != null ? cliente.getUsuario().getId_us() : "" %>" />    
        </div>
    </div> 
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
            <label class="block text-gray-700 mb-1">Contraseña</label>
            <input type="password" name="contrasenia"
                placeholder="<%= cliente != null ? cliente.getUsuario().getContrasena_us() : "" %>" 
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Repetir Contraseña</label>
            <input type="password" name="repetirContrasenia"
                placeholder="<%= cliente != null ? cliente.getUsuario().getContrasena_us() : "" %>" 
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
    </div>
    <hr class="my-6 border-t-2 border-orange-500">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
            <label class="block text-gray-700 mb-1">Nombre</label>
            <input type="text" name="nombre" 
                value="<%= cliente != null ? cliente.getNombre_cli() : "" %>" 
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Apellido</label>
            <input type="text" name="apellido" 
                value="<%= cliente != null ? cliente.getApellido_cli() : "" %>" 
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
		    <label class="block text-gray-700 mb-1">Sexo</label>
		    <input type="text" 
		           disabled 
		           value="<%= cliente != null 
		               ? (cliente.getSexo_cli() == 1 ? "Masculino" 
		                 : cliente.getSexo_cli() == 2 ? "Femenino" 
		                 : cliente.getSexo_cli() == 3 ? "Otro" 
		                 : "") 
		               : "" %>" 
		           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
		</div>
        <div>
            <label class="block text-gray-700 mb-1">DNI</label>
            <input disabled type="text" name="dni" 
                value="<%= cliente != null ? cliente.getDni_cli() : "" %>" 
                 required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
            <input type="hidden" name="dni" value="<%= cliente != null ? cliente.getDni_cli() : "" %>" />     
        </div>
        <div>
            <label class="block text-gray-700 mb-1">CUIL</label>
            <input disabled type="text" name="cuil" 
                value="<%= cliente != null ? cliente.getCuil_cli() : "" %>" 
                 required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Correo electrónico</label>
            <input type="email" name="correo" 
                value="<%= cliente != null ? cliente.getCorreo_cli() : "" %>" 
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Fecha de nacimiento</label>
            <input type="date" name="fechaNacimiento" 
                value="<%= cliente != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(cliente.getFecha_nacimiento_cli()) : "" %>" 
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Dirección</label>
            <input type="text" name="direccion" 
                value="<%= cliente != null ? cliente.getDireccion_cli() : "" %>" 
                class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
            <div>
                <label class="block text-gray-700 mb-1">Provincia</label>
                <select name="provincia" id="provincia" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
			    <option value="">Seleccione</option>
			    <%
			        List<Provincias> listaProvincias = (List<Provincias>) request.getAttribute("listaProvincias");
			        if (listaProvincias != null) {
			            for (Provincias prov : listaProvincias) {
			    %>
			                <option value="<%= prov.getId_prov() %>" <%= (cliente != null && cliente.getIdProvincia_cli() == prov.getId_prov()) ? "selected" : "" %>>
			                    <%= prov.getDescripcion_prov() %>
			                </option>
			    <%
			            }
			        }
			    %>
			</select>
            </div>
            <label class="block text-gray-700 mb-1">Localidad</label>
            <select name="localidad" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
			    <option value="">Seleccione</option>
			    <%
			        List<Localidades> listaLocalidades = (List<Localidades>) request.getAttribute("listaLocalidades");
			        if (listaLocalidades != null) {
			            for (Localidades loc : listaLocalidades) {
			                boolean seleccionada = (cliente != null && cliente.getIdLocalidad_cli() == loc.getId_loc());
			    %>
			        <option 
			            value="<%= loc.getId_loc() %>" 
			            data-idprovincia="<%= loc.getIdPro_loc() %>"
			            <%= seleccionada ? "selected" : "" %>>
			            <%= loc.getDescripcion_loc() %>
			        </option>
			    <%
			            }
			        }
			    %>
			</select>
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Teléfonos</label>
            <input type="text" name="telefono" 
                value="<%= cliente != null ? cliente.getTelefono_cli() : "" %>" 
                class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
    </div>
    <div class="flex flex-col sm:flex-row gap-2 justify-end pt-2">
        <button type="submit" name="accion2" value="modificar" class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Guardar Cliente</button>
        <button type="reset" class="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Limpiar</button>
    </div>
</form>

</div>

<script>
document.addEventListener('DOMContentLoaded', () => {
    const provinciaSelect = document.querySelector('select[name="provincia"]');
    const localidadSelect = document.querySelector('select[name="localidad"]');
    
    function filtrarLocalidades() {
        const idProvincia = provinciaSelect.value;
        const options = localidadSelect.querySelectorAll('option');
        
        options.forEach(option => {
            if (!option.value) return; 
            const idProv = option.dataset.idprovincia;
            option.style.display = (idProvincia === "" || idProvincia === idProv) ? 'block' : 'none';
        });
        
        
        if (localidadSelect.selectedOptions.length > 0) {
            const selectedOption = localidadSelect.selectedOptions[0];
            if (selectedOption.style.display === 'none') {
                localidadSelect.value = "";
            }
        }
    }    
    provinciaSelect.addEventListener('change', filtrarLocalidades);
    filtrarLocalidades(); 
});
</script>

</body>
</html>