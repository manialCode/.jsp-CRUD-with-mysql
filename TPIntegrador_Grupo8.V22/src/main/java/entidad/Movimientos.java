package entidad;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.MovimientoInvalidoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

public class Movimientos {
    private int idMovimiento_mo;
    private String CBU_mo;
    private Date fecha_mo;
    private BigDecimal importe_mo;
    private int tipoDeMovimiento_mo;
    private String detalle_mo;

    public Movimientos() {}
    
    public Movimientos(int idMovimiento_mo, String CBU_mo, Date fecha_mo, BigDecimal importe_mo,
            int tipoDeMovimiento_mo, String detalle_mo) {
    	
			this.idMovimiento_mo = idMovimiento_mo;
			this.CBU_mo = CBU_mo;
			this.fecha_mo = fecha_mo;
			this.importe_mo = importe_mo;
			this.tipoDeMovimiento_mo = tipoDeMovimiento_mo;
			this.detalle_mo = detalle_mo;
}

    public int getIdMovimiento_mo() {
        return idMovimiento_mo;
    }

    public void setIdMovimiento_mo(int idMovimiento_mo) {
        this.idMovimiento_mo = idMovimiento_mo;
    }

    public String getCBU_mo() {
        return CBU_mo;
    }

    public void setCBU_mo(String CBU_mo) {
        this.CBU_mo = CBU_mo;
    }

    public Date getFecha_mo() {
        return fecha_mo;
    }

    public void setFecha_mo(Date fecha_mo) {
        this.fecha_mo = fecha_mo;
    }

    public BigDecimal getImporte_mo() {
        return importe_mo;
    }

    public void setImporte_mo(BigDecimal importe_mo) {
        this.importe_mo = importe_mo;
    }

    public int getTipoDeMovimiento_mo() {
        return tipoDeMovimiento_mo;
    }

    public void setTipoDeMovimiento_mo(int tipoDeMovimiento_mo) {
        this.tipoDeMovimiento_mo = tipoDeMovimiento_mo;
    }

    public String getDetalle_mo() {
        return detalle_mo;
    }

    public void setDetalle_mo(String detalle_mo) {
        this.detalle_mo = detalle_mo;
    }
    public boolean esValido() {
        return CBU_mo != null && !CBU_mo.trim().isEmpty()
            && fecha_mo != null
            && importe_mo != null && importe_mo.compareTo(BigDecimal.ZERO) != 0
            && tipoDeMovimiento_mo > 0
            && detalle_mo != null && !detalle_mo.trim().isEmpty();
    }

    /**
     * Valida los campos de la entidad Movimientos y lanza una MovimientoInvalidoException
     * si alguna regla de negocio no se cumple.
     *
     * @throws MovimientoInvalidoException Si uno o más campos no son válidos.
     */
    public void validar() throws MovimientoInvalidoException {
        List<String> errores = new ArrayList<>();

        // 1. Validar CBU_mo
        if (CBU_mo == null || CBU_mo.trim().isEmpty()) {
            errores.add("El CBU del movimiento no puede ser nulo o vacío.");
        } else if (CBU_mo.trim().length() != 22 || !CBU_mo.matches("\\d+")) {
            errores.add("El CBU del movimiento debe contener 22 dígitos numéricos.");
        }

        // 2. Validar fecha_mo
        if (fecha_mo == null) {
            errores.add("La fecha del movimiento no puede ser nula.");
        } else {
            LocalDate hoy = LocalDate.now();
            LocalDate fechaMovimiento = fecha_mo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // La fecha del movimiento no puede ser en el futuro
            if (fechaMovimiento.isAfter(hoy)) {
                errores.add("La fecha del movimiento no puede ser posterior a la fecha actual.");
            }
        }

        // 3. Validar importe_mo
        if (importe_mo == null) {
            errores.add("El importe del movimiento no puede ser nulo.");
        }
        // El importe puede ser positivo o negativo (ej. extracciones), así que no validamos > 0
        // Opcional: Validar que no sea cero si el movimiento implica un cambio de saldo
        // if (importe_mo.compareTo(BigDecimal.ZERO) == 0) {
        //     errores.add("El importe del movimiento no puede ser cero.");
        // }

        // 4. Validar tipoDeMovimiento_mo
        if (tipoDeMovimiento_mo <= 0) { // Asumiendo que los IDs de tipo de movimiento son positivos
            errores.add("El tipo de movimiento no es válido.");
        }
        // Opcional: Validar que el ID exista en tu enum TipoMovimiento
        // if (TipoMovimiento.fromId(tipoDeMovimiento_mo) == null) {
        //     errores.add("El tipo de movimiento especificado no existe.");
        // }

        // 5. Validar detalle_mo
        if (detalle_mo == null || detalle_mo.trim().isEmpty()) {
            errores.add("El detalle del movimiento no puede ser nulo o vacío.");
        } else if (detalle_mo.trim().length() > 255) { // Límite de varchar(255)
            errores.add("El detalle del movimiento no puede exceder los 255 caracteres.");
        }

        if (!errores.isEmpty()) {
            throw new MovimientoInvalidoException(errores);
        }
    }

    @Override
    public String toString() {
        return "Movimientos [idMovimiento_mo=" + idMovimiento_mo + ", CBU_mo=" + CBU_mo + ", fecha_mo=" + fecha_mo + 
               ", importe_mo=" + importe_mo + ", tipoDeMovimiento_mo=" + tipoDeMovimiento_mo + ", detalle_mo=" + detalle_mo + "]";
    }
}
