package org.proyecto1.proyecto1.models.reporte;

public class AgenteConMasGanancia {
    private int usuario_id;
    private String nombreAgente;
    private double gananciaTotal;

    public AgenteConMasGanancia(int usuario_id, String nombreAgente, double gananciaTotal) {
        this.usuario_id = usuario_id;
        this.nombreAgente = nombreAgente;
        this.gananciaTotal = gananciaTotal;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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
