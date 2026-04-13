package org.proyecto1.proyecto1.models.reporte;

import java.time.LocalDate;

public class ReservacionCancelada {
    private int reservacionId;
    private String paquete;
    private LocalDate fechaCancelacion;
    private double montoReembolsado;
    private double perdidaAgencia;

    public ReservacionCancelada(int reservacionId, String paquete, LocalDate fechaCancelacion, double montoReembolsado, double perdidaAgencia) {
        this.reservacionId = reservacionId;
        this.paquete = paquete;
        this.fechaCancelacion = fechaCancelacion;
        this.montoReembolsado = montoReembolsado;
        this.perdidaAgencia = perdidaAgencia;
    }

    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public double getMontoReembolsado() {
        return montoReembolsado;
    }

    public void setMontoReembolsado(double montoReembolsado) {
        this.montoReembolsado = montoReembolsado;
    }

    public double getPerdidaAgencia() {
        return perdidaAgencia;
    }

    public void setPerdidaAgencia(double perdidaAgencia) {
        this.perdidaAgencia = perdidaAgencia;
    }

}
