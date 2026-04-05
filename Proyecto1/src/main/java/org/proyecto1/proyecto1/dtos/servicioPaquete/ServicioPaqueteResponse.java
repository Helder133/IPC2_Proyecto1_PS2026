package org.proyecto1.proyecto1.dtos.servicioPaquete;

import org.proyecto1.proyecto1.models.servicioPaquete.ServicioPaquete;

public class ServicioPaqueteResponse {
    private int proveedorId;
    private int paqueteId;
    private String descripcion;
    private double costo;

    public ServicioPaqueteResponse(ServicioPaquete servicioPaquete) {
        this.proveedorId = servicioPaquete.getProveedorId();
        this.paqueteId = servicioPaquete.getPaqueteId();
        this.descripcion = servicioPaquete.getDescripcion();
        this.costo = servicioPaquete.getCosto();
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public int getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(int paqueteId) {
        this.paqueteId = paqueteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
