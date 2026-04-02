package org.proyecto1.proyecto1.models.reservacion;

import java.time.LocalDate;

public class Reservacion {
    private int reservacionId;
    private int paqueteId;
    private int usuarioId;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    private int cantidadPersona;
    private double costoTotal;
    private double costoAgencia;
    private EnumReservacion estado;
    private double reembolso;
    private LocalDate fechaCancelacion;

    public Reservacion(int paqueteId, int usuarioId, LocalDate fechaViaje) {
        this.paqueteId = paqueteId;
        this.usuarioId = usuarioId;
        this.fechaViaje = fechaViaje;
        this.fechaCreacion = LocalDate.now();
    }

    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public int getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(int paqueteId) {
        this.paqueteId = paqueteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public int getCantidadPersona() {
        return cantidadPersona;
    }

    public void setCantidadPersona(int cantidadPersona) {
        this.cantidadPersona = cantidadPersona;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public double getCostoAgencia() {
        return costoAgencia;
    }

    public void setCostoAgencia(double costoAgencia) {
        this.costoAgencia = costoAgencia;
    }

    public EnumReservacion getEstado() {
        return estado;
    }

    public void setEstado(EnumReservacion estado) {
        this.estado = estado;
    }

    public double getReembolso() {
        return reembolso;
    }

    public void setReembolso(double reembolso) {
        this.reembolso = reembolso;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public boolean isValid() {
        return paqueteId > 0
                && usuarioId > 0
                && fechaViaje != null;
    }
}
