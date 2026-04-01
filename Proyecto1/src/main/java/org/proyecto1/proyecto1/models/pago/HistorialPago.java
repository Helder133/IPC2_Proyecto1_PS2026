package org.proyecto1.proyecto1.models.pago;

import java.time.LocalDate;

public class HistorialPago {
    private int historialId;
    private int reservacionId;
    private double monto;
    private EnumPago metodo;
    private LocalDate fecha;

    public HistorialPago(int reservacionId, double monto, EnumPago metodo, LocalDate fecha) {
        this.reservacionId = reservacionId;
        this.monto = monto;
        this.metodo = metodo;
        this.fecha = fecha;
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
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
}
