<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Movimientos" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    ArrayList<Movimientos> listaMovimientos = null;
    if (request.getAttribute("listaTransferencias") != null) {
        listaMovimientos = (ArrayList<Movimientos>) request.getAttribute("listaTransferencias");
    }
%>

<table class="w-full table-auto border-collapse border border-gray-300">
    <thead class="bg-gray-200">
        <tr>
            <th class="p-2 border border-gray-300">Fecha</th>
            <th class="p-2 border border-gray-300">Cuenta Origen (CBU)</th>
            <th class="p-2 border border-gray-300">Cuenta Destino (Detalle)</th>
            <th class="p-2 border border-gray-300">Monto</th>
            <th class="p-2 border border-gray-300">Observaciones</th>
        </tr>
    </thead>
    <tbody>
        <%
            if (listaMovimientos != null) {
                for (Movimientos mov : listaMovimientos) {
                    if (mov.getTipoDeMovimiento_mo() == 4) { 
        %>
        <tr>
            <td class="p-2 border"><%= mov.getFecha_mo() %></td>
            <td class="p-2 border"><%= mov.getCBU_mo() %></td>
            <td class="p-2 border"><%= mov.getDetalle_mo().split("-")[0] %></td> 
            <td class="p-2 border text-red-600 font-bold">- $<%= mov.getImporte_mo() %></td>
            <td class="p-2 border"><%= mov.getDetalle_mo() %></td>
        </tr>
        <%
                    }
                }
            } else {
        %>
        <tr>
            <td colspan="5" class="text-center p-4">No se encontraron transferencias.</td>
        </tr>
        <% } %>
    </tbody>
</table>

