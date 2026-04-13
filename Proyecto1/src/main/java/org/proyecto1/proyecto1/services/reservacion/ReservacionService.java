package org.proyecto1.proyecto1.services.reservacion;

import org.proyecto1.proyecto1.db.ClienteDAO;
import org.proyecto1.proyecto1.db.ReservacionDAO;
import org.proyecto1.proyecto1.db.config.DBConnection;
import org.proyecto1.proyecto1.dtos.reservacion.ReservacionRequest;
import org.proyecto1.proyecto1.dtos.reservacion.ReservacionUpdate;
import org.proyecto1.proyecto1.dtos.reservacion.cliente.ReservacionClienteRequest;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.cliente.Cliente;
import org.proyecto1.proyecto1.models.paqueteTuristico.PaqueteTuristico;
import org.proyecto1.proyecto1.models.reservacion.EnumReservacion;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;
import org.proyecto1.proyecto1.models.reservacion.ReservacionCliente;
import org.proyecto1.proyecto1.services.cliente.ClienteService;
import org.proyecto1.proyecto1.services.pago.HistorialPagoService;
import org.proyecto1.proyecto1.services.paqueteTuristico.PaqueteTuristicoService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReservacionService {
    public void insertDesdeArchivo(Reservacion reservacion, List<String> dpiOPasaporte) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        if (!reservacion.isValid()) throw new UserDataInvalidException("Los datos de la reservación son inválidos.");
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            String nuevoCodigo = generarNuevoCodigoArchivo(connection, reservacionDAO);
            PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
            Map<String, Double> precios = paqueteTuristicoService.getPrecios(reservacion.getPaqueteId(), connection);
            if (!precios.isEmpty()) {
                if (precios.get("precio_publico") == null) {
                    reservacion.setCostoTotal(0);
                } else {
                    reservacion.setCostoTotal(precios.get("precio_publico"));
                }
                reservacion.setCostoAgencia(precios.get("precio_agencia"));
            }
            reservacion.setCantidadPersona(dpiOPasaporte.size());
            reservacion.setCodigoArchivo(nuevoCodigo);
            int reservacionId = reservacionDAO.insert(reservacion, connection);
            reservacion.setReservacionId(reservacionId);
            ReservacionClienteService reservacionClienteService = new ReservacionClienteService();
            int getNumberClientesRegistrados = reservacionClienteService.getNumberClientesRegistrados(reservacionId, connection);
            PaqueteTuristico paqueteTuristico = paqueteTuristicoService.getById(reservacion.getPaqueteId(), connection);
            ClienteDAO clienteDAO = new ClienteDAO();
            for (String dpi : dpiOPasaporte) {
                if (getNumberClientesRegistrados >= paqueteTuristico.getCapacidadMaxima()) {
                    throw new UserDataInvalidException("El máximo de clientes en un mismo paquete, ya fue alcanzado.");
                }
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

    public void insert(ReservacionRequest reservacionRequest) throws SQLException, UserDataInvalidException {
        Reservacion reservacion = new Reservacion(reservacionRequest.getPaqueteId(), reservacionRequest.getUsuarioId(),
                reservacionRequest.getFechaViaje(), reservacionRequest.getFechaCreacion());
        reservacion.setCantidadPersona(reservacionRequest.getCantidadPersonas());
        if (!reservacion.isValid()) throw new UserDataInvalidException("Los datos de la reservación son inválidos.");
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            Map<String, Double> precios = paqueteTuristicoService.getPrecios(reservacion.getPaqueteId(), connection);
            if (!precios.isEmpty()) {
                if (precios.get("precio_publico") == null) {
                    reservacion.setCostoTotal(0);
                } else {
                    reservacion.setCostoTotal(precios.get("precio_publico"));
                }
                reservacion.setCostoAgencia(precios.get("precio_agencia"));
            }
            PaqueteTuristico paqueteTuristico = paqueteTuristicoService.getById(reservacion.getPaqueteId(), connection);
            if (reservacion.getCantidadPersona() > paqueteTuristico.getCapacidadMaxima())
                throw new UserDataInvalidException(String.format("Esta tratando de asignar %d clientes a un paquete que solo permite %d clientes", reservacion.getCantidadPersona(), paqueteTuristico.getCapacidadMaxima()));
            reservacionDAO.insert(reservacion, connection);
        } catch (SQLException | UserDataInvalidException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void update(ReservacionUpdate reservacionUpdate) throws SQLException, UserDataInvalidException {
        Reservacion reservacion = new Reservacion(reservacionUpdate.getPaqueteId(), reservacionUpdate.getUsuarioId(), reservacionUpdate.getFechaViaje());
        reservacion.setCantidadPersona(reservacionUpdate.getCantidadPersonas());
        reservacion.setReservacionId(reservacionUpdate.getReservacionId());
        if (!reservacion.isValid()) throw new UserDataInvalidException("Los datos de la reservación son inválidos.");
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            Map<String, Double> precios = paqueteTuristicoService.getPrecios(reservacion.getPaqueteId(), connection);
            if (!precios.isEmpty()) {
                if (precios.get("precio_publico") == null) {
                    reservacion.setCostoTotal(0);
                } else {
                    reservacion.setCostoTotal(precios.get("precio_publico"));
                }
                reservacion.setCostoAgencia(precios.get("precio_agencia"));
            }
            PaqueteTuristico paqueteTuristico = paqueteTuristicoService.getById(reservacion.getPaqueteId(), connection);
            if (reservacion.getCantidadPersona() > paqueteTuristico.getCapacidadMaxima())
                throw new UserDataInvalidException(String.format("Esta tratando de asignar %d clientes a un paquete que solo permite %d clientes", reservacion.getCantidadPersona(), paqueteTuristico.getCapacidadMaxima()));
            reservacionDAO.update(reservacion, connection);
        } catch (SQLException | UserDataInvalidException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void cancelarReservacion(int reservacionId) throws SQLException, UserDataInvalidException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        Optional<Reservacion> reservacionOptional = reservacionDAO.getById(reservacionId);
        if (reservacionOptional.isEmpty())
            throw new UserDataInvalidException("La reservación a cancelar no se encontró, vuelva a intentar.");
        if (reservacionOptional.get().getEstado() == EnumReservacion.Cancelada)
            throw new UserDataInvalidException("La reservación ya se encuentra cancelada.");
        if (reservacionOptional.get().getEstado() == EnumReservacion.Completada)
            throw new UserDataInvalidException("La reservación ya se encuentra completada por ende no se puede cancelar.");
        Reservacion reservacion = reservacionOptional.get();
        LocalDate fechaActual = LocalDate.now();
        long diasRestantesAlViaje = ChronoUnit.DAYS.between(fechaActual, reservacion.getFechaViaje());
        HistorialPagoService historialPagoService = new HistorialPagoService();
        double dineroAbonado = historialPagoService.pagosLlevado(reservacionId);
        if (diasRestantesAlViaje > 30) {
            reservacion.setReembolso(dineroAbonado);
        } else if (diasRestantesAlViaje >= 15) {
            double reembolso = dineroAbonado * 0.7;
            reservacion.setReembolso(reembolso);
        } else if (diasRestantesAlViaje >= 7) {
            double reembolso = dineroAbonado * 0.4;
            reservacion.setReembolso(reembolso);
        } else {
            throw new UserDataInvalidException("No se puede cancelar el viaje, ya que faltan menos de 7 días para que se realize.");
        }
        reservacion.setEstado(EnumReservacion.Cancelada);
        reservacion.setFechaCancelacion(fechaActual);
        reservacionDAO.cancelarReservacion(reservacion);
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

    public List<Reservacion> getByClientId(int cliente_id) throws SQLException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        return reservacionDAO.getByClienteId(cliente_id);
    }

    public List<Reservacion> getByUsuarioId(int usuarioId) throws SQLException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        return reservacionDAO.getByUsuarioId(usuarioId);
    }

    public Reservacion getById(int id) throws SQLException, UserDataInvalidException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        if (reservacionDAO.getById(id).isEmpty()) throw new UserDataInvalidException("La reservación no existe");
        return reservacionDAO.getById(id).get();
    }

    public void updateEstadoACompletada(int reservacion_id) throws SQLException, UserDataInvalidException {
        ReservacionDAO reservacionDAO = new ReservacionDAO();
        Optional<Reservacion> reservacionOptional = reservacionDAO.getById(reservacion_id);
        if (reservacionOptional.isEmpty())
            throw new UserDataInvalidException("La reservación no existe");
        if (reservacionOptional.get().getEstado() != EnumReservacion.Confirmada)
            throw new UserDataInvalidException("La reservación no se encuentra confirmada, por eso no se puede cambiar al estado: completada");
        reservacionDAO.updateEstado(reservacion_id, EnumReservacion.Completada);

    }

    public void agregarClienteAReservacion(ReservacionClienteRequest reservacionClienteRequest) throws SQLException, UserDataInvalidException, EntityAlreadyExistsException {
        ReservacionCliente reservacionCliente = new ReservacionCliente(reservacionClienteRequest.getReservacionId(), reservacionClienteRequest.getClienteId());
        if (!reservacionCliente.isValid())
            throw new UserDataInvalidException("Los datos de la reservación son incorrectos");
        Reservacion reservacion = getById(reservacionCliente.getReservacionId());
        ClienteService clienteService = new ClienteService();
        Cliente cliente = clienteService.getClientById(reservacionCliente.getClienteId());
        PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
        PaqueteTuristico paqueteTuristico = paqueteTuristicoService.getById(reservacion.getPaqueteId());
        ReservacionClienteService reservacionClienteService = new ReservacionClienteService();
        reservacionClienteService.insertCliente(reservacionCliente, paqueteTuristico.getCapacidadMaxima());
    }

    public void deleteClienteAReservacion(int reservacionId, int clienteId) throws SQLException, UserDataInvalidException {
        ReservacionClienteService reservacionClienteService = new ReservacionClienteService();
        reservacionClienteService.delete(reservacionId, clienteId);
    }

    public List<Cliente> getClientesDeUnaReservacion(int reservacionId) throws SQLException {
        ReservacionClienteService reservacionClienteService = new ReservacionClienteService();
        return reservacionClienteService.getClientesDeUnaReservacion(reservacionId);
    }

}
