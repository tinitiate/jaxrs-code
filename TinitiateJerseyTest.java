package com.tinitiate.jersey.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/data")  
public class TinitiateJerseyTest {

    @GET
    @Path("/{data}")
    public Response getParam(@PathParam("data") String data) {

        String result = "Tinitiate Test Message : " + data;
        return Response.status(200).entity(result).build();
    }

    @GET
    @Path("/greeting")
    public Response getGreeting() {

        String result = "Welcome to Tinitiate REST DEMO";
        return Response.status(200).entity(result).build();
    }
}