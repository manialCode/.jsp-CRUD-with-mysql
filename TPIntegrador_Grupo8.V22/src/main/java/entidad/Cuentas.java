package entidad;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.CuentaInvalidaException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

public class Cuentas {
    private String CBU_cu;
    private String dni_cu;
    private Date fechaCreacion_cu;
    private String numero_cu;
    private int idTipo_cu;
    private BigDecimal saldo_cu;
    private boolean estado_cu;
    private TipoCuenta tipoCuenta;

    public Cuentas() {}
    
    public Cuentas(String CBU_cu, String dni_cu, Date fechaCreacion_cu, String numero_cu, int idTipo_cu,
            BigDecimal saldo_cu, boolean estado_cu, TipoCuenta tipoCuenta) {
    	
		 this.CBU_cu = CBU_cu;
		 this.dni_cu = dni_cu;
		 this.fechaCreacion_cu = fechaCreacion_cu;
		 this.numero_cu = numero_cu;
		 this.idTipo_cu = idTipo_cu;
		 this.saldo_cu = saldo_cu;
		 this.estado_cu = estado_cu;
		 this.tipoCuenta = tipoCuenta;
}

    public String getCBU_cu() {
        return CBU_cu;
    }

    public void setCBU_cu(String CBU_cu) {
        this.CBU_cu = CBU_cu;
    }

    public String getDni_cu() {
        return dni_cu;
    }

    public void setDni_cu(String dni_cu) {
        this.dni_cu = dni_cu;
    }

    public Date getFechaCreacion_cu() {
        return fechaCreacion_cu;
    }

    public void setFechaCreacion_cu(Date fechaCreacion_cu) {
        this.fechaCreacion_cu = fechaCreacion_cu;
    }

    public String getNumero_cu() {
        return numero_cu;
    }

    public void setNumero_cu(String numero_cu) {
        this.numero_cu = numero_cu;
    }

    public int getIdTipo_cu() {
        return idTipo_cu;
    }

    public void setIdTipo_cu(int idTipo_cu) {
        this.idTipo_cu = idTipo_cu;
    }

    public BigDecimal getSaldo_cu() {
        return saldo_cu;
    }

    public void setSaldo_cu(BigDecimal saldo_cu) {
        this.saldo_cu = saldo_cu;
    }

    public boolean isEstado_cu() {
        return estado_cu;
    }

    public void setEstado_cu(boolean estado_cu) {
        this.estado_cu = estado_cu;
    }

    @Override
    public String toString() {
        return "Cuentas [CBU_cu=" + CBU_cu + ", dni_cu=" + dni_cu + ", fechaCreacion_cu=" + fechaCreacion_cu + 
               ", numero_cu=" + numero_cu + ", idTipo_cu=" + idTipo_cu + ", saldo_cu=" + saldo_cu + 
               ", estado_cu=" + estado_cu + "]";
    }

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	
	/**
     * Valida los campos de la entidad Cuentas y lanza una CuentaInvalidaException
     * si alguna regla de negocio no se cumple.
     *
     * @throws CuentaInvalidaException Si uno o más campos no son válidos.
     */
    public void validar() throws CuentaInvalidaException {
        List<String> errores = new ArrayList<>();

        // 1. Validar CBU_cu
        if (CBU_cu == null || CBU_cu.trim().isEmpty()) {
            errores.add("El CBU de la cuenta no puede ser nulo o vacío.");
        } else if (CBU_cu.trim().length() != 22 || !CBU_cu.matches("\\d+")) {
            errores.add("El CBU de la cuenta debe contener 22 dígitos numéricos.");
        }

        // 2. Validar dni_cu
        if (dni_cu == null || dni_cu.trim().isEmpty()) {
            errores.add("El DNI del cliente asociado a la cuenta no puede ser nulo o vacío.");
        } else if (!dni_cu.matches("\\d{7,8}")) {
            errores.add("El DNI del cliente asociado a la cuenta tiene un formato inválido.");
        }

        // 3. Validar fechaCreacion_cu
        if (fechaCreacion_cu == null) {
            errores.add("La fecha de creación de la cuenta no puede ser nula.");
        } else {
            LocalDate hoy = LocalDate.now();
            LocalDate fechaCreacion = fechaCreacion_cu.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (fechaCreacion.isAfter(hoy)) { // No puede ser en el futuro
                errores.add("La fecha de creación de la cuenta no puede ser en el futuro.");
            }
        }

        // 4. Validar numero_cu
        if (numero_cu == null || numero_cu.trim().isEmpty()) {
            errores.add("El número de cuenta no puede ser nulo o vacío.");
        } else if (!numero_cu.matches("\\d+")) { // Asumiendo que es solo numérico
            errores.add("El número de cuenta debe contener solo dígitos.");
        }
        // Opcional: Validar longitud específica o formato (ej. 10 dígitos)

        // 5. Validar idTipo_cu
        if (idTipo_cu <= 0) { // Asumiendo que los IDs de tipo de cuenta son positivos
            errores.add("El tipo de cuenta no es válido.");
        }

        // 6. Validar saldo_cu
        if (saldo_cu == null) {
            errores.add("El saldo de la cuenta no puede ser nulo.");
        }
        // Opcional: Permitir saldo negativo si es cuenta corriente, pero no al crear
        // if (saldo_cu.compareTo(BigDecimal.ZERO) < 0 && idTipo_cu == TipoCuenta.CAJA_AHORRO.getId()) {
        //     errores.add("Una Caja de Ahorro no puede tener saldo negativo.");
        // }

        // 7. Validar estado_cu (boolean)
        // No hay mucho que validar para un boolean, ya es true/false.
        // Podrías validar si el estado es consistente con otras reglas de negocio.

        if (!errores.isEmpty()) {
            throw new CuentaInvalidaException(errores);
        }
    }
}
