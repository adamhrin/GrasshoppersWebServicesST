/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

/**
 *
 * @author Adam
 */
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.internal.util.Base64;

/**
 *
 * @author Adam
 * SecurityFilter for BASIC AUTH
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    
    //TOTO BUDE INAK, nech je resource pre vsetky zabezpecene requesty jednotne, napr. /secured
    private static final String SECURED_URL_PREFIX = "secured"; //vsetky url ktore obsahuju resource UserService su authorizovane
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
            if (authHeader != null && authHeader.size() > 0) { //request obsahuje header
                String authToken = authHeader.get(0); //prvy header (mal by byt vzdy len jeden)
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, ""); //odstranime Basic_ z headeru
                String decodedString = Base64.decodeAsString(authToken); //username:password
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                String username = tokenizer.nextToken(); //username
                if (true) {
                    int i = 14;
                }
                String password = tokenizer.nextToken(); //password
                
                // realne tu idem do db a odkontrolujem ci take username a password existuje
                if ("user".equals(username) && "password".equals(password)) { 
                    return; //povol pristup na resource
                }
            }
            Response unauthorizedStatus = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("You cannot access this resource")
                    .build();
            requestContext.abortWith(unauthorizedStatus);
        }
    }
}

