package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.GenericDAO;

/**
 * This class is the base for all adapters.</br>
 * The classes deriving from it implement an Adapter pattern 
 * between the Service Layer and the Hessian DAO layer</br>
 * 
 *  The methods implemented in this class use the Template Method pattern 
 *  to delegate specifics to the children.
 * 
 * It encapsulates the CRUD methods and other common functionality.
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
     * Returns the resource ID.
     */
    // We can get rid of this method if we make all entities implement a getID method
    protected abstract Integer getResourceID(R resource);
    
    
	/**
	 * Returns the ID to be used during resource creation.</br>
	 * Note that this ID is NOT the Resource ID. 
	 * In fact, by default it is the Account ID. </br>
	 * Child classes can overwrite this method if needed.
	 * 
	 * @param resource The resource to get the ID from
	 * @return The ID to be used during creation of the resource
	 */
    protected Integer getResourceCreationID(R resource){
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
		return getDAO().create(getResourceCreationID(resource), resource);
    }
    
    /**
     * Update a resource.
     * 
     * @param resource The resource to be updated
     * @return The updated resource or null if creation failed.
     */
    public R update(R resource){
        if (getDAO().update(resource) != 0)
            return getDAO().findByID(getResourceID(resource));
        return null;    
    };
    
    /**
     * Deletes a resource
     * 
     * @param id The ID of the resource to be deleted
     * @return 1 if successful, 0 if failed.
     */
    public Integer delete(Integer id){
    	return getDAO().deleteByID(id);
    }; 

    /**
     * Finds and retrieves a resource by ID.
     * 
     * @param id The ID of the resource to find
     * @return The resource, or null if not found.
     */
    public R findByID(Integer id){
    	return getDAO().findByID(id);	
    }
    
    
}
