package org.proyecto1.proyecto1.services.archivo;

import org.apache.commons.lang3.StringUtils;
import org.proyecto1.proyecto1.models.usuario.EnumUsuario;
import org.proyecto1.proyecto1.models.usuario.Usuario;
import org.proyecto1.proyecto1.services.usuario.UsuarioService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ArchivoService {
    public List<String> leerArchivo(InputStream fileInputStream) throws IOException {
        List<String> resumenErrores = new ArrayList<>();
        int numeroLinea = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {
            String line;
            while (StringUtils.isNotBlank(line = reader.readLine())) {
                numeroLinea++;

                try {
                    int inicio = 0;
                    int fin = line.indexOf('(');
                    if (fin != -1) {
                        String subline = line.substring(inicio, fin);
                        switch (subline) {
                            case "USUARIO" -> {
                                String datos = extraerDatos(line);
                                Usuario usuario = crearUsuario(datos);
                                UsuarioService usuarioService = new UsuarioService();
                                usuarioService.insertDesdeArchivo(usuario);
                            } case "DESTINO" -> {
                                System.out.println(subline);
                                subline = extraerDatos(line);
                                System.out.println(subline);
                            } case "PROVEEDOR" -> {
                                System.out.println(subline);
                                subline = extraerDatos(line);
                                System.out.println(subline);
                            } case "PAQUETE" -> {
                                System.out.println(subline);
                                subline = extraerDatos(line);
                                System.out.println(subline);
                            } case "SERVICIO_PAQUETE" -> {
                                System.out.println(subline);
                                subline = extraerDatos(line);
                                System.out.println(subline);
                            } case "CLIENTE" -> {
                                System.out.println(subline);
                                subline = extraerDatos(line);
                                System.out.println(subline);
                            } case "RESERVACION" -> {
                                System.out.println(subline);
                                subline = extraerDatos(line);
                                System.out.println(subline);
                            } case "PAGO" -> {
                                System.out.println(subline);
                                subline = extraerDatos(line);
                                System.out.println(subline);
                            }
                        }
                    }
                } catch (Exception e) {
                    String errorMsg = String.format("Error en línea %d [%s]: %s", numeroLinea, line, e.getMessage());
                    resumenErrores.add(errorMsg);
                }
            }
        }
        return resumenErrores;
    }

    private Usuario crearUsuario(String subline) {
        String[] datos = subline.split(",");

        if (datos.length < 3) {
            throw new IllegalArgumentException("Faltan parámetros para crear USUARIO. Se esperaban 3.");
        }

        String nombre = datos[0].trim();
        String password = datos[1].trim();
        EnumUsuario rol;

        switch (datos[2].trim()) {
            case "1" -> rol = EnumUsuario.Atencion_al_Cliente;
            case "2" -> rol = EnumUsuario.Operaciones;
            case "3" -> rol = EnumUsuario.Administrador;
            default -> throw new IllegalArgumentException("Rol inválido: " + datos[2] + ". Debe ser 1, 2 o 3.");
        }

        return new Usuario(nombre, password, rol);
    }

    private String extraerDatos(String line) {
        int inicio = line.indexOf('(');
        int fin = line.lastIndexOf(')');

        if (inicio != -1 && fin != -1 && inicio < fin) {
            return line.substring(inicio + 1, fin);
        }
        return "";
    }

}
