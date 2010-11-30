package com.inthinc.pro.service.adapters;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Address;

/**
 * Adapter for the Address resources.
 * 
 * @author dcueva
 *
 */
@Component
public class AddressDAOAdapter extends BaseDAOAdapter<Address> {

    @Autowired
    private AddressDAO addressDAO;

	@Override
	public List<Address> getAll() {
        throw new NotImplementedException();
	}

	@Override
	protected GenericDAO<Address, Integer> getDAO() {
		return addressDAO;
	}

	@Override
	protected Integer getResourceID(Address address) {
		return address.getAddrID();
	}

	// Getters and setters -------------------------------------------------
    
	/**
	 * @return the addressDAO
	 */
	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	/**
	 * @param addressDAO the addressDAO to set
	 */
	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	
}
