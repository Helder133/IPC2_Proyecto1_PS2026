package org.proyecto1.proyecto1.db;

import org.apache.commons.lang3.StringUtils;
import org.proyecto1.proyecto1.db.config.CRUD;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.models.usuario.EnumUsuario;
import org.proyecto1.proyecto1.models.usuario.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO implements CRUD<Usuario> {
    private static final String LOGIN = "SELECT * FROM usuario WHERE nombre = ? AND password = ? AND estado = 1";
    private static final String INSERT_USER = "INSERT INTO usuario (nombre, password, rol) VALUES (?, ?, ?)";
    private static final String UPDATE_USER_WITH_PASSWORD = "UPDATE usuario SET nombre = ?, password = ?, rol = ? WHERE usuario_id = ?";
    private static final String UPDATE_USER_WITHOUT_PASSWORD = "UPDATE usuario SET nombre = ?, rol = ? WHERE usuario_id = ?";
    private static final String DELETE_USER = "DELETE FROM usuario WHERE usuario_id = ?";
    private static final String GET_USER_BY_ID = "SELECT * FROM usuario WHERE usuario_id = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM usuario";
    private static final String EXISTS_USER = "SELECT usuario_id FROM usuario WHERE nombre = ?";
    private static final String GET_BY_COINCIDENCE = "SELECT * FROM usuario WHERE nombre LIKE ?";
    private static final String VALID_UPDATE = "SELECT usuario_id FROM usuario WHERE usuario_id <> ? AND nombre = ?";
    private static final String UPDATE_ESTADO = "UPDATE usuario SET estado = NOT estado WHERE usuario_id = ?";

    public void updateEstado(int usuarioId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement updateEstado = connection.prepareStatement(UPDATE_ESTADO)) {
            updateEstado.setInt(1, usuarioId);
            updateEstado.executeUpdate();
        }
    }

    public Optional<Usuario> login(Usuario usuario) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement login1 = connection.prepareStatement(LOGIN)) {
            login1.setString(1, usuario.getNombre());
            login1.setString(2, usuario.getPassword());
            try (ResultSet resultSet = login1.executeQuery()) {
                if (resultSet.next()) return Optional.of(getUser(resultSet));
                return Optional.empty();
            }
        }
    }

    public int existsUser(String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement exists = connection.prepareStatement(EXISTS_USER)) {
            exists.setString(1, nombre);
            try (ResultSet resultSet = exists.executeQuery()) {
                if (resultSet.next()) return resultSet.getInt("usuario_id");
                return -1;
            }
        }
    }

    public List<Usuario> getByCoincidence(String nombre) throws SQLException {
        List<Usuario> get = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getByCoincidence_ = connection.prepareStatement(GET_BY_COINCIDENCE)){
            getByCoincidence_.setString(1, "%" + nombre + "%");
            try (ResultSet resultSet = getByCoincidence_.executeQuery()) {
                while (resultSet.next()) {
                    get.add(getUser(resultSet));
                }
                return get;
            }
        }
    }

    public boolean validUpdate(int usuario_id, String nombre) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement validUpdate_ = connection.prepareStatement(VALID_UPDATE)) {
            validUpdate_.setInt(1, usuario_id);
            validUpdate_.setString(2, nombre);
            try (ResultSet resultSet = validUpdate_.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void insert(Usuario usuario) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERT_USER)) {
            insert.setString(1, usuario.getNombre());
            insert.setString(2, usuario.getPassword());
            insert.setString(3, usuario.getRol().name());
            insert.executeUpdate();
        }
    }

    @Override
    public void update(Usuario usuario) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        if (StringUtils.isBlank(usuario.getPassword())) {
            try (PreparedStatement update = connection.prepareStatement(UPDATE_USER_WITHOUT_PASSWORD)) {
                update.setString(1, usuario.getNombre());
                update.setString(2, usuario.getRol().name());
                update.setInt(3, usuario.getUsuario_id());
                update.executeUpdate();
            }
        } else {
            try (PreparedStatement update = connection.prepareStatement(UPDATE_USER_WITH_PASSWORD)) {
                update.setString(1, usuario.getNombre());
                update.setString(2, usuario.getPassword());
                update.setString(3, usuario.getRol().name());
                update.setInt(4, usuario.getUsuario_id());
                update.executeUpdate();
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(DELETE_USER)) {
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<Usuario> getById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getUser = connection.prepareStatement(GET_USER_BY_ID)) {
            getUser.setInt(1, id);
            try (ResultSet resultSet = getUser.executeQuery()) {
                if (resultSet.next()) return Optional.of(getUser(resultSet));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Usuario> getAll() throws SQLException {
        List<Usuario> get = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getAll = connection.prepareStatement(GET_ALL_USERS);
             ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) {
                get.add(getUser(resultSet));
            }
            return get;
        }
    }

    private Usuario getUser(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario(resultSet.getString("nombre"),
                "",
                EnumUsuario.valueOf(resultSet.getString("rol")));
        usuario.setUsuario_id(resultSet.getInt("usuario_id"));
        usuario.setEstado(resultSet.getBoolean("estado"));
        return usuario;
    }

}
