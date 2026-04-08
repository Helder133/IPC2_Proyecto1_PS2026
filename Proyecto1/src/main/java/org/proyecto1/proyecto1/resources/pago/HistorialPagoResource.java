package org.proyecto1.proyecto1.resources.pago;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.pago.HistorialPagoRequest;
import org.proyecto1.proyecto1.dtos.pago.HistorialPagoResponse;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.services.pago.HistorialPagoService;

import java.sql.SQLException;
import java.util.List;

@Path("/historial-pago")
public class HistorialPagoResource {

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newHistorialPago(HistorialPagoRequest historialPagoRequest) {
        try {
            HistorialPagoService historialPagoService = new HistorialPagoService();
            historialPagoService.insertar(historialPagoRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{reservacionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistorialPagoReservacion(@PathParam("reservacionId") int reservacionId) {
        try {
            HistorialPagoService historialPagoService = new HistorialPagoService();
            List<HistorialPagoResponse> historialPagoResponses = historialPagoService.getHistorialPagoReservacion(reservacionId)
                    .stream()
                    .map(HistorialPagoResponse::new)
                    .toList();
            return Response.ok(historialPagoResponses).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
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