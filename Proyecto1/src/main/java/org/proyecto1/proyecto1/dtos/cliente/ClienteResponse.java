package org.proyecto1.proyecto1.dtos.cliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.proyecto1.proyecto1.models.cliente.Cliente;

import java.time.LocalDate;

public class ClienteResponse {

    private int cliente_id;
    private String dpi_o_pasaporte;
    private String nombre;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fecha;
    private String telefono;
    private String email;
    private String nacionalidad;

    public ClienteResponse(Cliente cliente) {
        this.cliente_id = cliente.getCliente_id();
        this.dpi_o_pasaporte = cliente.getDpi_o_pasaporte();
        this.nombre = cliente.getNombre();
        this.fecha = cliente.getFecha();
        this.telefono = cliente.getTelefono();
        this.email = cliente.getEmail();
        this.nacionalidad = cliente.getNacionalidad();
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


}
