package org.proyecto1.proyecto1.dtos.reporte;

import org.proyecto1.proyecto1.models.reporte.DestinoMasConcurrido;

public class DestinoMasConcurridoResponse {
    private String nombreDestino;
    private int cantidadViajes;
    private int totolTurista;

    public DestinoMasConcurridoResponse(DestinoMasConcurrido destinoMasConcurrido) {
        this.nombreDestino = destinoMasConcurrido.getNombreDestino();
        this.cantidadViajes = destinoMasConcurrido.getCantidadViajes();
        this.totolTurista = destinoMasConcurrido.getTotolTurista();
    }

    public int getCantidadViajes() {
        return cantidadViajes;
    }

    public void setCantidadViajes(int cantidadViajes) {
        this.cantidadViajes = cantidadViajes;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public int getTotolTurista() {
        return totolTurista;
    }

    public void setTotolTurista(int totolTurista) {
        this.totolTurista = totolTurista;
    }
}
