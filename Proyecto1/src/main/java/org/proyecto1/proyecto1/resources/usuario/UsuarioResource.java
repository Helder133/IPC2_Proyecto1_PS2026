package org.proyecto1.proyecto1.resources.usuario;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.usuario.UsuarioRequest;
import org.proyecto1.proyecto1.dtos.usuario.UsuarioResponse;
import org.proyecto1.proyecto1.dtos.usuario.UsuarioUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.services.usuario.UsuarioService;

import java.sql.SQLException;
import java.util.List;

@Path("usuario")
public class UsuarioResource {

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertUser(UsuarioRequest usuarioRequest) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.insert(usuarioRequest);
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
    public Response getAllUsers() {
        try {
            UsuarioService usuarioService = new UsuarioService();
            List<UsuarioResponse> getAllUsers = usuarioService.getAllUsers()
                    .stream()
                    .map(UsuarioResponse::new).
                    toList();
            return Response.ok(getAllUsers).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @GET
    @Path("{parameter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByParameter(@PathParam("parameter") String parameter) {
        UsuarioService usuarioService = new UsuarioService();
        try {
            int id = Integer.parseInt(parameter);
            return Response.ok(new UsuarioResponse(usuarioService.getById(id))).build();
        } catch (NumberFormatException e) {
            return getByCoincidence(parameter, usuarioService);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(), 1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response getByCoincidence(String parameter, UsuarioService usuarioService) {
        try {
            List<UsuarioResponse> getByCoincidence = usuarioService.getByCoincidence(parameter)
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
            return Response.ok(getByCoincidence).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UsuarioUpdate usuarioUpdate) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.update(usuarioUpdate);
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
    @Path("/actualizar/estado/{id}")
    public Response updateUserEstado(@PathParam("id") int usuarioId) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.updateEstado(usuarioId);
            return Response.ok().build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    @DELETE
    @Path("/eliminar/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.delete(id);
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