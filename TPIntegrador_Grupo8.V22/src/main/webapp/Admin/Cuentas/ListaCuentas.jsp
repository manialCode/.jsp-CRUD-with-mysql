<%@page import="java.util.ArrayList"%>
<%@page import="entidad.Cuentas"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%
			ArrayList<Cuentas> listaCuentas = (ArrayList<Cuentas>) request.getAttribute("listaCuentas");
			if (listaCuentas == null) {
			    listaCuentas = new ArrayList<Cuentas>();
			}
		%>	
        <table class="min-w-full border border-gray-300 rounded-lg">
            <thead>
			    <tr>
			        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Tipo de cuenta</th>
			        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Dni del cliente</th>
			        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Numero de cuenta</th>
			        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">CBU</th>
			        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Saldo</th>
			        <th class="bg-gray-100 px-4 py-2 border-b border-gray-300 text-left">Acciones</th>
			    </tr>
			</thead>
            <tbody>
                <% 
                if (listaCuentas==null || listaCuentas.isEmpty()){
                	%>
                	<div id="alert-no-pending-loans"
			class="flex items-center p-4 mb-4 text-yellow-800 border border-yellow-300 bg-yellow-50 rounded-lg"
			role="alert">
			<svg class="shrink-0 w-5 h-5" aria-hidden="true"
				xmlns="http://www.w3.org/2000/svg" fill="currentColor"
				viewBox="0 0 20 20">
				<path
					d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z" />
			</svg>
			<div class="ml-3 text-base font-medium">
				No se encuentra ninguna cuenta
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
                  for(Cuentas cuentas : listaCuentas) {
                %>
				    <tr class="cuenta-row">
				        <td class="px-4 py-2 border-b border-gray-200">
				            <%= cuentas.getIdTipo_cu() == 1 ? "Caja de ahorro" :
					             cuentas.getIdTipo_cu() == 2 ? "Cuenta corriente" : "-" %>
				        </td>
				        <td class="px-4 py-2 border-b border-gray-200"><%= cuentas.getDni_cu() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%= cuentas.getNumero_cu() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%= cuentas.getCBU_cu() %></td>
				        <td class="px-4 py-2 border-b border-gray-200"><%= cuentas.getSaldo_cu() %></td>				        
				        <td class="px-4 py-2 border-b border-gray-200">
				            <form method="post" action="CuentasServlet" class="inline-flex gap-2">
				                <input type="hidden" name="cbu" value="<%= cuentas.getCBU_cu() %>" />
				                <button type="submit" name="accion" value="cuentaAModificar" class="bg-yellow-500 hover:bg-yellow-600 text-white font-semibold py-1 px-3 rounded-md">Modificar</button>
				                <button type="submit" name="accion" value="baja" class="bg-red-600 hover:bg-red-700 text-white font-semibold py-1 px-3 rounded-md">Eliminar</button>
				            </form>
				        </td>
				    </tr>
              <%
                  }
                  }
              %>
            </tbody>
        </table> 
<!-- PAGINACION -->     
<div id="paginationCuentas" class="mt-4 flex justify-center gap-2"></div>

<script>
document.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('.cuenta-row');
    const rowsPerPage = 5;
    const totalPages = Math.ceil(rows.length / rowsPerPage);
    let currentPage = 1;

    function showPage(page) {
        currentPage = page;
        rows.forEach((row, index) => {
            row.style.display = (index >= (page - 1) * rowsPerPage && index < page * rowsPerPage) ? '' : 'none';
        });

        document.querySelectorAll('#paginationCuentas button').forEach(btn => {
            btn.classList.remove('bg-blue-600', 'text-white', 'font-semibold');
            btn.classList.add('bg-gray-200', 'hover:bg-gray-300');
            if (+btn.dataset.page === currentPage) {
                btn.classList.remove('bg-gray-200', 'hover:bg-gray-300');
                btn.classList.add('bg-blue-600', 'text-white', 'font-semibold');
            }
        });
    }

    function setupPagination() {
        const pagination = document.getElementById('paginationCuentas');
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