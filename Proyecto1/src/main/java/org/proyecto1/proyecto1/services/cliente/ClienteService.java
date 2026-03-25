package org.proyecto1.proyecto1.services.cliente;

import org.proyecto1.proyecto1.db.ClienteDAO;
import org.proyecto1.proyecto1.dtos.cliente.ClienteRequest;
import org.proyecto1.proyecto1.dtos.cliente.ClienteUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.cliente.Cliente;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    public void insertClient(ClienteRequest clienteRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Cliente cliente = new Cliente(clienteRequest.getDpi_o_pasaporte(), clienteRequest.getNombre(), clienteRequest.getFecha(), clienteRequest.getEmail(), clienteRequest.getTelefono(), clienteRequest.getNacionalidad());
        if (!cliente.isValid()) throw new UserDataInvalidException("Los datos del cliente son inválidos");
        ClienteDAO clienteDAO = new ClienteDAO();
        Optional<Cliente> clienteOptional = clienteDAO.existsClient(cliente.getDpi_o_pasaporte());
        if (clienteOptional.isPresent()) throw new EntityAlreadyExistsException("El dpi o pasaporte que desea registrar, ya esta registrado en otro cliente.");
        clienteDAO.insert(cliente);
    }

    public void updateClient(ClienteUpdate clienteUpdate) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        Cliente cliente = new Cliente(clienteUpdate.getDpi_o_pasaporte(), clienteUpdate.getNombre(), clienteUpdate.getFecha(), clienteUpdate.getEmail(), clienteUpdate.getTelefono(), clienteUpdate.getNacionalidad());
        cliente.setCliente_id(clienteUpdate.getCliente_id());
        if (!cliente.isValid()) throw new UserDataInvalidException("Los datos del cliente son inválidos");
        ClienteDAO clienteDAO = new ClienteDAO();;
        if (clienteDAO.validUpdate(cliente.getCliente_id(), cliente.getDpi_o_pasaporte())) throw new EntityAlreadyExistsException("El dpi o pasaporte que desea registrar, ya esta registrado en otro cliente.");
        clienteDAO.update(cliente);
    }

    public void deleteClient(int id) throws SQLException, UserDataInvalidException {
        ClienteDAO clienteDAO = new ClienteDAO();
        Optional<Cliente> clienteOptional = clienteDAO.getById(id);
        if (clienteOptional.isEmpty()) throw new UserDataInvalidException("El cliente a eliminar no existe");
        clienteDAO.delete(id);
    }

    public Cliente getClientById(int id) throws SQLException, UserDataInvalidException {
        ClienteDAO clienteDAO = new ClienteDAO();
        Optional<Cliente> clienteOptional = clienteDAO.getById(id);
        if (clienteOptional.isEmpty()) throw new UserDataInvalidException("El cliente no existe");
        return clienteOptional.get();
    }

    public List<Cliente> getClientsByCoincidence(String parameter) throws SQLException {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.getByCoincidence(parameter);
    }

    public List<Cliente> getAllClients() throws SQLException {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.getAll();
    }

}
