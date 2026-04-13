package org.proyecto1.proyecto1.resources.reporte;
import jakarta.ws.rs.*;

@Path("/hello-world")
public class ReporteResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}