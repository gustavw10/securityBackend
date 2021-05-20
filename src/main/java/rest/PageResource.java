/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PageDTO;
import dtos.PagesDTO;
import dtos.UserDTO;
import dtos.UsersDTO;
import entities.Page;
import entities.Request;
import entities.Role;
import entities.User;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.PageFacade;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Gustav
 */
@Path("page")
public class PageResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final PageFacade PAGEFACADE =  PageFacade.getPageFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("insertPage")
    @RolesAllowed({"user", "admin"})
    public String insertPage(String page) {
        EntityManager em = EMF.createEntityManager();
        String thisuser = securityContext.getUserPrincipal().getName();
        
        PageDTO pageDTO = GSON.fromJson(page, PageDTO.class);
        Page pageReturn = PAGEFACADE.insertText(pageDTO);
        Request req = PAGEFACADE.requestLogger(pageReturn.getId(), "POST", thisuser);     
        
        return GSON.toJson(pageReturn);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pages")
    @RolesAllowed({"user", "admin"})
    public String getPages() throws NotFoundException {
        //String thisuser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        PagesDTO pages = PAGEFACADE.getPages();
        
        return GSON.toJson(pages);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("page/{id}")
    @RolesAllowed({"user", "admin"})
    public String getPage(@PathParam("id") Long id) throws NotFoundException {
        //String thisuser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        PageDTO page = PAGEFACADE.getPage(id);
        
        return GSON.toJson(page);
    }
    
    @PUT
    @Path("editPage/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    public String updatePage(@PathParam("id") long id,  String page) throws NotFoundException { 
        EntityManager em = EMF.createEntityManager();
        String thisuser = securityContext.getUserPrincipal().getName();
        
        PageDTO checkRights = PAGEFACADE.getPage(id);
        
        PageDTO pageToAdd = GSON.fromJson(page, PageDTO.class);
        PageDTO pageDTO = PAGEFACADE.editPage(pageToAdd, id);
        Request req = PAGEFACADE.requestLogger(id, "PUT", thisuser);
        
        return GSON.toJson(pageDTO);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("loggedInAs")
    @RolesAllowed({"user", "admin"})
    public String getLoggedInAs() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return GSON.toJson(thisuser);
    }
    
    @DELETE
    @Path("deletePage/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    public String deletePage(@PathParam("id") long id)   {
        EntityManager em = EMF.createEntityManager();
        String thisuser = securityContext.getUserPrincipal().getName();
        
        PageDTO page = PAGEFACADE.deletePage(id);
        Request req = PAGEFACADE.requestLogger(id, "DEL", thisuser);
        
        return GSON.toJson(page);
    }
    
    public static void main(String[] args) {
        PageResource res = new PageResource();
        res.insertPage("{\"title\":\"Moby Dick Chapter 2\", \"text\":\"attem\", \"mainAuthor\":\"admin\"}" );
    }
}
