/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Gustav
 */
public class AdminToken {
    
    String token;

    public AdminToken(String token) {
        this.token = token;
    }
    
    public AdminToken(){
        
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
