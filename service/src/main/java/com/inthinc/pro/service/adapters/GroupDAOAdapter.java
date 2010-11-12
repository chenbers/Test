/**
 * 
 */
package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.Group;

/**
 * Adapter for the Driver resources.
 *  
 * @author dcueva
 */
public class GroupDAOAdapter extends BaseDAOAdapter<Group> {

    private GroupDAO groupDAO;	
	
	@Override
	public List<Group> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDAO<Group, Integer> getDAO() {
		return groupDAO;
	}

}
