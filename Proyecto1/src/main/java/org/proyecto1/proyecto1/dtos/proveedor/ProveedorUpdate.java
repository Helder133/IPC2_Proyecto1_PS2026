package org.proyecto1.proyecto1.dtos.proveedor;

import org.proyecto1.proyecto1.models.proveedor.EnumProveedor;

public class ProveedorUpdate {
    private int proveedorId;
    private String nombre;
    private String pais;
    private EnumProveedor tipo;
    private String contacto;

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public EnumProveedor getTipo() {
        return tipo;
    }

    public void setTipo(EnumProveedor tipo) {
        this.tipo = tipo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}
