package org.proyecto1.proyecto1.services.paqueteTuristico;

import org.proyecto1.proyecto1.db.PaqueteTuristicoDAO;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;

import java.sql.SQLException;
import java.util.List;

public class PaqueteTuristicoService {
    public void insertDesdeArchivo(PaqueteTuristico paqueteTuristico) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!paqueteTuristico.isValid())
            throw new UserDataInvalidException("Los datos del paquete turístico son inválidos.");
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        if (paqueteTuristicoDAO.existsName(paqueteTuristico.getNombre()) != -1)
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro paquete turístico.");
        paqueteTuristicoDAO.insert(paqueteTuristico);
    }

    public int existsName(String name) throws SQLException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        return paqueteTuristicoDAO.existsName(name);
    }

    public void delete(int id) throws SQLException, UserDataInvalidException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        if (paqueteTuristicoDAO.getById(id).isEmpty())
            throw new UserDataInvalidException("El paquete turístico a eliminar no existe.");
        paqueteTuristicoDAO.delete(id);
    }

    public List<PaqueteTuristico> getAllPaqueteTuristico() throws SQLException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        return paqueteTuristicoDAO.getAll();
    }

    public PaqueteTuristico getById(int id) throws SQLException, UserDataInvalidException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        if (paqueteTuristicoDAO.getById(id).isEmpty())
            throw new UserDataInvalidException("El paquete turístico no existe.");
        return paqueteTuristicoDAO.getById(id).get();
    }

}
