package entidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.PrestamoInvalidoException;
import util.EstadoPrestamo;

import java.math.BigDecimal;

public class Prestamos {
    private int id_pre;
    private int cant_Cuotas;
    private String CBU_pre;
    private String dni_pre;
    private Date fecha_pre;
    private BigDecimal importePedido_pre;
    private BigDecimal intereses_pre;
    private BigDecimal montoTotal_pre;
    private BigDecimal montoMensual_pre;
    private String estado_pre;

    public Prestamos() {}
    
    public Prestamos(int id_pre, String CBU_pre, String dni_pre, Date fecha_pre, BigDecimal importePedido_pre,
            BigDecimal intereses_pre, BigDecimal montoTotal_pre, BigDecimal montoMensual_pre,
            String estado_pre) {
    	
			this.id_pre = id_pre;
			this.CBU_pre = CBU_pre;
			this.dni_pre = dni_pre;
			this.fecha_pre = fecha_pre;
			this.importePedido_pre = importePedido_pre;
			this.intereses_pre = intereses_pre;
			this.montoTotal_pre = montoTotal_pre;
			this.montoMensual_pre = montoMensual_pre;
			this.estado_pre = estado_pre;
}

    public int getId_pre() {
        return id_pre;
    }

    public void setId_pre(int id_pre) {
        this.id_pre = id_pre;
    }

    public String getCBU_pre() {
        return CBU_pre;
    }

    public void setCBU_pre(String CBU_pre) {
        this.CBU_pre = CBU_pre;
    }

    public String getDni_pre() {
        return dni_pre;
    }

    public void setDni_pre(String dni_pre) {
        this.dni_pre = dni_pre;
    }

    public Date getFecha_pre() {
        return fecha_pre;
    }

    public void setFecha_pre(Date fecha_pre) {
        this.fecha_pre = fecha_pre;
    }

    public BigDecimal getImportePedido_pre() {
        return importePedido_pre;
    }

    public void setImportePedido_pre(BigDecimal importePedido_pre) {
        this.importePedido_pre = importePedido_pre;
    }

    public BigDecimal getIntereses_pre() {
        return intereses_pre;
    }

    public void setIntereses_pre(BigDecimal intereses_pre) {
        this.intereses_pre = intereses_pre;
    }

    public BigDecimal getMontoTotal_pre() {
        return montoTotal_pre;
    }

    public void setMontoTotal_pre(BigDecimal montoTotal_pre) {
        this.montoTotal_pre = montoTotal_pre;
    }

    public BigDecimal getMontoMensual_pre() {
        return montoMensual_pre;
    }

    public void setMontoMensual_pre(BigDecimal montoMensual_pre) {
        this.montoMensual_pre = montoMensual_pre;
    }

    public String getEstado_pre() {
        return estado_pre;
    }

    public void setEstado_pre(String estado_pre) {
        this.estado_pre = estado_pre;
    }

    @Override
    public String toString() {
        return "Prestamos [id_pre=" + id_pre + ", CBU_pre=" + CBU_pre + ", dni_pre=" + dni_pre + 
               ", fecha_pre=" + fecha_pre + ", importePedido_pre=" + importePedido_pre + 
               ", intereses_pre=" + intereses_pre + ", montoTotal_pre=" + montoTotal_pre + 
               ", montoMensual_pre=" + montoMensual_pre + ", estado_pre=" + estado_pre + "]";
    }
    /**
     * Valida los campos de la entidad Prestamos.
     * Lanza PrestamoInvalidoException si se encuentra algún error.
     * @throws PrestamoInvalidoException Si uno o más campos no son válidos.
     */
    public void validar() throws PrestamoInvalidoException {
        List<String> errores = new ArrayList<>();

        // 1. Validar CBU_pre
        if (CBU_pre == null || CBU_pre.trim().isEmpty()) {
            errores.add("El CBU del préstamo no puede estar vacío.");
        } else if (CBU_pre.trim().length() < 20) {
            errores.add("El CBU del préstamo debe tener al menos 20 dígitos.");
        }

        // 2. Validar dni_pre
        if (dni_pre == null || dni_pre.trim().isEmpty()) {
            errores.add("El DNI del cliente no puede estar vacío.");
        } else if (dni_pre.trim().length() < 7 || dni_pre.trim().length() > 9) {
            errores.add("El DNI del cliente tiene un formato incorrecto.");
        }

        // 3. Validar fecha_pre
        if (fecha_pre == null) {
            errores.add("La fecha del préstamo no puede ser nula.");
        } else if (fecha_pre.after(new Date())) {
            errores.add("La fecha del préstamo no puede ser posterior a la fecha actual.");
        }

        // 4. Validar importes (BigDecimal)
        if (importePedido_pre == null || importePedido_pre.compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El importe pedido del préstamo debe ser un valor positivo.");
        }
        if (intereses_pre == null || intereses_pre.compareTo(BigDecimal.ZERO) < 0) {
            errores.add("Los intereses del préstamo no pueden ser negativos.");
        }
        if (montoTotal_pre == null || montoTotal_pre.compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El monto total del préstamo debe ser un valor positivo.");
        }else if (importePedido_pre == null && intereses_pre == null) {
            errores.add("El monto total del préstamo ha sido nulo.");
        }
        if (montoMensual_pre == null || montoMensual_pre.compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El monto mensual de la cuota debe ser un valor positivo.");
        }
        // Validar si las cuotas son mas que 0.
        if(cant_Cuotas <= 0) {
        	errores.add("las cuotas no deben ser inferiores a 0");
        } 
        // 5. Validar estado_pre
        if (estado_pre == null || estado_pre.trim().isEmpty()) {
            errores.add("El estado del préstamo no puede estar vacío.");
        } else {
            String estadoNormalizado = estado_pre.trim().toUpperCase();
            if (!estadoNormalizado.equals(EstadoPrestamo.PENDIENTE.getDescripcion().toUpperCase()) && 
                !estadoNormalizado.equals(EstadoPrestamo.APROBADO.getDescripcion().toUpperCase()) &&
                !estadoNormalizado.equals(EstadoPrestamo.RECHAZADO.getDescripcion().toUpperCase()) &&
                !estadoNormalizado.equals(EstadoPrestamo.PAGADO.getDescripcion().toUpperCase())) {
                errores.add("El estado del préstamo no es válido.");
            }
        }
        
        // Si hay errores, lanza la excepción con la lista de errores
        if (!errores.isEmpty()) {
            throw new PrestamoInvalidoException(errores);
        }
    }

	public int getCant_Cuotas() {
		return cant_Cuotas;
	}

	public void setCant_Cuotas(int cant_Cuotas) {
		this.cant_Cuotas = cant_Cuotas;
	}

}
