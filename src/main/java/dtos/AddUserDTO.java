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
public class AddUserDTO {
    
    String addUser;
    
    public AddUserDTO(String addUser){
        this.addUser = addUser;
    }
    
    public AddUserDTO(){
        
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }
}
