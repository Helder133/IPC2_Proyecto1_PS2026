package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.cliente.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAO implements CRUD<Cliente> {
    private static final String INSERT_CLIENT = "INSERT INTO cliente (dpi_o_pasaporte, nombre, fecha, telefono, email, nacionalidad) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT = "UPDATE cliente SET dpi_o_pasaporte = ?, nombre = ?, fecha = ?, telefono = ?, email = ?, nacionalidad = ? WHERE cliente_id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM cliente WHERE cliente_id = ?";
    private static final String GET_CLIENT_BY_ID = "SELECT * FROM cliente WHERE cliente_id = ?";
    private static final String GET_ALL_CLIENTS = "SELECT * FROM cliente";
    private static final String EXISTS_CLIENT = "SELECT * FROM cliente WHERE dpi_o_pasaporte = ?";
    private static final String GET_CLIENT_BY_COINCIDENCE = "SELECT * FROM cliente WHERE dpi_o_pasaporte LIKE ? OR nombre LIKE ?";
    private static final String VALID_UPDATE = "SELECT cliente_id FROM cliente WHERE cliente_id <> ? AND dpi_o_pasaporte = ?";

    public boolean validUpdate(int cliente_id, String dpi_o_pasaporte) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validUpdate = connection.prepareStatement(VALID_UPDATE)) {
            validUpdate.setInt(1, cliente_id);
            validUpdate.setString(2, dpi_o_pasaporte);
            try (ResultSet resultSet = validUpdate.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public Optional<Cliente> existsClient(String dpi_o_pasaporte) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement exists = connection.prepareStatement(EXISTS_CLIENT)) {
            exists.setString(1, dpi_o_pasaporte);
            try (ResultSet resultSet = exists.executeQuery()) {
                if (resultSet.next()) return Optional.of(getClient(resultSet));
                return Optional.empty();
            }
        }
    }

    public List<Cliente> getByCoincidence(String parameter) throws SQLException {
        List<Cliente> get = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getByCoincidence = connection.prepareStatement(GET_CLIENT_BY_COINCIDENCE)) {
            getByCoincidence.setString(1, "%" + parameter + "%");
            getByCoincidence.setString(2, "%" + parameter + "%");
            try (ResultSet resultSet = getByCoincidence.executeQuery()) {
                while (resultSet.next()) get.add(getClient(resultSet));
                return get;
            }
        }
    }

    @Override
    public void insert(Cliente cliente) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT_CLIENT)) {
            insert.setString(1, cliente.getDpi_o_pasaporte());
            insert.setString(2, cliente.getNombre());
            insert.setDate(3, Date.valueOf(cliente.getFecha()));
            insert.setString(4, cliente.getTelefono());
            insert.setString(5, cliente.getEmail());
            insert.setString(6, cliente.getNacionalidad());
            insert.executeUpdate();
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(UPDATE_CLIENT)) {
            update.setString(1, cliente.getDpi_o_pasaporte());
            update.setString(2, cliente.getNombre());
            update.setDate(3, Date.valueOf(cliente.getFecha()));
            update.setString(4, cliente.getTelefono());
            update.setString(5, cliente.getEmail());
            update.setString(6, cliente.getNacionalidad());
            update.setInt(7, cliente.getCliente_id());
            update.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(DELETE_CLIENT)) {
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<Cliente> getById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getClient = connection.prepareStatement(GET_CLIENT_BY_ID)) {
            getClient.setInt(1, id);
            try (ResultSet resultSet = getClient.executeQuery()) {
                if (resultSet.next()) return Optional.of(getClient(resultSet));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Cliente> getAll() throws SQLException {
        List<Cliente> get = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL_CLIENTS);
             ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) get.add(getClient(resultSet));
            return get;
        }
    }

    private Cliente getClient(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente(resultSet.getString("dpi_o_pasaporte"),
                resultSet.getString("nombre"),
                resultSet.getDate("fecha").toLocalDate(),
                resultSet.getString("email"),
                resultSet.getString("telefono"),
                resultSet.getString("nacionalidad"));
        cliente.setCliente_id(resultSet.getInt("cliente_id"));
        return cliente;
    }

}
