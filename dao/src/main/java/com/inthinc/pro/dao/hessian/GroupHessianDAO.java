package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Group;

public class GroupHessianDAO extends GenericHessianDAO<Group, Integer> implements GroupDAO
{
    private static final long serialVersionUID = 1L;
    private AddressDAO addressDAO;
    
    @Override
    public Integer create(Integer acctID, Group group)
    {
    	createAddressIfNeeded(acctID, group);
    	group.setName(group.getName() == null ? "" : group.getName().trim());
        return super.create(acctID, group);
    }
    private void createAddressIfNeeded(Integer acctID, Group group){
    	
        if (group.getAddress() != null)
        {
        	createAddress(acctID, group);
        }
    }
    private void createAddress(Integer acctID, Group group){
        Integer addressID = getReturnKey(getSiloService().createAddr(acctID, getMapper().convertToMap(group.getAddress())), Address.class);
        group.getAddress().setAddrID(addressID);
        group.setAddressID(addressID);
    }
    @Override
	public Integer update(Group group) {
    	
    	updateOrCreateAddressAsNeeded(group, group.getAddress());
        group.setName(group.getName() == null ? "" : group.getName().trim());
    	return super.update(group);
	}
    private void updateOrCreateAddressAsNeeded(Group group, Address address){
        if (addressExists(address)){
            getSiloService().updateAddr(address.getAddrID(), getMapper().convertToMap(address));
        }
        else if (addressNeedsCreating(group.getAddressID(),address)){
        	createAddress(address.getAccountID(), group);
        }
    }
    @Override
	public Integer delete(Group group) {
		deleteAddress(group.getAddress());
		return deleteByID(group.getGroupID());
	}
    private Integer deleteAddress(Address address){
    	if (addressExists(address)){
        	//Can't delete an address?
//    		return addressDAO.deleteByID(address.getAddrID());
    	}
    	return null;
    }
	private boolean addressExists(Address address){
    	return (address != null) && !address.isEmpty() && address.getAddrID() != null;
    }
    private boolean addressNeedsCreating(Integer addrID,Address address){
    	return address != null && (address.getAddrID() == null || address.getAddrID().intValue() == 0);
    }
    
	@Override
    public List<Group> getGroupHierarchy(Integer acctID, Integer groupID)
    {
        try
        {
            List <Group> allGroups = getGroupsByAcctID(acctID);
            List <Group> groupHierarchy = new ArrayList<Group>();
            for (Group group : allGroups)
            {
                if (group.getGroupID().equals(groupID))
                {
                    groupHierarchy.add(group);
                    addChildren(allGroups, groupHierarchy, group.getGroupID());
                    break;
                }
            }
            
            
            return groupHierarchy;
            
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    private void addChildren(List<Group> allGroups , List<Group> groupHierarchy, Integer parentID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(parentID))
            {
                groupHierarchy.add(group);
                addChildren(allGroups, groupHierarchy, group.getGroupID());
            }
        }
    }
    
    @Override
    public List<Group> getGroupsByAcctID(Integer acctID)
    {
        try
        {
            List<Map<String, Object>> groups = getSiloService().getGroupsByAcctID(acctID);
            return getMapper().convertToModelObject(groups, Group.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }
	public AddressDAO getAddressDAO() {
		return addressDAO;
	}
	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}
}
