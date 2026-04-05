package org.proyecto1.proyecto1.dtos.proveedor;

import org.proyecto1.proyecto1.models.proveedor.EnumProveedor;
import org.proyecto1.proyecto1.models.proveedor.Proveedor;

public class ProveedorResponse {
    private int proveedor_id;
    private String nombre;
    private String pais;
    private EnumProveedor tipo;
    private String contacto;

    public ProveedorResponse(Proveedor proveedor) {
        this.proveedor_id = proveedor.getProveedor_id();
        this.nombre = proveedor.getNombre();
        this.pais = proveedor.getPais();
        this.tipo = proveedor.getTipo();
        this.contacto = proveedor.getContacto();
    }

    public int getProveedor_id() {
        return proveedor_id;
    }

    public void setProveedor_id(int proveedor_id) {
        this.proveedor_id = proveedor_id;
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
