package org.proyecto1.proyecto1.dtos.reporte.mejorAgente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.proyecto1.proyecto1.models.reporte.mejorAgente.DetalleReservacion;

import java.time.LocalDate;

public class DetalleReservacionResponse {
    private int reservacionId;
    private String paqueteNombre;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaViaje;
    private int cantidadPersonas;
    private double monto;

    public DetalleReservacionResponse(DetalleReservacion detalleReservacion) {
        this.reservacionId = detalleReservacion.getReservacionId();
        this.paqueteNombre = detalleReservacion.getPaqueteNombre();
        this.fechaCreacion = detalleReservacion.getFechaCreacion();
        this.fechaViaje = detalleReservacion.getFechaViaje();
        this.cantidadPersonas = detalleReservacion.getCantidadPersonas();
        this.monto = detalleReservacion.getMonto();
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
