package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.destino.Destino;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;
import org.proyecto1.proyecto1.models.reservacion.EnumReservacion;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservacionDAO implements CRUD<Reservacion> {
    private static final String INSERT = "INSERT INTO reservacion (paquete_id, usuario_id, fecha_creacion, fecha_viaje, cantidad_persona, costo_total, costo_agencia, codigo_archivo) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE reservacion SET paquete_id = ?, fecha_viaje = ?, cantidad_persona = ?, costo_total = ?, costo_agencia = ? WHERE reservacion_id = ?";
    private static final String DELETE = "DELETE FROM reservacion WHERE reservacion_id = ?";
    private static final String GET_BY_ID = "SELECT r.reservacion_id, r.paquete_id, r.usuario_id, r.fecha_viaje, r.fecha_creacion, r.cantidad_persona, r.costo_total, r.costo_agencia, r.estado AS estado_reservacion, r.reembolso, r.fecha_cancelacion, r.codigo_archivo, p.destino_id, p.nombre AS nombre_destino, p.duracion, p.precio_publico, p.capacidad_maxima, p.descripcion AS descripcion_paquete, p.estado AS estado_paquete, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, ( SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id ) AS anticipo, ( SELECT COALESCE(SUM(r2.cantidad_persona), 0) FROM reservacion r2 WHERE r2.paquete_id = r.paquete_id AND r2.fecha_viaje >= CURRENT_DATE AND r2.estado IN ('Pendiente', 'Confirmada') ) AS total_ocupado FROM reservacion r JOIN paquete_turistico p ON r.paquete_id = p.paquete_id JOIN destino d ON p.destino_id = d.destino_id  WHERE r.reservacion_id = ?";
    private static final String GET_ALL = "SELECT r.reservacion_id, r.paquete_id, r.usuario_id, r.fecha_viaje, r.fecha_creacion, r.cantidad_persona, r.costo_total, r.costo_agencia, r.estado AS estado_reservacion, r.reembolso, r.fecha_cancelacion, r.codigo_archivo, p.destino_id, p.nombre AS nombre_destino, p.duracion, p.precio_publico, p.capacidad_maxima, p.descripcion AS descripcion_paquete, p.estado AS estado_paquete, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, ( SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id ) AS anticipo, ( SELECT COALESCE(SUM(r2.cantidad_persona), 0) FROM reservacion r2 WHERE r2.paquete_id = r.paquete_id AND r2.fecha_viaje >= CURRENT_DATE AND r2.estado IN ('Pendiente', 'Confirmada') ) AS total_ocupado FROM reservacion r JOIN paquete_turistico p ON r.paquete_id = p.paquete_id JOIN destino d ON p.destino_id = d.destino_id";
    private static final String UPDATE_ESTADO = "UPDATE reservacion SET estado = ? WHERE reservacion_id = ?";
    private static final String GET_BY_CLIENTE_ID = "SELECT r.reservacion_id, r.paquete_id, r.usuario_id, r.fecha_viaje, r.fecha_creacion, r.cantidad_persona, r.costo_total, r.costo_agencia, r.estado AS estado_reservacion, r.reembolso, r.fecha_cancelacion, r.codigo_archivo, p.destino_id, p.nombre AS nombre_destino, p.duracion, p.precio_publico, p.capacidad_maxima, p.descripcion AS descripcion_paquete, p.estado AS estado_paquete, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, ( SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id ) AS anticipo, ( SELECT COALESCE(SUM(r2.cantidad_persona), 0) FROM reservacion r2 WHERE r2.paquete_id = r.paquete_id AND r2.fecha_viaje >= CURRENT_DATE AND r2.estado IN ('Pendiente', 'Confirmada') ) AS total_ocupado FROM reservacion r JOIN paquete_turistico p ON r.paquete_id = p.paquete_id JOIN reservacion_cliente rc ON r.reservacion_id = rc.reservacion_id JOIN destino d ON p.destino_id = d.destino_id  WHERE rc.cliente_id = ?";
    private static final String GET_ULTIMO_CODIGO_ARCHIVO = "SELECT codigo_archivo FROM reservacion WHERE codigo_archivo IS NOT NULL ORDER BY reservacion_id DESC LIMIT 1";
    private static final String GET_BY_CODIGO_ARCHIVO = "SELECT reservacion_id FROM reservacion WHERE codigo_archivo = ?";
    private static final String RESERVACION_CANCELADA = "UPDATE reservacion SET estado = ?, fecha_cancelacion = ?, reembolso = ? WHERE reservacion_id = ?";
    private static final String GET_BY_USUARIO_ID = "SELECT r.reservacion_id, r.paquete_id, r.usuario_id, r.fecha_viaje, r.fecha_creacion, r.cantidad_persona, r.costo_total, r.costo_agencia, r.estado AS estado_reservacion, r.reembolso, r.fecha_cancelacion, r.codigo_archivo, p.destino_id, p.nombre AS nombre_destino, p.duracion, p.precio_publico, p.capacidad_maxima, p.descripcion AS descripcion_paquete, p.estado AS estado_paquete, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, ( SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id ) AS anticipo, ( SELECT COALESCE(SUM(r2.cantidad_persona), 0) FROM reservacion r2 WHERE r2.paquete_id = r.paquete_id AND r2.fecha_viaje >= CURRENT_DATE AND r2.estado IN ('Pendiente', 'Confirmada') ) AS total_ocupado FROM reservacion r JOIN paquete_turistico p ON r.paquete_id = p.paquete_id JOIN destino d ON p.destino_id = d.destino_id WHERE r.usuario_id = ?";

    public void cancelarReservacion(Reservacion reservacion) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement cancelarReservacion = connection.prepareStatement(RESERVACION_CANCELADA)){
            cancelarReservacion.setString(1,reservacion.getEstado().name());
            cancelarReservacion.setDate(2, Date.valueOf(reservacion.getFechaCancelacion()));
            cancelarReservacion.setDouble(3, reservacion.getReembolso());
            cancelarReservacion.setInt(4, reservacion.getReservacionId());
            cancelarReservacion.execute();
        }
    }

    public String getUltimoCodigoArchivo(Connection connection) throws SQLException {
        try (PreparedStatement getUltimoCodigoArchivo = connection.prepareStatement(GET_ULTIMO_CODIGO_ARCHIVO);
             ResultSet resultSet = getUltimoCodigoArchivo.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("codigo_archivo");
            } else {
                return "RES-00000";
            }
        }
    }

    public int getByCodigoArchivo(String codigoArchivo) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getByCodigoArchivo = connection.prepareStatement(GET_BY_CODIGO_ARCHIVO)) {
            getByCodigoArchivo.setString(1, codigoArchivo);
            try (ResultSet resultSet = getByCodigoArchivo.executeQuery()) {
                if (resultSet.next()) return resultSet.getInt("reservacion_id");
                return -1;
            }
        }
    }

    public List<Reservacion> getByUsuarioId(int usuario_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Reservacion> reservaciones = new ArrayList<>();
        try (PreparedStatement getByUsuarioId = connection.prepareStatement(GET_BY_USUARIO_ID)) {
            getByUsuarioId.setInt(1, usuario_id);
            try (ResultSet resultSet = getByUsuarioId.executeQuery()) {
                while (resultSet.next()) reservaciones.add(extraerDatos(resultSet));
                return reservaciones;
            }
        }
    }

    public List<Reservacion> getByClienteId(int cliente_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Reservacion> reservaciones = new ArrayList<>();
        try (PreparedStatement getByUserId = connection.prepareStatement(GET_BY_CLIENTE_ID)) {
            getByUserId.setInt(1, cliente_id);
            try (ResultSet resultSet = getByUserId.executeQuery()) {
                while (resultSet.next()) reservaciones.add(extraerDatos(resultSet));
                return reservaciones;
            }
        }
    }

    public void updateEstado(int reservacionId, EnumReservacion estado, Connection connection) throws SQLException {
        try (PreparedStatement updateEstado = connection.prepareStatement(UPDATE_ESTADO)) {
            updateEstado.setString(1, estado.name());
            updateEstado.setInt(2, reservacionId);
            updateEstado.executeUpdate();
        }
    }

    public void updateEstado(int reservacionId, EnumReservacion estado) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement updateEstado = connection.prepareStatement(UPDATE_ESTADO)) {
            updateEstado.setString(1, estado.name());
            updateEstado.setInt(2, reservacionId);
            updateEstado.executeUpdate();
        }
    }

    @Override
    public void insert(Reservacion reservacion) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT)) {
            insert.setInt(1, reservacion.getPaqueteId());
            insert.setInt(2, reservacion.getUsuarioId());
            insert.setDate(3, Date.valueOf(reservacion.getFechaCreacion()));
            insert.setDate(4, Date.valueOf(reservacion.getFechaViaje()));
            insert.setInt(5, reservacion.getCantidadPersona());
            insert.setDouble(6, reservacion.getCostoTotal());
            insert.setDouble(7, reservacion.getCostoAgencia());
            insert.setString(8, reservacion.getCodigoArchivo());
            insert.executeUpdate();
        }
    }

    public int insert(Reservacion reservacion, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            insert.setInt(1, reservacion.getPaqueteId());
            insert.setInt(2, reservacion.getUsuarioId());
            insert.setDate(3, Date.valueOf(reservacion.getFechaCreacion()));
            insert.setDate(4, Date.valueOf(reservacion.getFechaViaje()));
            insert.setInt(5, reservacion.getCantidadPersona());
            insert.setDouble(6, reservacion.getCostoTotal());
            insert.setDouble(7, reservacion.getCostoAgencia());
            insert.setString(8, reservacion.getCodigoArchivo());
            insert.executeUpdate();

            try (ResultSet generatedKeys = insert.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                else throw new SQLException("No se pudo obtener el ID de la reservación.");
            }
        }
    }

    @Override
    public void update(Reservacion reservacion) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(UPDATE)) {
            update.setInt(1, reservacion.getPaqueteId());
            update.setDate(2, Date.valueOf(reservacion.getFechaViaje()));
            update.setInt(3, reservacion.getCantidadPersona());
            update.setInt(4, reservacion.getReservacionId());
            update.executeUpdate();
        }
    }

    public void update(Reservacion reservacion, Connection connection) throws SQLException {
        try (PreparedStatement update = connection.prepareStatement(UPDATE)) {
            update.setInt(1, reservacion.getPaqueteId());
            update.setDate(2, Date.valueOf(reservacion.getFechaViaje()));
            update.setInt(3, reservacion.getCantidadPersona());
            update.setDouble(4, reservacion.getCostoTotal());
            update.setDouble(5, reservacion.getCostoAgencia());
            update.setInt(6, reservacion.getReservacionId());
            update.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(DELETE)) {
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<Reservacion> getById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getById = connection.prepareStatement(GET_BY_ID)) {
            getById.setInt(1, id);
            try (ResultSet resultSet = getById.executeQuery()) {
                if (resultSet.next()) return Optional.of(extraerDatos(resultSet));
                return Optional.empty();
            }
        }
    }

    public Optional<Reservacion> getById(int id, Connection connection) throws SQLException {
        try (PreparedStatement getById = connection.prepareStatement(GET_BY_ID)) {
            getById.setInt(1, id);
            try (ResultSet resultSet = getById.executeQuery()) {
                if (resultSet.next()) return Optional.of(extraerDatos(resultSet));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Reservacion> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Reservacion> reservaciones = new ArrayList<>();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) reservaciones.add(extraerDatos(resultSet));
            return reservaciones;
        }
    }

    private Reservacion extraerDatos(ResultSet resultSet) throws SQLException {
        Reservacion reservacion = new Reservacion(resultSet.getInt("paquete_id"),
                resultSet.getInt("usuario_id"),
                resultSet.getDate("fecha_viaje").toLocalDate());
        reservacion.setReservacionId(resultSet.getInt("reservacion_id"));
        reservacion.setFechaCreacion(resultSet.getDate("fecha_creacion").toLocalDate());
        reservacion.setEstado(EnumReservacion.valueOf(resultSet.getString("estado_reservacion")));
        reservacion.setCantidadPersona(resultSet.getInt("cantidad_persona"));
        reservacion.setCostoTotal(resultSet.getDouble("costo_total"));
        reservacion.setCostoAgencia(resultSet.getDouble("costo_agencia"));
        reservacion.setTotalPagoRealizado(resultSet.getDouble("anticipo"));
        double reembolso = resultSet.getDouble("reembolso");
        if (resultSet.wasNull()) {
            reservacion.setReembolso(0.0);
        } else {
            reservacion.setReembolso(reembolso);
        }
        reservacion.setFechaCancelacion(resultSet.getDate("fecha_cancelacion") != null ? resultSet.getDate("fecha_cancelacion").toLocalDate() : null);
        reservacion.setCodigoArchivo(resultSet.getString("codigo_archivo"));

        PaqueteTuristico paqueteTuristico = new PaqueteTuristico(resultSet.getInt("destino_id"),
                resultSet.getString("nombre_destino"),
                resultSet.getInt("duracion"),
                resultSet.getDouble("precio_publico"),
                resultSet.getInt("capacidad_maxima"));
        paqueteTuristico.setPaqueteId(resultSet.getInt("paquete_id"));
        paqueteTuristico.setDescripcion(resultSet.getString("descripcion_paquete"));
        paqueteTuristico.setEstado(resultSet.getBoolean("estado_paquete"));

        int totalOcupado = resultSet.getInt("total_ocupado");
        boolean esAltaDemanda = false;
        if (paqueteTuristico.getCapacidadMaxima() > 0) {
            double porcentaje = (double) totalOcupado / paqueteTuristico.getCapacidadMaxima();
            esAltaDemanda = (porcentaje >= 0.8);
        }

        paqueteTuristico.setAltaDemanda(esAltaDemanda);

        Destino destino = new Destino(resultSet.getString("nombre_destino"),
                resultSet.getString("pais"),
                resultSet.getString("descripcion_destino"));
        destino.setClima_mejor_epoca(resultSet.getString("clima_mejor_epoca"));
        destino.setImagen(resultSet.getString("imagen"));
        destino.setDestino_id(resultSet.getInt("destino_id"));

        paqueteTuristico.setDestino(destino);

        reservacion.setPaqueteTuristico(paqueteTuristico);
        return reservacion;
    }
}
