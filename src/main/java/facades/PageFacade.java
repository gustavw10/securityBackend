/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PageDTO;
import dtos.PagesDTO;
import dtos.RequestsDTO;
import entities.Page;
import entities.Request;
import errorhandling.IllegalOperationException;
import errorhandling.NotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Gustav
 */
public class PageFacade {
    
    private static EntityManagerFactory emf;
    private static PageFacade instance; 
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    private PageFacade() {
        
    }
        public static PageFacade getPageFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PageFacade();
        }
        return instance;
    }
    
    public Page insertText(PageDTO pageDTO) throws IllegalOperationException{
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        //LAV MYSQL INJECT TEST HER
        ArrayList itemsToSanitize = new ArrayList<String>();
        itemsToSanitize.add(pageDTO.getTitle());
        itemsToSanitize.add(pageDTO.getText());
        itemsToSanitize.add(pageDTO.getMainAuthor());

        PageFacade fac = new PageFacade();
        boolean flag = fac.sanitizeInput(itemsToSanitize);

        if (flag) {
            throw new IllegalOperationException();
        } else {
            Page page = new Page(pageDTO.getTitle(), pageDTO.getText(), pageDTO.getMainAuthor());
            em.persist(page);
            em.getTransaction().commit();
            em.close();

            return page;
        }
    }
    
     public PageDTO editPage(PageDTO pageDTO, long id) throws IllegalOperationException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Page page = em.find(Page.class, id);
            
            ArrayList itemsToSanitize = new ArrayList<String>();
            itemsToSanitize.add(pageDTO.getTitle());
            itemsToSanitize.add(pageDTO.getText());
            PageFacade fac = new PageFacade();
            boolean flag = fac.sanitizeInput(itemsToSanitize);
        
            if(flag){
                throw new IllegalOperationException();
            } else {
                page.setTitle(pageDTO.getTitle());
                page.setText(pageDTO.getText());
                em.getTransaction().commit(); 
                return new PageDTO(page);
            }
           
        } finally {
            em.close();
        }
    }
     
    
    public PagesDTO getPages() throws NotFoundException{
        EntityManager em = emf.createEntityManager();
        
         try {
            PagesDTO pages = new PagesDTO(em.createQuery("SELECT p from Page p " ).getResultList());
            if (pages == null) {
                throw new NotFoundException("Could not find anything with the given query.");
            } else {
                return pages;
            }
        } finally {
            em.close();
        }
    }
    
        public RequestsDTO getRequests() throws NotFoundException{
        EntityManager em = emf.createEntityManager();
        
         try {
            RequestsDTO reqs = new RequestsDTO(em.createQuery("SELECT r from Request r " ).getResultList());
            if (reqs == null) {
                throw new NotFoundException("Could not find anything with the given query.");
            } else {
                return reqs;
            }
        } finally {
            em.close();
        }
    }
    
    
    public PageDTO getPage(long id)  throws NotFoundException {
       EntityManager em = emf.createEntityManager();
       try {
           Page page = em.find(Page.class, id);
           if (page == null) {
                throw new NotFoundException(String.format("Page with id: (%d) not found.", id));
            } else {
                return new PageDTO(page);
           }
       } finally {
           em.close();
       }
    }
    
    public String editWriteRights(long id, String addUser) throws NotFoundException, IllegalOperationException {
        EntityManager em = emf.createEntityManager();

        PageFacade fac = new PageFacade();
        ArrayList itemsToSanitize = new ArrayList<>();
        itemsToSanitize.add(addUser);
        boolean flag = fac.sanitizeInputHard(itemsToSanitize);

        if (flag) {
            throw new IllegalOperationException();
        }

        try {
            Page page = em.find(Page.class, id);
            if (page == null) {
                throw new NotFoundException(String.format("Page with id: (%d) not found.", id));
            } else {
                em.getTransaction().begin();
                String str = page.getWritePermission();
                List<String> users = Arrays.asList(str.split(","));

                boolean containsOrNot = users.contains(addUser) ? true : false;

                if (containsOrNot) {
                    return "Write permission already contains user.";
                } else {
                    str = str + addUser + ",";
                    page.setWritePermission(str);
                }
                em.getTransaction().commit();
                return "Added user " + addUser + ".";
            }
        } finally {
            em.close();
        }
    }

    public String editDeleteRights(long id, String addUser) throws NotFoundException, IllegalOperationException {
        EntityManager em = emf.createEntityManager();
        
        PageFacade fac = new PageFacade();
        ArrayList itemsToSanitize = new ArrayList<>();
        itemsToSanitize.add(addUser);
        boolean flag = fac.sanitizeInputHard(itemsToSanitize);

        if (flag) {
            throw new IllegalOperationException();
        }
        
        try {
            Page page = em.find(Page.class, id);
            if (page == null) {
                throw new NotFoundException(String.format("Page with id: (%d) not found.", id));
            } else {
                em.getTransaction().begin();
                String str = page.getDeletePermission();
                List<String> users = Arrays.asList(str.split(","));

                boolean containsOrNot = users.contains(addUser) ? true : false;

                if (containsOrNot) {
                    return "Delete permission already contains user.";
                } else {
                    str = str + addUser + ",";
                    page.setDeletePermission(str);
                }
                em.getTransaction().commit();
                return "Added user " + addUser + ".";
            }
        } finally {
            em.close();
        }
    }
    
    public PageDTO deletePage(long id) {
        EntityManager em = emf.createEntityManager();   
        
        Page page = em.find(Page.class, id);
        if (page == null) {
            //throw new PersonNotFoundException("Could not delete, provided id does not exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(page);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new PageDTO(page);
    }
    
    public Request requestLogger(long id, String type, String thisuser){
        EntityManager em = emf.createEntityManager();
    
        em.getTransaction().begin();
        Request req = new Request(type, thisuser, id);
        em.persist(req);
        em.getTransaction().commit();
        em.close();
        return req;
    }
    
    public boolean sanitizeInput(ArrayList list) throws IllegalOperationException{
        Pattern p = Pattern.compile("[^.,a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        
        for(int i = 0; i < list.size(); i++){
            Matcher m = p.matcher(list.get(i).toString());
            boolean b = m.find();
            if (b){
            return true;
            }
        }
        return false;
    }
    
        public boolean sanitizeInputHard(ArrayList list) throws IllegalOperationException{
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        
        for(int i = 0; i < list.size(); i++){
            Matcher m = p.matcher(list.get(i).toString());
            boolean b = m.find();
            if (b){
            return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) throws NotFoundException, IOException, IllegalOperationException {
//         EntityManager em = emf.createEntityManager();
//          Query query = em.createQuery("SELECT p FROM Page p WHERE p.id = '51");
//            PageDTO page = (PageDTO)query.getSingleResult();
        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
        PageFacade PAGEFACADE =  PageFacade.getPageFacade(EMF);
        String us = "us";
        //PAGEFACADE.requestLogger(2, "DEL", us);
        ArrayList a = new ArrayList<String>();
        a.add("Okay");
        a.add("Ille++a");
        
        boolean te = PAGEFACADE.sanitizeInput(a);
        System.out.println(te);
    }
    
}
