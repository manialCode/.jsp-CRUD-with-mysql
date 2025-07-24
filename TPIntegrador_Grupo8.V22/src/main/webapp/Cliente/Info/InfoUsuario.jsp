<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="util.Pagina"%>
<%@page import="entidad.Clientes"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@page import="entidad.Clientes"%>
<%@page import="entidad.Provincias"%>
<%@page import="entidad.Localidades"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />  
  <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
  <title>Informacion de usuario</title>
</head>
<body>
<jsp:include page="../navbarClientes.jsp">
    <jsp:param name="pagina" value="<%= Pagina.INICIO.getValor() %>" />
</jsp:include>

<%
    Clientes cliente = (Clientes) session.getAttribute("clienteLogueado");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>


<div class="max-w-xl mx-auto mt-10 p-6 bg-white border border-gray-200 rounded-lg shadow">
  <h2 class="text-2xl font-bold text-gray-800 mb-4">Información Personal</h2>

  <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">DNI</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getDni_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">CUIL</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getCuil_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Nombre</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getNombre_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Apellido</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getApellido_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Fecha de nacimiento</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded">
        <%= cliente != null && cliente.getFecha_nacimiento_cli() != null ? sdf.format(cliente.getFecha_nacimiento_cli()) : "" %>
      </p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Dirección</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getDireccion_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Correo</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getCorreo_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Teléfonos</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getTelefono_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Localidad</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getIdLocalidad_cli() : "" %></p>
    </div>

    <div>
      <label class="block mb-1 text-sm font-medium text-gray-700">Provincia</label>
      <p class="bg-gray-100 text-gray-800 p-2 rounded"><%= cliente != null ? cliente.getIdProvincia_cli() : "" %></p>
    </div>
  </div>
</div>

</body>
</html>