/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Page;
import entities.Request;
import java.util.Date;

/**
 *
 * @author Gustav
 */
public class RequestDTO {
    
    private Long id;
    private String type;
    private String user;
    private Long pageId;
    private Date date;

    public RequestDTO(String type, String user, Long pageId, Date date) {
        this.type = type;
        this.user = user;
        this.pageId = pageId;
        this.date = date;
    }

    public RequestDTO(Long id, String type, String user, Long pageId, Date date) {
        this.id = id;
        this.type = type;
        this.user = user;
        this.pageId = pageId;
        this.date = date;
    }
    
    public RequestDTO(Request request){
        this.id = request.getId();
        this.type = request.getType();
        this.user = request.getUser();
        this.date = request.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
