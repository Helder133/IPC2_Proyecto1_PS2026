package org.proyecto1.proyecto1.services.destino;

import org.proyecto1.proyecto1.db.DestinoDAO;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.destino.Destino;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DestinoService {
    public void insertDesdeArchivo(Destino destino) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!destino.isValid()) throw new UserDataInvalidException("Los datos del destino son inválidos.");
        DestinoDAO destinoDAO = new DestinoDAO();
        if (destinoDAO.existsName(destino.getNombre()) != -1) throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro destino.");
        destinoDAO.insert(destino);
    }

    public List<Destino> getAllDestino () throws SQLException {
        DestinoDAO destinoDAO = new DestinoDAO();
        return destinoDAO.getAll();
    }

    public Destino getDestinoById (int id) throws SQLException, UserDataInvalidException {
        DestinoDAO destinoDAO = new DestinoDAO();
        Optional<Destino> destinoOptional = destinoDAO.getById(id);
        if (destinoOptional.isEmpty()) throw new UserDataInvalidException("El destino no existe.");
        return destinoOptional.get();
    }

    public int existsName(String name) throws SQLException {
        DestinoDAO destinoDAO = new DestinoDAO();
        return destinoDAO.existsName(name);
    }

    public void delete(int id) throws SQLException, UserDataInvalidException {
        DestinoDAO destinoDAO = new DestinoDAO();
        Optional<Destino> destinoOptional = destinoDAO.getById(id);
        if (destinoOptional.isEmpty()) throw new UserDataInvalidException("El destino a eliminar no existe.");
        destinoDAO.delete(id);
    }

}
