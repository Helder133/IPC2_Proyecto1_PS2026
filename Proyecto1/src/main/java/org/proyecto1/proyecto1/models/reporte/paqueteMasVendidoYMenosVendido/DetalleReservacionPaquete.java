package org.proyecto1.proyecto1.models.reporte.paqueteMasVenvido;

import java.time.LocalDate;

public class DetalleReservacionPaquete {
    private int reservacionId;
    private String nombreAgente;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    private int cantidadPersonas;
    private double costoTotal;

    public DetalleReservacionPaquete(int reservacionId, String nombreAgente, LocalDate fechaCreacion, LocalDate fechaViaje, int cantidadPersonas, double costoTotal) {
        this.reservacionId = reservacionId;
        this.nombreAgente = nombreAgente;
        this.fechaCreacion = fechaCreacion;
        this.cantidadPersonas = cantidadPersonas;
        this.fechaViaje = fechaViaje;
        this.costoTotal = costoTotal;
    }

    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
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

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}
