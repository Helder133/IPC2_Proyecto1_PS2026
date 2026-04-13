package org.proyecto1.proyecto1.dtos.reporte;

import org.proyecto1.proyecto1.models.reporte.ReservacionConfirmada;

public class ReservacionConfirmadaResponse {
    private int reservacionId;
    private String paquete;
    private int pasajeros;
    private String agente;
    private double costoTotal;

    public ReservacionConfirmadaResponse(ReservacionConfirmada reservacionConfirmada) {
        this.reservacionId = reservacionConfirmada.getReservacionId();
        this.paquete = reservacionConfirmada.getPaquete();
        this.pasajeros = reservacionConfirmada.getPasajeros();
        this.agente = reservacionConfirmada.getAgente();
        this.costoTotal = reservacionConfirmada.getCostoTotal();
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

    public int getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(int pasajeros) {
        this.pasajeros = pasajeros;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}
