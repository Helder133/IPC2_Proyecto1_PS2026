package org.proyecto1.proyecto1.dtos.usuario;

import org.proyecto1.proyecto1.models.usuario.EnumUsuario;

public class UsuarioRequest {

    private String nombre;
    private String password;
    private EnumUsuario rol;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnumUsuario getRol() {
        return rol;
    }

    public void setRol(EnumUsuario rol) {
        this.rol = rol;
    }

}
