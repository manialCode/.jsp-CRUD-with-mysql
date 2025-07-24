<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="entidad.Usuario"%>
    <% Usuario usu = (Usuario)session.getAttribute("usuario"); %>
<%
String paginaActiva = request.getParameter("pagina");
%>

<!-- NAVBAR -->
<nav class="bg-white border-b border-gray-200 sticky inset-0">
    <div
        class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
        <a href="#" class="flex items-center space-x-3"> <img
            src="https://flowbite.com/docs/images/logo.svg" class="h-8"
            alt="Flowbite Logo" /> <span
            class="self-center text-2xl font-semibold text-gray-900">Banco
                G8</span>
        </a>

        <!-- Usuario -->
        <div class="flex items-center md:order-2 relative group space-x-2">
            <!-- Label ADMIN -->
            <span
                class="bg-red-600 text-white text-xs font-bold px-2 py-1 rounded-full">ADMIN</span>

            <button type="button"
                class="flex text-sm bg-gray-800 rounded-full focus:ring-4 focus:ring-gray-300">
                <span class="sr-only">Open user menu</span> <img
                    class="w-8 h-8 rounded-full"
                    src="/docs/images/people/profile-picture-3.jpg" alt="user photo" />
            </button>

            <!-- Dropdown -->
            <div
                class="absolute top-0 left-0 z-50 hidden group-hover:block mt-2 w-48 bg-white divide-y divide-gray-100 rounded-lg shadow">
                <div class="px-4 py-3">
                    <span class="block text-sm text-gray-900"><%=usu.getUsuario_us() %></span> <span
                        class="block text-sm text-gray-500 truncate">name@flowbite.com</span>
                </div>
                <ul class="py-2">
                    <li><a href="#"
                        class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Info
                            de Usuario</a></li>
                    <li><a href="#"
                        class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Cambiar
                            la contraseña</a></li>
                    <li><a href="<%=request.getContextPath()%>/NavegacionServlet?accion=logout"
                        class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Log
                            out</a></li>
                </ul>
            </div>

            <!-- Botón responsive -->
            <button data-collapse-toggle="navbar-user" type="button"
                class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200">
                <span class="sr-only">Open main menu</span>
                <svg class="w-5 h-5" aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
          <path stroke="currentColor" stroke-linecap="round"
                        stroke-linejoin="round" stroke-width="2"
                        d="M1 1h15M1 7h15M1 13h15" />
        </svg>
            </button>
        </div>

        <!-- Menú principal -->
        <div
            class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1"
            id="navbar-user">
            <ul
                class="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 md:flex-row md:mt-0 md:border-0 md:bg-white">
                <li><a href="<%=request.getContextPath()%>/NavegacionServlet?accion=cuentas&pagina=cuentas"
                    class="<%="cuentas".equals(paginaActiva) ? "text-blue-700" : ""%>">
                        Cuentas </a></li>
                <li><a href="<%=request.getContextPath()%>/NavegacionServlet?accion=clientes&pagina=clientes"
                    class="<%="clientes".equals(paginaActiva) ? "text-blue-700" : ""%>">
                        Clientes </a></li>
                <li><a
                    href="<%=request.getContextPath()%>/NavegacionServlet?accion=prestamosAdmin&pagina=prestamos"
                    class="<%="prestamos".equals(paginaActiva) ? "text-blue-700" : ""%>">
                        Préstamos </a></li>
                <li><a href="<%=request.getContextPath()%>/NavegacionServlet?accion=reportes&pagina=reportes"
                    class="<%="reportes".equals(paginaActiva) ? "text-blue-700" : ""%>">
                        Reportes/Estadísticas </a></li>
            </ul>
        </div>
    </div>
</nav>