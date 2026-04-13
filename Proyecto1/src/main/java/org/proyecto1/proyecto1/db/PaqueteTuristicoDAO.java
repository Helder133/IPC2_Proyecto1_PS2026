package org.proyecto1.proyecto1.db;

import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.destino.Destino;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PaqueteTuristicoDAO implements CRUD<PaqueteTuristico> {
    private static final String INSERT = "INSERT INTO paquete_turistico (nombre, destino_id, duracion, precio_publico, capacidad_maxima, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE paquete_turistico SET nombre = ?, destino_id = ?, duracion = ?, precio_publico = ?, capacidad_maxima = ?, descripcion = ?, estado = ? WHERE paquete_id = ?";
    private static final String DELETE = "DELETE FROM paquete_turistico WHERE paquete_id = ?";
    private static final String GET_BY_ID = "SELECT pt.paquete_id, pt.destino_id, pt.nombre AS nombre_paquete, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, COALESCE(SUM(r.cantidad_persona), 0) AS total_ocupado FROM paquete_turistico pt JOIN destino d ON pt.destino_id = d.destino_id LEFT JOIN reservacion r ON pt.paquete_id = r.paquete_id AND r.fecha_viaje >= CURRENT_DATE AND r.estado IN ('Pendiente', 'Confirmada') WHERE pt.paquete_id = ? GROUP BY pt.paquete_id, pt.destino_id, pt.nombre, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre, d.pais, d.descripcion, d.clima_mejor_epoca, d.imagen";
    private static final String GET_ALL = "SELECT pt.paquete_id, pt.destino_id, pt.nombre AS nombre_paquete, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, COALESCE(SUM(r.cantidad_persona), 0) AS total_ocupado FROM paquete_turistico pt JOIN destino d ON pt.destino_id = d.destino_id LEFT JOIN reservacion r ON pt.paquete_id = r.paquete_id AND r.fecha_viaje >= CURRENT_DATE AND r.estado IN ('Pendiente', 'Confirmada') GROUP BY pt.paquete_id, pt.destino_id, pt.nombre, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre, d.pais, d.descripcion, d.clima_mejor_epoca, d.imagen";
    private static final String EXISTS_NAME = "SELECT paquete_id FROM paquete_turistico WHERE nombre = ?";
    private static final String GET_BY_COINCIDENCE = "SELECT pt.paquete_id, pt.destino_id, pt.nombre AS nombre_paquete, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, COALESCE(SUM(r.cantidad_persona), 0) AS total_ocupado FROM paquete_turistico pt JOIN destino d ON pt.destino_id = d.destino_id LEFT JOIN reservacion r ON pt.paquete_id = r.paquete_id AND r.fecha_viaje >= CURRENT_DATE AND r.estado IN ('Pendiente', 'Confirmada') WHERE pt.nombre LIKE ? GROUP BY pt.paquete_id, pt.destino_id, pt.nombre, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre, d.pais, d.descripcion, d.clima_mejor_epoca, d.imagen";
    private static final String VALID_UPDATE = "SELECT paquete_id FROM paquete_turistico WHERE paquete_id <> ? AND nombre = ?";
    private static final String UPDATE_ESTADO = "UPDATE paquete_turistico SET estado = NOT estado WHERE paquete_id = ?";
    private static final String GET_PRECIOS = "SELECT pt.precio_publico, COALESCE(SUM(sp.costo), 0) AS precio_agencia FROM paquete_turistico pt LEFT JOIN servicio_paquete sp ON pt.paquete_id = sp.paquete_id WHERE pt.paquete_id = ? GROUP BY pt.paquete_id, pt.precio_publico";

    public Map<String, Double> getPrecios(int paqueteId, Connection connection) throws SQLException, UserDataInvalidException {
        Map<String, Double> precios = new HashMap<String, Double>();
        try (PreparedStatement getPrecios = connection.prepareStatement(GET_PRECIOS)) {
            getPrecios.setInt(1, paqueteId);
            try (ResultSet resultSet = getPrecios.executeQuery()){
                if (resultSet.next()) {
                    precios.put("precio_publico", resultSet.getDouble("precio_publico"));
                    precios.put("precio_agencia", resultSet.getDouble("precio_agencia"));
                }
                return precios;
            }
        }
    }

    public int existsName(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement existName = connection.prepareStatement(EXISTS_NAME)) {
            existName.setString(1, name);
            try (ResultSet resultSet = existName.executeQuery();){
                if (resultSet.next()) return resultSet.getInt("paquete_id");
                return -1;
            }
        }
    }

    public List<PaqueteTuristico> getByCoincidence(String parameter) throws SQLException {
        List<PaqueteTuristico> get = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getByCoincidence = connection.prepareStatement(GET_BY_COINCIDENCE)) {
            getByCoincidence.setString(1, "%" + parameter + "%");
            try (ResultSet resultSet = getByCoincidence.executeQuery()) {
                while (resultSet.next()) get.add(extraerDatos(resultSet));
                return get;
            }
        }
    }

    public boolean validUpdate(int paquete_id, String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validUpdate = connection.prepareStatement(VALID_UPDATE)) {
            validUpdate.setInt(1, paquete_id);
            validUpdate.setString(2, nombre);
            try (ResultSet resultSet = validUpdate.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public void updateEstado(int paquete_id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement updateEstado = connection.prepareStatement(UPDATE_ESTADO)) {
            updateEstado.setInt(1, paquete_id);
            updateEstado.executeUpdate();
        }
    }

    @Override
    public void insert(PaqueteTuristico paqueteTuristico) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT)){
            insert.setString(1, paqueteTuristico.getNombre());
            insert.setInt(2, paqueteTuristico.getDestinoId());
            insert.setInt(3, paqueteTuristico.getDuracion());
            insert.setDouble(4, paqueteTuristico.getPrecioPublico());
            insert.setInt(5, paqueteTuristico.getCapacidadMaxima());
            insert.setString(6, paqueteTuristico.getDescripcion());
            insert.executeUpdate();
        }
    }

    @Override
    public void update(PaqueteTuristico paqueteTuristico) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement update = connection.prepareStatement(UPDATE)){
            update.setString(1, paqueteTuristico.getNombre());
            update.setInt(2, paqueteTuristico.getDestinoId());
            update.setInt(3, paqueteTuristico.getDuracion());
            update.setDouble(4, paqueteTuristico.getPrecioPublico());
            update.setInt(5, paqueteTuristico.getCapacidadMaxima());
            update.setString(6, paqueteTuristico.getDescripcion());
            update.setBoolean(7, paqueteTuristico.isEstado());
            update.setInt(8, paqueteTuristico.getPaqueteId());
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
    public Optional<PaqueteTuristico> getById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getById = connection.prepareStatement(GET_BY_ID)) {
            getById.setInt(1, id);
            try (ResultSet resultSet = getById.executeQuery()) {
                if (resultSet.next()) return Optional.of(extraerDatos(resultSet));
                return Optional.empty();
            }
        }
    }

    public Optional<PaqueteTuristico> getById(int id, Connection connection) throws SQLException {
        try (PreparedStatement getById = connection.prepareStatement(GET_BY_ID)) {
            getById.setInt(1, id);
            try (ResultSet resultSet = getById.executeQuery()) {
                if (resultSet.next()) return Optional.of(extraerDatos(resultSet));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<PaqueteTuristico> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        List<PaqueteTuristico> paquetes = new ArrayList<>();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) paquetes.add(extraerDatos(resultSet));
            return paquetes;
        }
    }

    private PaqueteTuristico extraerDatos(ResultSet resultSet) throws SQLException {
        PaqueteTuristico paqueteTuristico = new PaqueteTuristico(
                resultSet.getInt("destino_id"),
                resultSet.getString("nombre_paquete"),
                resultSet.getInt("duracion"),
                resultSet.getDouble("precio_publico"),
                resultSet.getInt("capacidad_maxima"));
        paqueteTuristico.setPaqueteId(resultSet.getInt("paquete_id"));
        paqueteTuristico.setDescripcion(resultSet.getString("descripcion"));
        paqueteTuristico.setEstado(resultSet.getBoolean("estado"));

        Destino destino = new Destino(
                resultSet.getString("nombre_destino"),
                resultSet.getString("pais"),
                resultSet.getString("descripcion"));
        destino.setDestino_id(resultSet.getInt("destino_id"));
        destino.setClima_mejor_epoca(resultSet.getString("clima_mejor_epoca"));
        destino.setImagen(resultSet.getString("imagen"));

        int totalOcupado = resultSet.getInt("total_ocupado");
        boolean esAltaDemanda = false;
        if (paqueteTuristico.getCapacidadMaxima() > 0) {
            double porcentaje = (double) totalOcupado / paqueteTuristico.getCapacidadMaxima();
            esAltaDemanda = (porcentaje >= 0.8);
        }

        paqueteTuristico.setAltaDemanda(esAltaDemanda);

        paqueteTuristico.setDestino(destino);

        return paqueteTuristico;
    }
}
