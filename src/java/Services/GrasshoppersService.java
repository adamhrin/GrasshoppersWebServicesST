/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

/**
 *
 * @author Adam
 */
import CustomException.CustomException;
import Dao.BrigadesDao;
import Dao.CategoriesDao;
import Dao.TrainingsDao;
import Dao.UserDao;
import Models.Brigade;
import Models.Category;
import Dao.ComponentsDao;
import Dao.LocationsDao;
import Models.Training;
import Models.User;
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET; 
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;  
import javax.ws.rs.core.Response;

//@Path("/secured/GrasshoppersService")
@Path("GrasshoppersService")
public class GrasshoppersService {
    TrainingsDao trainingsDao = new TrainingsDao();  
    BrigadesDao brigadesDao = new BrigadesDao();
    CategoriesDao categoriesDao = new CategoriesDao();
    LocationsDao locationsDao = new LocationsDao();
    
    @GET 
    @Path("/trainings") 
    @Produces(MediaType.APPLICATION_JSON)
    // pribudne tu id playera ako parameter
    public /*List<Training>*/ Response getTrainings(){ 
        Response response = null;
        try {
            response = Response.ok().entity(trainingsDao.getTrainings()).build();
        } catch (CustomException ex) {
            Logger.getLogger(GrasshoppersService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    } 
    
    @GET 
    @Path("/brigades") 
    @Produces(MediaType.APPLICATION_JSON)
    // pribudne tu id playera ako parameter
    public /*List<Brigade>*/ Response getBrigades(){ 
        Response response = null;
        try {
            response = Response.ok().entity(brigadesDao.getBrigades()).build();
        } catch (CustomException ex) {
            Logger.getLogger(GrasshoppersService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    } 
    
    @GET 
    @Path("/categories") 
    @Produces(MediaType.APPLICATION_JSON)
    public /*List<Category>*/ Response getCategories(){ 
        Response response = null;
        try {
            response = Response.ok().entity(categoriesDao.getCategories()).build();
        } catch (CustomException ex) {
            Logger.getLogger(GrasshoppersService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    @GET 
    @Path("/locations") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocations(){ 
        Response response = null;
        try {
            response = Response.ok().entity(locationsDao.getLocations()).build();
        } catch (CustomException ex) {
            Logger.getLogger(GrasshoppersService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
//    
//    @GET 
//    @Path("/brigades") 
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Brigade> getBrigades(){ 
//        return brigadesDao.getBrigades();
//    }
//    
//    @GET 
//    @Path("/brigades") 
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Brigade> getBrigades(){ 
//        return brigadesDao.getBrigades();
//    }
    
    @POST
    @Path("/training") 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertTraining(String training){ 
        return training;
    }
    
    
}
