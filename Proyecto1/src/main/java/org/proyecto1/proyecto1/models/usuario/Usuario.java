package org.proyecto1.proyecto1.models.usuario;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Usuario {
    private int usuario_id;
    private String nombre;
    private String password;
    private EnumUsuario rol;
    private boolean estado;

    public Usuario(String nombre, String password, EnumUsuario rol) {
        this.nombre = nombre;
        this.password = encriptarContrasena(password);
        this.rol = rol;
        this.estado = true;

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
        this.password = encriptarContrasena(password);
    }

    public EnumUsuario getRol() {
        return rol;
    }

    public void setRol(EnumUsuario rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(nombre) &&
                StringUtils.isNotBlank(password) &&
                rol != null;
    }

    public boolean isValidUpdate() {
        return StringUtils.isNotBlank(nombre) &&
                rol != null;
    }

    private String encriptarContrasena(String contraseña) {
        byte[] message = contraseña.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(message);
    }
}
