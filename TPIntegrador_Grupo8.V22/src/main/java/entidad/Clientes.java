package entidad;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.ClienteInvalidoException;

public class Clientes {
    private String dni_cli;
    private String cuil_cli;
    private String nombre_cli;
    private String apellido_cli;
    private int sexo_cli;
	private Date fecha_nacimiento_cli;
    private String direccion_cli;
    private int idProvincia_cli;
    private int idLocalidad_cli;
    private String correo_cli;
    private String telefono_cli;
    private int idUsuario_cli;
    private boolean estado_cli;
    private Usuario usuario;

    public Clientes() {}
    
    
    public Clientes(String dni_cli, String cuil_cli, String nombre_cli, String apellido_cli, int sexo_cli,
			Date fecha_nacimiento_cli, String direccion_cli, int idProvincia_cli, int idLocalidad_cli,
			String correo_cli, String telefono_cli, int idUsuario_cli, boolean estado_cli, Usuario usuario) {
		super();
		this.dni_cli = dni_cli;
		this.cuil_cli = cuil_cli;
		this.nombre_cli = nombre_cli;
		this.apellido_cli = apellido_cli;
		this.sexo_cli = sexo_cli;
		this.fecha_nacimiento_cli = fecha_nacimiento_cli;
		this.direccion_cli = direccion_cli;
		this.idProvincia_cli = idProvincia_cli;
		this.idLocalidad_cli = idLocalidad_cli;
		this.correo_cli = correo_cli;
		this.telefono_cli = telefono_cli;
		this.idUsuario_cli = idUsuario_cli;
		this.estado_cli = estado_cli;
		this.usuario = usuario;
	}

    public String getDni_cli() {
        return dni_cli;
    }

    public void setDni_cli(String dni_cli) {
        this.dni_cli = dni_cli;
    }

    public String getCuil_cli() {
        return cuil_cli;
    }

    public void setCuil_cli(String cuil_cli) {
        this.cuil_cli = cuil_cli;
    }

    public String getNombre_cli() {
        return nombre_cli;
    }

    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    public String getApellido_cli() {
        return apellido_cli;
    }

    public void setApellido_cli(String apellido_cli) {
        this.apellido_cli = apellido_cli;
    }

    public int getSexo_cli() {
		return sexo_cli;
	}

	public void setSexo_cli(int sexo_cli) {
		this.sexo_cli = sexo_cli;
	}
    
    public Date getFecha_nacimiento_cli() {
        return fecha_nacimiento_cli;
    }

    public void setFecha_nacimiento_cli(Date fecha_nacimiento_cli) {
        this.fecha_nacimiento_cli = fecha_nacimiento_cli;
    }

    public String getDireccion_cli() {
        return direccion_cli;
    }

    public void setDireccion_cli(String direccion_cli) {
        this.direccion_cli = direccion_cli;
    }

    public int getIdProvincia_cli() {
        return idProvincia_cli;
    }

    public void setIdProvincia_cli(int idProvincia_cli) {
        this.idProvincia_cli = idProvincia_cli;
    }

    public int getIdLocalidad_cli() {
        return idLocalidad_cli;
    }

    public void setIdLocalidad_cli(int idLocalidad_cli) {
        this.idLocalidad_cli = idLocalidad_cli;
    }

    public String getCorreo_cli() {
        return correo_cli;
    }

    public void setCorreo_cli(String correo_cli) {
        this.correo_cli = correo_cli;
    }

    public String getTelefono_cli() {
        return telefono_cli;
    }

    public void setTelefono_cli(String telefono_cli) {
        this.telefono_cli = telefono_cli;
    }

    public int getIdUsuario_cli() {
        return idUsuario_cli;
    }

    public void setIdUsuario_cli(int idUsuario_cli) {
        this.idUsuario_cli = idUsuario_cli;
    }

    public boolean isEstado_cli() {
        return estado_cli;
    }

    public void setEstado_cli(boolean estado_cli) {
        this.estado_cli = estado_cli;
    }
    
    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Clientes [dni_cli=" + dni_cli + ", cuil_cli=" + cuil_cli + ", nombre_cli=" + nombre_cli
				+ ", apellido_cli=" + apellido_cli + ", sexo_cli=" + sexo_cli + ", fecha_nacimiento_cli="
				+ fecha_nacimiento_cli + ", direccion_cli=" + direccion_cli + ", idProvincia_cli=" + idProvincia_cli
				+ ", idLocalidad_cli=" + idLocalidad_cli + ", correo_cli=" + correo_cli + ", telefono_cli="
				+ telefono_cli + ", idUsuario_cli=" + idUsuario_cli + ", estado_cli=" + estado_cli + ", usuario="
				+ usuario + "]";
	}

