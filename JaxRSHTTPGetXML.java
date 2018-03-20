package com.tinitiate.jersey.rs;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.io.File;
import javax.xml.bind.*;

//NOTES:
// 1. The PATH(URL) to invoke this class, MAKE SURE to append the /rest/ from 
//    the **web.xml**
// 2. The "extends Application" enables JAX RS to read the web.xml parameters
@Path("/http_get_xml")
public class JaxRSHTTPGetXML extends Application {

    // variable to store path of JSON File
    String TmpFilePath = "";

    // Create XML object
    TiData XMLData = new TiData();
    // XML Parts
    String XMLwebsite = "";
    String XMLcourse = "";
    String XMLsocial = "";

    // Constructor
    // 1. Get resource XML file Path from web.xml
    // 2. Read XML data using the "JAXBContext" and "Unmarshaller" 
    public JaxRSHTTPGetXML(@Context ServletContext context) throws JAXBException {

        // Step 1.
        // Read the XML file path from web.xml
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


    // Retrieves entire XML data from the Tidata.xml file
    // Use the URL (works in browser):
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_xml/get_xml_website_data
    @GET
    @Path("/get_xml_website_data")
    public Response GetXMLWebsiteData() {

        String gXMLData = "";
        gXMLData = gXMLData + "\n" + "XML DATA ELEMENT - website: " + "<br>";
        gXMLData = gXMLData + XMLData.getWebsite();
        XMLwebsite = gXMLData;

        // Return the result
        return Response.status(200).entity(gXMLData).build();
    }    


    // Retrieves entire XML data from the Tidata.xml file
    // Use the URL (works in browser):
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_xml/get_xml_course_data
    @GET
    @Path("/get_xml_course_data")
    public Response GetXMLCourseData() {

        String gXMLData = "";
        gXMLData = gXMLData + "\n" + "XML DATA ELEMENT - course: " + "<br>";
        for(Courses course : XMLData.getCourses()) {

            gXMLData = gXMLData + course.getId() + " ";
            gXMLData = gXMLData + course.getType() + " ";
            gXMLData = gXMLData + course.getName() + "<br>";
        }
        XMLcourse = gXMLData;
        
        // Return the result
        return Response.status(200).entity(gXMLData).build();
    }    


    // Retrieves entire XML data from the Tidata.xml file
    // Use the URL (works in browser):
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_xml/get_xml_social_data
    @GET
    @Path("/get_xml_social_data")
    public Response GetXMLSocialData() {

        String gXMLData = "";
        gXMLData = "XML DATA ELEMENT - social: " + "<br>";
        for(Social social : XMLData.getSocial()) {
            gXMLData = gXMLData + social.getId() + " ";
            gXMLData = gXMLData + social.getSocial() + "<br>";
        }
        XMLsocial = gXMLData;
                
        // Return the result
        return Response.status(200).entity(gXMLData).build();
    }    


    // Retrieves entire XML data from the Tidata.xml file
    // Use the URL (works in browser):
    // http://localhost:8080/TinitiateJerseyRS/rest/http_get_xml/get_all_xml_data
    @GET
    @Path("/get_all_xml_data")
    public Response GetAllXMLData() {

        String gXMLData = XMLwebsite + XMLcourse + XMLsocial;

        // Return the result
        return Response.status(200).entity(gXMLData).build();
    }
}
