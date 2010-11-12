package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.GenericDAO;

/**
 * This class is the base for all adapters.</br>
 * The classes deriving from it implement an Adapter pattern 
 * between the Service Layer and the Hessian DAO layer</br>
 * 
 * It also encapsulates common functionality.
 * 
 * @param <R> The Resource being handled by the concrete Adapter.
 * 
 * @author dcueva
 */
public abstract class BaseDAOAdapter<R> {

	/**
	 * Retrieve all the resources of type <R>.
	 * 
	 * @return A list of objects of type <R>
	 */
    public abstract List<R> getAll();


    // Methods to be called back in the children
    
    /**
     * Returns the DAO that will handle the actual back end calls
     */
    protected abstract GenericDAO<R, Integer> getDAO();

	/**
	 * Returns the ID of the resource.
	 * By default, this is the Account ID.
	 * Child classes can overwrite this method if needed.
	 * 
	 * @param resource The resource to get the ID from
	 * @return The ID of the resource
	 */
    protected Integer getResourceID(R resource){
		// Will be replaced by TiwiProPrincipal
		// getUser().getPerson().getAcctID();    	
      return null;	
    }

	
    /**
     * Create a resource.
     * 
     * @param resource The resource to be created
     * @return The resource ID or null if creation failed.
     */
    public Integer create(R resource){
		return getDAO().create(getResourceID(resource), resource);
    }
  
    
}
