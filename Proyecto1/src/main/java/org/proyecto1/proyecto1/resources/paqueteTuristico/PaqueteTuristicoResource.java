package org.proyecto1.proyecto1.resources.paqueteTuristico;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.paqueteTuristico.PaqueteTuristicoRequest;
import org.proyecto1.proyecto1.dtos.paqueteTuristico.PaqueteTuristicoResponse;
import org.proyecto1.proyecto1.dtos.paqueteTuristico.PaqueteTuristicoUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.services.paqueteTuristico.PaqueteTuristicoService;

import java.sql.SQLException;
import java.util.List;

@Path("/paquete-turistico")
public class PaqueteTuristicoResource {

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPaqueteTuristico(PaqueteTuristicoRequest paqueteTuristicoRequest) {
        try {
            PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
            paqueteTuristicoService.insert(paqueteTuristicoRequest);
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
    public Response getAllPaqueteTuristico() {
        try {
            PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
            List<PaqueteTuristicoResponse> paquetesTuristicos = paqueteTuristicoService.getAllPaqueteTuristico()
                    .stream()
                    .map(PaqueteTuristicoResponse::new)
                    .toList();
            return Response.ok(paquetesTuristicos).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{parameter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaqueteTuristico(@PathParam("parameter") String parameter) {
        PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
        try {
            int id = Integer.parseInt(parameter);
            return Response.ok(new PaqueteTuristicoResponse(paqueteTuristicoService.getById(id))).build();
        } catch (NumberFormatException e) {
            return getByCoincidence(parameter, paqueteTuristicoService);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("-/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePaqueteTuristico(PaqueteTuristicoUpdate paqueteTuristicoUpdate) {
        try {
            PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
            paqueteTuristicoService.update(paqueteTuristicoUpdate);
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
    public Response deletePaqueteTuristico(@PathParam("id") int id) {
        try {
            PaqueteTuristicoService paqueteTuristicoService = new PaqueteTuristicoService();
            paqueteTuristicoService.delete(id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response getByCoincidence(String parameter, PaqueteTuristicoService paqueteTuristicoService) {
        try {
            List<PaqueteTuristicoResponse> paqueteTuristicoResponses = paqueteTuristicoService.getByCoincidence(parameter)
                    .stream()
                    .map(PaqueteTuristicoResponse::new)
                    .toList();
            return Response.ok(paqueteTuristicoResponses).build();
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