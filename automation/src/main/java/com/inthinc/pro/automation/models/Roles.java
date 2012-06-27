package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Roles implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8931077543995177409L;

    public Roles(){}

    public Roles(List<Integer> role) {
        this.role = role;
    }

    private List<Integer> role;
    
    public void setRole(Object role){
        if (this.role == null){
            this.role = new ArrayList<Integer>();
        }
        
        if (role instanceof List<?>){
            for (Object obj : (List<?>)role){
                if (obj instanceof String){
                    this.role.add(Integer.parseInt((String) obj));
                } else if (obj instanceof Integer){
                    this.role.add((Integer) obj);
                }
            }
        } else if (role instanceof String){
                this.role.add(Integer.parseInt((String) role));
        }
    }
    
    public List<Integer> getRole(){
        return role;
    }
    
    @Override
    public String toString(){
        return role.toString();
    }
    

}
