package org.proyecto1.proyecto1.services.pago;

import org.proyecto1.proyecto1.db.HistorialPagoDAO;
import org.proyecto1.proyecto1.db.ReservacionDAO;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.pago.HistorialPago;
import org.proyecto1.proyecto1.models.reservacion.EnumReservacion;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class HistorialPagoService {
    public void insertDesdeArchivo(HistorialPago historialPago) throws SQLException, UserDataInvalidException {
        if (!historialPago.isValid()) throw new UserDataInvalidException("Los datos del pago son inválidos");
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            HistorialPagoDAO historialPagoDAO = new HistorialPagoDAO();
            ReservacionDAO reservacionDAO = new ReservacionDAO();
            Optional<Reservacion> reservacionOptional = reservacionDAO.getById(historialPago.getReservacionId(), connection);
            if (reservacionOptional.isEmpty())
                throw new UserDataInvalidException("La reservación asociada al pago no existe");
            Reservacion reservacion = reservacionOptional.get();
            double precioViaje = reservacion.getCostoTotal();
            double anticipoActual = historialPagoDAO.getAnticipo(historialPago.getReservacionId(), connection);
            double nuevoAnticipo = anticipoActual + historialPago.getMonto();
            if (reservacion.getEstado().equals(EnumReservacion.Pendiente)) {
                if (precioViaje == nuevoAnticipo) {
                    reservacionDAO.updateEstado(historialPago.getReservacionId(), EnumReservacion.Confirmada, connection);
                }
            }
            historialPagoDAO.insert(historialPago, connection);
            connection.commit();
        } catch (SQLException | UserDataInvalidException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public double pagosLlevado(int reservacionId) throws SQLException {
        HistorialPagoDAO historialPagoDAO = new HistorialPagoDAO();
        return historialPagoDAO.getAnticipo(reservacionId);
    }

}
