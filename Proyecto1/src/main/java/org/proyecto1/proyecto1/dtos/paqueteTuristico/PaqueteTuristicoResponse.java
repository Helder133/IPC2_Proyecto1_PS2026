package org.proyecto1.proyecto1.dtos.paqueteTuristico;

import org.proyecto1.proyecto1.dtos.destino.DestinoResponse;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;

public class PaqueteTuristicoResponse {
    private int paqueteId;
    private String nombre;
    private int destinoId;
    private int duracion;
    private double precioPublico;
    private int capacidadMaxima;
    private String descripcion;
    private boolean estado;
    private DestinoResponse destinoResponse;

    public PaqueteTuristicoResponse(PaqueteTuristico paqueteTuristico) {
        this.paqueteId = paqueteTuristico.getPaqueteId();
        this.nombre = paqueteTuristico.getNombre();
        this.destinoId = paqueteTuristico.getDestinoId();
        this.duracion = paqueteTuristico.getDuracion();
        this.precioPublico = paqueteTuristico.getPrecioPublico();
        this.capacidadMaxima = paqueteTuristico.getCapacidadMaxima();
        this.descripcion = paqueteTuristico.getDescripcion();
        this.estado = paqueteTuristico.isEstado();
        if (paqueteTuristico.getDestino() != null) {
            this.destinoResponse = new DestinoResponse(paqueteTuristico.getDestino());
        }
    }

    public int getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(int paqueteId) {
        this.paqueteId = paqueteId;
    }

    public int getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(int destinoId) {
        this.destinoId = destinoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getPrecioPublico() {
        return precioPublico;
    }

    public void setPrecioPublico(double precioPublico) {
        this.precioPublico = precioPublico;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public DestinoResponse getDestinoResponse() {
        return destinoResponse;
    }

    public void setDestinoResponse(DestinoResponse destinoResponse) {
        this.destinoResponse = destinoResponse;
    }
}
