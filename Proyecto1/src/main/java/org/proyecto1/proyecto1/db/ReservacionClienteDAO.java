package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.reservacion.ReservacionCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservacionClienteDAO implements CRUD<ReservacionCliente> {
    private static final String INSERT = "INSERT INTO reservacion_cliente (reservacion_id, cliente_id) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE reservacion_cliente SET cliente_id = ? WHERE reservacion_id = ?";
    private static final String DELETE = "DELETE FROM reservacion_cliente WHERE reservacion_id = ? AND cliente_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM reservacion_cliente WHERE reservacion_id = ?";
    private static final String GET_ALL = "SELECT * FROM reservacion_cliente";
    private static final String EXISTS = "SELECT * FROM reservacion_cliente WHERE reservacion_id = ? AND cliente_id = ?";

    public boolean exists(ReservacionCliente reservacionCliente) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement exists = connection.prepareStatement(EXISTS)){
            exists.setInt(1, reservacionCliente.getReservacionId());
            exists.setInt(2, reservacionCliente.getClienteId());
            try (ResultSet resultSet = exists.executeQuery()){
                return resultSet.next();
            }
        }
    }

    public boolean exists(ReservacionCliente reservacionCliente, Connection connection) throws SQLException {
        try (PreparedStatement exists = connection.prepareStatement(EXISTS)){
            exists.setInt(1, reservacionCliente.getReservacionId());
            exists.setInt(2, reservacionCliente.getClienteId());
            try (ResultSet resultSet = exists.executeQuery()){
                return resultSet.next();
            }
        }
    }

    public List<ReservacionCliente> getByReservacionId(int reservacionId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<ReservacionCliente> reservacionClientes = new ArrayList<>();
        try (PreparedStatement getByReservacionId = connection.prepareStatement(GET_BY_ID)){
            getByReservacionId.setInt(1, reservacionId);
            try (ResultSet resultSet = getByReservacionId.executeQuery()){
                while (resultSet.next()) reservacionClientes.add(extraerDatos(resultSet));
                return reservacionClientes;
            }
        }
    }

    @Override
    public void insert(ReservacionCliente reservacionCliente) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT)){
            insert.setInt(1, reservacionCliente.getReservacionId());
            insert.setInt(2, reservacionCliente.getClienteId());
            insert.executeUpdate();
        }
    }

    public void insert(ReservacionCliente reservacionCliente, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERT)){
            insert.setInt(1, reservacionCliente.getReservacionId());
            insert.setInt(2, reservacionCliente.getClienteId());
            insert.executeUpdate();
        }
    }

    @Override
    public void update(ReservacionCliente reservacionCliente) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(UPDATE)){
            update.setInt(1, reservacionCliente.getClienteId());
            update.setInt(2, reservacionCliente.getReservacionId());
            update.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    public void delete(int reservacionId, int clienteId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(DELETE)){
            delete.setInt(1, reservacionId);
            delete.setInt(2, clienteId);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<ReservacionCliente> getById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<ReservacionCliente> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<ReservacionCliente> reservacionClientes = new ArrayList<>();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = getAll.executeQuery()){
            while (resultSet.next()) reservacionClientes.add(extraerDatos(resultSet));
            return reservacionClientes;
        }
    }

    private ReservacionCliente extraerDatos(ResultSet resultSet) throws SQLException {
        return new ReservacionCliente(resultSet.getInt("reservacion_id"),
                resultSet.getInt("cliente_id"));
    }
}
