package org.proyecto1.proyecto1.resources.cliente;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.cliente.ClienteRequest;
import org.proyecto1.proyecto1.dtos.cliente.ClienteResponse;
import org.proyecto1.proyecto1.dtos.cliente.ClienteUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.services.cliente.ClienteService;

import java.sql.SQLException;
import java.util.List;

@Path("cliente")
public class ClienteResource {

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertCliente(ClienteRequest clienteRequest) {
        try {
            ClienteService clienteService = new ClienteService();
            clienteService.insertClient(clienteRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(), 2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCliente(ClienteUpdate clienteUpdate) {
        try {
            ClienteService clienteService = new ClienteService();
            clienteService.updateClient(clienteUpdate);
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
    public Response deleteCliente(@PathParam("id") int id) {
        try {
            ClienteService clienteService = new ClienteService();
            clienteService.deleteClient(id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClient() {
        try {
            ClienteService clienteService = new ClienteService();
            List<ClienteResponse> getAllClients = clienteService.getAllClients()
                    .stream()
                    .map(ClienteResponse::new)
                    .toList();
            return Response.ok(getAllClients).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{parameter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByParameter(@PathParam("parameter") String parameter) {
        ClienteService clienteService = new ClienteService();
        try {
            int id = Integer.parseInt(parameter);
            return Response.ok(new ClienteResponse(clienteService.getClientById(id))).build();
        } catch (NumberFormatException e) {
            return getByCoincidence(parameter, clienteService);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response getByCoincidence(String parameter, ClienteService clienteService) {
        try {
            List<ClienteResponse> getByCoincidence = clienteService.getClientsByCoincidence(parameter)
                    .stream()
                    .map(ClienteResponse::new)
                    .toList();
            return Response.ok(getByCoincidence).build();
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