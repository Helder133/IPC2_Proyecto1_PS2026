package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.reporte.*;
import org.proyecto1.proyecto1.models.reporte.mejorAgente.DetalleReservacion;
import org.proyecto1.proyecto1.models.reporte.mejorAgente.ReporteMejorAgente;
import org.proyecto1.proyecto1.models.reporte.paqueteMasVendidoYMenosVendido.DetalleReservacionPaquete;
import org.proyecto1.proyecto1.models.reporte.paqueteMasVendidoYMenosVendido.ReportePaqueteMasVendidoYMenosVendido;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReporteDAO {
    private static final String RESERVACION_CONFIRMADA = "SELECT r.reservacion_id, pt.nombre AS paquete, r.cantidad_persona AS pasajeros, u.nombre AS agente, r.costo_total FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id JOIN usuario u ON r.usuario_id = u.usuario_id WHERE r.estado = 'Confirmada' AND r.fecha_creacion BETWEEN ? AND ?";
    private static final String RESERVACION_CANCELADAS = "SELECT r.reservacion_id, pt.nombre AS paquete, r.fecha_cancelacion, COALESCE(r.reembolso, 0) AS monto_reembolsado, (r.costo_agencia - (COALESCE(SUM(hp.monto), 0) - COALESCE(r.reembolso, 0))) AS perdida_agencia FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id LEFT JOIN historial_pago hp ON r.reservacion_id = hp.reservacion_id WHERE r.estado = 'Cancelada' AND r.fecha_cancelacion BETWEEN ? AND ? GROUP BY r.reservacion_id, pt.nombre, r.fecha_cancelacion, r.reembolso, r.costo_agencia";
    private static final String GANANCIAS_DE_UN_INTERVALO = "SELECT SUM(total_pagado - costo_agencia) AS ganancia_bruta, SUM(reembolso_aplicado) AS total_reembolsos, SUM((total_pagado - costo_agencia) - reembolso_aplicado) AS ganancia_neta FROM ( SELECT  r.reservacion_id, COALESCE(r.costo_agencia, 0) AS costo_agencia, COALESCE(r.reembolso, 0) AS reembolso_aplicado, (SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id) AS total_pagado FROM reservacion r WHERE r.fecha_creacion BETWEEN ? AND ?) AS resumen_financiero";
    private static final String MEJOR_AGENTE = "SELECT u.usuario_id, u.nombre, SUM(r.costo_total) AS total_vendido FROM reservacion r JOIN usuario u ON r.usuario_id = u.usuario_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? GROUP BY u.usuario_id, u.nombre ORDER BY total_vendido DESC LIMIT 1";
    private static final String MEJOR_AGENTE_DETALLE = "SELECT r.reservacion_id, pt.nombre AS paquete_nombre, r.fecha_creacion, r.fecha_viaje, r.cantidad_persona, r.costo_total FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id WHERE r.usuario_id = ? AND r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ?";
    private static final String AGENTE_CON_MAS_GANANCIA = "SELECT u.usuario_id, u.nombre AS agente, SUM((resumen.total_pagado - resumen.costo_agencia) - resumen.reembolso_aplicado) AS ganancia_neta FROM ( SELECT r.reservacion_id, r.usuario_id, COALESCE(r.costo_agencia, 0) AS costo_agencia, COALESCE(r.reembolso, 0) AS reembolso_aplicado, (SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id) AS total_pagado FROM reservacion r WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? ) AS resumen JOIN usuario u ON resumen.usuario_id = u.usuario_id GROUP BY u.usuario_id, u.nombre ORDER BY ganancia_neta DESC LIMIT 1";
    private static final String MEJOR_PAQUETE = "SELECT pt.paquete_id, pt.nombre, COUNT(r.reservacion_id) AS total_reservaciones FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? GROUP BY pt.paquete_id, pt.nombre ORDER BY total_reservaciones DESC LIMIT 1";
    private static final String PAQUETE_DETALLE = "SELECT r.reservacion_id, u.nombre AS agente, r.fecha_creacion, r.fecha_viaje, r.cantidad_persona, r.costo_total FROM reservacion r JOIN usuario u ON r.usuario_id = u.usuario_id WHERE r.paquete_id = ? AND r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ?";
    private static final String PEOR_PAQUETE = "SELECT pt.paquete_id, pt.nombre, COUNT(r.reservacion_id) AS total_reservaciones FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? GROUP BY pt.paquete_id, pt.nombre ORDER BY total_reservaciones ASC LIMIT 1";
    private static final String DESTINO_MAS_CONCURRIDO = "SELECT d.nombre AS destino, COUNT(r.reservacion_id) AS cantidad_viajes, SUM(r.cantidad_persona) AS total_turistas FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id JOIN destino d ON pt.destino_id = d.destino_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_viaje BETWEEN ? AND ? GROUP BY d.destino_id, d.nombre ORDER BY cantidad_viajes DESC";

    public List<ReservacionConfirmada> getReservacionConfirmada(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<ReservacionConfirmada> reservacionConfirmada = new ArrayList<>();
        try (PreparedStatement getReservacionConfirmada = connection.prepareStatement(RESERVACION_CONFIRMADA)) {
            getReservacionConfirmada.setDate(1, Date.valueOf(fechaInicio));
            getReservacionConfirmada.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getReservacionConfirmada.executeQuery()) {
                while (resultSet.next()) {
                    ReservacionConfirmada reservacionConfirmada1 = new ReservacionConfirmada(
                            resultSet.getInt("reservacion_id"),
                            resultSet.getString("paquete"),
                            resultSet.getInt("pasajeros"),
                            resultSet.getString("agente"),
                            resultSet.getDouble("costo_total")
                    );
                    reservacionConfirmada.add(reservacionConfirmada1);
                }
                return reservacionConfirmada;
            }
        }
    }

    public List<ReservacionCancelada> getReservacionCancelada(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<ReservacionCancelada> reservacionCancelada = new ArrayList<>();
        try (PreparedStatement getReservacionCancelada = connection.prepareStatement(RESERVACION_CANCELADAS)) {
            getReservacionCancelada.setDate(1, Date.valueOf(fechaInicio));
            getReservacionCancelada.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getReservacionCancelada.executeQuery()) {
                while (resultSet.next()) {
                    ReservacionCancelada reservacionCancelada1 = new ReservacionCancelada(
                            resultSet.getInt("reservacion_id"),
                            resultSet.getString("paquete"),
                            resultSet.getDate("fecha_cancelacion").toLocalDate(),
                            resultSet.getDouble("monto_reembolsado"),
                            resultSet.getDouble("perdida_agencia")
                    );
                    reservacionCancelada.add(reservacionCancelada1);
                }
                return reservacionCancelada;
            }
        }
    }

    public GananciaEnUnIntervaloDeTiempo getGananciaEnUnIntervaloDeTiempo(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getGananciaEnUnIntervaloDeTiempo = connection.prepareStatement(GANANCIAS_DE_UN_INTERVALO)) {
            getGananciaEnUnIntervaloDeTiempo.setDate(1, Date.valueOf(fechaInicio));
            getGananciaEnUnIntervaloDeTiempo.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getGananciaEnUnIntervaloDeTiempo.executeQuery()) {
                if (resultSet.next()) {
                    return new GananciaEnUnIntervaloDeTiempo(
                            resultSet.getDouble("ganancia_bruta"),
                            resultSet.getDouble("total_reembolsos"),
                            resultSet.getDouble("ganancia_neta")
                    );
                }
                return new GananciaEnUnIntervaloDeTiempo(0.0, 0.0, 0.0);
            }
        }
    }

    public Optional<ReporteMejorAgente> getMejorAgente(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getMejorAgente = connection.prepareStatement(MEJOR_AGENTE)) {
            getMejorAgente.setDate(1, Date.valueOf(fechaInicio));
            getMejorAgente.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getMejorAgente.executeQuery()) {
                if (resultSet.next()) {
                    int agenteId = resultSet.getInt("usuario_id");
                    String nombreAgente = resultSet.getString("nombre");
                    double totalVendido = resultSet.getDouble("total_vendido");
                    List<DetalleReservacion> detalleReservaciones = getDetalleReservacionesDelAgente(agenteId, fechaInicio, fechaFin);
                    return Optional.of(new ReporteMejorAgente(nombreAgente, totalVendido, detalleReservaciones));
                }
                return Optional.empty();
            }
        }
    }

    private List<DetalleReservacion> getDetalleReservacionesDelAgente(int agenteId, LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<DetalleReservacion> detalleReservaciones = new ArrayList<>();
        try (PreparedStatement getDetalleReservaciones = connection.prepareStatement(MEJOR_AGENTE_DETALLE)) {
            getDetalleReservaciones.setInt(1, agenteId);
            getDetalleReservaciones.setDate(2, Date.valueOf(fechaInicio));
            getDetalleReservaciones.setDate(3, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getDetalleReservaciones.executeQuery()) {
                while (resultSet.next()) {
                    DetalleReservacion detalleReservacion = new DetalleReservacion(
                            resultSet.getString("paquete_nombre"),
                            resultSet.getInt("reservacion_id"),
                            resultSet.getDate("fecha_creacion").toLocalDate(),
                            resultSet.getDate("fecha_viaje").toLocalDate(),
                            resultSet.getInt("cantidad_persona"),
                            resultSet.getDouble("costo_total")
                    );
                    detalleReservaciones.add(detalleReservacion);
                }
                return detalleReservaciones;
            }
        }
    }

    public Optional<AgenteConMasGanancia> getAgenteConMasGanancia(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getAgenteConMasGanancia = connection.prepareStatement(AGENTE_CON_MAS_GANANCIA)) {
            getAgenteConMasGanancia.setDate(1, Date.valueOf(fechaInicio));
            getAgenteConMasGanancia.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getAgenteConMasGanancia.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new AgenteConMasGanancia(
                            resultSet.getInt("usuario_id"),
                            resultSet.getString("agente"),
                            resultSet.getDouble("ganancia_neta")
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<ReportePaqueteMasVendidoYMenosVendido> getPaqueteMasVendido(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getPaqueteMasVendido = connection.prepareStatement(MEJOR_PAQUETE)) {
            getPaqueteMasVendido.setDate(1, Date.valueOf(fechaInicio));
            getPaqueteMasVendido.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getPaqueteMasVendido.executeQuery()) {
                if (resultSet.next()) {
                    int paqueteId = resultSet.getInt("paquete_id");
                    String nombrePaquete = resultSet.getString("nombre");
                    int totalReservaciones = resultSet.getInt("total_reservaciones");
                    List<DetalleReservacionPaquete> detalleReservaciones = getDetalleReservacionesDelPaquete(paqueteId, fechaInicio, fechaFin);
                    return Optional.of(new ReportePaqueteMasVendidoYMenosVendido(paqueteId, nombrePaquete, totalReservaciones, detalleReservaciones));
                }
                return Optional.empty();
            }
        }
    }

    private List<DetalleReservacionPaquete> getDetalleReservacionesDelPaquete(int paqueteId, LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<DetalleReservacionPaquete> detalleReservaciones = new ArrayList<>();
        try (PreparedStatement getDetalleReservaciones = connection.prepareStatement(PAQUETE_DETALLE)) {
            getDetalleReservaciones.setInt(1, paqueteId);
            getDetalleReservaciones.setDate(2, Date.valueOf(fechaInicio));
            getDetalleReservaciones.setDate(3, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getDetalleReservaciones.executeQuery()) {
                while (resultSet.next()) {
                    DetalleReservacionPaquete detalleReservacion = new DetalleReservacionPaquete(
                            resultSet.getInt("reservacion_id"),
                            resultSet.getString("agente"),
                            resultSet.getDate("fecha_creacion").toLocalDate(),
                            resultSet.getDate("fecha_viaje").toLocalDate(),
                            resultSet.getInt("cantidad_persona"),
                            resultSet.getDouble("costo_total")
                    );
                    detalleReservaciones.add(detalleReservacion);
                }
                return detalleReservaciones;
            }
        }
    }

    public Optional<ReportePaqueteMasVendidoYMenosVendido> getPaqueteMenosVendido(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getPaqueteMasVendido = connection.prepareStatement(PEOR_PAQUETE)) {
            getPaqueteMasVendido.setDate(1, Date.valueOf(fechaInicio));
            getPaqueteMasVendido.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getPaqueteMasVendido.executeQuery()) {
                if (resultSet.next()) {
                    int paqueteId = resultSet.getInt("paquete_id");
                    String nombrePaquete = resultSet.getString("nombre");
                    int totalReservaciones = resultSet.getInt("total_reservaciones");
                    List<DetalleReservacionPaquete> detalleReservaciones = getDetalleReservacionesDelPaquete(paqueteId, fechaInicio, fechaFin);
                    return Optional.of(new ReportePaqueteMasVendidoYMenosVendido(paqueteId, nombrePaquete, totalReservaciones, detalleReservaciones));
                }
                return Optional.empty();
            }
        }
    }


    public List<DestinoMasConcurrido> getDestinoMasConcurrido(LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<DestinoMasConcurrido> destinoMasConcurrido = new ArrayList<>();
        try (PreparedStatement getDestinoMasConcurrido = connection.prepareStatement(DESTINO_MAS_CONCURRIDO)) {
            getDestinoMasConcurrido.setDate(1, Date.valueOf(fechaInicio));
            getDestinoMasConcurrido.setDate(2, Date.valueOf(fechaFin));
            try (ResultSet resultSet = getDestinoMasConcurrido.executeQuery()) {
                while (resultSet.next()) {
                    DestinoMasConcurrido destinoMasConcurrido1 = new DestinoMasConcurrido(
                            resultSet.getString("destino"),
                            resultSet.getInt("total_turistas"),
                            resultSet.getInt("cantidad_viajes")

                    );
                    destinoMasConcurrido.add(destinoMasConcurrido1);
                }
                return destinoMasConcurrido;
            }

        }
    }

}
