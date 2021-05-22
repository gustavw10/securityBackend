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
    private String mainAuthor;
    private String writeRights;
    private String deleteRights;
    private String adminRights;

    public PageDTO(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }
    
    public PageDTO(Long id, String title, String text, String mainAuthor) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.mainAuthor = mainAuthor;
    }
    
    public PageDTO(String title, String text) {
        this.title = title;
        this.text = text;
    }
    
    public PageDTO(Page page){
        this.id = page.getId();
        this.title = page.getTitle();
        this.text = page.getText();
        this.mainAuthor = page.getMainAuthor();
        this.writeRights = page.getWritePermission();
        this.deleteRights = page.getDeletePermission();
        this.adminRights = page.getAdminPermission();
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

    public String getMainAuthor() {
        return mainAuthor;
    }

    public String getWriteRights() {
        return writeRights;
    }

    public void setWriteRights(String writeRights) {
        this.writeRights = writeRights;
    }

    public String getDeleteRights() {
        return deleteRights;
    }

    public void setDeleteRights(String deleteRights) {
        this.deleteRights = deleteRights;
    }

    public String getAdminRights() {
        return adminRights;
    }

    public void setAdminRights(String adminRights) {
        this.adminRights = adminRights;
    }
    
}
