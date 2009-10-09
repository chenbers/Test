package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.service.AddressService;
import com.inthinc.pro.util.SecureAddressDAO;

public class AddressServiceImpl implements AddressService {

    private SecureAddressDAO addressDAO;

    public Address get(Integer addressID) {
        return addressDAO.findByID(addressID);
    }

    public Integer create(Address address) {
        return addressDAO.create(address);
    }

    public Integer update(Address address) {
        return addressDAO.update(address);
    }

    public Integer delete(Integer addressID) {
        return addressDAO.delete(addressID);
    }

    public List<Integer> create(List<Address> addresss) {
        List<Integer> results = new ArrayList<Integer>();
        for (Address address : addresss)
            results.add(create(address));
        return results;
    }

    public List<Integer> update(List<Address> addresses) {
        List<Integer> results = new ArrayList<Integer>();
        for (Address address : addresses)
            results.add(update(address));
        return results;
    }

    public List<Integer> delete(List<Integer> addressIDs) {
        List<Integer> results = new ArrayList<Integer>();
        for (Integer id : addressIDs) {
            results.add(delete(id));
        }
        return results;
    }

    public void setAddressDAO(SecureAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public SecureAddressDAO getAddressDAO() {
        return addressDAO;
    }

	public List<Address> getAll() {
		// TODO Auto-generated method stub NOT IMPLEMENTED IN HESSIAN
		return null;
	}

}
