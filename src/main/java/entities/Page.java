/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Gustav
 */
@Entity
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TEXT")
    private String writePermission;
    @Column(columnDefinition = "TEXT")
    private String deletePermission;
    @Column(columnDefinition = "TEXT")
    private String adminPermission;
    @Column(columnDefinition = "TEXT")
    private String mainAuthor;
    
    
    public Page(){
    }

    public Page(String title, String text) {
        this.title = title;
        this.text = text;
    }
    
    public Page(String title, String text, String mainAuthor) {
        this.title = title;
        this.text = text;
        this.mainAuthor = mainAuthor;
    }
    
    public Page(String title, String text, String mainAuthor, String writePermission, String deletePermission, String adminPermission) {
        this.title = title;
        this.text = text;
        this.mainAuthor = mainAuthor;
        this.writePermission = writePermission;
        this.deletePermission = deletePermission;
        this.adminPermission = adminPermission;
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

    public String getWritePermission() {
        return writePermission;
    }

    public void setWritePermission(String writePermission) {
        this.writePermission = writePermission;
    }

    public String getDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(String deletePermission) {
        this.deletePermission = deletePermission;
    }

    public String getAdminPermission() {
        return adminPermission;
    }

    public void setAdminPermission(String adminPermission) {
        this.adminPermission = adminPermission;
    }

    public String getMainAuthor() {
        return mainAuthor;
    }

    public void setMainAuthor(String mainAuthor) {
        this.mainAuthor = mainAuthor;
    }

}
