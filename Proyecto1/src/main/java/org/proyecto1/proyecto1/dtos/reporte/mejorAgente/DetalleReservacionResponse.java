package org.proyecto1.proyecto1.models.reporte.mejorAgente;

import java.time.LocalDate;

public class DetalleReservacion {
    private int reservacionId;
    private String paqueteNombre;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    private int cantidadPersonas;
    private double monto;

    public DetalleReservacion(String paqueteNombre, int reservacionId, LocalDate fechaCreacion, LocalDate fechaViaje, int cantidadPersonas, double monto) {
        this.paqueteNombre = paqueteNombre;
        this.reservacionId = reservacionId;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje = fechaViaje;
        this.cantidadPersonas = cantidadPersonas;
        this.monto = monto;
    }

    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPaqueteNombre() {
        return paqueteNombre;
    }

    public void setPaqueteNombre(String paqueteNombre) {
        this.paqueteNombre = paqueteNombre;
    }
}
