package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import negocio.ProvinciaNegocio;
import negocioImpl.ProvinciaNegocioImpl;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Clientes;
import entidad.Cuentas;
import entidad.Provincias;
import entidad.Localidades;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocioImpl.ClienteNegocioImpl;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClienteNegocio clienteNegocio;

    public ClienteServlet() {
        super();
        clienteNegocio = new ClienteNegocioImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //cargarListaClientes(request);
        //request.getRequestDispatcher("ABML_Clientes.jsp").forward(request, response);   	
        String accion = request.getParameter("accion");
        
        cargarListaClientes(request);
        
        if (accion == null || accion.equals("listar")) {       	
            List<Clientes> lista = clienteNegocio.getAll();
            
            request.setAttribute("listaClientes", lista);
            request.getRequestDispatcher("Admin/Clientes/ABML_Clientes.jsp").forward(request, response);
        } else if (accion.equals("buscar")) {        	
            String filtroDni = request.getParameter("filtroDni");
            String filtroApellido = request.getParameter("filtroApellido");
            String filtroCorreo = request.getParameter("filtroCorreo");
            
            List<Clientes> listaFiltrada = clienteNegocio.obtenerClientesFiltradas(filtroDni, filtroApellido, filtroCorreo);
            request.setAttribute("listaClientes", listaFiltrada);
            cargarProvinciasYLocalidades(request);
            request.getRequestDispatcher("Admin/Clientes/ABML_Clientes.jsp").forward(request, response);
        }
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String accion2 = request.getParameter("accion2");

        if ("modificar".equals(accion2)) {
            modificarCliente(request);
            cargarListaClientes(request);
            cargarProvinciasYLocalidades(request);
            request.getRequestDispatcher("Admin/Clientes/ABML_Clientes.jsp").forward(request, response);
            return;
        }
        
        
        if (accion == null) {
            doGet(request, response);
            return;
        }

        switch (accion) {
            case "alta":               
            	String errorAlta = altaCliente(request);
                if (errorAlta != null) {
                    ProvinciaNegocio provinciaNegs = new ProvinciaNegocioImpl();
                    List<Provincias> listaProvinciass = provinciaNegs.getAll();
                    request.setAttribute("listaProvincias", listaProvinciass);
                    request.setAttribute("formularioAlta", true);                    
                }
                break;
            case "baja":            	
                bajaCliente(request);
                break;
            case "clienteAModificar":
                String dniSeleccionado = request.getParameter("dni");
                Clientes cliente = clienteNegocio.getByDNI(dniSeleccionado);

                if (cliente != null) {
                                	
                	cargarProvinciasYLocalidades(request);
                    request.setAttribute("clienteAModificar", cliente);
                    request.getRequestDispatcher("Admin/Clientes/ModificarClienteForm.jsp").forward(request, response);
                    return;
                    
                } else {
                    request.setAttribute("error", "Cliente no encontrado");
                    request.getRequestDispatcher("Admin/Clientes/ABML_Clientes.jsp").forward(request, response);
                    return;
                }
            default:
                doGet(request, response);
                break;
        }
        cargarListaClientes(request);
        cargarProvinciasYLocalidades(request);
        request.getRequestDispatcher("Admin/Clientes/ABML_Clientes.jsp").forward(request, response);
    }

    private void cargarListaClientes(HttpServletRequest request) {
        ArrayList<Clientes> listaClientes = clienteNegocio.getAll();
        request.setAttribute("listaClientes", listaClientes);
    }
    
    private void cargarProvinciasYLocalidades(HttpServletRequest request) {   	
        ProvinciaNegocio provinciaNeg = new ProvinciaNegocioImpl();
        List<Provincias> listaProvincias = provinciaNeg.getAll();
        request.setAttribute("listaProvincias", listaProvincias);

        ClienteNegocio clienteNeg = new ClienteNegocioImpl();
        List<Localidades> listaLocalidades = clienteNeg.getLocalidades();
        request.setAttribute("listaLocalidades", listaLocalidades);
    }

    private String altaCliente(HttpServletRequest request) {
    	
    	String error = validarDatosCliente(request);
    	if (error != null) {
    	    request.setAttribute("mensaje", error);
    	    return error;
    	}
    	
    	try {
            Clientes cliente = new Clientes();
            Usuario usuario = new Usuario();

            usuario.setUsuario_us(request.getParameter("usuario"));
            usuario.setContrasena_us(request.getParameter("contrasenia"));
            usuario.setIdTipo_us(2);

            cliente.setUsuario(usuario);
            cliente.setDni_cli(request.getParameter("dni"));
            cliente.setNombre_cli(request.getParameter("nombre"));
            cliente.setApellido_cli(request.getParameter("apellido"));
            cliente.setSexo_cli(Integer.parseInt(request.getParameter("sexo")));
            cliente.setCuil_cli(request.getParameter("cuil"));
            cliente.setCorreo_cli(request.getParameter("correo"));
            cliente.setDireccion_cli(request.getParameter("direccion"));
            cliente.setTelefono_cli(request.getParameter("telefonos"));
            cliente.setFecha_nacimiento_cli(java.sql.Date.valueOf(request.getParameter("fechaNacimiento")));
            cliente.setIdProvincia_cli(Integer.parseInt(request.getParameter("provincia")));
            cliente.setIdLocalidad_cli(Integer.parseInt(request.getParameter("localidad")));
            

            boolean insertado = clienteNegocio.insertar(cliente);          
            request.setAttribute("mensaje", insertado ? "Cliente registrado con éxito." : "Error al registrar cliente.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al procesar los datos del cliente.");
        }
    	cargarListaClientes(request);
    	return null;
    }

    private void modificarCliente(HttpServletRequest request) {
    	    	
    	try {
            Clientes cliente = new Clientes();
            Usuario usuario = new Usuario();

            usuario.setContrasena_us(request.getParameter("contrasenia"));
            usuario.setId_us(Integer.parseInt(request.getParameter("idUsuario")));
            cliente.setUsuario(usuario);

            cliente.setDni_cli(request.getParameter("dni"));
            cliente.setNombre_cli(request.getParameter("nombre"));
            cliente.setApellido_cli(request.getParameter("apellido"));
            cliente.setCorreo_cli(request.getParameter("correo"));
            cliente.setDireccion_cli(request.getParameter("direccion"));
            cliente.setTelefono_cli(request.getParameter("telefono"));
            cliente.setFecha_nacimiento_cli(java.sql.Date.valueOf(request.getParameter("fechaNacimiento")));
            cliente.setIdProvincia_cli(Integer.parseInt(request.getParameter("provincia")));
            cliente.setIdLocalidad_cli(Integer.parseInt(request.getParameter("localidad")));
            cliente.setIdUsuario_cli(Integer.parseInt(request.getParameter("idUsuario")));
            
            System.out.println("DNI: " + cliente.getDni_cli());
            System.out.println("Nombre: " + cliente.getNombre_cli());
            System.out.println("Apellido: " + cliente.getApellido_cli());
            System.out.println("Sexo: " + cliente.getSexo_cli());
            System.out.println("Fecha Nac: " + cliente.getFecha_nacimiento_cli());
            System.out.println("Correo: " + cliente.getCorreo_cli());
            System.out.println("Direccion: " + cliente.getDireccion_cli());
            System.out.println("Telefono: " + cliente.getTelefono_cli());
            System.out.println("Provincia: " + cliente.getIdProvincia_cli());
            System.out.println("Localidad: " + cliente.getIdLocalidad_cli());
            System.out.println("Usuario ID: " + cliente.getIdUsuario_cli());
            System.out.println("Usuario Contrasena: " + cliente.getUsuario().getContrasena_us());

            boolean modificado = clienteNegocio.modificar(cliente);
            request.setAttribute("mensaje", modificado ? "Cliente modificado correctamente." : "Error al modificar cliente.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al procesar la modificación del cliente.");
        }
        cargarListaClientes(request);
        
    }

    private void bajaCliente(HttpServletRequest request) {
        try {
            String dni = request.getParameter("dni");
            Clientes cliente = new Clientes();
            cliente.setDni_cli(dni);

            boolean eliminado = clienteNegocio.delete(cliente);
            request.setAttribute("mensaje", eliminado ? "Cliente eliminado correctamente." : "Error al eliminar cliente.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el cliente.");
        }
        cargarListaClientes(request);
    }
    
    private String validarDatosCliente(HttpServletRequest request) {
        // Nombre
        String nombre = request.getParameter("nombre");
        if (nombre == null || !nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            return "El nombre solo puede contener letras y espacios.";
        }

        // Apellido
        String apellido = request.getParameter("apellido");
        if (apellido == null || !apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
            return "El apellido solo puede contener letras y espacios.";
        }

        // DNI
        String dni = request.getParameter("dni");
        if (dni == null || !dni.matches("\\d{7,9}")) {
            return "El DNI debe tener entre 7 a 9 dígitos numéricos.";
        }

        // CUIL
        String cuil = request.getParameter("cuil");
        if (cuil == null || !cuil.matches("\\d{9,11}")) {
            return "El CUIL debe tener entre 9 a 11 dígitos numéricos.";
        }

        // Teléfono
        String telefono = request.getParameter("telefonos");
        if (telefono == null || !telefono.matches("\\d{6,15}")) {
            return "El teléfono debe tener entre 6 y 15 dígitos numéricos.";
        }

        // Contraseñas 
        /*String contrasenia = request.getParameter("contrasenia");
        String repetirContrasenia = request.getParameter("repetirContrasenia");
        if (contrasenia == null || repetirContrasenia == null || !contrasenia.equals(repetirContrasenia)) {
            return "Las contraseñas no coinciden.";
        }        */

        return null;
    }///Cambio
}