package org.proyecto1.proyecto1.models.reporte.mejorAgente;

import java.util.List;

public class ReporteMejorAgente {
    private String nombreAgente;
    private double totalVendido;
    private List<DetalleReservacion> reservaciones;

    public ReporteMejorAgente(String nombreAgente, double totalVendido, List<DetalleReservacion> reservaciones) {
        this.nombreAgente = nombreAgente;
        this.totalVendido = totalVendido;
        this.reservaciones = reservaciones;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public List<DetalleReservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<DetalleReservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }
}
