package org.proyecto1.proyecto1.dtos.reporte.paqueteMasVendidoYMenosVendido;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.proyecto1.proyecto1.models.reporte.paqueteMasVendidoYMenosVendido.DetalleReservacionPaquete;

import java.time.LocalDate;

public class DetalleReservacionPaqueteResponse {
    private int reservacionId;
    private String nombreAgente;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaViaje;
    private int cantidadPersonas;
    private double costoTotal;

    public DetalleReservacionPaqueteResponse(DetalleReservacionPaquete detalleReservacionPaquete) {
        this.reservacionId = detalleReservacionPaquete.getReservacionId();
        this.nombreAgente = detalleReservacionPaquete.getNombreAgente();
        this.fechaCreacion = detalleReservacionPaquete.getFechaCreacion();
        this.fechaViaje = detalleReservacionPaquete.getFechaViaje();
        this.cantidadPersonas = detalleReservacionPaquete.getCantidadPersonas();
        this.costoTotal = detalleReservacionPaquete.getCostoTotal();
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
