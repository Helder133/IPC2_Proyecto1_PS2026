package org.proyecto1.proyecto1.services.servicioPaquete;

import org.proyecto1.proyecto1.db.ServicioPaqueteDAO;
import org.proyecto1.proyecto1.dtos.servicioPaquete.ServicioPaqueteRequest;
import org.proyecto1.proyecto1.dtos.servicioPaquete.ServicioPaqueteUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.servicioPaquete.ServicioPaquete;

import java.sql.SQLException;
import java.util.List;

public class ServicioPaqueteService {
    public void insertDesdeArchivo(ServicioPaquete servicioPaquete) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!servicioPaquete.isValid())
            throw new UserDataInvalidException("Los datos del servicio del paquete son inválidos");
        ServicioPaqueteDAO servicioPaqueteDAO = new ServicioPaqueteDAO();
        if (servicioPaqueteDAO.exists(servicioPaquete.getPaqueteId(), servicioPaquete.getProveedorId()))
            throw new EntityAlreadyExistsException("El servicio del paquete ya existe");
        servicioPaqueteDAO.insert(servicioPaquete);
    }

    public void insert(ServicioPaqueteRequest servicioPaqueteRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        ServicioPaquete servicioPaquete = new ServicioPaquete(servicioPaqueteRequest.getProveedorId(), servicioPaqueteRequest.getPaqueteId(),
                servicioPaqueteRequest.getDescripcion(), servicioPaqueteRequest.getCosto());
        if (!servicioPaquete.isValid())
            throw new UserDataInvalidException("Los datos del servicio del paquete son inválidos");
        ServicioPaqueteDAO servicioPaqueteDAO = new ServicioPaqueteDAO();
        if (servicioPaqueteDAO.exists(servicioPaquete.getPaqueteId(), servicioPaquete.getProveedorId()))
            throw new EntityAlreadyExistsException("El servicio del paquete ya existe");
        servicioPaqueteDAO.insert(servicioPaquete);
    }

    public void update(ServicioPaqueteUpdate servicioPaqueteUpdate) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        ServicioPaquete servicioPaquete = new ServicioPaquete(servicioPaqueteUpdate.getProveedorId(), servicioPaqueteUpdate.getPaqueteId(),
                servicioPaqueteUpdate.getDescripcion(), servicioPaqueteUpdate.getCosto());
        if (!servicioPaquete.isValid())
            throw new UserDataInvalidException("Los datos del servicio del paquete son inválidos");
        ServicioPaqueteDAO servicioPaqueteDAO = new ServicioPaqueteDAO();
        if (!servicioPaqueteDAO.exists(servicioPaquete.getPaqueteId(), servicioPaquete.getProveedorId()))
            throw new EntityAlreadyExistsException("El servicio del paquete no existe");
        servicioPaqueteDAO.update(servicioPaquete);
    }

    public List<ServicioPaquete> getByPaqueteId(int paquete_id) throws SQLException {
        ServicioPaqueteDAO servicioPaqueteDAO = new ServicioPaqueteDAO();
        return servicioPaqueteDAO.getByPaqueteId(paquete_id);
    }

    public List<ServicioPaquete> getAllServicioPaquete() throws SQLException {
        ServicioPaqueteDAO servicioPaqueteDAO = new ServicioPaqueteDAO();
        return servicioPaqueteDAO.getAll();
    }

    public void delete(int paquete_id, int proveedor_id) throws SQLException {
        ServicioPaqueteDAO servicioPaqueteDAO = new ServicioPaqueteDAO();
        if (!servicioPaqueteDAO.exists(paquete_id, proveedor_id))
            throw new SQLException("El servicio del paquete no existe");
        servicioPaqueteDAO.delete(paquete_id, proveedor_id);
    }

}
