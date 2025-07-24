<%@page import="util.Pagina"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.ArrayList"%>
<%@page import="entidad.Clientes"%>
<%@page import="entidad.Cuentas"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="negocioImpl.CuentasNegocioImpl"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Cliente - Transferencias</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
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
    <jsp:param name="pagina" value="<%= Pagina.TRANSFERENCIAS.getValor() %>" />
</jsp:include>
<!-- CONTENIDO -->
<div class="max-w-6xl mx-auto mt-10 bg-white rounded-lg shadow-md p-6">
    <h2 class="text-2xl font-bold mb-6">Realizar Transferencia</h2>
    
	
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
		

    <form action="TransferenciaServlet" method="post" class="bg-orange-100 p-4 rounded-lg mb-6">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
          	<label class="block mb-1 font-medium">Cuenta Origen</label> 
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
                <label class="block font-semibold mb-1">Transferir a</label>
                <select id="clienteSelect" name="dniSeleccionado" onchange="this.form.submit()" required
			        class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
			        <option value="">Seleccione</option>
			        <%
			            ArrayList<Clientes> listaClientes = (ArrayList<Clientes>) request.getAttribute("listaClientess");
			            String dniSeleccionado = request.getParameter("dniSeleccionado");
			            if (listaClientes != null) {
			                for (Clientes cliente : listaClientes) {
			                
			                    boolean selected = dniSeleccionado != null && dniSeleccionado.equals(cliente.getDni_cli());
			        %>
			        
			            <option value="<%= cliente.getDni_cli() %>" <%= selected ? "selected" : "" %>>
			                <%= cliente.getNombre_cli() %> <%= cliente.getApellido_cli() %>
			            </option>
			        <%
			                	
			                }
			            }
			        %>
			    </select>
            </div>
            <div>
                <label class="block font-semibold mb-1">CBU Destino</label>
                <!-- Select de Cuentas (CBU), inicialmente vacío -->
				<select id="cbuDestinoSelect" name="cbu" required
			    	class="w-full px-3 py-2 border border-gray-300 rounded-md bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-200">
				    <option value="">Seleccione una cuenta</option>
				    <%
				        ArrayList<Cuentas> listaCuentas = (ArrayList<Cuentas>) request.getAttribute("listaCuentasFiltradas");
				        if (listaCuentas != null) {
				            for (Cuentas cuenta : listaCuentas) {
				    %>
				        <option value="<%= cuenta.getCBU_cu() %>"><%= cuenta.getCBU_cu() %></option>
				    <%
				            }
				        }
				    %>
				</select>
            </div>
            <div>
                <label class="block font-semibold mb-1">Monto a transferir</label>
                <input type="number" name="monto" required class="w-full border border-gray-300 rounded-lg p-2" placeholder="$0.00">
            </div>
            <div>
                <label class="block font-semibold mb-1">Detalle (opcional)</label>
                <input type="text" name="detalle" class="w-full border border-gray-300 rounded-lg p-2" placeholder="Ej: Pago alquiler">
            </div>
        </div>
        <div class="text-right mt-4">
            <button type="submit" name="accion" value="enviar" class="bg-orange-500 text-white px-6 py-2 rounded-md hover:bg-orange-600 font-semibold">Enviar</button>
        </div>
    </form>

    <h2 class="text-xl font-bold mb-4">Historial de Transferencias</h2>

    <div class="overflow-x-auto">
       <jsp:include page="ListaTransferencias.jsp"></jsp:include>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const clienteSelect = document.getElementById("clienteSelect");
    const cbuDestinoSelect = document.getElementById("cbuDestinoSelect");

    clienteSelect.addEventListener("change", function () {
        const dni = this.value;
        cbuDestinoSelect.innerHTML = '<option value="">Cargando cuentas...</option>';

        if (!dni) {
            cbuDestinoSelect.innerHTML = '<option value="">Seleccione un cliente primero</option>';
            return;
        }

        fetch("TransferenciaServlet?dni=" + encodeURIComponent(dni))
            .then(response => response.json())
            .then(cuentas => {
                cbuDestinoSelect.innerHTML = '<option value="">Seleccione una cuenta</option>';
                cuentas.forEach(cuenta => {
                    const option = document.createElement("option");
                    option.value = cuenta.CBU_cu;
                    option.textContent = cuenta.CBU_cu;
                    cbuDestinoSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al cargar cuentas:", error);
                cbuDestinoSelect.innerHTML = '<option value="">Error al cargar cuentas</option>';
            });
    });
});
</script>

</body>
</html>
