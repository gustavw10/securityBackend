/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Page;

/**
 *
 * @author Gustav
 */
public class PageDTO {
    
    private Long id;
    private String title;
    private String text;

    public PageDTO(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }
    
    public PageDTO(String title, String text) {
        this.title = title;
        this.text = text;
    }
    
    public PageDTO(Page page){
        this.id = page.getId();
        this.title = page.getTitle();
        this.text = page.getText();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
