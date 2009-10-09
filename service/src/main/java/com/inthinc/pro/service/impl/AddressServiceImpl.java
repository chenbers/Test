package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.service.AddressService;

public class AddressServiceImpl extends BaseService implements AddressService {

    private AddressDAO addressDAO;

    public Address get(Integer addressID) {
        Address address = securityBean.getAddress(addressID);

        if (securityBean.isAuthorized(address))
            return address;

        return null;
    }

    public Integer add(Address address) {
        if (securityBean.isAuthorized(address))
            return addressDAO.create(getAccountID(), address);

        return -1;
    }

    public Integer update(Address address) {
        if (securityBean.isAuthorized(address))
            return addressDAO.update(address);

        return -1;
    }

    public Integer delete(Integer addressID) {
        if (securityBean.isAuthorizedByAddressID(addressID))
            return addressDAO.deleteByID(addressID);

        return -1;
    }

    public List<Integer> add(List<Address> addresss) {
        List<Integer> results = new ArrayList<Integer>();
        for (Address address : addresss)
            results.add(add(address));
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

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

	public List<Address> getAll() {
		// TODO Auto-generated method stub NOT IMPLEMENTED IN HESSIAN
		return null;
	}

}
