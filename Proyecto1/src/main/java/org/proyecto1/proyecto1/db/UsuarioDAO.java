package org.proyecto1.proyecto1.db;

import org.apache.commons.lang3.StringUtils;
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
    private static final String login = "SELECT * FROM usuario WHERE nombre = ? AND password = ?";
    private static final String insertUser = "INSERT INTO usuario (nombre, password, rol) VALUES (?, ?, ?)";
    private static final String updateUserWithPassword = "UPDATE usuario SET nombre = ?, password = ?, rol = ? WHERE usuario_id = ?";
    private static final String updateUserWithoutPassword = "UPDATE usuario SET nombre = ?, rol = ? WHERE usuario_id = ?";
    private static final String deleteUser = "DELETE FROM usuario WHERE usuario_id = ?";
    private static final String getUserById = "SELECT * FROM usuario WHERE usuario_id = ?";
    private static final String getAllUsers = "SELECT * FROM usuario";
    private static final String existsUser = "SELECT usuario_id FROM usuario WHERE nombre = ?";
    private static final String getByCoincidence = "SELECT * FROM usuario WHERE nombre LIKE ?";
    private static final String validUpdate = "SELECT usuario_id FROM usuario WHERE usuario_id <> ? AND nombre = ?";

    public Optional<Usuario> login(Usuario usuario) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement login1 = connection.prepareStatement(login)) {
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
        try (PreparedStatement exists = connection.prepareStatement(existsUser)) {
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
        try (PreparedStatement getByCoincidence_ = connection.prepareStatement(getByCoincidence)){
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
        try (PreparedStatement validUpdate_ = connection.prepareStatement(validUpdate)) {
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
        try (PreparedStatement insert = connection.prepareStatement(insertUser)) {
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
            try (PreparedStatement update = connection.prepareStatement(updateUserWithoutPassword)) {
                update.setString(1, usuario.getNombre());
                update.setString(2, usuario.getRol().name());
                update.setInt(3, usuario.getUsuario_id());
                update.executeUpdate();
            }
        } else {
            try (PreparedStatement update = connection.prepareStatement(updateUserWithPassword)) {
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
        try (PreparedStatement delete = connection.prepareStatement(deleteUser)) {
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<Usuario> getById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement getUser = connection.prepareStatement(getUserById)) {
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
        try (PreparedStatement getAll = connection.prepareStatement(getAllUsers);
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
        return usuario;
    }

}
