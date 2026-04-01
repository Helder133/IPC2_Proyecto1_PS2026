package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.servicioPaquete.ServicioPaquete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicioPaqueteDAO implements CRUD<ServicioPaquete> {
    private static final String INSERT = "INSERT INTO servicio_paquete (proveedor_id, paquete_id, descripcion, costo) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE servicio_paquete SET proveedor_id = ?, descripcion = ?, costo = ? WHERE paquete_id = ?";
    private static final String DELETE = "DELETE FROM servicio_paquete WHERE paquete_id = ? AND proveedor_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM servicio_paquete WHERE paquete_id = ?";
    private static final String GET_ALL = "SELECT * FROM servicio_paquete";

    public List<ServicioPaquete> getByPaqueteId(int paquete_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<ServicioPaquete> servicios = new ArrayList<>();
        try (PreparedStatement getByPaqueteId = connection.prepareStatement(GET_BY_ID)) {
            getByPaqueteId.setInt(1, paquete_id);
            try (ResultSet resultSet = getByPaqueteId.executeQuery()) {
                while (resultSet.next()) servicios.add(extraerDatos(resultSet));
                return servicios;
            }
        }
    }

    @Override
    public void insert(ServicioPaquete servicioPaquete) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT)) {
            insert.setInt(1, servicioPaquete.getProveedorId());
            insert.setInt(2, servicioPaquete.getPaqueteId());
            insert.setString(3, servicioPaquete.getDescripcion());
            insert.setDouble(4, servicioPaquete.getCosto());
            insert.executeUpdate();
        }
    }

    @Override
    public void update(ServicioPaquete servicioPaquete) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(UPDATE)) {
            update.setInt(1, servicioPaquete.getProveedorId());
            update.setString(2, servicioPaquete.getDescripcion());
            update.setDouble(3, servicioPaquete.getCosto());
            update.setInt(4, servicioPaquete.getPaqueteId());
            update.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {}

    public void delete(int paquete_id, int proveedor_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(DELETE)) {
            delete.setInt(1, paquete_id);
            delete.setInt(2, proveedor_id);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<ServicioPaquete> getById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<ServicioPaquete> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<ServicioPaquete> servicios = new ArrayList<>();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) servicios.add(extraerDatos(resultSet));
            return servicios;
        }
    }

    private ServicioPaquete extraerDatos(ResultSet resultSet) throws SQLException {
        return new ServicioPaquete(
                resultSet.getInt("proveedor_id"),
                resultSet.getInt("paquete_id"),
                resultSet.getString("descripcion"),
                resultSet.getDouble("costo"));
    }
}
