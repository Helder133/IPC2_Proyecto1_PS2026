package org.proyecto1.proyecto1.models.reporte;

public class ReservacionConfirmada {
    private int reservacionId;
    private String paquete;
    private int pasajeros;
    private String agente;
    private double costoTotal;

    public ReservacionConfirmada(int reservacionId, String paquete, int pasajeros, String agente, double costoTotal) {
        this.reservacionId = reservacionId;
        this.paquete = paquete;
        this.pasajeros = pasajeros;
        this.agente = agente;
        this.costoTotal = costoTotal;
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
