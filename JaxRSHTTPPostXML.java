package com.tinitiate.jersey.rs;

import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.bind.*;


// NOTES:
// 1. The PATH(URL) to invoke this class, MAKE SURE to append the /rest/ from
//    the **web.xml**
// 2. The "extends Application" enables JAX RS to read the web.xml parameters
@Path("/http_post_xml")
public class JaxRSHTTPPostXML {

    // variable to store path of JSON File
    String TmpFilePath = "";

    // Create XML object
    TiData XMLData = new TiData();

    // CONSTRUCTOR
    // 1. Get resource XML file Path from web.xml,
    //    Located in the PROJECt-NAME > WebContent > WEB-INF > web.xml
    // 2. Get the XML data from the "JAXBContext" and "Unmarshaller"
    //    into the "XMLData" Object.
    public JaxRSHTTPPostXML(@Context ServletContext context) throws JAXBException {

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


    // AddCourseXMLData METHOD
    // 1. This method ADDs new values to the Course Element of the tidata.xml file
    // 2. The SubElements in the Course Element are CourseID, CoruseType, CourseName
    // 3. Sample URL to Add a new XML element
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_post_xml/add_course_element/<course id>/<course-type>/<course-name>
    @POST
    @Path("/add_course_element/{CourseID}/{CourseType}/{CourseName}")
    public Response AddCourseXMLData( @PathParam("CourseID") String CourseID
                                     ,@PathParam("CourseType") String CourseType
                                     ,@PathParam("CourseName") String CourseName) {

        // STEP 1.
        // Create a Courses Object
        Courses cNew = new Courses();
        // Add the URL Parameters to the
        cNew.setId(CourseID);
        cNew.setType(CourseType);
        cNew.setName(CourseName);

        // STEP 2.
        // Get Existing Courses from XML
        List<Courses> courses = XMLData.getCourses();

        // STEP 3.
        courses.add(cNew);
        
        
        // STEP 4.
        // Add to the Courses Object to Courses Array
        XMLData.setCourses(courses);

        // STEP 5.
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
        return Response.status(200).entity("New Course Values Added" + " to path " + TmpFilePath).build();
    }

    

    // AddSocialXMLData METHOD
    // 1. This method ADDs new values to the Social Element of the tidata.xml file
    // 2. Sample URL to Add a new XML element
    //    http://localhost:8080/TinitiateJerseyRS/rest/http_post_xml/add_social_element/<social id>/<social-value>
    @POST
    @Path("/add_social_element/{SocialID}/{SocialValue}")
    public Response AddSocialXMLData( @PathParam("SocialID") String SocialID
                                     ,@PathParam("SocialValue") String SocialValue) {

        // STEP 1.
        // Create Social Object and store input values from the URL
        Social sNew =  new Social();
        sNew.setId(SocialID);
        sNew.setSocial(SocialValue);

        // STEP 2.
        // Get Existing Courses from XML
        List<Social> social = XMLData.getSocial();

        // STEP 3.
        // Add the New Social Data
        social.add(sNew);

        // STEP 4.
        // Add to the Social Object to Social List
        XMLData.setSocial(social);

        // STEP 5.
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
        return Response.status(200).entity("New Social Values Added" + " to path " + TmpFilePath).build();
    }
}
