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
 *
 * @author Gustav
 */
@Path("page")
public class PageResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final PageFacade PAGEFACADE =  PageFacade.getPageFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("insertPage")
    @RolesAllowed({"user", "admin"})
    public String insertPage(String page) {
        //String thisuser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        PageDTO pageDTO = GSON.fromJson(page, PageDTO.class);
        Page pageReturn = PAGEFACADE.insertText(pageDTO);
             
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
    
}
