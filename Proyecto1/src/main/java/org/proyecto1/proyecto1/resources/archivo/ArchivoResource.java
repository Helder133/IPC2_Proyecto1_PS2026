package org.proyecto1.proyecto1.resources.archivo;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.proyecto1.proyecto1.services.archivo.ArchivoService;

import java.io.InputStream;

@Path("/archivo")
public class ArchivoResource {
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cargaDeArchivo(@FormDataParam("file")InputStream fileInputStream,
                                   @FormDataParam("file")FormDataContentDisposition fileMetaData) {
        ArchivoService archivoService = new ArchivoService();
        try {
            archivoService.leerArchivo(fileInputStream);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }


    }
}