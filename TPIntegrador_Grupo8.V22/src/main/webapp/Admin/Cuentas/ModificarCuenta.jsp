<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entidad.Cuentas" %>
<%@ page import="entidad.TipoCuenta" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administrador - ABML Cuentas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans">


<!-- NAVBAR -->
<jsp:include page="../navbarAdmin.jsp">
    <jsp:param name="pagina" value="<%= Pagina.CUENTAS.getValor() %>" />
</jsp:include>
<div class="max-w-6xl mx-auto mt-10 bg-white p-8 rounded-xl shadow-md">
    <div class="text-xl font-bold mb-6">Gestión de Cuentas</div>

    <div class="flex flex-col sm:flex-row gap-4 mb-6">	
        <button onclick="window.location.href='NavegacionServlet?accion=cuentas'" class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-md transition-colors" type="button"> Volver </button>
    	<button name="accion" class="bg-orange-600 hover:bg-orange-700 text-white font-semibold py-2 px-6 rounded-md transition-colors" type="button">Modificar Cuenta</button>         
    </div>

<%
    Cuentas cuenta = (Cuentas) request.getAttribute("cuentaAModificar");
    List<TipoCuenta> listaTiposCuenta = (List<TipoCuenta>) request.getAttribute("listaTiposCuenta");
%>

<form id="form-modificar-cuenta" class="space-y-4" method="post" action="CuentasServlet">
    <hr class="my-6 border-t-2 border-orange-500">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
            <label class="block text-gray-700 mb-1">CBU</label>
            <input disabled type="text" name="cbu"
                value="<%= cuenta != null ? cuenta.getCBU_cu() : "" %>"
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-300 text-gray-500" />
            <input type="hidden" name="CBU" 
                value="<%= cuenta != null ? cuenta.getCBU_cu() : "" %>" />
        </div>
        <div>
            <label class="block text-gray-700 mb-1">DNI del Cliente</label>
            <input disabled type="text" name="dni" 
                value="<%= cuenta != null ? cuenta.getDni_cu() : "" %>"
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-300 text-gray-500" />
            <input type="hidden" name="dni" value="<%= cuenta != null ? cuenta.getDni_cu() : "" %>" />  
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Número de Cuenta</label>
            <input type="text" name="NumeroDeCuenta"
                value="<%= cuenta != null ? cuenta.getNumero_cu() : "" %>"
                required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
            <label class="block text-gray-700 mb-1">Tipo de Cuenta</label>
            <select name="TipoDeCuenta" required class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
                     <option value="1" <%= cuenta != null && cuenta.getIdTipo_cu() == 1 ? "selected" : "" %>>Caja de ahorro</option>
    				 <option value="2" <%= cuenta != null && cuenta.getIdTipo_cu() == 2 ? "selected" : "" %>>Cuenta corriente</option>                   
            </select>
        </div>
    </div>

    <div class="flex flex-col sm:flex-row gap-2 justify-end pt-2">
        <button type="submit" name="accion2" value="modificar" 
            class="bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">
            Guardar Cambios
        </button>
        <button type="reset" 
            class="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">
            Limpiar
        </button>
    </div>
</form>

</div>
</body>
</html>