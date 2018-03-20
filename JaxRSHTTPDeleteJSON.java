package com.tinitiate.jersey.rs;

// JaxRSHTTPDeleteJSON

//Required Libraries
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import javax.servlet.ServletContext;


//NOTES:
//1. The PATH(URL) to invoke this class, MAKE SURE to append the /rest/ from 
//the **web.xml**
//2. The "extends Application" enables JAX RS to read the web.xml parameters
@Path("/http_delete_json")
public class JaxRSHTTPDeleteJSON extends Application {

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
    public JaxRSHTTPDeleteJSON(@Context ServletContext context) {
    
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
    @DELETE
    @Path("/element/{ElementName}")
    public Response UpdateJSONFile(@PathParam("ElementName") String ElementName)
    {
       // Step 1.
       // Check Input JSON outer/root level element
       // Elements in JSON are courses, database, scripting, website, social
       if (ElementName.equalsIgnoreCase("database")) {
    
           CoursesJSONObj.remove(ElementName);
       }
       else if (ElementName.equals("website")) {
    
           TiJSONObj.remove(ElementName);
       }
       else if (ElementName.equals("social")) {
    
           TiJSONObj.remove(ElementName);
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

//USE A REST TEST TOOL LIKE POSTMAN , DO NOT TEST THE FOLLOWING IN A BROWSER
//NOTE: 
// 1. After First "http://localhost:8080/TinitiateJerseyRS/rest/http_delete_json/update_json"
//   There are TWO parameters 
//    (1) JSON Element Name
//    (2) Element Value
// 2. The below statements are used to update various elements
//    Use the URLs as POST in the REST Test Tool PostMan
//
//    http://localhost:8080/TinitiateJerseyRS/rest/http_delete_json/element/database
//    http://localhost:8080/TinitiateJerseyRS/rest/http_delete_json/element/website
//    http://localhost:8080/TinitiateJerseyRS/rest/http_delete_json/element/social
//
// 3. Compare the JSON File Before and After running the URLs or change the 
//    values to the Element Values to see their removal in the JSON file    
}
