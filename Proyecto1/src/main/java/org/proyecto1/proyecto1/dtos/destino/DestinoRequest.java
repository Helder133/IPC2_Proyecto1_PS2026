package org.proyecto1.proyecto1.dtos.destino;

public class DestinoRequest {
    private String nombre;
    private String pais;
    private String descripcion;
    private String clima_mejor_epoca;
    private String imagen;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClima_mejor_epoca() {
        return clima_mejor_epoca;
    }

    public void setClima_mejor_epoca(String clima_mejor_epoca) {
        this.clima_mejor_epoca = clima_mejor_epoca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
