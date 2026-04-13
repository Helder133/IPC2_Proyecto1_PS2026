package org.proyecto1.proyecto1.models.reporte;

public class DestinoMaConcurrido {
    private String nombreDestino;
    private int cantidadViajes;
    private int totolTurista;

    public DestinoMaConcurrido(String nombreDestino, int totolTurista, int cantidadViajes) {
        this.nombreDestino = nombreDestino;
        this.totolTurista = totolTurista;
        this.cantidadViajes = cantidadViajes;
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
