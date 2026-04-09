package org.proyecto1.proyecto1.services.proveedor;

import org.apache.commons.lang3.StringUtils;
import org.proyecto1.proyecto1.db.ProveedorDAO;
import org.proyecto1.proyecto1.dtos.proveedor.ProveedorRequest;
import org.proyecto1.proyecto1.dtos.proveedor.ProveedorUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.proveedor.Proveedor;

import java.sql.SQLException;
import java.util.List;

public class ProveedorService {
    public void insertDesdeArchivo(Proveedor proveedor) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!proveedor.idValid()) throw new UserDataInvalidException("Los datos del proveedor son inválidos");
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        if (proveedorDAO.existsName(proveedor.getProveedor_id(), proveedor.getNombre()))
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro proveedor.");
        if (StringUtils.isNotBlank(proveedor.getContacto()) && proveedorDAO.existsContacto(proveedor.getProveedor_id(), proveedor.getContacto()))
            throw new EntityAlreadyExistsException("El contacto que desea registrar, ya esta registrado en otro proveedor.");
        proveedorDAO.insert(proveedor);
    }

    public void insert(ProveedorRequest proveedorRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Proveedor proveedor = new Proveedor(proveedorRequest.getNombre(), proveedorRequest.getPais(), proveedorRequest.getTipo());
        if (StringUtils.isNotBlank(proveedorRequest.getContacto())) proveedor.setContacto(proveedorRequest.getContacto());
        if (!proveedor.idValid()) throw new UserDataInvalidException("Los datos del proveedor son inválidos");
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        if (proveedorDAO.existsName(proveedor.getProveedor_id(), proveedor.getNombre()))
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro proveedor.");
        if (proveedorDAO.existsContacto(proveedor.getProveedor_id(), proveedor.getContacto()))
            throw new EntityAlreadyExistsException("El contacto que desea registrar, ya esta registrado en otro proveedor.");
        proveedorDAO.insert(proveedor);
    }

    public void update(ProveedorUpdate proveedorUpdate) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Proveedor proveedor = new Proveedor(proveedorUpdate.getNombre(), proveedorUpdate.getPais(), proveedorUpdate.getTipo());
        if (StringUtils.isNotBlank(proveedorUpdate.getContacto())) proveedor.setContacto(proveedorUpdate.getContacto());
        proveedor.setProveedor_id(proveedorUpdate.getProveedorId());
        if (!proveedor.idValid()) throw new UserDataInvalidException("Los datos del proveedor son inválidos");
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        if (proveedorDAO.existsName(proveedor.getProveedor_id(), proveedor.getNombre()))
            throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro proveedor.");
        if (proveedorDAO.existsContacto(proveedor.getProveedor_id(), proveedor.getContacto()))
            throw new EntityAlreadyExistsException("El contacto que desea registrar, ya esta registrado en otro proveedor.");
        proveedorDAO.update(proveedor);
    }

    public void delete(int id) throws SQLException, UserDataInvalidException {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        if (proveedorDAO.getById(id).isEmpty()) throw new UserDataInvalidException("El proveedor a eliminar no existe");
        proveedorDAO.delete(id);
    }

    public List<Proveedor> getAllProveedor() throws SQLException {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        return proveedorDAO.getAll();
    }

    public List<Proveedor> getByCoincidence(String parameter) throws SQLException {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        return proveedorDAO.getByCoincidence(parameter);
    }

    public int existsName(String name) throws SQLException {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        return proveedorDAO.existsName(name);
    }

    public Proveedor getById(int id) throws SQLException, UserDataInvalidException {
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        if (proveedorDAO.getById(id).isEmpty()) throw new UserDataInvalidException("El proveedor no existe");
        return proveedorDAO.getById(id).get();
    }

}
