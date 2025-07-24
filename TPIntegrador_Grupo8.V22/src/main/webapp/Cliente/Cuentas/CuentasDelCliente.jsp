<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="util.Pagina"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />  
  <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
  <title>Cuentas del Cliente</title>
</head>
<body>
<jsp:include page="../navbarClientes.jsp">
    <jsp:param name="pagina" value="<%= Pagina.INICIO.getValor() %>" />
</jsp:include>

<div class="max-w-xl mx-auto mt-10 p-6 bg-white border border-gray-200 rounded-lg shadow">
  <h2 class="text-2xl font-bold text-gray-800 mb-4">Mis Cuentas</h2>

  <div class="mb-4">
    <label for="cuentaSeleccionada" class="block mb-1 text-sm font-medium text-gray-700">
      Seleccione una cuenta
    </label>
    <select id="cuentaSeleccionada" name="cuentaSeleccionada" class="w-full border border-gray-300 p-2 rounded">
      <option value="">-- Seleccionar --</option>
      <option value="1234567890123456789012">1234567890123456789012 - Cuenta Corriente</option>
      <option value="9876543210987654321098">9876543210987654321098 - Caja de Ahorro</option>
      <option value="1122334455667788990011">1122334455667788990011 - Cuenta Sueldo</option>
    </select>
  </div>

  <div class="mt-6 p-4 bg-gray-100 border border-gray-300 rounded">
    <p class="text-gray-700">
      <strong>Saldo de la cuenta 1234567890123456789012:</strong> $15,350.75
    </p>
  </div>

</div>

</body>
</html>