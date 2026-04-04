package org.proyecto1.proyecto1.models.paqueteTuristico;

import org.apache.commons.lang3.StringUtils;

public class PaqueteTuristico {
    private int paqueteId;
    private int destinoId;
    private String nombre;
    private int duracion;
    private double precioPublico;
    private int capacidadMaxima;
    private String descripcion;
    private boolean estado;

    public PaqueteTuristico(int destinoId, String nombre, int duracion, double precioPublico, int capacidadMaxima) {
        this.destinoId = destinoId;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precioPublico = precioPublico;
        this.capacidadMaxima = capacidadMaxima;
        this.estado = true;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public boolean isValid() {
        return StringUtils.isNotBlank(nombre) &&
                duracion > 0 &&
                precioPublico > 0 &&
                capacidadMaxima > 0;
    }

}
