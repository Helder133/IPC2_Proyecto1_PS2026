package org.proyecto1.proyecto1.models.servicioPaquete;

import org.apache.commons.lang3.StringUtils;

public class ServicioPaquete {
    private int proveedorId;
    private int paqueteId;
    private String descripcion;
    private double costo;

    public ServicioPaquete(int proveedorId, int paqueteId, String descripcion, double costo) {
        this.proveedorId = proveedorId;
        this.paqueteId = paqueteId;
        this.descripcion = descripcion;
        this.costo = costo;
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

    public boolean isValid() {
        return StringUtils.isNotBlank(descripcion)
                && costo > 0
                && proveedorId > 0
                && paqueteId > 0;
    }
}
