/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.util.ArrayUtils;
import dtos.AddUserDTO;
import dtos.AdminToken;
import dtos.PageDTO;
import dtos.PagesDTO;
import dtos.RequestsDTO;
import dtos.UserDTO;
import dtos.UsersDTO;
import entities.Page;
import entities.Request;
import entities.Role;
import entities.User;
import errorhandling.IllegalOperationException;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.PageFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String insertPage(String page) throws IllegalOperationException {
        //EntityManager em = EMF.createEntityManager();
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
        PagesDTO pages = PAGEFACADE.getPages();  
        return GSON.toJson(pages);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("requests")
    @RolesAllowed({"admin"})
    public String getRequests() throws NotFoundException {
        RequestsDTO reqs = PAGEFACADE.getRequests();  
        return GSON.toJson(reqs);
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
    public String updatePage(@PathParam("id") long id,  String page) throws NotFoundException, IllegalOperationException { 
        String thisuser = securityContext.getUserPrincipal().getName();  
        PageResource res = new PageResource();
        boolean key = res.getKey(id, true, false, thisuser);
        
        if(key){
        PageDTO pageToAdd = GSON.fromJson(page, PageDTO.class);
        PageDTO pageDTO = PAGEFACADE.editPage(pageToAdd, id);
        Request req = PAGEFACADE.requestLogger(id, "PUT", thisuser);
        return GSON.toJson(pageDTO);
        }
        return GSON.toJson("Permission denied.");
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
    public String deletePage(@PathParam("id") long id) throws NotFoundException   {
        String thisuser = securityContext.getUserPrincipal().getName();
        PageResource res = new PageResource();
        boolean key = res.getKey(id, false, true, thisuser);
        
        if(key){
        PageDTO page = PAGEFACADE.deletePage(id);
        Request req = PAGEFACADE.requestLogger(id, "DEL", thisuser);
        return GSON.toJson(page);
        }
        return GSON.toJson("Permission denied.");
    }
    
    @PUT
    @Path("editWriteRights/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    public String editWriteRights(@PathParam("id") long id,  String addUser) throws NotFoundException, IllegalOperationException { 
        String thisuser = securityContext.getUserPrincipal().getName();
        PageResource res = new PageResource();
        boolean key = res.getKey(id, false, false, thisuser);
        
        if(key){
            AddUserDTO userToAdd = GSON.fromJson(addUser, AddUserDTO.class);
            String username = userToAdd.getAddUser();
            String returnedMessage = PAGEFACADE.editWriteRights(id, username);
            return GSON.toJson(returnedMessage);
        }
        return GSON.toJson("Permission denied.");
    }
    
    @PUT
    @Path("editDeleteRights/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    public String editDeleteRights(@PathParam("id") long id,  String addUser) throws NotFoundException, IllegalOperationException { 
        String thisuser = securityContext.getUserPrincipal().getName();
        PageResource res = new PageResource();
        boolean key = res.getKey(id, false, false, thisuser);
        
        if(key){
            AddUserDTO userToAdd = GSON.fromJson(addUser, AddUserDTO.class);
            String username = userToAdd.getAddUser();
            String returnedMessage = PAGEFACADE.editDeleteRights(id, username);
            return GSON.toJson(returnedMessage);
        }
        return GSON.toJson("Permission denied.");
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAdminToken")
    @RolesAllowed({"admin"})
    public String getAdminToken() {
        String thisuser = securityContext.getUserPrincipal().getName();
        AdminToken token = new AdminToken();
        token.setToken("true");
        return GSON.toJson(token);
    }
    
      public boolean getKey(Long id, boolean write, boolean del, String user) throws NotFoundException{
        boolean writeKey = false, delKey = false, adminKey = false, authorKey = false;
        String[] writeRights = null, delRights = null, adminRights = null, authorRights = null;
        String thisuser = user;
        PageDTO rightsDTO = PAGEFACADE.getPage(id);
        if(rightsDTO.getWriteRights() != null){
            writeRights = rightsDTO.getWriteRights().replaceAll("\\s","").split(",");}
        if(rightsDTO.getDeleteRights() != null){
            delRights = rightsDTO.getWriteRights().replaceAll("\\s","").split(",");}
        if(rightsDTO.getAdminRights() != null){
            adminRights = rightsDTO.getAdminRights().replaceAll("\\s","").split(",");}
        if(rightsDTO.getMainAuthor() != null){
            authorRights = rightsDTO.getMainAuthor().replaceAll("\\s","").split(",");}
        if(write && writeRights != null){writeKey = Arrays.asList(writeRights).contains(thisuser);}
        if(del && delRights != null){delKey = Arrays.asList(delRights).contains(thisuser);}
        if(adminRights != null)adminKey = Arrays.asList(adminRights).contains(thisuser);
        if(authorRights != null)authorKey = Arrays.asList(authorRights).contains(thisuser);
        if(writeKey || delKey || adminKey || authorKey){
        return true;
    }
        return false;
    }
    
    public static void main(String[] args) throws NotFoundException {
        PageResource res = new PageResource();    
    }
}
