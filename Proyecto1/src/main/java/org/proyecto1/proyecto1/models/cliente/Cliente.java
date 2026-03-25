package org.proyecto1.proyecto1.models.cliente;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class Cliente {
    private int cliente_id;
    private String dpi_o_pasaporte;
    private String nombre;
    private LocalDate fecha;
    private String telefono;
    private String email;
    private String nacionalidad;

    public Cliente(String dpi_o_pasaporte, String nombre, LocalDate fecha, String email, String telefono, String nacionalidad) {
        this.dpi_o_pasaporte = dpi_o_pasaporte;
        this.nombre = nombre;
        this.fecha = fecha;
        this.email = email;
        this.telefono = telefono;
        this.nacionalidad = nacionalidad;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getDpi_o_pasaporte() {
        return dpi_o_pasaporte;
    }

    public void setDpi_o_pasaporte(String dpi_o_pasaporte) {
        this.dpi_o_pasaporte = dpi_o_pasaporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(dpi_o_pasaporte)
                && StringUtils.isNotBlank(nombre)
                && fecha != null
                && StringUtils.isNotBlank(telefono)
                && StringUtils.isNotBlank(email)
                && StringUtils.isNotBlank(nacionalidad);
    }
}
