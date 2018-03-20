package com.tinitiate.jersey.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/response")
public class JAXRSResponseFormats {

    // Generate Plain Text response using TEXT_PLAIN
    @GET
    @Path("/text")
    @Produces(MediaType.TEXT_PLAIN)
    public Response responsePlainText() {
 
        String result = "Tinitiate Jersey RS Plain Text Response.";
        return Response.status(200).entity(result).build();
    }

    // Generate XML response using TEXT_XML
    @GET
    @Path("/xml")
    @Produces(MediaType.TEXT_XML)
    public Response responseXML() {
        String result = "<?xml version=\"1.0\"?>" + "<message> Tinitiate Jersey RS XML Response" + "</message>";
        return Response.status(200).entity(result).build();
    }

    // Generate JSON response using APPLICATION_JSON
    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response responseJSON() {
        String result = "{\"Tinitiate Jersey RS JSON Response\"}";
        return Response.status(200).entity(result).build();
    }

    // Generate HTML response using TEXT_HTML
    @GET
    @Path("/html")
    @Produces(MediaType.TEXT_HTML)
    public Response responseHTML() {
        String result = "<html> " + "<title>" + "Tinitiate" + "</title>"
                        + "<body><h1>" + "Tinitiate Jersey RS HTML Response" + "</body></h1>" + "</html> ";
        return Response.status(200).entity(result).build();
    }

}