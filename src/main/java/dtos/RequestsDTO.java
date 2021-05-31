/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Page;
import entities.Request;
import dtos.RequestDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustav
 */
public class RequestsDTO {
    
    private List<RequestDTO> requestsDTO = new ArrayList();
    
    public RequestsDTO(List<Request> requests){
        for(Request p : requests){
            requestsDTO.add(new RequestDTO(p));
        }
    }

    public List<RequestDTO> getRequestsDTO() {
        return requestsDTO;
    }

    public void setRequestsDTO(List<RequestDTO> requestsDTO) {
        this.requestsDTO = requestsDTO;
    }

}
