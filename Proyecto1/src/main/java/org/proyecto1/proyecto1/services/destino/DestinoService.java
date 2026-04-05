package org.proyecto1.proyecto1.services.destino;

import org.apache.commons.lang3.StringUtils;
import org.proyecto1.proyecto1.db.DestinoDAO;
import org.proyecto1.proyecto1.dtos.destino.DestinoRequest;
import org.proyecto1.proyecto1.dtos.destino.DestinoUpdate;
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
        if (destinoDAO.existsName(destino.getNombre()) != -1)
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro destino.");
        destinoDAO.insert(destino);
    }

    public void insert(DestinoRequest destinoRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Destino destino = new Destino(destinoRequest.getNombre(), destinoRequest.getPais(), destinoRequest.getDescripcion());
        if (StringUtils.isNotBlank(destino.getClima_mejor_epoca()))
            destino.setClima_mejor_epoca(destinoRequest.getClima_mejor_epoca());
        if (StringUtils.isNotBlank(destino.getImagen())) destino.setImagen(destinoRequest.getImagen());
        if (!destino.isValid()) throw new UserDataInvalidException("Los datos del destino son inválidos.");
        DestinoDAO destinoDAO = new DestinoDAO();
        if (destinoDAO.existsName(destino.getNombre()) != -1)
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro destino.");
        destinoDAO.insert(destino);
    }

    public void update(DestinoUpdate destinoUpdate) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Destino destino = new Destino(destinoUpdate.getNombre(), destinoUpdate.getPais(), destinoUpdate.getDescripcion());
        destino.setDestino_id(destinoUpdate.getDestino_id());
        if (StringUtils.isNotBlank(destinoUpdate.getClima_mejor_epoca()))
            destino.setClima_mejor_epoca(destinoUpdate.getClima_mejor_epoca());
        if (StringUtils.isNotBlank(destinoUpdate.getImagen())) destino.setImagen(destinoUpdate.getImagen());
        if (!destino.isValid()) throw new UserDataInvalidException("Los datos del destino son inválidos.");
        DestinoDAO destinoDAO = new DestinoDAO();
        if (destinoDAO.validUpdate(destino.getDestino_id(), destino.getNombre()))
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro destino.");
        destinoDAO.update(destino);
    }

    public List<Destino> getAllDestino() throws SQLException {
        DestinoDAO destinoDAO = new DestinoDAO();
        return destinoDAO.getAll();
    }

    public Destino getDestinoById(int id) throws SQLException, UserDataInvalidException {
        DestinoDAO destinoDAO = new DestinoDAO();
        Optional<Destino> destinoOptional = destinoDAO.getById(id);
        if (destinoOptional.isEmpty()) throw new UserDataInvalidException("El destino no existe.");
        return destinoOptional.get();
    }

    public List<Destino> getByCoincidence(String parameter) throws SQLException {
        DestinoDAO destinoDAO = new DestinoDAO();
        return destinoDAO.getByCoincidence(parameter);
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
