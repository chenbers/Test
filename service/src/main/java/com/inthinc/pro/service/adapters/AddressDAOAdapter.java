package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.model.Address;

/**
 * Adapter for the Address resources.
 * 
 * @author dcueva
 *
 */
public class AddressDAOAdapter extends BaseDAOAdapter<Address> {

    private AddressDAO addressDAO;

	@Override
	public List<Address> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDAO<Address, Integer> getDAO() {
		return addressDAO;
	}

	@Override
	protected Integer getResourceID(Address address) {
		return address.getAddrID();
	}

	
}
