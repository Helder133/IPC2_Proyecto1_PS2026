package org.proyecto1.proyecto1.dtos.pago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.proyecto1.proyecto1.models.pago.EnumPago;

import java.time.LocalDate;

public class HistorialPagoRequest {
    private int reservacionId;
    private double monto;
    private EnumPago metodo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fecha;

    public EnumPago getMetodo() {
        return metodo;
    }

    public void setMetodo(EnumPago metodo) {
        this.metodo = metodo;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
