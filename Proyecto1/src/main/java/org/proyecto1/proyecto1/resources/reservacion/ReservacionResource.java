package org.proyecto1.proyecto1.resources.reservacion;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.reservacion.ReservacionRequest;
import org.proyecto1.proyecto1.dtos.reservacion.ReservacionResponse;
import org.proyecto1.proyecto1.dtos.reservacion.ReservacionUpdate;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;
import org.proyecto1.proyecto1.services.reservacion.ReservacionService;

import java.sql.SQLException;
import java.util.List;

@Path("/reservacion")
public class ReservacionResource {

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newReservacion(ReservacionRequest reservacionRequest) {
        try {
            ReservacionService reservacionService = new ReservacionService();
            reservacionService.insert(reservacionRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservaciones() {
        try {
            ReservacionService reservacionService = new ReservacionService();
            List<ReservacionResponse> reservacionResponses = reservacionService.getAllReservacion()
                    .stream()
                    .map(ReservacionResponse::new)
                    .toList();
            return Response.ok(reservacionResponses).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("/cliente/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservacionesCliente(@PathParam("id") int id) {
        try {
            ReservacionService reservacionService = new ReservacionService();
            List<ReservacionResponse> reservacionResponses = reservacionService.getByClientId(id)
                    .stream()
                    .map(ReservacionResponse::new)
                    .toList();
            return Response.ok(reservacionResponses).build();
        }  catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservacion(@PathParam("id") int id) {
        try {
            ReservacionService reservacionService = new ReservacionService();
            Reservacion reservacion = reservacionService.getById(id);
            return Response.ok(new ReservacionResponse(reservacion)).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateReservacion(ReservacionUpdate reservacionUpdate) {
        try {
            ReservacionService reservacionService = new ReservacionService();
            reservacionService.update(reservacionUpdate);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("/actualizar/cancelar/{id}")
    public Response cancelReservacion(@PathParam("id") int id) {
        try {
            ReservacionService reservacionService = new ReservacionService();
            reservacionService.cancelarReservacion(id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @DELETE
    @Path("/eliminar/{id}")
    public Response deleteReservacion(@PathParam("id") int id) {
        try {
            ReservacionService reservacionService = new ReservacionService();
            reservacionService.delete(id);
            return Response.ok().build();
        } catch (UserDataInvalidException e)  {
            return errorEjecucion(e.getMessage(), 1);
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