package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Roles extends BaseEntity implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8931077543995177409L;

    public Roles(){}

    public Roles(List<Integer> role) {
        this.role = role;
    }

    private List<Integer> role;
    
    
    public void setRole(List<Integer> role){
        this.role = role;
    }
    
    public List<Integer> getRole(){
        return role;
    }
    
    @Override
    public String toString(){
        return role.toString();
    }
    

}
