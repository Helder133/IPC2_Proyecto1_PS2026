package org.proyecto1.proyecto1.resources.destino;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.destino.DestinoRequest;
import org.proyecto1.proyecto1.dtos.destino.DestinoResponse;
import org.proyecto1.proyecto1.dtos.destino.DestinoUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.services.destino.DestinoService;

import java.sql.SQLException;
import java.util.List;

@Path("/destino")
public class DestinoResource {

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDestino(DestinoRequest destinoRequest) {
        try {
            DestinoService destinoService = new DestinoService();
            destinoService.insert(destinoRequest);
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
    public Response getDestinos() {
        try {
            DestinoService destinoService = new DestinoService();
            List<DestinoResponse> destinos = destinoService.getAllDestino()
                    .stream()
                    .map(DestinoResponse::new)
                    .toList();
            return Response.ok(destinos).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{parameter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDestino(@PathParam("parameter") String parameter) {
        DestinoService destinoService = new DestinoService();
        try {
            int parameterId = Integer.parseInt(parameter);
            return Response.ok(new DestinoResponse(destinoService.getDestinoById(parameterId))).build();
        } catch (NumberFormatException e) {
            return getByCoincidence(parameter, destinoService);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        }
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDestino(DestinoUpdate destinoUpdate) {
        try {
            DestinoService destinoService = new DestinoService();
            destinoService.update(destinoUpdate);
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
    @Path("/eliminar/{id}")
    public Response deleteDestino(@PathParam("id") int id) {
        try {
            DestinoService destinoService = new DestinoService();
            destinoService.delete(id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response getByCoincidence(String parameter, DestinoService destinoService) {
        try {
            List<DestinoResponse> destinos = destinoService.getByCoincidence(parameter)
                    .stream()
                    .map(DestinoResponse::new)
                    .toList();
            return Response.ok(destinos).build();
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