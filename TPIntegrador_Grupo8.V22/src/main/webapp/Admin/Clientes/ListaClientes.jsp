<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@page import="entidad.Clientes"%>
<%@page import="entidad.Provincias"%>
<%@page import="entidad.Localidades"%>
     <%
        ArrayList<Clientes> listaClientes = null;
        if(request.getAttribute("listaClientes")!=null)
        {
        	listaClientes = (ArrayList)request.getAttribute("listaClientes");
        }
        %>
        
        <%! 
		    public String obtenerNombreProvincia(ArrayList<Provincias> listaProvincias, int id) {
		        for (Provincias p : listaProvincias) {
		            if (p.getId_prov() == id) {
		                return p.getDescripcion_prov();
		            }
		        }
		        return "Desconocido";
		    }
        %>
        <%!
		    public String obtenerNombreLocalidad(ArrayList<Localidades> listaLocalidades, int id) {
		        for (Localidades l : listaLocalidades) {
		            if (l.getId_loc() == id) {
		                return l.getDescripcion_loc();
		            }
		        }
		        return "Desconocido";
		    }
		%>
		<%
		    ArrayList<Provincias> listaProvincias = (ArrayList<Provincias>) request.getAttribute("listaProvincias");
		    ArrayList<Localidades> listaLocalidades = (ArrayList<Localidades>) request.getAttribute("listaLocalidades");
		%>
		
		<%! 
		    public String obtenerDescripcionSexo(int idSexo) {
		        switch (idSexo) {
		            case 1:
		                return "M";
		            case 2:
		                return "F";
		            case 3:
		                return "Otro";
		            default:
		                return "Desconocido";
		        }
		    }
		%>
            <table class="min-w-full border border-gray-300 rounded-lg">
                <thead>
				    <tr>
				        <!-- <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Usuario</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">DNI</th> -->
				        				        
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">DNI</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">CUIL</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Nombre</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Apellido</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Sexo</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Correo</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Fecha Nac.</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Dirección</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Provincia</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Localidad</th>
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Teléfono</th>				        
				        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Acciones</th>
				    </tr>
				</thead>

                <tbody>
                <% 
                if(listaClientes == null || listaClientes.isEmpty()){
                	%>
                	<div id="alert-no-clients" class="flex items-center p-4 mb-4 text-yellow-800 border border-yellow-300 bg-yellow-50 rounded-lg" role="alert">
			<svg class="shrink-0 w-5 h-5" aria-hidden="true"
				xmlns="http://www.w3.org/2000/svg" fill="currentColor"
				viewBox="0 0 20 20">
				<path
					d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z" />
			</svg>
			<div class="ml-3 text-base font-medium">
				No se encontraron clientes
			</div>
			<button type="button"
				class="ml-auto -mx-1.5 -my-1.5 bg-yellow-50 text-yellow-500 rounded-lg focus:ring-2 focus:ring-yellow-400 p-1.5 hover:bg-yellow-100 inline-flex items-center justify-center h-8 w-8"
				data-dismiss-target="#alert-no-pending-loans" aria-label="Close">
				<span class="sr-only">Cerrar alerta</span>
				<svg class="w-3 h-3" aria-hidden="true"
					xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
					<path stroke="currentColor" stroke-linecap="round"
						stroke-linejoin="round" stroke-width="2"
						d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
				</svg>
			</button>
			</div>
                	<%
                }else{
                  for(Clientes cliente : listaClientes)
                  {
                		  %>
				    <tr class="cliente-row">
				        <!-- <td class="px-4 py-2 border-b border-gray-200">${cliente.usuario.usuario_us}</td>
				        <td class="px-4 py-2 border-b border-gray-200">${cliente.dni_cli}</td>z-->
				        

				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getDni_cli() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getCuil_cli() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getNombre_cli() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getApellido_cli() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=obtenerDescripcionSexo(cliente.getSexo_cli()) %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getCorreo_cli() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getFecha_nacimiento_cli() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getDireccion_cli() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%= obtenerNombreProvincia(listaProvincias, cliente.getIdProvincia_cli()) %></td>
						<td class="px-4 py-2 border-b border-gray-200"><%= obtenerNombreLocalidad(listaLocalidades, cliente.getIdLocalidad_cli()) %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%=cliente.getTelefono_cli() %></td>				        
				        <td class="px-4 py-2 border-b border-gray-200">
				            <form method="post" action="ClienteServlet" class="inline-flex gap-2">
				                <input type="hidden" name="dni" value="<%=cliente.getDni_cli()%>"/>
				                <button type="submit" name="accion" value="clienteAModificar" class="bg-yellow-500 hover:bg-yellow-600 text-white font-semibold py-1 px-3 rounded-md">Modificar</button>
				                <button type="submit" name="accion" value="baja" class="bg-red-600 hover:bg-red-700 text-white font-semibold py-1 px-3 rounded-md">Eliminar</button>
				            </form>
				        </td>
				    </tr>
				<%
				}}
				%>

                </tbody>
            </table>
            
            <div id="pagination" class="mt-4 flex justify-center gap-2"></div>

<!-- PAGINACION -->
<script>
document.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('.cliente-row');
    const rowsPerPage = 5;
    const totalPages = Math.ceil(rows.length / rowsPerPage);
    let currentPage = 1;

    function showPage(page) {
        currentPage = page;
        rows.forEach((row, index) => {
            row.style.display = (index >= (page - 1) * rowsPerPage && index < page * rowsPerPage) ? '' : 'none';
        });

        document.querySelectorAll('#pagination button').forEach(btn => {
            btn.classList.remove('bg-blue-600', 'text-white', 'font-semibold');
            btn.classList.add('bg-gray-200', 'hover:bg-gray-300');
            if (+btn.dataset.page === currentPage) {
                btn.classList.remove('bg-gray-200', 'hover:bg-gray-300');
                btn.classList.add('bg-blue-600', 'text-white', 'font-semibold');
            }
        });
    }

    function setupPagination() {
        const pagination = document.getElementById('pagination');
        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement('button');
            btn.textContent = i;
            btn.dataset.page = i;
            btn.className = 'px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300';
            btn.addEventListener('click', () => showPage(i));
            pagination.appendChild(btn);
        }
    }

    if (rows.length > 0) {
        setupPagination();
        showPage(1);
    }
});
</script>