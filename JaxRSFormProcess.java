package com.tinitiate.jersey.rs;

import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/form-process")
public class JaxRSFormProcess {

    // 1. Read the Form Elements by name as they appear in tinitiate-form.html file
    // 2. Print all form elements to the "form-elements" webpage, using the
    //    @FormParam of this method
    @POST
    @Path("/form-elements")
    public Response responseMsg(@FormParam("textVal") String textVal
                               ,@FormParam("country") String country
                               ,@FormParam("food") List<String> food
                               ,@FormParam("languages") String languages
                               ,@FormParam("database") List<String> database) {

        // STEP 1.
        // Read the List<String> or Multiple values to a Sting
        String sFood = " ";
        for(String s: food) {
            sFood = sFood + " " + s;
        }

        String sDatabase = " ";
        for(String s: database) {
            sDatabase = sDatabase + " " + s;
        }


        // STEP 2.
        // Append all varibles into a single string
        String PageOutput = "Text Box value: " + textVal + "<br><hr>" +
                            "Radio Button value: " + country + "<br><hr>" +
                            "Check Box: " + sFood + "<br><hr>" +
                            "Drop down select " + languages + "<br><hr>" +
                            "Multiple select: " + sDatabase;

        // STEP 3.
        // Return output as Response Object
        return Response.status(200).entity(PageOutput).build();

    }
}
