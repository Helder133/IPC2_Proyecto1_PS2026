package org.proyecto1.proyecto1.resources.proveedor;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.proyecto1.proyecto1.dtos.proveedor.ProveedorRequest;
import org.proyecto1.proyecto1.dtos.proveedor.ProveedorResponse;
import org.proyecto1.proyecto1.dtos.proveedor.ProveedorUpdate;
import org.proyecto1.proyecto1.exceptions.EntityAlreadyExistsException;
import org.proyecto1.proyecto1.exceptions.UserDataInvalidException;
import org.proyecto1.proyecto1.services.proveedor.ProveedorService;

import java.sql.SQLException;
import java.util.List;

@Path("/proveedor")
public class ProveedorResource {

    @POST
    @Path("/insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearProveedor(ProveedorRequest proveedorRequest) {
        try {
            ProveedorService proveedorService = new ProveedorService();
            proveedorService.insert(proveedorRequest);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(),1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(),2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProveedor() {
        try {
            ProveedorService proveedorService = new ProveedorService();
            List<ProveedorResponse> proveedores = proveedorService.getAllProveedor()
                    .stream()
                    .map(ProveedorResponse::new)
                    .toList();
            return Response.ok(proveedores).build();
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @GET
    @Path("{parameter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProveedor(@PathParam("parameter") String parameter) {
        ProveedorService proveedorService = new ProveedorService();
        try {
            int id = Integer.parseInt(parameter);
            return Response.ok(new ProveedorResponse(proveedorService.getById(id))).build();
        } catch (NumberFormatException e) {
            return getByCoincidence(parameter, proveedorService);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(),1);
        }
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProveedor(ProveedorUpdate proveedorUpdate) {
        try {
            ProveedorService proveedorService = new ProveedorService();
            proveedorService.update(proveedorUpdate);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(),1);
        } catch (EntityAlreadyExistsException e) {
            return errorEjecucion(e.getMessage(),2);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(),3);
        }
    }

    @DELETE
    @Path("/eliminar/{id}")
    public Response deleteProveedor(@PathParam("id") int id) {
        try {
            ProveedorService proveedorService = new ProveedorService();
            proveedorService.delete(id);
            return Response.ok().build();
        } catch (UserDataInvalidException e) {
            return errorEjecucion(e.getMessage(),1);
        } catch (SQLException e) {
            return errorEjecucion(e.getMessage(), 3);
        }
    }

    private Response getByCoincidence(String parameter, ProveedorService proveedorService) {
        try {
            List<ProveedorResponse> proveedores = proveedorService.getByCoincidence(parameter)
                    .stream()
                    .map(ProveedorResponse::new)
                    .toList();
            return Response.ok(proveedores).build();
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