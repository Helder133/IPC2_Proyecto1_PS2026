package org.proyecto1.proyecto1.models.reporte.paqueteMasVendidoYMenosVendido;

import java.util.List;

public class ReportePaqueteMasVendidoYMenosVendido {
    private int paqueteId;
    private String nombrePaquete;
    private int totalReservacion;
    private List<DetalleReservacionPaquete> reservaciones;

    public ReportePaqueteMasVendidoYMenosVendido(int paqueteId, String nombrePaquete, int totalReservacion, List<DetalleReservacionPaquete> reservaciones) {
        this.paqueteId = paqueteId;
        this.nombrePaquete = nombrePaquete;
        this.totalReservacion = totalReservacion;
        this.reservaciones = reservaciones;
    }

    public int getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(int paqueteId) {
        this.paqueteId = paqueteId;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }

    public int getTotalReservacion() {
        return totalReservacion;
    }

    public void setTotalReservacion(int totalReservacion) {
        this.totalReservacion = totalReservacion;
    }

    public List<DetalleReservacionPaquete> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<DetalleReservacionPaquete> reservaciones) {
        this.reservaciones = reservaciones;
    }
}
