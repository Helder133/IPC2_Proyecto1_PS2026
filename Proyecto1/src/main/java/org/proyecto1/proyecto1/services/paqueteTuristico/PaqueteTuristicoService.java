package org.proyecto1.proyecto1.services.paqueteTuristico;

import org.apache.commons.lang3.StringUtils;
import org.proyecto1.proyecto1.db.PaqueteTuristicoDAO;
import org.proyecto1.proyecto1.dtos.paqueteTuristico.PaqueteTuristicoRequest;
import org.proyecto1.proyecto1.dtos.paqueteTuristico.PaqueteTuristicoUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaqueteTuristicoService {
    public void insertDesdeArchivo(PaqueteTuristico paqueteTuristico) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!paqueteTuristico.isValid())
            throw new UserDataInvalidException("Los datos del paquete turístico son inválidos, por favor verifique.");
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        if (paqueteTuristicoDAO.existsName(paqueteTuristico.getNombre()) != -1)
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro paquete turístico.");
        paqueteTuristicoDAO.insert(paqueteTuristico);
    }

    public void insert(PaqueteTuristicoRequest paqueteTuristicoRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        PaqueteTuristico paqueteTuristico = new PaqueteTuristico(paqueteTuristicoRequest.getDestinoId(),
                paqueteTuristicoRequest.getNombre(), paqueteTuristicoRequest.getDuracion(),
                paqueteTuristicoRequest.getPrecioPublico(), paqueteTuristicoRequest.getCapacidadMaxima());
        if (StringUtils.isNotBlank(paqueteTuristicoRequest.getDescripcion()))
            paqueteTuristico.setDescripcion(paqueteTuristicoRequest.getDescripcion());
        if (!paqueteTuristico.isValid())
            throw new UserDataInvalidException("Los datos del paquete turístico son inválidos, por favor verifique.");
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        if (paqueteTuristicoDAO.existsName(paqueteTuristico.getNombre()) != -1)
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro paquete turístico.");
        paqueteTuristicoDAO.insert(paqueteTuristico);
    }

    public void update(PaqueteTuristicoUpdate paqueteTuristicoUpdate) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        PaqueteTuristico paqueteTuristico = new PaqueteTuristico(paqueteTuristicoUpdate.getDestinoId(),
                paqueteTuristicoUpdate.getNombre(), paqueteTuristicoUpdate.getDuracion(),
                paqueteTuristicoUpdate.getPrecioPublico(), paqueteTuristicoUpdate.getCapacidadMaxima());
        if (StringUtils.isNotBlank(paqueteTuristicoUpdate.getDescripcion()))
            paqueteTuristico.setDescripcion(paqueteTuristicoUpdate.getDescripcion());
        paqueteTuristico.setPaqueteId(paqueteTuristicoUpdate.getPaqueteId());
        if (!paqueteTuristico.isValid())
            throw new UserDataInvalidException("Los datos del paquete turístico son inválidos, por favor verifique.");
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        if (paqueteTuristicoDAO.validUpdate(paqueteTuristico.getPaqueteId(), paqueteTuristico.getNombre()))
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro paquete turístico.");
        paqueteTuristicoDAO.update(paqueteTuristico);
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

    public List<PaqueteTuristico> getByCoincidence(String parameter) throws SQLException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        return paqueteTuristicoDAO.getByCoincidence(parameter);
    }

    public PaqueteTuristico getById(int id) throws SQLException, UserDataInvalidException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        Optional<PaqueteTuristico> paqueteTuristico = paqueteTuristicoDAO.getById(id);
        if (paqueteTuristico.isEmpty())
            throw new UserDataInvalidException("El paquete turístico no existe.");
        return paqueteTuristico.get();
    }

    public PaqueteTuristico getById(int id, Connection connection) throws SQLException, UserDataInvalidException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        Optional<PaqueteTuristico> paqueteTuristico = paqueteTuristicoDAO.getById(id, connection);
        if (paqueteTuristico.isEmpty())
            throw new UserDataInvalidException("El paquete turístico no existe.");
        return paqueteTuristico.get();
    }

    public Map<String, Double> getPrecios(int paqueteId, Connection connection) throws SQLException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        return paqueteTuristicoDAO.getPrecios(paqueteId, connection);
    }

    public void updateEstado(int paqueteId) throws SQLException {
        PaqueteTuristicoDAO paqueteTuristicoDAO = new PaqueteTuristicoDAO();
        paqueteTuristicoDAO.updateEstado(paqueteId);
    }

}
