package org.proyecto1.proyecto1.resources.archivo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.proyecto1.proyecto1.services.archivo.ArchivoService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/archivo")
public class ArchivoResource {
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cargaDeArchivo(@FormDataParam("file") InputStream fileInputStream) {
        ArchivoService archivoService = new ArchivoService();
        List<String> resumenErrores = List.of();
        try {
            resumenErrores = archivoService.leerArchivo(fileInputStream);
            if (resumenErrores.isEmpty()) {
                return Response.ok().build();
            }  else {
                return Response.status(Response.Status.BAD_REQUEST).entity(resumenErrores).build();
            }

        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }


    }
}