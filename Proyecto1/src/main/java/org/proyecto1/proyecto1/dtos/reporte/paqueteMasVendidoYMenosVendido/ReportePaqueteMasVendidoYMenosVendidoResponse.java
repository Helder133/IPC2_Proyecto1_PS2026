package org.proyecto1.proyecto1.dtos.reporte.paqueteMasVenvido;

import org.proyecto1.proyecto1.models.reporte.paqueteMasVenvido.DetalleReservacionPaquete;
import org.proyecto1.proyecto1.models.reporte.paqueteMasVenvido.ReportePaqueteMasVendido;

import java.util.List;

public class ReportePaqueteMasVendidoResponse {
    private int paqueteId;
    private String nombrePaquete;
    private int totalReservacion;
    private List<DetalleReservacionPaqueteResponse> reservaciones;

    public ReportePaqueteMasVendidoResponse(ReportePaqueteMasVendido reportePaqueteMasVendido) {
        this.paqueteId = reportePaqueteMasVendido.getPaqueteId();
        this.nombrePaquete = reportePaqueteMasVendido.getNombrePaquete();
        this.totalReservacion = reportePaqueteMasVendido.getTotalReservacion();
        this.reservaciones = reportePaqueteMasVendido.getReservaciones().stream().map(DetalleReservacionPaqueteResponse::new).toList();
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

    public List<DetalleReservacionPaqueteResponse> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<DetalleReservacionPaqueteResponse> reservaciones) {
        this.reservaciones = reservaciones;
    }
}
