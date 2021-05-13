/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PageDTO;
import dtos.PagesDTO;
import entities.Page;
import errorhandling.NotFoundException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Gustav
 */
public class PageFacade {
    
    private static EntityManagerFactory emf;
    private static PageFacade instance; 
    
    private PageFacade() {
        
    }
        public static PageFacade getPageFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PageFacade();
        }
        return instance;
    }
    
    public Page insertText(PageDTO pageDTO){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        //LAV MYSQL INJECT TEST HER
        Page page = new Page(pageDTO.getTitle(), pageDTO.getText());
        em.persist(page);
        em.getTransaction().commit();
        em.close();
        
        return page;
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
    
}
