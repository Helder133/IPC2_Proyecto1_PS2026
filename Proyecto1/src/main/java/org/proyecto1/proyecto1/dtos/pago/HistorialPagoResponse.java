package org.proyecto1.proyecto1.dtos.pago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.proyecto1.proyecto1.models.pago.EnumPago;
import org.proyecto1.proyecto1.models.pago.HistorialPago;

import java.time.LocalDate;

public class HistorialPagoResponse {
    private int historialId;
    private int reservacionId;
    private double monto;
    private EnumPago metodo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fecha;

    public HistorialPagoResponse(HistorialPago historialPago) {
        this.historialId = historialPago.getHistorialId();
        this.reservacionId = historialPago.getReservacionId();
        this.monto = historialPago.getMonto();
        this.metodo = historialPago.getMetodo();
        this.fecha = historialPago.getFecha();
    }

    public int getHistorialId() {
        return historialId;
    }

    public void setHistorialId(int historialId) {
        this.historialId = historialId;
    }

    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public EnumPago getMetodo() {
        return metodo;
    }

    public void setMetodo(EnumPago metodo) {
        this.metodo = metodo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
