package com.tinitiate.jersey.rs;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/")
public class JaxRSFileDownload {

    // Files with path on Server Side for Download
    static final String image_file_path = "c:\\Personal\\tipic.png";
    static final String text_file_path = "c:\\Personal\\tidata.json";
    static final String pdf_file_path = "c:\\Personal\\tidata.pdf";


    // Method to Download Image file
    @GET
    @Path("/download-image")
    @Produces("image/png") // 
    public Response DownloadImageFile() {

        File file = new File(image_file_path);
        ResponseBuilder response = Response.ok((Object) file);

        response.header( "Content-Disposition"
                        ,"attachment; filename=tipic.png");

        return response.build();
    }


    // Method to Download Text file
    @GET
    @Path("/download-text")
    @Produces("text/plain")
    public Response DownloadTextFile() {

        File file = new File(text_file_path);
        ResponseBuilder response = Response.ok((Object) file);

        response.header( "Content-Disposition"
                        ,"attachment; filename=tidata.json");

        return response.build();
    }


    // Method to Download PDF file
    @GET
    @Path("/download-pdf")
    @Produces("application/pdf")
    public Response DownloadPDFFile() {

        File file = new File(pdf_file_path);
        ResponseBuilder response = Response.ok((Object) file);

        response.header( "Content-Disposition"
                        ,"attachment; filename=tidata.pdf");

        return response.build();
    }
}
