package org.proyecto1.proyecto1.resources.usuario;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.usuario.LoginRequest;
import org.proyecto1.proyecto1.dtos.usuario.UsuarioResponse;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.models.usuario.Usuario;
import org.proyecto1.proyecto1.services.usuario.UsuarioService;

import java.sql.SQLException;

@Path("login")
public class LoginResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        UsuarioService usuarioService = new UsuarioService();
        try {
            Usuario usuario = usuarioService.login(loginRequest);
            return Response.ok(new UsuarioResponse(usuario)).build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(),1);
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