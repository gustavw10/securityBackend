/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Page;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustav
 */
public class PagesDTO {
    
    private List<PageDTO> pagesDTO = new ArrayList();
    
    public PagesDTO(List<Page> pages){
        for(Page p : pages){
            pagesDTO.add(new PageDTO(p));
        }
    }

    public List<PageDTO> getPagesDTO() {
        return pagesDTO;
    }

    public void setPagesDTO(List<PageDTO> pagesDTO) {
        this.pagesDTO = pagesDTO;
    }
    
}
