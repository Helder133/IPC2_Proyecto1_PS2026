package org.proyecto1.proyecto1.dtos.reporte;

import org.proyecto1.proyecto1.models.reporte.AgenteConMasGanancia;

public class AgenteConMasGananciaResponse {
    private int usuarioId;
    private String nombreAgente;
    private double gananciaTotal;

    public AgenteConMasGananciaResponse(AgenteConMasGanancia agente) {
        this.usuarioId = agente.getUsuario_id();
        this.nombreAgente = agente.getNombreAgente();
        this.gananciaTotal = agente.getGananciaTotal();
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public void setNombreAgente(String nombreAgente) {
        this.nombreAgente = nombreAgente;
    }

    public double getGananciaTotal() {
        return gananciaTotal;
    }

    public void setGananciaTotal(double gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
    }

}
