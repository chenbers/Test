package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Silo extends BaseEntity 
{
    @ID
    Integer siloID;
    
    public Silo()
    {
        
    }

    public Integer getSiloID()
    {
        return siloID;
    }

    public void setSiloID(Integer siloID)
    {
        this.siloID = siloID;
    }
    
    
}
