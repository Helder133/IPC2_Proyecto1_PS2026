package org.proyecto1.proyecto1.models.reporte;

public class GananciaEnUnIntervaloDeTiempo {
    private double gananciaBruta;
    private double totalReembolsos;
    private double gananciaNeta;

    public GananciaEnUnIntervaloDeTiempo(double gananciaBruta, double totalReembolsos, double gananciaNeta) {
        this.gananciaBruta = gananciaBruta;
        this.totalReembolsos = totalReembolsos;
        this.gananciaNeta = gananciaNeta;
    }

    public double getGananciaBruta() {
        return gananciaBruta;
    }

    public void setGananciaBruta(double gananciaBruta) {
        this.gananciaBruta = gananciaBruta;
    }

    public double getTotalReembolsos() {
        return totalReembolsos;
    }

    public void setTotalReembolsos(double totalReembolsos) {
        this.totalReembolsos = totalReembolsos;
    }

    public double getGananciaNeta() {
        return gananciaNeta;
    }

    public void setGananciaNeta(double gananciaNeta) {
        this.gananciaNeta = gananciaNeta;
    }

}
