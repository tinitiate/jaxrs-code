package com.tinitiate.jersey.rs;

import javax.servlet.ServletContext;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.*;


// NOTES:
// 1. The PATH(URL) to invoke this class, MAKE SURE to append the /rest/ from
//    the **web.xml**
// 2. The "extends Application" enables JAX RS to read the web.xml parameters
@Path("/http_put_xml")
public class JaxRSHTTPPutXML {

    // variable to store path of JSON File
    String TmpFilePath = "";

    // Create XML object
    TiData XMLData = new TiData();

    // CONSTRUCTOR
    // 1. Get resource XML file Path from web.xml,
    //    Located in the PROJECt-NAME > WebContent > WEB-INF > web.xml
    // 2. Get the XML data from the "JAXBContext" and "Unmarshaller"
    //    into the "XMLData" Object.
    public JaxRSHTTPPutXML(@Context ServletContext context) throws JAXBException {

        // Step 1.
        // Read the XML file path from web.xml, Where the 
        TmpFilePath = context.getInitParameter("XMLResource");

        // Step 2.
        // Read XML data into Object "XMLData"
        try {
            // Read the XML file into the file object
            File file = new File(TmpFilePath);
            JAXBContext jaxbObj = JAXBContext.newInstance(TiData.class);
            Unmarshaller jaxbUnmarshaller  = jaxbObj.createUnmarshaller();

            //  Create an Elements
            XMLData = (TiData) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) { e.printStackTrace(); }
    }


    // UpdateXMLData METHOD
    // 1. This method updates elements of the tidata.xml file
    // 2. Use with CuRL or PostMan with the @POST method, Cannot run @POST in browser.
    // 3. The URL path expects the ElementName, ID and the ElementValue 
    // 4. For ELEMENTS where there is NO ID, any ID value will work, But an 
    //    ID value is required.
    // 5. UPDATE "WEBSITE" ELEMENT,
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_put_xml/update_element_by_id/website/<any-value-doesnt-matter>/<new-value>
    //    UPDATE "COURSES > TYPE" ELEMENT,
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_put_xml/update_element_by_id/type/<id number>/<new-value>
    //    UPDATE "COURSES > NAME," ELEMENT,
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_put_xml/update_element_by_id/name/<id number>/<new-value>
    //    UPDATE "SOCIAL" ELEMENT,
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_put_xml/update_element_by_id/social/<id number>/<new-value>
    @PUT
    @Path("/update_element_by_id/{ElementName}/{ID}/{ElementValue}")
    public Response UpdateXMLData( @PathParam("ElementName") String ElementName
                                  ,@PathParam("ID") String ID
                                  ,@PathParam("ElementValue") String ElementValue) {

        // STEP 1.
        // 1. There are FOUR ELEMENTS in the tiData.xml courses, type, name,
        //    website, social
        // 2. Update the XML element by the attribute ID element
        // 3. Website Element doesnt have an ID attribute, so any values passed
        //    in the POST URL for the ID value will be ignored, It is needed 
        //    to pass a dummy value as the URL for this function expects it.
        if (ElementName.equalsIgnoreCase("website")) {

            XMLData.setWebsite(ElementValue);
        }
        else if (ElementName.equalsIgnoreCase("type")) {

            // Create Empty Courses Object
            List<Courses> crs = new ArrayList<Courses>();
            for (Courses c : XMLData.getCourses()) {
                if (c.getId().equalsIgnoreCase(ID)) {
                    // Update "Courses" with Given New Social Value
                    c.setType(ElementValue);
                }
                crs.add(c);
            }
            // Add updated Course Object "c" to XMLData
            XMLData.setCourses(crs);
        }
        else if (ElementName.equalsIgnoreCase("name")) {
            // Create Empty Courses Object
            List<Courses> crs = new ArrayList<Courses>();
            for (Courses c : XMLData.getCourses()) {
                if (c.getId().equalsIgnoreCase(ID)) {
                    // Update "Courses" with Given New Social Value
                    c.setType(ElementValue);
                }
                crs.add(c);
            }
            // Add updated Course Object "c" to XMLData
            XMLData.setCourses(crs);
        }
        else if (ElementName.equalsIgnoreCase("social")) {
            // Create Empty Social Object
            List<Social> soc = new ArrayList<Social>();
            for (Social s: XMLData.getSocial()) {
                if (s.getId().equalsIgnoreCase(ID)) {
                    // Update "Social" with Given New Social Value
                    s.setSocial(ElementValue);
                }
                soc.add(s);
            }
            // Add updated Social Object "c" to XMLData
            XMLData.setSocial(soc);
        }


        // STEP 2.
        // Write the Updated "TiData" XML object back to the tidata.xml file
        try {
            // Create JAXBContext Object for the TiData Class 
            JAXBContext jaxbObj = JAXBContext.newInstance(TiData.class);
            Marshaller marshallerObj = jaxbObj.createMarshaller();  
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Write back to the tidata.xml to the PATH TmpFilePath
            marshallerObj.marshal( XMLData,new FileOutputStream(TmpFilePath));
        } catch (Exception e) { e.printStackTrace(); }

        // Return the result
        return Response.status(200).entity("tidata.xml file updated to path " + TmpFilePath).build();
    }
}
