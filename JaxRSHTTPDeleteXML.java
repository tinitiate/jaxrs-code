package com.tinitiate.jersey.rs;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.bind.*;


// NOTES:
// 1. The PATH(URL) to invoke this class, MAKE SURE to append the /rest/ from
//    the **web.xml**
// 2. The "extends Application" enables JAX RS to read the web.xml parameters
@Path("/http_delete_xml")
public class JaxRSHTTPDeleteXML {

    // variable to store path of JSON File
    String TmpFilePath = "";

    // Create XML object
    TiData XMLData = new TiData();

    // CONSTRUCTOR
    // 1. Get resource XML file Path from web.xml,
    //    Located in the PROJECt-NAME > WebContent > WEB-INF > web.xml
    // 2. Get the XML data from the "JAXBContext" and "Unmarshaller"
    //    into the "XMLData" Object.
    public JaxRSHTTPDeleteXML(@Context ServletContext context) throws JAXBException {

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


    // DeleteCourseXMLData METHOD
    // 1. This method ADDs new values to the Course Element of the tidata.xml file
    // 2. The SubElements in the Course Element are CourseID, CoruseType, CourseName
    // 3. Sample URL to Add a new XML element
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_delete_xml/delete_course_element/<course id>
    @DELETE
    @Path("/delete_course_element/{CourseID}")
    public Response DeleteCourseXMLData( @PathParam("CourseID") String CourseID) {

        // UPDATE Courses Array element by deleting the Input CourseID from 
        // the nested Courses XML element

        // STEP 1.
        // Loop through the XMLData Courses Element Values
        for (Courses c: XMLData.getCourses()) {
            if(c.getId().equals(CourseID)) {
                XMLData.getCourses().remove(c);
            }
        }

        // STEP 2.
        // Write to XML File
        try {
            // Create JAXBContext Object for the TiData Class 
            JAXBContext jaxbObj = JAXBContext.newInstance(TiData.class);
            Marshaller marshallerObj = jaxbObj.createMarshaller();  
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Write back to the tidata.xml to the PATH TmpFilePath
            marshallerObj.marshal( XMLData,new FileOutputStream(TmpFilePath));
        } catch (Exception e) { e.printStackTrace(); }

        // Return the result
        return Response.status(200).entity("Course "+ CourseID +" deleted from file: " + TmpFilePath).build();
    }


    // DeleteSocialXMLData METHOD
    // 1. This method ADDs new values to the Social Element of the tidata.xml file
    // 2. Sample URL to Add a new XML element
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_delete_xml/delete_social_element/<social id>
    @DELETE
    @Path("/delete_social_element/{SocialID}")
    public Response DeleteSocialXMLData( @PathParam("SocialID") String SocialID) {

        // STEP 1.
        // Loop through the XMLData Social Element Values
        // and remove the ID that is supplied to delete social element 
        for (Social s : XMLData.getSocial()) {
            if(s.getId().equals(SocialID)) {
                XMLData.getSocial().remove(s);
            }
        }

        // STEP 2.
        // Write to XML File
        try {
            // Create JAXBContext Object for the TiData Class 
            JAXBContext jaxbObj = JAXBContext.newInstance(TiData.class);
            Marshaller marshallerObj = jaxbObj.createMarshaller();  
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Write back to the tidata.xml to the PATH TmpFilePath
            marshallerObj.marshal( XMLData,new FileOutputStream(TmpFilePath));
        } catch (Exception e) { e.printStackTrace(); }

        // Return the result
        return Response.status(200).entity("Social Element at ID: " + SocialID + " deleted.").build();
    }
}
