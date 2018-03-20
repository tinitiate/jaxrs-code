package com.tinitiate.jersey.rs;

// Required Libraries
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.servlet.ServletContext;

//NOTES:
//1. The PATH(URL) to invoke this class, MAKE SURE to append the /rest/ from 
// the **web.xml**
//2. The "extends Application" enables JAX RS to read the web.xml parameters
@Path("/http_get_json")
public class JaxRSHTTPGetJSON extends Application {

    // variable to store path of JSON File
    String TmpFilePath;

    // JSON Object
    JsonObject TiObjJSON;


    // 1. CONSTUCTOR
    // 2. Parameter is the "@Context" of Type ServletContext
    // 3. From the context get the context-param from the web.xml
    // 4. We have used the "JSONResource" as context-name, that has the path 
    //    to the resource "JSON" file 
    public JaxRSHTTPGetJSON(@Context ServletContext context) {
        
        // Step 1.
        // Read the JSON file path from web.xml
        TmpFilePath = context.getInitParameter("JSONResource");
        
        // Step 2.
        // Read the JSON file into the JSON Object 
        try {
            InputStream fis = new FileInputStream(TmpFilePath);

            JsonReader reader = Json.createReader(fis);
            TiObjJSON = reader.readObject();
            reader.close();

        } catch (Exception e) { e.printStackTrace(); }
    }


    // Retrieves Data from the JSON file
    // Use the URL:
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_json/get_all_json_data
    @GET
    @Path("/get_all_json_data")
    public Response GetAllJSONData() {

        String getData = "";
        getData = TiObjJSON.toString();

        // Return the result
        return Response.status(200).entity(getData).build();
    }


    // Retrieves PARTIAL Data from the TiJSON, for the element website
    // Use the URL (works in browser):
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_json/get_ti_json_website
    @GET
    @Path("/get_ti_json_website")
    public Response GetPartialJSONWebsite() {

        String getData = "";
        getData = TiObjJSON.getString("website");

        // Return the result
        return Response.status(200).entity(getData).build();
    }


    // Retrieves PARTIAL Data from the TiJSON, for the element "Courses"
    // Use the URL (works in browser):
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_json/get_ti_json_courses
    @GET
    @Path("/get_ti_json_courses")
    public Response GetPartialJSONCourses() {

        String getData = "";
        JsonObject coursesObject = TiObjJSON.getJsonObject("courses");
        getData = coursesObject.getString("scripting") + "\n" + 
                  coursesObject.getString("database");

        // Return the result
        return Response.status(200).entity(getData).build();
    }


    // Retrieves PARTIAL Data from the TiJSON, for the element "social"
    // Use the URL (works in browser):
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_json/get_ti_json_social
    @GET
    @Path("/get_ti_json_social")
    public Response GetPartialJSONSocial() {

        String getData = "Social Media: ";
        JsonArray SocialMediaArry = TiObjJSON.getJsonArray("social");

        for (JsonValue SMjsonValue : SocialMediaArry) {
            getData += SMjsonValue.toString() + " ";
        }

        // Return the result
        return Response.status(200).entity(getData).build();
    }
}
