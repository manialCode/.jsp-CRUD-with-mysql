<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidad.Clientes"%>
<%@page import="entidad.Cuentas"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="negocioImpl.CuentasNegocioImpl"%>
<%@page import="util.Pagina"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cliente - Solicitar Préstamo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-sans">
<%
	Clientes clienteLogueado = null;
	CuentaNegocio cuentaNegocio = new CuentasNegocioImpl();
	ArrayList<Cuentas> cuentas = null;
	
	if (session.getAttribute("clienteLogueado") != null) {
		clienteLogueado = (Clientes) session.getAttribute("clienteLogueado");
	}
	
	cuentas = cuentaNegocio.getByDNI(clienteLogueado.getDni_cli());
	%>
<!-- NAVBAR -->
<jsp:include page="../navbarClientes.jsp">
    <jsp:param name="pagina" value="<%= Pagina.PRESTAMOS.getValor() %>" />
</jsp:include>
	<script>
    function mostrarFormulario(tipo = 'Solicitar') {
        document.getElementById("SolicitarPrestamo").classList.add("hidden");
        document.getElementById("PagosPrestamos").classList.add("hidden");
        if (tipo === 'Solicitar') {
            document.getElementById("SolicitarPrestamo").classList.remove("hidden");
        } else if (tipo === 'Pagos') {
            document.getElementById("PagosPrestamos").classList.remove("hidden");
        }
    }
	</script>


<!-- CONTENIDO PRINCIPAL -->
<div class="max-w-6xl mx-auto mt-10 bg-white rounded-lg shadow-md p-6">

	<!-- MENSAJES -->
	<%
		    String mensaje = (String) request.getAttribute("mensaje");
		    String tipo = (String) request.getAttribute("tipoMensaje");
		    if (mensaje != null && tipo != null) {
		        String colorClase = "text-green-800 bg-green-100 border-green-300";
		        if ("error".equals(tipo)) {
		            colorClase = "text-red-800 bg-red-100 border-red-300";
		        }
		%>
		    <div class="mt-4 px-4 py-3 rounded-md <%= colorClase %> border">
		        <%= mensaje %>
		    </div>
		<%
		    }
		%>
	<!-- MENSAJES -->
	
    <div class="text-xl font-bold mb-6">Préstamos</div>
    
    <div class="flex flex-col sm:flex-row gap-4 mb-6">
        <button class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-md transition-colors" onclick="mostrarFormulario('Solicitar')" type="button">Solicitar Prestamos</button>
        <button class="bg-orange-500 hover:bg-orange-600 text-white font-semibold py-2 px-6 rounded-md transition-colors" onclick="mostrarFormulario('Pagos')" type="button">Gestionar Pagos</button>
    </div>
    
    <form id="SolicitarPrestamo" class="hidden space-y-4" method="post" action="<%= request.getContextPath() %>/PrestamoServlet">
    
     <hr class="my-6 border-t-2 border-blue-500">
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
            <label class="block text-gray-700 mb-1">Cantidad de cuotas:</label>
            <select name="cuotas" class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
                <option value="3">3 cuotas</option>
                <option value="6">6 cuotas</option>
                <option value="12">12 cuotas</option>
                <option value="24">24 cuotas</option>
            </select>
        </div>
		<div>
            <label class="block text-gray-700 mb-1">Monto solicitado:</label>
            <input
			    type="number"
			    name="monto"
			    id="montoSolicitado"
			    step="500"
			    min="500"
			    value="500"
			    required
			    placeholder="Monto minimo de $500"
			    class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200" />
        </div>
        <div>
			    <label class="block text-gray-700 mb-1">Interés (%):</label>
			    <input type="text" id="interes" name="interes" readonly
			           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-50" />
			</div>
			<div>
			    <label class="block text-gray-700 mb-1">Monto total a devolver:</label>
			    <input type="text" id="montoTotal" name="montoTotal" readonly
			           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-50" />
			</div>
			<div>
			    <label class="block text-gray-700 mb-1">Monto mensual por cuota:</label>
			    <input type="text" id="montoMensual" name="montoMensual" readonly
			           class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-50" />
		</div>
        
		<input type="hidden" name="dniCli" value="<%=clienteLogueado.getDni_cli() %>"  />
        <button type="submit" name="estadoAccion" value="solicitar" class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-md transition-colors">Enviar Solicitud</button>
    </form>
   <jsp:include page="ListaCuotas.jsp"></jsp:include>
</div>

<script>
const tasas = {
    3: 10,
    6: 20,
    12: 35,
    24: 50
};

function recalcularPrestamo() {
    let monto = parseFloat(document.getElementById("montoSolicitado").value);
    let cuotas = parseInt(document.querySelector("select[name='cuotas']").value);

    if (!monto || !cuotas) return;

    let tasa = tasas[cuotas] || 0;
    let interes = monto * (tasa / 100);
    let montoTotal = monto + interes;
    let montoMensual = montoTotal / cuotas;

    document.getElementById("interes").value = tasa + "%";
    document.getElementById("montoTotal").value = montoTotal.toFixed(2);
    document.getElementById("montoMensual").value = montoMensual.toFixed(2);
}

document.getElementById("montoSolicitado").addEventListener("input", recalcularPrestamo);
document.querySelector("select[name='cuotas']").addEventListener("change", recalcularPrestamo);
document.addEventListener("DOMContentLoaded", recalcularPrestamo);
</script>
</body>
</html>