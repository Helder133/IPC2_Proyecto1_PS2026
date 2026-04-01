package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.models.pago.HistorialPago;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class historialPagoDAO implements CRUD<HistorialPago> {
    private static final String INSERT = "INSERT INTO historial_pago (reservacion_id, monto, metodo, fecha) VALUES (?, ?, ?, ?)";
    private static final String GET_ANTICIPO = "SELECT SUM(monto) AS anticipo FROM historial_pago WHERE reservacion_id = ?";

    public void insert(HistorialPago historialPago, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERT)) {
            insert.setInt(1, historialPago.getReservacionId());
            insert.setDouble(2, historialPago.getMonto());
            insert.setString(3, historialPago.getMetodo().name());
            insert.setDate(4, Date.valueOf(historialPago.getFecha()));
            insert.executeUpdate();
        }
    }

    public double getAnticipo(int reservacionId, Connection connection) throws SQLException {
        try (PreparedStatement getAnticipo = connection.prepareStatement(GET_ANTICIPO)) {
            getAnticipo.setInt(1, reservacionId);
            try (ResultSet resultSet = getAnticipo.executeQuery()) {
                if (resultSet.next()) return resultSet.getDouble("anticipo");
                return 0.0;
            }
        }
    }

    @Override
    public void insert(HistorialPago historialPago) throws SQLException {

    }

    @Override
    public void update(HistorialPago historialPago) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public Optional<HistorialPago> getById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<HistorialPago> getAll() throws SQLException {
        return List.of();
    }
}
