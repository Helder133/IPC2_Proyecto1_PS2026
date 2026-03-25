package org.proyecto1.proyecto1.services.usuario;

import org.proyecto1.proyecto1.db.UsuarioDAO;
import org.proyecto1.proyecto1.dtos.usuario.LoginRequest;
import org.proyecto1.proyecto1.dtos.usuario.UsuarioRequest;
import org.proyecto1.proyecto1.dtos.usuario.UsuarioUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.usuario.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    public Usuario login(LoginRequest loginRequest) throws UserDataInvalidException, SQLException {
        Usuario usuario = new Usuario(loginRequest.getNombre(), loginRequest.getPassword(), null);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Optional<Usuario> usuarioOptional = usuarioDAO.login(usuario);
        if (usuarioOptional.isEmpty()) throw new UserDataInvalidException("Usuario o contraseña incorrectos");
        return usuarioOptional.get();
    }

    public void insert(UsuarioRequest usuarioRequest) throws UserDataInvalidException, EntityAlreadyExistsException, SQLException {
        Usuario usuario = new Usuario(usuarioRequest.getNombre(), usuarioRequest.getPassword(), usuarioRequest.getRol());
        if (!usuario.isValid()) throw new UserDataInvalidException("Los datos del usuario son inválidos");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int usuario_id = usuarioDAO.existsUser(usuario.getNombre());
        if (usuario_id != -1) throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro usuario.");
        usuarioDAO.insert(usuario);
    }

    public void update(UsuarioUpdate usuarioUpdate) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Usuario usuario = new Usuario(usuarioUpdate.getNombre(), usuarioUpdate.getPassword(), usuarioUpdate.getRol());
        usuario.setUsuario_id(usuarioUpdate.getUsuario_id());
        if (!usuario.isValidUpdate()) throw new UserDataInvalidException("Los datos del usuario son inválidos");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.validUpdate(usuario.getUsuario_id(), usuario.getNombre())) throw new EntityAlreadyExistsException("El nombre de usuario ya existe");
        usuarioDAO.update(usuario);
    }

    public List<Usuario> getAllUsers () throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.getAll();
    }

    public List<Usuario> getByCoincidence(String nombre) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.getByCoincidence(nombre);
    }

    public Usuario getById(int id) throws SQLException, UserDataInvalidException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Optional<Usuario> usuarioOptional = usuarioDAO.getById(id);
        if (usuarioOptional.isEmpty()) throw new UserDataInvalidException("El usuario no existe");
        return usuarioOptional.get();
    }

    public void delete(int id) throws SQLException, UserDataInvalidException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Optional<Usuario> usuarioOptional = usuarioDAO.getById(id);
        if (usuarioOptional.isEmpty()) throw new UserDataInvalidException("El usuario a eliminar no existe");
        usuarioDAO.delete(id);
    }

}
