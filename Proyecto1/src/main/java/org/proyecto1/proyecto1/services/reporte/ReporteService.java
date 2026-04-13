package org.proyecto1.proyecto1.services.reporte;

import org.proyecto1.proyecto1.db.ReporteDAO;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.reporte.*;
import org.proyecto1.proyecto1.models.reporte.mejorAgente.ReporteMejorAgente;
import org.proyecto1.proyecto1.models.reporte.paqueteMasVendidoYMenosVendido.ReportePaqueteMasVendidoYMenosVendido;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class ReporteService {

    private LocalDate formatearFecha(String fecha) throws UserDataInvalidException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(fecha.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new UserDataInvalidException(("Fecha de pago inválida: " + fecha + ". El formato debe ser dd/MM/yyyy."));
        }

    }

    public List<ReservacionConfirmada> getReservacionConfirmada(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getReservacionConfirmada(fechaInicio, fechaFin);
    }

    public List<ReservacionCancelada> getReservacionCancelada(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getReservacionCancelada(fechaInicio, fechaFin);
    }

    public GananciaEnUnIntervaloDeTiempo gananciaEnUnIntervaloDeTiempo(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getGananciaEnUnIntervaloDeTiempo(fechaInicio, fechaFin);
    }

    public Optional<ReporteMejorAgente> getMejorAgente(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getMejorAgente(fechaInicio, fechaFin);
    }

    public Optional<AgenteConMasGanancia> getAgenteConMasGanancias(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getAgenteConMasGanancia(fechaInicio, fechaFin);
    }

    public Optional<ReportePaqueteMasVendidoYMenosVendido> getPaqueteMasVendido(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getPaqueteMasVendido(fechaInicio, fechaFin);
    }

    public Optional<ReportePaqueteMasVendidoYMenosVendido> getPaqueteMenosVendido(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getPaqueteMenosVendido(fechaInicio, fechaFin);
    }

    public List<DestinoMasConcurrido> getDestinoMasConcurrido(String inicio, String fin) throws SQLException, UserDataInvalidException {
        LocalDate fechaInicio = formatearFecha(inicio);
        LocalDate fechaFin = formatearFecha(fin);
        ReporteDAO reporteDAO = new ReporteDAO();
        return reporteDAO.getDestinoMasConcurrido(fechaInicio, fechaFin);
    }
}
