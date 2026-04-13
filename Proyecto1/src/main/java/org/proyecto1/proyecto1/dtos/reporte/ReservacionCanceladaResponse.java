package org.proyecto1.proyecto1.dtos.reporte;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.proyecto1.proyecto1.models.reporte.ReservacionCancelada;

import java.time.LocalDate;

public class ReservacionCanceladaResponse {
    private int reservacionId;
    private String paquete;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaCancelacion;
    private double montoReembolsado;
    private double perdidaAgencia;

    public ReservacionCanceladaResponse(ReservacionCancelada reservacionCancelada) {
        this.reservacionId = reservacionCancelada.getReservacionId();
        this.paquete = reservacionCancelada.getPaquete();
        this.fechaCancelacion = reservacionCancelada.getFechaCancelacion();
        this.montoReembolsado = reservacionCancelada.getMontoReembolsado();
        this.perdidaAgencia = reservacionCancelada.getPerdidaAgencia();
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
