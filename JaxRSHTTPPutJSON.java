package com.tinitiate.jersey.rs;

// Required Libraries
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import javax.servlet.ServletContext;


//NOTES:
//1. The PATH(URL) to invoke this class, MAKE SURE to append the /rest/ from 
//   the **web.xml**
//2. The "extends Application" enables JAX RS to read the web.xml parameters
@Path("/http_put_json")
public class JaxRSHTTPPutJSON extends Application {

    // variable to store path of JSON File
    String TmpFilePath;

    // JSON File Object
    JSONObject TiJSONObj;

    // JSON Root level Object
    JSONObject CoursesJSONObj;


    // 1. CONSTUCTOR
    // 2. Parameter is the "@Context" of Type ServletContext
    // 3. From the context get the context-param from the web.xml
    // 4. We have used the "JSONResource" as context-name, that has the path 
    //    to the resource "JSON" file 
    public JaxRSHTTPPutJSON(@Context ServletContext context) {

        // Step 1.
        // Read the JSON file path from web.xml
        TmpFilePath = context.getInitParameter("JSONResource");

        // Step 2.
        // Read the JSON file into the JSON Object into TiObjJSON
        JSONParser parser = new JSONParser();
        try {

            Object object = parser.parse(new FileReader(TmpFilePath));
            TiJSONObj = (JSONObject)object;
           
        } catch (Exception e) { e.printStackTrace(); }

        // Step 3.
        // Read the Scripting JSON element of TiObjJSON Object into CoursesObjJSON
        CoursesJSONObj = (JSONObject)TiJSONObj.get("courses");
    }


    // This method writes to the JSON file
    @PUT
    @Path("/update_json/{ElementName}/{ElementValue}")
    public Response UpdateJSONFile( @PathParam("ElementName") String ElementName
                                   ,@PathParam("ElementValue") String ElementValue)
    {
        // Step 1.
        // Check Input JSON outer/root level element
        // Elements in JSON are courses, database, scripting, website, social
        if (ElementName.equalsIgnoreCase("database")) {

            CoursesJSONObj.put("database", ElementValue);
        }
        else if (ElementName.equals("scripting")) {

            CoursesJSONObj.put("scripting", ElementValue);
        }
        else if (ElementName.equals("website")) {

            TiJSONObj.put("website", ElementValue);
        }
        else if (ElementName.equals("social")) {

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(ElementValue);

            TiJSONObj.put("social", ElementValue);
        }


        // Step 2.
        // Read the JSON file into the JSON Object
        try {
            FileWriter fileWriter = new FileWriter(TmpFilePath);

            fileWriter.write(TiJSONObj.toJSONString());
            fileWriter.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the result
        return Response.status(200).entity(TmpFilePath + " file updated !").build();

    }

// USE A REST TEST TOOL LIKE POSTMAN , DO NOT TEST THE FOLLOWING IN A BROWSER
// NOTE: 
// 1. After First "http://localhost:8080/TinitiateJerseyRS/rest/http_put_json/update_json"
//    There are TWO parameters 
//    (1) JSON Element Name
//    (2) Element Value
// 2. The below statements are used to update various elements
//    Use the URLs as PUT in the REST Test Tool PostMan
// http://localhost:8080/TinitiateJerseyRS/rest/http_put_json/update_json/database/mysql
// http://localhost:8080/TinitiateJerseyRS/rest/http_put_json/update_json/scripting/php
// http://localhost:8080/TinitiateJerseyRS/rest/http_put_json/update_json/website/www.tinitiate.com
// http://localhost:8080/TinitiateJerseyRS/rest/http_put_json/update_json/website/youtube
// 3. Compare the JSON File Before and After running the URLs or change the 
//    values to the Element Values to see the change in the JSON file    
}
