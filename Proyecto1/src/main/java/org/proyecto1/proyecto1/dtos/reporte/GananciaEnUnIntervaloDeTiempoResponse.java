package org.proyecto1.proyecto1.dtos.reporte;

import org.proyecto1.proyecto1.models.reporte.GananciaEnUnIntervaloDeTiempo;

public class GananciaEnUnIntervaloDeTiempoResponse {
    private double gananciaBruta;
    private double totalReembolsos;
    private double gananciaNeta;

    public GananciaEnUnIntervaloDeTiempoResponse(GananciaEnUnIntervaloDeTiempo ganancia) {
        this.gananciaBruta = ganancia.getGananciaBruta();
        this.totalReembolsos = ganancia.getTotalReembolsos();
        this.gananciaNeta = ganancia.getGananciaNeta();
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
