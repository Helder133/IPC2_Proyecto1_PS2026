package org.proyecto1.proyecto1.resources.servicioPaquete;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.servicioPaquete.ServicioPaqueteRequest;
import org.proyecto1.proyecto1.dtos.servicioPaquete.ServicioPaqueteResponse;
import org.proyecto1.proyecto1.dtos.servicioPaquete.ServicioPaqueteUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.services.servicioPaquete.ServicioPaqueteService;

import java.sql.SQLException;
import java.util.List;

@Path("/servicio-paquete")
public class ServicioPaqueteResource {

    @POST
    @Path("insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newServicioPaquete(ServicioPaqueteRequest servicioPaqueteRequest) {
        try {
            ServicioPaqueteService servicioPaqueteService = new ServicioPaqueteService();
            servicioPaqueteService.insert(servicioPaqueteRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            ServicioPaqueteService servicioPaqueteService = new ServicioPaqueteService();
            List<ServicioPaqueteResponse> servicioPaqueteResponses = servicioPaqueteService.getAllServicioPaquete()
                    .stream()
                    .map(ServicioPaqueteResponse::new)
                    .toList();
            return Response.ok(servicioPaqueteResponses).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{paquete_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByPaqueteId(@PathParam("paquete_id") int paquete_id) {
        try {
            ServicioPaqueteService servicioPaqueteService = new ServicioPaqueteService();
            List<ServicioPaqueteResponse> servicioPaqueteResponses = servicioPaqueteService.getByPaqueteId(paquete_id)
                    .stream()
                    .map(ServicioPaqueteResponse::new)
                    .toList();
            return Response.ok(servicioPaqueteResponses).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

//    revisar que esta dando error a la hora de intentar hacer una actualizacion
    @PUT
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateServicioPaquete(ServicioPaqueteUpdate servicioPaqueteUpdate) {
        try {
            ServicioPaqueteService servicioPaqueteService = new ServicioPaqueteService();
            servicioPaqueteService.update(servicioPaqueteUpdate);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @DELETE
    @Path("eliminar/{paquete_id}/{proveedor_id}")
    public Response deleteServicioPaquete(@PathParam("paquete_id") int paquete_id, @PathParam("proveedor_id") int proveedor_id) {
        try {
            ServicioPaqueteService servicioPaqueteService = new ServicioPaqueteService();
            servicioPaqueteService.delete(paquete_id, proveedor_id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
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