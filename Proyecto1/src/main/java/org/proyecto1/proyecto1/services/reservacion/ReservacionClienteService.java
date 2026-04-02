package org.proyecto1.proyecto1.services.reservacion;

import org.proyecto1.proyecto1.db.ReservacionClienteDAO;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.reservacion.ReservacionCliente;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservacionClienteService {
    public void insertDesdeArchivo(ReservacionCliente reservacionCliente, Connection connection) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!reservacionCliente.isValid())
            throw new UserDataInvalidException("Los datos de la reservación del cliente son inválidos");
        ReservacionClienteDAO reservacionClienteDAO = new ReservacionClienteDAO();
        if (reservacionClienteDAO.exists(new ReservacionCliente(reservacionCliente.getReservacionId(), reservacionCliente.getClienteId()), connection))
            throw new EntityAlreadyExistsException("El cliente ya está asociado a la reservación");
        reservacionClienteDAO.insert(reservacionCliente, connection);
    }

    public void delete(int reservacionId, int clienteId) throws SQLException, UserDataInvalidException {
        ReservacionClienteDAO reservacionClienteDAO = new ReservacionClienteDAO();
        if (!reservacionClienteDAO.exists(new ReservacionCliente(reservacionId, clienteId)))
            throw new UserDataInvalidException("La asociación entre el cliente y la reservación no existe");
        reservacionClienteDAO.delete(reservacionId, clienteId);
    }

}
