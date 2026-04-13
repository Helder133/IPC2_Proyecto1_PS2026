package org.proyecto1.proyecto1.resources.reporte;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.reporte.*;
import org.proyecto1.proyecto1.dtos.reporte.DestinoMasConcurridoResponse;
import org.proyecto1.proyecto1.dtos.reporte.mejorAgente.ReporteMejorAgenteResponse;
import org.proyecto1.proyecto1.dtos.reporte.paqueteMasVendidoYMenosVendido.ReportePaqueteMasVendidoYMenosVendidoResponse;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.reporte.AgenteConMasGanancia;
import org.proyecto1.proyecto1.models.reporte.mejorAgente.ReporteMejorAgente;
import org.proyecto1.proyecto1.models.reporte.paqueteMasVendidoYMenosVendido.ReportePaqueteMasVendidoYMenosVendido;
import org.proyecto1.proyecto1.services.reporte.ReporteService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Path("/reporte")
public class ReporteResource {
    // formato esperado yyyy-MM-dd
    @GET
    @Path("/{fechaInicio}/reservacion/confirmada/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservacionesConfirmadas(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            List<ReservacionConfirmadaResponse> reservacionConfirmadaResponses = reporteService.getReservacionConfirmada(fechaInicio, fechaFin)
                    .stream()
                    .map(ReservacionConfirmadaResponse::new)
                    .toList();
            return Response.ok(reservacionConfirmadaResponses).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("/{fechaInicio}/reservacion/cancelada/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservacionesCancelada(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            List<ReservacionCanceladaResponse> reservacionCanceladaResponses = reporteService.getReservacionCancelada(fechaInicio, fechaFin)
                    .stream()
                    .map(ReservacionCanceladaResponse::new)
                    .toList();
            return Response.ok(reservacionCanceladaResponses).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("/{fechaInicio}/ganancia/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGananciaEnUnIntervaloDeTiempo(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            return Response.ok(new GananciaEnUnIntervaloDeTiempoResponse(reporteService.gananciaEnUnIntervaloDeTiempo(fechaInicio, fechaFin))).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("/{fechaInicio}/usuario/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMejorAgente(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            Optional<ReporteMejorAgente> reporteMejorAgenteOptional = reporteService.getMejorAgente(fechaInicio, fechaFin);
            if (reporteMejorAgenteOptional.isPresent()) {
                return Response.ok(new ReporteMejorAgenteResponse(reporteMejorAgenteOptional.get())).build();
            } else {
                return Response.ok().build();
            }
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("/{fechaInicio}/usuario/ganancias/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAgenteConMasGanancias(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            Optional<AgenteConMasGanancia> agenteConMasGananciaOptional = reporteService.getAgenteConMasGanancias(fechaInicio, fechaFin);
            if (agenteConMasGananciaOptional.isPresent()) {
                return Response.ok(new AgenteConMasGananciaResponse(agenteConMasGananciaOptional.get())).build();
            } else {
                return Response.ok().build();
            }
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("/{fechaInicio}/paquete/mas-vendido/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaqueteMasVendido(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            Optional<ReportePaqueteMasVendidoYMenosVendido> paqueteMasVendidoVendido = reporteService.getPaqueteMasVendido(fechaInicio, fechaFin);
            if (paqueteMasVendidoVendido.isPresent()) {
                return Response.ok(new ReportePaqueteMasVendidoYMenosVendidoResponse(paqueteMasVendidoVendido.get())).build();
            } else {
                return Response.ok().build();
            }
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("/{fechaInicio}/paquete/menos-vendido/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaqueteMenosVendido(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            Optional<ReportePaqueteMasVendidoYMenosVendido> paqueteMasVendidoVendido = reporteService.getPaqueteMenosVendido(fechaInicio, fechaFin);
            if (paqueteMasVendidoVendido.isPresent()) {
                return Response.ok(new ReportePaqueteMasVendidoYMenosVendidoResponse(paqueteMasVendidoVendido.get())).build();
            } else {
                return Response.ok().build();
            }
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("/{fechaInicio}/destino/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestinoMasConcurrido(@PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
        try {
            ReporteService reporteService = new ReporteService();
            List<DestinoMasConcurridoResponse> destinoMasConcurridoResponses = reporteService.getDestinoMasConcurrido(fechaInicio, fechaFin)
                    .stream()
                    .map(DestinoMasConcurridoResponse::new)
                    .toList();
            return Response.ok(destinoMasConcurridoResponses).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }


    private Response errorEjecucion(String mensaje, int tipo) {
        switch (tipo) {
            // UserDataInvalidException
            case 1 -> {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"" + mensaje + "\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            // EntityAlreadyExistsException
            case 2 -> {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\": \"" + mensaje + "\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            // SQLException
            case 3 -> {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"" + mensaje + "\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        }
        return null;
    }
}