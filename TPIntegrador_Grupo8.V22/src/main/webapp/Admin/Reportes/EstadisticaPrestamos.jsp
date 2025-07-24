<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- component -->
<div class="shadow-lg rounded-lg overflow-hidden ">
  <div class="py-3 px-5 bg-gray-50">Distribucion de prestamos</div>
  <canvas class="p-1 ml-40 mr-40 mt-10 " id="PrestamosPie"></canvas>
</div>

<!-- Required chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- Chart pie -->
<script>
//Recuperación de los datos del servidor (desde JSP a JavaScript)
var resultadoReporte4String = '${resultadoReporte4}';

// Eliminar los corchetes al inicio y al final
resultadoReporte4String = resultadoReporte4String.substring(1, resultadoReporte4String.length - 1); // "Aprobado: 7, Pendiente: 2, Rechazado: 6"

// Dividir la cadena por ', ' para obtener cada par clave-valor
var pares = resultadoReporte4String.split(', '); // ["Aprobado: 7", "Pendiente: 2", "Rechazado: 6"]

var datosPrestamos = {}; // Objeto para almacenar los datos extraídos

pares.forEach(function(par) {
    // Para cada par, dividir por ': ' para obtener el estado y el valor
    var partes = par.split(': '); // ej., ["Aprobado", "7"]
    if (partes.length === 2) {
        var estado = partes[0].trim(); // "Aprobado"
        var cantidad = parseInt(partes[1].trim()); // 7

        // Almacenar en el objeto
        datosPrestamos[estado] = cantidad;
    }
});

console.log(datosPrestamos);
// Ahora puedes acceder a los valores:
var aprobados = datosPrestamos['Aprobado'] || 0; // Si no existe, default a 0
var pendientes = datosPrestamos['Pendiente'] || 0;
var rechazados = datosPrestamos['Rechazado'] || 0;
var pagos = datosPrestamos['Pago'] || 0;

  const dataPie = {
    labels: ["Aprobados", "Rechazados", "Pagos", "Pendientes"],
    datasets: [
      {
        label: "Cant de prestamos: ",
        data: [aprobados, rechazados, pagos, pendientes],
        backgroundColor: [
          "rgb(133, 105, 241)",
          "rgb(164, 101, 241)",
          "rgb(101, 143, 241)",
          "rgb(101, 105, 255)",
        ],
        hoverOffset: 4,
      },
    ],
  };

  const configPie = {
    type: "pie",
    data: dataPie,
    options: {},
  };

  var chartBar = new Chart(document.getElementById("PrestamosPie"), configPie);
</script>