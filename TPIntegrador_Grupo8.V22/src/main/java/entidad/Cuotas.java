package entidad;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.CuotaInvalidaException;

import java.math.BigDecimal;

public class Cuotas {
    private int id_cuo;
    private int idPrestamo_cuo;
    private BigDecimal monto_cuo;
    private Date fechaVencimiento_cuo;
    private Date fechaPago_cuo;
    private String CBU_pago_cuo;
    private boolean estado_cuo;

    public Cuotas() {}
    
    public Cuotas(int id_cuo, int idPrestamo_cuo, BigDecimal monto_cuo, Date fechaVencimiento_cuo,
            Date fechaPago_cuo, String CBU_pago_cuo, boolean estado_cuo) {
    	
			  this.id_cuo = id_cuo;
			  this.idPrestamo_cuo = idPrestamo_cuo;
			  this.monto_cuo = monto_cuo;
			  this.fechaVencimiento_cuo = fechaVencimiento_cuo;
			  this.fechaPago_cuo = fechaPago_cuo;
			  this.CBU_pago_cuo = CBU_pago_cuo;
			  this.estado_cuo = estado_cuo;
}

    public int getId_cuo() {
        return id_cuo;
    }

    public void setId_cuo(int id_cuo) {
        this.id_cuo = id_cuo;
    }

    public int getIdPrestamo_cuo() {
        return idPrestamo_cuo;
    }

    public void setIdPrestamo_cuo(int idPrestamo_cuo) {
        this.idPrestamo_cuo = idPrestamo_cuo;
    }

    public BigDecimal getMonto_cuo() {
        return monto_cuo;
    }

    public void setMonto_cuo(BigDecimal monto_cuo) {
        this.monto_cuo = monto_cuo;
    }

    public Date getFechaVencimiento_cuo() {
        return fechaVencimiento_cuo;
    }

    public void setFechaVencimiento_cuo(Date fechaVencimiento_cuo) {
        this.fechaVencimiento_cuo = fechaVencimiento_cuo;
    }

    public Date getFechaPago_cuo() {
        return fechaPago_cuo;
    }

    public void setFechaPago_cuo(Date fechaPago_cuo) {
        this.fechaPago_cuo = fechaPago_cuo;
    }

    public String getCBU_pago_cuo() {
        return CBU_pago_cuo;
    }

    public void setCBU_pago_cuo(String CBU_pago_cuo) {
        this.CBU_pago_cuo = CBU_pago_cuo;
    }

    public boolean isEstado_cuo() {
        return estado_cuo;
    }

    public void setEstado_cuo(boolean estado_cuo) {
        this.estado_cuo = estado_cuo;
    }


    @Override
    public String toString() {
        return "Cuotas [id_cuo=" + id_cuo + ", idPrestamo_cuo=" + idPrestamo_cuo + ", monto_cuo=" + monto_cuo + 
               ", fechaVencimiento_cuo=" + fechaVencimiento_cuo + ", fechaPago_cuo=" + fechaPago_cuo + 
               ", CBU_pago_cuo=" + CBU_pago_cuo + ", estado_cuo=" + estado_cuo + ", montoMensual_cuo=" + "]";
    }

    /**
     * Valida los campos de la entidad Cuotas y lanza una CuotaInvalidaException
     * si alguna regla de negocio no se cumple.
     * Esto asegura que el objeto Cuotas tenga datos consistentes antes de ser
     * persistido o procesado.
     * @throws CuotaInvalidaException Si uno o más campos no son válidos.
     */
    public void validar() throws CuotaInvalidaException {
        List<String> errores = new ArrayList<>();

        // 1. Validar idPrestamo_cuo
        if (idPrestamo_cuo <= 0) {
            errores.add("El ID del préstamo asociado a la cuota debe ser un valor positivo.");
        }

        // 2. Validar monto_cuo
        if (monto_cuo == null || monto_cuo.compareTo(BigDecimal.ZERO) <= 0) {
            errores.add("El monto de la cuota debe ser un valor positivo.");
        }

        // 3. Validar fechaVencimiento_cuo
        if (fechaVencimiento_cuo == null) {
            errores.add("La fecha de vencimiento de la cuota no puede ser nula.");
        }
        // Validar que la fecha de vencimiento no sea demasiado antigua o en el futuro lejano
         if (fechaVencimiento_cuo.before(new Date(0))) { 
             errores.add("La fecha de vencimiento es inválida.");
         }

        // 4. Validar fechaPago_cuo (si la cuota está pagada)
        if (isEstado_cuo()) { // Si estado_cuo es true (pagada)
            if (fechaPago_cuo == null) {
                errores.add("La fecha de pago no puede ser nula si la cuota está pagada.");
            } else {
                // La fecha de pago no puede ser en el futuro
                if (fechaPago_cuo.after(new Date())) {
                    errores.add("La fecha de pago no puede ser posterior a la fecha actual.");
                }
                // La fecha de pago no puede ser anterior a la fecha de vencimiento (o muy anterior)
                if (fechaVencimiento_cuo != null && fechaPago_cuo.before(fechaVencimiento_cuo)) {
                    // Esto es una regla de negocio: ¿se permite pagar antes de vencer?
                    // Si no se permite, podrías ajustar la lógica.
                    // Aquí, si se paga antes de vencer, no lo considero un error grave, pero lo marco si es muy anterior.
                    // Si la fecha de pago es significativamente anterior a la de vencimiento, podría ser un error.
                    // Ejemplo: if (fechaPago_cuo.before(new Date(fechaVencimiento_cuo.getTime() - (1000L * 60 * 60 * 24 * 30)))) { // Más de 30 días antes
                    //     errores.add("La fecha de pago es demasiado anterior a la fecha de vencimiento.");
                    // }
                }
            }
        } else { // Si estado_cuo es false (pendiente)
            if (fechaPago_cuo != null) {
                errores.add("La fecha de pago debe ser nula si la cuota no está pagada.");
            }
        }
        
        // 5. Validar CBU_pago_cuo (si la cuota está pagada)
        if (isEstado_cuo()) { // Si estado_cuo es true (pagada)
            if (CBU_pago_cuo == null || CBU_pago_cuo.trim().isEmpty()) {
                errores.add("El CBU de pago no puede ser nulo o vacío si la cuota está pagada.");
            } else if (CBU_pago_cuo.trim().length() != 22 || !CBU_pago_cuo.matches("\\d+")) {
                errores.add("El CBU de pago debe contener 22 dígitos numéricos si la cuota está pagada.");
            }
        } else { // Si estado_cuo es false (pendiente)
            if (CBU_pago_cuo != null && !CBU_pago_cuo.trim().isEmpty()) {
                errores.add("El CBU de pago debe ser nulo o vacío si la cuota no está pagada.");
            }
        }

        // (puede haber pequeñas diferencias por redondeo o pagos parciales, si se permiten)
        // if (monto_cuo != null && montoMensual_cuo != null &&
        //     monto_cuo.compareTo(montoMensual_cuo.multiply(new BigDecimal("1.1"))) > 0) { // Mayor al 110%
        //     errores.add("El monto de la cuota pagada excede significativamente el monto mensual original.");
        // }


        // Si se encontraron errores, lanzar la excepción
        if (!errores.isEmpty()) {
            throw new CuotaInvalidaException(errores);
        }
    }
}
