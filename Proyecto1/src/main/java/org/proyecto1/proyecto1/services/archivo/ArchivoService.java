package org.proyecto1.proyecto1.services.archivo;

import org.apache.commons.lang3.StringUtils;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.cliente.Cliente;
import org.proyecto1.proyecto1.models.destino.Destino;
import org.proyecto1.proyecto1.models.pago.EnumPago;
import org.proyecto1.proyecto1.models.pago.HistorialPago;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;
import org.proyecto1.proyecto1.models.proveedor.EnumProveedor;
import org.proyecto1.proyecto1.models.proveedor.Proveedor;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;
import org.proyecto1.proyecto1.models.servicioPaquete.ServicioPaquete;
import org.proyecto1.proyecto1.models.usuario.EnumUsuario;
import org.proyecto1.proyecto1.models.usuario.Usuario;
import org.proyecto1.proyecto1.services.cliente.ClienteService;
import org.proyecto1.proyecto1.services.destino.DestinoService;
import org.proyecto1.proyecto1.services.pago.HistorialPagoService;
import org.proyecto1.proyecto1.services.paqueteTuristico.PaqueteTuristicoService;
import org.proyecto1.proyecto1.services.proveedor.ProveedorService;
import org.proyecto1.proyecto1.services.reservacion.ReservacionService;
import org.proyecto1.proyecto1.services.servicioPaquete.ServicioPaqueteService;
import org.proyecto1.proyecto1.services.usuario.UsuarioService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
                            }
                            case "DESTINO" -> {
                                String datos = extraerDatos(line);
                                Destino destino = crearDestino(datos);
                                DestinoService destinoService = new DestinoService();
                                destinoService.insertDesdeArchivo(destino);
                            }
                            case "PROVEEDOR" -> {
                                String datos = extraerDatos(line);
                                Proveedor proveedor = crearProveedor(datos);
                                ProveedorService proveedorService = new ProveedorService();
                                proveedorService.insertDesdeArchivo(proveedor);
                            }
                            case "PAQUETE" -> {
                                String datos = extraerDatos(line);
                                PaqueteTuristico paquete = crearPaqueteTuristico(datos);
                                PaqueteTuristicoService paqueteService = new PaqueteTuristicoService();
                                paqueteService.insertDesdeArchivo(paquete);
                            }
                            case "SERVICIO_PAQUETE" -> {
                                String datos = extraerDatos(line);
                                ServicioPaquete servicioPaquete = crearServicioPaquete(datos);
                                ServicioPaqueteService servicioPaqueteService = new ServicioPaqueteService();
                                servicioPaqueteService.insertDesdeArchivo(servicioPaquete);
                            }
                            case "CLIENTE" -> {
                                String datos = extraerDatos(line);
                                Cliente cliente = crearCliente(datos);
                                ClienteService clienteService = new ClienteService();
                                clienteService.insertDesdeArchivo(cliente);
                            }
                            case "RESERVACION" -> {
                                String datos = extraerDatos(line);
                                Reservacion reservacion = crearReservacion(datos);
                                List<String> clienteReservacion = extraerClienteParaReservacion(datos);
                                ReservacionService reservacionService = new ReservacionService();
                                reservacionService.insertDesdeArchivo(reservacion, clienteReservacion);
                            }
                            case "PAGO" -> {
                                String datos = extraerDatos(line);
                                HistorialPago historialPago = extraerHistorialPago(datos);
                                HistorialPagoService historialPagoService = new HistorialPagoService();
                                historialPagoService.insertDesdeArchivo(historialPago);
                            }
                            default -> {
                                String errorMsg = String.format("Error en línea %d: Tipo de entidad desconocida [%s]", numeroLinea, subline);
                                resumenErrores.add(errorMsg);
                            }
                        }
                    }
                } catch (Exception e) {
                    String errorMsg = String.format("Error en línea %d %s: %s", numeroLinea, line.trim().replace("\"", ""), e.getMessage().trim().replace("\"", ""));
                    resumenErrores.add(errorMsg);
                }
            }
        }
        return resumenErrores;
    }

    private Usuario crearUsuario(String subline) throws IllegalArgumentException {
        String[] datos = subline.split(",");

        if (datos.length < 3) {
            throw new IllegalArgumentException("Faltan parámetros para crear USUARIO. Se esperaban 3.");
        }

        String nombre = datos[0].trim().replace("\"", "");
        String password = datos[1].trim().replace("\"", "");
        EnumUsuario rol;

        switch (datos[2].trim().replace("\"", "")) {
            case "1" -> rol = EnumUsuario.Atencion_al_Cliente;
            case "2" -> rol = EnumUsuario.Operaciones;
            case "3" -> rol = EnumUsuario.Administrador;
            default -> throw new IllegalArgumentException("Rol inválido: " + datos[2] + ". Debe ser 1, 2 o 3.");
        }

        return new Usuario(nombre, password, rol);
    }

    private Destino crearDestino(String subline) throws IllegalArgumentException {
        String[] datos = subline.split(",");

        if (datos.length < 3) {
            throw new IllegalArgumentException("Faltan parámetros para crear DESTINO. Se esperaban 3.");
        }
        String nombre = datos[0].trim().replace("\"", "");
        String pais = datos[1].trim().replace("\"", "");
        String descripcion = datos[2].trim().replace("\"", "");

        return new Destino(nombre, pais, descripcion);

    }

    private Proveedor crearProveedor(String subline) throws IllegalArgumentException {
        String[] datos = subline.split(",");

        if (datos.length < 3) {
            throw new IllegalArgumentException("Faltan parámetros para crear PROVEEDOR. Se esperaban 3.");
        }
        String nombre = datos[0].trim().replace("\"", "");
        EnumProveedor rol;
        switch (datos[1].trim().replace("\"", "")) {
            case "1" -> rol = EnumProveedor.Aerolinea;
            case "2" -> rol = EnumProveedor.Hotel;
            case "3" -> rol = EnumProveedor.Tour;
            case "4" -> rol = EnumProveedor.Traslado;
            case "5" -> rol = EnumProveedor.Otro;
            default -> throw new IllegalArgumentException("Rol inválido: " + datos[1] + ". Debe ser 1, 2, 3, 4 o 5.");
        }
        String pais = datos[2].trim().replace("\"", "");

        return new Proveedor(nombre, pais, rol);

    }

    private PaqueteTuristico crearPaqueteTuristico(String subline) throws SQLException, IllegalArgumentException {
        String[] datos = subline.split(",");

        if (datos.length < 5) {
            throw new IllegalArgumentException("Faltan parámetros para crear PAQUETE. Se esperaban 5.");
        }

        String nombre = datos[0].trim().replace("\"", "");
        String destino = datos[1].trim().replace("\"", "");
        int duracion;
        double precioPublico;
        int capacidadMaxima;
        try {
            duracion = Integer.parseInt(datos[2].trim().replace("\"", ""));
            precioPublico = Double.parseDouble(datos[3].trim().replace("\"", ""));
            capacidadMaxima = Integer.parseInt(datos[4].trim().replace("\"", ""));
            if (duracion < 0 || precioPublico < 0 || capacidadMaxima < 0) {
                throw new IllegalArgumentException("Duración, precio y capacidad máxima no pueden ser negativas: " + duracion + ", " + precioPublico + ", " + capacidadMaxima);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Duración, precio y capacidad máxima deben ser numéricos. Error: " + e.getMessage());
        }

        DestinoService destinoService = new DestinoService();
        if (destinoService.existsName(destino) == -1) {
            throw new UserDataInvalidException("El destino especificado no existe: " + destino);
        }
        int destinoId = destinoService.existsName(destino);

        return new PaqueteTuristico(destinoId, nombre, duracion, precioPublico, capacidadMaxima);
    }

    private ServicioPaquete crearServicioPaquete(String subline) throws SQLException, UserDataInvalidException, IllegalArgumentException {
        String[] datos = subline.split(",");

        if (datos.length < 4) {
            throw new IllegalArgumentException("Faltan parámetros para crear SERVICIO_PAQUETE. Se esperaban 4.");
        }

        String nombrePaquete = datos[0].trim().replace("\"", "");
        String nombreProveedor = datos[1].trim().replace("\"", "");
        String descripcion = datos[2].trim().replace("\"", "");
        double precio;
        try {
            precio = Double.parseDouble(datos[3].trim().replace("\"", ""));
            if (precio < 0) {
                throw new IllegalArgumentException("El precio no puede ser negativo: " + precio);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El precio debe ser un valor numérico. Error: " + e.getMessage());
        }

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

    private Cliente crearCliente(String subline) throws IllegalArgumentException {
        String[] datos = subline.split(",");
        if (datos.length < 6) {
            throw new IllegalArgumentException("Faltan parámetros para crear CLIENTE. Se esperaban 6.");
        }
        String dpiOPasaporte = datos[0].trim().replace("\"", "");
        String nombre = datos[1].trim().replace("\"", "");
        LocalDate fechaNacimiento;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaNacimiento = LocalDate.parse(datos[2].trim().replace("\"", ""), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha de nacimiento inválida: " + datos[2] + ". El formato debe ser dd/MM/yyyy.");
        }
        String telefono = datos[3].trim().replace("\"", "");
        String email = datos[4].trim().replace("\"", "");
        String nacionalidad = datos[5].trim().replace("\"", "");

        return new Cliente(dpiOPasaporte, nombre, fechaNacimiento, email, telefono, nacionalidad);
    }

    private Reservacion crearReservacion(String subline) throws IllegalArgumentException, SQLException {
        String[] datos = subline.split(",");
        if (datos.length < 4) {
            throw new IllegalArgumentException("Faltan parámetros para crear RESERVACION. Se esperaban 4.");
        }
        PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
        String paquete = datos[0].trim().replace("\"", "");
        int paqueteId = paqueteTuristicoService.existsName(paquete);
        if (paqueteId == -1) throw new IllegalArgumentException("El Paquete no existe: " + paquete);
        UsuarioService usuarioService = new UsuarioService();
        String usuario = datos[1].trim().replace("\"", "");
        int usuarioId = usuarioService.existsName(usuario);
        if (usuarioId == -1) throw new IllegalArgumentException("El usuario no existe: " + usuario);
        LocalDate fechaViaje;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaViaje = LocalDate.parse(datos[2].trim().replace("\"", ""), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha de reservación inválida: " + datos[2] + ". El formato debe ser dd/MM/yyyy.");
        }

        return new Reservacion(paqueteId, usuarioId, fechaViaje);
    }

    private List<String> extraerClienteParaReservacion(String subline) {
        String[] datos = subline.split(",");
        return Arrays.stream(datos[3].trim().replace("\"", "").split("\\|"))
                .map(String::trim)
                .toList();
    }

    private HistorialPago extraerHistorialPago(String subline) throws IllegalArgumentException, SQLException {
        String[] datos = subline.split(",");
        if (datos.length < 4) {
            throw new IllegalArgumentException("Faltan parámetros para crear PAGO. Se esperaban 4.");
        }
        ReservacionService reservacionService = new ReservacionService();
        String codigoArchivo = datos[0].trim().replace("\"", "");
        int reservacionId = reservacionService.getByCodigoArchivo(codigoArchivo);
        double monto;
        try {
            monto = Double.parseDouble(datos[1].trim().replace("\"", ""));
            if (monto <= 0)  throw new IllegalArgumentException("El monto debe ser mayor a 0");
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El monto debe ser un valor numérico. Error:" + e.getMessage());
        }
        EnumPago metodoPago;
        switch (datos[2].trim().replace("\"", "")) {
            case "1" -> metodoPago = EnumPago.Efectivo;
            case "2" -> metodoPago = EnumPago.Tarjeta;
            case "3" -> metodoPago = EnumPago.Transferencia;
            default ->
                    throw new IllegalArgumentException("Método de pago inválido: " + datos[2] + ". Debe ser 1, 2 o 3.");
        }
        LocalDate fechaPago;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaPago = LocalDate.parse(datos[3].trim().replace("\"", ""), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha de pago inválida: " + datos[3] + ". El formato debe ser dd/MM/yyyy.");
        }

        return new HistorialPago(reservacionId, monto, metodoPago, fechaPago);

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
