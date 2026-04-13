package org.proyecto1.proyecto1.dtos.reporte.mejorAgente;

import org.proyecto1.proyecto1.models.reporte.mejorAgente.DetalleReservacion;
import org.proyecto1.proyecto1.models.reporte.mejorAgente.ReporteMejorAgente;

import java.util.List;

public class ReporteMejorAgenteRespnse {
    private String nombreAgente;
    private double totalVendido;
    private List<DetalleReservacionResponse> reservaciones;

    public ReporteMejorAgenteRespnse(ReporteMejorAgente reporteMejorAgente) {
        this.nombreAgente = reporteMejorAgente.getNombreAgente();
        this.totalVendido = reporteMejorAgente.getTotalVendido();
        this.reservaciones = reporteMejorAgente.getReservaciones().stream().map(DetalleReservacionResponse::new).toList();
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

    public List<DetalleReservacionResponse> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<DetalleReservacionResponse> reservaciones) {
        this.reservaciones = reservaciones;
    }
}
