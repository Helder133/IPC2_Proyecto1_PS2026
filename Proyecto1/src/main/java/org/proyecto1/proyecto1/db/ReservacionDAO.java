package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.reservacion.EnumReservacion;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservacionDAO implements CRUD<Reservacion> {
    private static final String INSERT = "INSERT INTO reservacion (paquete_id, usuario_id, fecha_creacion, fecha_viaje, cantidad_persona, costo_total, costo_agencia, codigo_archivo) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE reservacion SET paquete_id = ?, fecha_viaje = ?, estado = ? WHERE reservacion_id = ?";
    private static final String DELETE = "DELETE FROM reservacion WHERE reservacion_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM reservacion WHERE reservacion_id = ?";
    private static final String GET_ALL = "SELECT * FROM reservacion";
    private static final String UPDATE_ESTADO = "UPDATE reservacion SET estado = ? WHERE reservacion_id = ?";
    private static final String GET_BY_USER_ID = "SELECT r.* FROM reservacion AS r JOIN reservacion_cliente AS rc ON r.reservacion_id = rc.reservacion_id WHERE rc.cliente_id = ?";
    private static final String GET_ULTIMO_CODIGO_ARCHIVO = "SELECT codigo_archivo FROM reservacion WHERE codigo_archivo IS NOT NULL ORDER BY reservacion_id DESC LIMIT 1";
    private static final String GET_BY_CODIGO_ARCHIVO = "SELECT reservacion_id FROM reservacion WHERE codigo_archivo = ?";

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

    public List<Reservacion> getByUserId(int cliente_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Reservacion> reservaciones = new ArrayList<>();
        try (PreparedStatement getByUserId = connection.prepareStatement(GET_BY_USER_ID)) {
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
            update.setString(3, reservacion.getEstado().name());
            update.setInt(4, reservacion.getReservacionId());
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
        reservacion.setEstado(EnumReservacion.valueOf(resultSet.getString("estado")));
        reservacion.setCantidadPersona(resultSet.getInt("cantidad_persona"));
        reservacion.setCostoTotal(resultSet.getDouble("costo_total"));
        reservacion.setCostoAgencia(resultSet.getDouble("costo_agencia"));
        double reembolso = resultSet.getDouble("reembolso");
        if (resultSet.wasNull()) {
            reservacion.setReembolso(0.0);
        } else {
            reservacion.setReembolso(reembolso);
        }
        reservacion.setFechaCancelacion(resultSet.getDate("fecha_cancelacion") != null ? resultSet.getDate("fecha_cancelacion").toLocalDate() : null);
        return reservacion;
    }
}
