package com.inthinc.pro.model;

// objects that are stored as a reference to a static table in the db
// we exchange IDs with the back end for these
// i.e. map from object to ID when storing, map from ID to object when retrieving
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface ReferenceEntity
{

    public Integer retrieveID();
    
    
}
