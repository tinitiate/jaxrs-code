
package com.tinitiate.jersey.rs;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.ClientConfig;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class JaxRSHTTPClientGET {

    // Base URL where to apply the GET request
    static String GETTestURL = "http://localhost:8080/TinitiateJerseyRS/rest/http_get_json";


    // Main Program that calls the GET URLs
    public static void main(String[] args) {
        
        
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget WSservice = client.target(getBaseURI());


        // Call a rest GET service
        System.out.println(WSservice.path("rest").path("get_ti_json_courses").request().accept(MediaType.TEXT_PLAIN).get(String.class));
    }


    // Return The URL to lookup 
    private static URI getBaseURI() {  

        // The server runs here  
      return UriBuilder.fromUri(GETTestURL).build();  
    }    
}
