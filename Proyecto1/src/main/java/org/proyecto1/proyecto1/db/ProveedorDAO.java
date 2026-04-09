package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.proveedor.EnumProveedor;
import org.proyecto1.proyecto1.models.proveedor.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProveedorDAO implements CRUD<Proveedor> {
    private static final String INSERT = "INSERT INTO proveedor (nombre, pais, tipo, contacto) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE proveedor SET nombre = ?, pais = ?, tipo = ?, contacto = ? WHERE proveedor_id = ?";
    private static final String DELETE = "DELETE FROM proveedor WHERE proveedor_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM proveedor WHERE proveedor_id = ?";
    private static final String GET_ALL = "SELECT * FROM proveedor";
    private static final String EXISTS_NAME = "SELECT proveedor_id FROM proveedor WHERE nombre = ?";
    private static final String VALIDAR_UPDATE_NAME = "SELECT proveedor_id FROM proveedor WHERE proveedor_id <> ? AND nombre = ?";
    private static final String VALIDAR_UPDATE_CONTACTO = "SELECT proveedor_id FROM proveedor WHERE proveedor_id <> ? AND contacto = ?";
    private static final String GET_BY_COINCIDENCE = "SELECT * FROM proveedor WHERE nombre LIKE ?";

    public List<Proveedor> getByCoincidence(String parameter) throws SQLException {
        List<Proveedor> get = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getByCoincidence = connection.prepareStatement(GET_BY_COINCIDENCE)) {
            getByCoincidence.setString(1, "%" + parameter + "%");
            try (ResultSet resultSet = getByCoincidence.executeQuery()) {
                while (resultSet.next()) get.add(extraerDatos(resultSet));
                return get;
            }
        }
    }

    public boolean existsName(int proveedor_id, String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validUpdate = connection.prepareStatement(VALIDAR_UPDATE_NAME)) {
            validUpdate.setInt(1, proveedor_id);
            validUpdate.setString(2, nombre);
            try (ResultSet resultSet = validUpdate.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean existsContacto(int proveedor_id, String contacto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validUpdate = connection.prepareStatement(VALIDAR_UPDATE_CONTACTO)) {
            validUpdate.setInt(1, proveedor_id);
            validUpdate.setString(2, contacto);
            try (ResultSet resultSet = validUpdate.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public int existsName(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement existName = connection.prepareStatement(EXISTS_NAME)) {
            existName.setString(1, name);
            try (ResultSet resultSet = existName.executeQuery();){
                if (resultSet.next()) return resultSet.getInt("proveedor_id");
                return -1;
            }
        }
    }

    @Override
    public void insert(Proveedor proveedor) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT)){
            insert.setString(1, proveedor.getNombre());
            insert.setString(2, proveedor.getPais());
            insert.setString(3, proveedor.getTipo().name());
            insert.setString(4, proveedor.getContacto());
            insert.executeUpdate();
        }
    }

    @Override
    public void update(Proveedor proveedor) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(UPDATE)){
            update.setString(1, proveedor.getNombre());
            update.setString(2, proveedor.getPais());
            update.setString(3, proveedor.getTipo().name());
            update.setString(4, proveedor.getContacto());
            update.setInt(5, proveedor.getProveedor_id());
            update.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(DELETE)){
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<Proveedor> getById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getById = connection.prepareStatement(GET_BY_ID)){
            getById.setInt(1, id);
            try (ResultSet resultSet = getById.executeQuery()){
                if (resultSet.next()) return Optional.of(extraerDatos(resultSet));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Proveedor> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Proveedor> proveedores = new ArrayList<>();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = getAll.executeQuery()){
            while (resultSet.next()) proveedores.add(extraerDatos(resultSet));
            return proveedores;
        }
    }

    private Proveedor extraerDatos(ResultSet resultSet) throws SQLException {
        Proveedor proveedor = new Proveedor(resultSet.getString("nombre"),
                resultSet.getString("pais"),
                EnumProveedor.valueOf(resultSet.getString("tipo")));
        proveedor.setContacto(resultSet.getString("contacto"));
        proveedor.setProveedor_id(resultSet.getInt("proveedor_id"));
        return proveedor;
    }
}
