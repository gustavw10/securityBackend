/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import dtos.UsersDTO;
import entities.Role;
import entities.User;
import errorhandling.MissingInputException;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 * REST Web Service

 */


@Path("info")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    //added
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    
    @GET
    @Path("all")
    @RolesAllowed({"admin"})
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllUsers() {
        UsersDTO users = FACADE.getAllUsers();
        return GSON.toJson(users);
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed({"user", "admin"})
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello, user. You are logged into '" + thisuser + "'\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("loggedInAs")
    @RolesAllowed({"user", "admin"})
    public String getLoggedInAs() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return GSON.toJson(thisuser);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createuser")
    public String createUser(String user) throws MissingInputException {
       UserDTO u = GSON.fromJson(user, UserDTO.class);
       UserDTO returnUser = FACADE.createUser(u);
       return GSON.toJson(returnUser);
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteuser/{name}")
    @RolesAllowed("admin")
    public String deleteUser(@PathParam("name") String name) {
        UserDTO userDeleted = FACADE.deleteUser(name);
        return GSON.toJson(userDeleted);
    }
}
