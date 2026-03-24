package org.proyecto1.proyecto1.dtos.usuario;

import org.proyecto1.proyecto1.models.usuario.EnumUsuario;
import org.proyecto1.proyecto1.models.usuario.Usuario;

public class UsuarioResponse {
    private int usuario_id;
    private String nombre;
    private String password;
    private EnumUsuario rol;

    public UsuarioResponse(Usuario usuario) {
        this.usuario_id = usuario.getUsuario_id();
        this.nombre = usuario.getNombre();
        this.password = "";
        this.rol = usuario.getRol();
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

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