	 /**
     * Valida los campos de la entidad Clientes y lanza una ClienteInvalidoException
     * si alguna regla de negocio no se cumple.
     *
     * @throws ClienteInvalidoException Si uno o más campos no son válidos.
     */
    public void validar() throws ClienteInvalidoException {
        List<String> errores = new ArrayList<>();

        // 1. Validar DNI
        if (dni_cli == null || dni_cli.trim().isEmpty()) {
            errores.add("El DNI no puede ser nulo o vacío.");
        } else if (!dni_cli.matches("\\d{7,8}")) { // DNI típico de 7 u 8 dígitos numéricos
            errores.add("El DNI debe contener 7 u 8 dígitos numéricos.");
        }

        // 2. Validar CUIL
        if (cuil_cli == null || cuil_cli.trim().isEmpty()) {
            errores.add("El CUIL no puede ser nulo o vacío.");
        } else if (!cuil_cli.matches("\\d{2}-\\d{8}-\\d{1}") && !cuil_cli.matches("\\d{11}")) { // Formato XX-XXXXXXXX-X o XXXXXXXXXXX
            errores.add("El CUIL tiene un formato inválido (ej. XX-XXXXXXXX-X o 11 dígitos).");
        }

        // 3. Validar Nombre
        if (nombre_cli == null || nombre_cli.trim().isEmpty()) {
            errores.add("El nombre no puede ser nulo o vacío.");
        } else if (nombre_cli.trim().length() > 50) {
            errores.add("El nombre no puede exceder los 50 caracteres.");
        }

        // 4. Validar Apellido
        if (apellido_cli == null || apellido_cli.trim().isEmpty()) {
            errores.add("El apellido no puede ser nulo o vacío.");
        } else if (apellido_cli.trim().length() > 50) {
            errores.add("El apellido no puede exceder los 50 caracteres.");
        }

        // 5. Validar Sexo (asumiendo 1, 2, o algún otro valor válido)
        if (sexo_cli < 0 || sexo_cli > 2) { // Ejemplo: 0=Otro, 1=Masculino, 2=Femenino
            errores.add("El sexo del cliente no es válido.");
        }

        // 6. Validar Fecha de Nacimiento
        if (fecha_nacimiento_cli == null) {
            errores.add("La fecha de nacimiento no puede ser nula.");
        } else {
            LocalDate hoy = LocalDate.now();
            LocalDate fechaNacimiento = fecha_nacimiento_cli.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // Asumiendo que el cliente debe ser mayor de 18 años
            if (fechaNacimiento.plusYears(18).isAfter(hoy)) {
                errores.add("El cliente debe ser mayor de 18 años.");
            }
            // Opcional: No permitir fechas de nacimiento en el futuro
            if (fechaNacimiento.isAfter(hoy)) {
                errores.add("La fecha de nacimiento no puede ser en el futuro.");
            }
        }

        // 7. Validar Dirección
        if (direccion_cli == null || direccion_cli.trim().isEmpty()) {
            errores.add("La dirección no puede ser nula o vacía.");
        } else if (direccion_cli.trim().length() > 100) {
            errores.add("La dirección no puede exceder los 100 caracteres.");
        }

        // 8. Validar ID Provincia
        if (idProvincia_cli <= 0) {
            errores.add("Debe seleccionar una provincia válida.");
        }

        // 9. Validar ID Localidad
        if (idLocalidad_cli <= 0) {
            errores.add("Debe seleccionar una localidad válida.");
        }

        // 10. Validar Correo Electrónico
        if (correo_cli == null || correo_cli.trim().isEmpty()) {
            errores.add("El correo electrónico no puede ser nulo o vacío.");
        } else if (!correo_cli.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) { // Regex simple para email
            errores.add("El formato del correo electrónico es inválido.");
        } else if (correo_cli.trim().length() > 50) {
            errores.add("El correo electrónico no puede exceder los 50 caracteres.");
        }

        // 11. Validar Teléfono
        if (telefono_cli == null || telefono_cli.trim().isEmpty()) {
            errores.add("El teléfono no puede ser nulo o vacío.");
        } else if (!telefono_cli.matches("^\\d{10,20}$")) { // Ejemplo: 10 a 20 dígitos numéricos
            errores.add("El teléfono debe contener solo dígitos y tener entre 10 y 20 caracteres.");
        }

        // 12. Validar ID Usuario (FK)
        if (idUsuario_cli <= 0) {
            errores.add("El ID de usuario asociado no es válido.");
        }

        // 13. Validar Estado (boolean)
        // No hay mucho que validar para un boolean, ya es true/false.
        // Podrías validar si el estado es consistente con otras reglas de negocio.
        // Por ejemplo, si un cliente nuevo siempre debe ser 'activo'.

        if (!errores.isEmpty()) {
            throw new ClienteInvalidoException(errores);
        }
    }

	
}

