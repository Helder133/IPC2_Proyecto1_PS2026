package org.proyecto1.proyecto1.services.proveedor;

import org.proyecto1.proyecto1.db.ProveedorDAO;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.proveedor.Proveedor;

import java.sql.SQLException;
import java.util.List;

public class ProveedorService {
    public void insertDesdeArchivo(Proveedor proveedor) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!proveedor.idValid()) throw new UserDataInvalidException("Los datos del proveedor son inválidos");
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        if (proveedorDAO.existsName(proveedor.getNombre()) != -1) throw new EntityAlreadyExistsException("El nombre que desea registrar, ya esta registrado en otro proveedor.");
        proveedorDAO.insert(proveedor);
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
