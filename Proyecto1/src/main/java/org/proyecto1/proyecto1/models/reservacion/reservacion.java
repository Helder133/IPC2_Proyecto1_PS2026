package org.proyecto1.proyecto1.models.reservacion;

import java.time.LocalDate;

public class reservacion {
    private int reservacionId;
    private int paqueteId;
    private int usuarioId;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    private int cantidadPersona;
    private double costoTotal;
    private double costoAgencia;
    private EnumReservacion estado;

    public reservacion(int paqueteId, int usuarioId, LocalDate fechaViaje) {
        this.paqueteId = paqueteId;
        this.usuarioId = usuarioId;
        this.fechaViaje = fechaViaje;
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
}
