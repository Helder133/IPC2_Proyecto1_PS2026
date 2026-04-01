package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.destino.Destino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DestinoDAO implements CRUD<Destino> {
    private static final String INSERT = "INSERT INTO destino (nombre, pais, descripcion, clima_mejor_epoca, imagen) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE destino SET nombre = ?, pais = ?, descripcion = ?, clima_mejor_epoca = ?, imagen = ? WHERE destino_id = ?";
    private static final String DELETE = "DELETE FROM destino WHERE destino_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM destino WHERE destino_id = ?";
    private static final String GET_ALL = "SELECT * FROM destino";
    private static final String EXISTS_NAME = "SELECT cliente_id FROM destino WHERE nombre = ?";
    private static final String VALIDAR_UPDATE = "SELECT destino_id FROM destino WHERE destino_id <> ? AND nombre = ?";

    public int existsName(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement existName = connection.prepareStatement(EXISTS_NAME)) {
            existName.setString(1, name);
            try (ResultSet resultSet = existName.executeQuery();){
                if (resultSet.next()) return resultSet.getInt("destino_id");
                return -1;
            }
        }
    }

    public boolean validUpdate(int destino_id, String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validUpdate = connection.prepareStatement(VALIDAR_UPDATE)) {
            validUpdate.setInt(1, destino_id);
            validUpdate.setString(2, nombre);
            try (ResultSet resultSet = validUpdate.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void insert(Destino destino) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT)){
            insert.setString(1, destino.getNombre());
            insert.setString(2, destino.getPais());
            insert.setString(3, destino.getDescripcion());
            insert.setString(4, destino.getClima_mejor_epoca());
            insert.setString(5, destino.getImagen());
            insert.executeUpdate();
        }
    }

    @Override
    public void update(Destino destino) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(UPDATE)){
            update.setString(1, destino.getNombre());
            update.setString(2, destino.getPais());
            update.setString(3, destino.getDescripcion());
            update.setString(4, destino.getClima_mejor_epoca());
            update.setString(5, destino.getImagen());
            update.setInt(6, destino.getDestino_id());
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
    public Optional<Destino> getById(int id) throws SQLException {
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
    public List<Destino> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<Destino> destinos = new java.util.ArrayList<>();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL);
             ResultSet resultSet = getAll.executeQuery()){
            while (resultSet.next()) destinos.add(extraerDatos(resultSet));
            return destinos;
        }
    }

    private Destino extraerDatos(ResultSet resultSet) throws SQLException {
        Destino destino = new Destino(resultSet.getString("nombre"), resultSet.getString("pais"), resultSet.getString("descripcion"));
        destino.setClima_mejor_epoca(resultSet.getString("clima_mejor_epoca"));
        destino.setImagen(resultSet.getString("imagen"));
        destino.setDestino_id(resultSet.getInt("destino_id"));
        return destino;
    }
}
