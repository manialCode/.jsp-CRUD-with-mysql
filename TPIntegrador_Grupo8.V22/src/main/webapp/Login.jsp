<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.tailwindcss.com"></script>
<title>Login</title>
</head>
<body>
<section class="bg-gray-50">
  <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
      <a href="#" class="flex items-center mb-6 text-2xl font-semibold text-gray-900">
          <img class="w-8 h-8 mr-2" src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg" alt="logo">
          Banco G8    
      </a>
      <div class="w-full bg-white rounded-lg shadow border md:mt-0 sm:max-w-md xl:p-0">
          <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
              <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl">
                  Ingresa a tu cuenta
              </h1>

              <!-- FORMULARIO CON ACTION AL SERVLET -->
              <form class="space-y-4 md:space-y-6" action="LoginServlet" method="post">
                  <div>
                      <label for="usuario" class="block mb-2 text-sm font-medium text-gray-900">Usuario</label>
                      <input type="text" name="usuario" id="usuario" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" required>
                  </div>
                  <div>
                      <label for="contrasena" class="block mb-2 text-sm font-medium text-gray-900">Contraseña</label>
                      <input type="password" name="contrasena" id="contrasena" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5" required>
                  </div>

                  <!-- MENSAJE DE ERROR -->
                 	<c:if test="${not empty error}">
			    	<p class="text-red-600 text-sm">${error}</p>
					</c:if>

                  <button type="submit" class="w-full text-white bg-blue-600 hover:bg-blue-700 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                      Ingresar
                  </button>
              </form>
          </div>
      </div>
  </div>
</section>
</body>
</html>