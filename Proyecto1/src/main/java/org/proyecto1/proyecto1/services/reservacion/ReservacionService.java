package org.proyecto1.proyecto1.services.reservacion;

import org.proyecto1.proyecto1.db.ClienteDAO;
import org.proyecto1.proyecto1.db.ReservacionDAO;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.cliente.Cliente;
import org.proyecto1.proyecto1.models.reservacion.EnumReservacion;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;
import org.proyecto1.proyecto1.models.reservacion.ReservacionCliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReservacionService {
    public void insertDesdeArchivo(Reservacion reservacion, List<String> dpiOPasaporte) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!reservacion.isValid()) throw new UserDataInvalidException("Los datos de la reservación son inválidos");
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            String nuevoCodigo = generarNuevoCodigoArchivo(connection, reservacionDAO);
            reservacion.setCodigoArchivo(nuevoCodigo);
            int reservacionId = reservacionDAO.insert(reservacion, connection);
            reservacion.setReservacionId(reservacionId);
            ReservacionClienteService reservacionClienteService = new ReservacionClienteService();
            ClienteDAO clienteDAO = new ClienteDAO();
            for (String dpi : dpiOPasaporte) {
                Optional<Cliente> clienteOptional = clienteDAO.existsClient(dpi, connection);
                if (clienteOptional.isEmpty())
                    throw new UserDataInvalidException("El cliente con dpi o pasaporte " + dpi + " no existe");
                int clienteId = clienteOptional.get().getCliente_id();
                reservacionClienteService.insertDesdeArchivo(new ReservacionCliente(reservacionId, clienteId), connection);
            }
            connection.commit();

        } catch (SQLException | UserDataInvalidException | EntityAlreadyExistsException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private String generarNuevoCodigoArchivo(Connection connection, ReservacionDAO reservacionDAO) throws SQLException {
        String ultimoCodigo = reservacionDAO.getUltimoCodigoArchivo(connection);
        int siguienteNumero = 1;
        if (ultimoCodigo.startsWith("RES-")) {
            String numeroStr = ultimoCodigo.substring(4);
            try {
                siguienteNumero = Integer.parseInt(numeroStr) + 1;
            } catch (NumberFormatException e) {
                System.err.printf(e.getMessage());
            }
        }
        return String.format("RES-%05d", siguienteNumero);
    }

    public int getByCodigoArchivo(String codigo) throws SQLException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        return reservacionDAO.getByCodigoArchivo(codigo);
    }

    public List<Reservacion> getAllReservacion() throws SQLException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        return reservacionDAO.getAll();
    }

    public void delete(int id) throws SQLException, UserDataInvalidException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        if (reservacionDAO.getById(id).isEmpty())
            throw new UserDataInvalidException("La reservación a eliminar no existe");
        reservacionDAO.delete(id);
    }

    public List<Reservacion> getByUserId(int cliente_id) throws SQLException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        return reservacionDAO.getByUserId(cliente_id);
    }

    public Reservacion getById(int id) throws SQLException, UserDataInvalidException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        if (reservacionDAO.getById(id).isEmpty()) throw new UserDataInvalidException("La reservación no existe");
        return reservacionDAO.getById(id).get();
    }

    public void updateEstado(int reservacion_id, EnumReservacion estado, Connection connection) throws SQLException, UserDataInvalidException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        if (reservacionDAO.getById(reservacion_id).isEmpty())
            throw new UserDataInvalidException("La reservación no existe");
        reservacionDAO.updateEstado(reservacion_id, estado, connection);

    }
}
