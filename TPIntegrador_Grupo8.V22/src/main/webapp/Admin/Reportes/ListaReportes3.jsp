<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="shadow-lg rounded-lg overflow-hidden ">
  <div class="py-3 px-5 bg-gray-50">Distribucion de prestamos</div>   
   <form action="ReportesServlet" method="get">
        <input type="hidden" name="accion" value="reporte3" />
        <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded-md mb-4">Actualizar estadística</button>
      </form>
      <div class="bg-transparent p-4 text-center rounded"><canvas id="GraficoReporte3" width = "1280px" height = "740px" style="border:1px solid #000000;width:100%;height:100%"></canvas></div>
</div>

<script>
var canvas = document.getElementById("GraficoReporte3");
var request = JSON.stringify('${resultadoReporte3}');
var grafico = canvas.getContext("2d");
var CuentaAhorro = request.substring(18,20);
var CuentaCorriente = request.substring(40,42);

console.log(canvas.style.width);

var mitad = canvas.width/2;


grafico.fillStyle = "green";
grafico.fillRect(canvas.width*0.20,canvas.height*0.8, canvas.width*0.10 , -(canvas.height * 0.03)*CuentaAhorro);

grafico.fillStyle = "red";
grafico.fillRect(canvas.width*0.20 + mitad,canvas.height*0.8, canvas.width*0.10 , -(canvas.height * 0.03)*CuentaCorriente);

grafico.fillStyle = "black";
grafico.font = "38px serif"
grafico.fillText("Caja de ahorro: " + CuentaAhorro,canvas.width*0.12, canvas.height*0.9)

grafico.fillText("Cuenta Corriente: " + CuentaCorriente,canvas.width*0.12 + mitad, canvas.height*0.9)
</script>