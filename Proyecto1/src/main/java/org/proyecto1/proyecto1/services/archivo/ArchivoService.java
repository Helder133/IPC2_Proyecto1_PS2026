package org.proyecto1.proyecto1.services.archivo;

import org.apache.commons.lang3.StringUtils;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.destino.Destino;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;
import org.proyecto1.proyecto1.models.proveedor.EnumProveedor;
import org.proyecto1.proyecto1.models.proveedor.Proveedor;
import org.proyecto1.proyecto1.models.servicioPaquete.ServicioPaquete;
import org.proyecto1.proyecto1.models.usuario.EnumUsuario;
import org.proyecto1.proyecto1.models.usuario.Usuario;
import org.proyecto1.proyecto1.services.destino.DestinoService;
import org.proyecto1.proyecto1.services.paqueteTuristico.PaqueteTuristicoService;
import org.proyecto1.proyecto1.services.proveedor.ProveedorService;
import org.proyecto1.proyecto1.services.servicioPaquete.ServicioPaqueteService;
import org.proyecto1.proyecto1.services.usuario.UsuarioService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
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
                                String datos = extraerDatos(line);
                                Destino destino = crearDestino(datos);
                                DestinoService destinoService = new DestinoService();
                                destinoService.insertDesdeArchivo(destino);
                            } case "PROVEEDOR" -> {
                                String datos = extraerDatos(line);
                                Proveedor proveedor = crearProveedor(datos);
                                ProveedorService proveedorService = new ProveedorService();
                                proveedorService.insertDesdeArchivo(proveedor);
                            } case "PAQUETE" -> {
                                String datos = extraerDatos(line);
                                PaqueteTuristico paquete = crearPaqueteTuristico(datos);
                                PaqueteTuristicoService paqueteService = new PaqueteTuristicoService();
                                paqueteService.insertDesdeArchivo(paquete);
                            } case "SERVICIO_PAQUETE" -> {
                                String datos = extraerDatos(line);
                                ServicioPaquete servicioPaquete = crearServicioPaquete(datos);
                                ServicioPaqueteService servicioPaqueteService = new ServicioPaqueteService();
                                servicioPaqueteService.insertDesdeArchivo(servicioPaquete);
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

    private Destino crearDestino(String subline) {
        String[] datos = subline.split(",");

        if (datos.length < 3) {
            throw new IllegalArgumentException("Faltan parámetros para crear DESTINO. Se esperaban 3.");
        }
        String nombre = datos[0].trim();
        String pais = datos[1].trim();
        String descripcion = datos[2].trim();

        return new Destino(nombre, pais, descripcion);

    }

    private Proveedor crearProveedor(String subline) {
        String[] datos = subline.split(",");

        if (datos.length < 3) {
            throw new IllegalArgumentException("Faltan parámetros para crear PROVEEDOR. Se esperaban 3.");
        }
        String nombre = datos[0].trim();
        String pais = datos[2].trim();
        EnumProveedor rol;
        switch (datos[1].trim()) {
            case "1" -> rol = EnumProveedor.Aerolinea;
            case "2" -> rol = EnumProveedor.Hotel;
            case "3" -> rol = EnumProveedor.Tour;
            case "4" -> rol = EnumProveedor.Traslado;
            case "5" -> rol = EnumProveedor.Otro;
            default -> throw new IllegalArgumentException("Rol inválido: " + datos[1] + ". Debe ser 1, 2, 3, 4 o 5.");
        }

        return new Proveedor(nombre, pais, rol);

    }

    private PaqueteTuristico crearPaqueteTuristico(String subline) throws SQLException, UserDataInvalidException {
        String[] datos = subline.split(",");

        if (datos.length < 5) {
            throw new IllegalArgumentException("Faltan parámetros para crear PAQUETE. Se esperaban 5.");
        }

        String nombre = datos[0].trim();
        String destino = datos[1].trim();
        int duracion = Integer.parseInt(datos[2].trim());
        double precioPublico = Double.parseDouble(datos[3].trim());
        int capacidadMaxima = Integer.parseInt(datos[4].trim());

        DestinoService destinoService = new DestinoService();
        int destinoId = destinoService.existsName(destino);
        if (destinoId == -1) {
            throw new UserDataInvalidException("El destino especificado no existe: " + destino);
        }

        return new PaqueteTuristico(destinoId, nombre, duracion, precioPublico, capacidadMaxima);
    }

    private ServicioPaquete crearServicioPaquete(String subline) throws SQLException, UserDataInvalidException {
        String[] datos = subline.split(",");

        if (datos.length < 4) {
            throw new IllegalArgumentException("Faltan parámetros para crear SERVICIO_PAQUETE. Se esperaban 4.");
        }

        String nombrePaquete = datos[0].trim();
        String nombreProveedor = datos[1].trim();
        String descripcion = datos[2].trim();
        double precio = Double.parseDouble(datos[3].trim());

        PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
        int paqueteId = paqueteTuristicoService.existsName(nombrePaquete);
        if (paqueteId == -1) {
            throw new UserDataInvalidException("El paquete turístico especificado no existe: " + nombrePaquete);
        }
        ProveedorService proveedorService = new ProveedorService();
        int proveedorId = proveedorService.existsName(nombreProveedor);
        if (proveedorId == -1) {
            throw new UserDataInvalidException("El proveedor especificado no existe: " + nombreProveedor);
        }

        return new ServicioPaquete(proveedorId, paqueteId, descripcion, precio);

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
