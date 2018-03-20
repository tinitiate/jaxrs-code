package com.tinitiate.jersey.rs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;  
import org.glassfish.jersey.media.multipart.FormDataParam;  

@Path("/")
public class JaxRSFileUpload {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile( @FormDataParam("UploadFile") InputStream UploadFileInputStream
                               ,@FormDataParam("UploadFile") FormDataContentDisposition UploadFileInfo) {

        String ServerUploadsFolder = "c://personal//uploads//" + UploadFileInfo.getFileName();

        // save it
        try {
            FileOutputStream out = new FileOutputStream(new File(ServerUploadsFolder));
            byte[] bytes = new byte[1024];
            int bytesRead = 0;

            out = new FileOutputStream(new File(ServerUploadsFolder));

            while ((bytesRead = UploadFileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, bytesRead);
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String output = "File uploaded to serverpath: " + ServerUploadsFolder;

        return Response.status(200).entity(output).build();
    }
}
