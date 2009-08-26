package com.inthinc.pro.backing;


import com.inthinc.pro.model.EntityType;

/**
 * 
 * @author mstrong
 * 
 *         IdentifiableBean is meant to be a common interface that allows any bean to implement. By doing so, the user of the bean will be able to determine the identity of the
 *         bean with out prior knowledge of the entity type.
 * 
 */
public interface IdentifiableEntityBean extends Comparable<IdentifiableEntityBean>{

    public Integer getId();

    public void setId(Integer id);

    public String getName();

    public EntityType getEntityType();
    
    public Object getEntity();
    
    public String getLongName();
    
}
