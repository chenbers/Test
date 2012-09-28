package com.inthinc.pro.util;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;

public class SecureAddressDAO extends SecureDAO<Address> {

    private AddressDAO addressDAO;

    @Override
    public boolean isAuthorized(Address address) {
        if (address != null) {
            if (isInthincUser() || getAccountID().equals(address.getAccountID()))
                return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer addressID) {
        return isAuthorized(findByID(addressID));
    }

    @Override
    public Address findByID(Integer addressID) {
        Address address = addressDAO.findByID(addressID);
        if (isAuthorized(address))
            return address;
        return null;
    }

    @Override
    public Integer create(Address address) {
        if (isAuthorized(address))
            return addressDAO.create(getAccountID(), address);

        return null;
    }

    @Override
    public Address update(Address address) {
        if (isAuthorized(address) && addressDAO.update(address) != 0)
            return addressDAO.findByID(address.getAddrID());
        return null;
    }

    @Override
    public Integer delete(Integer addressID) {
        if (isAuthorized(addressID))
            return addressDAO.deleteByID(addressID);

        return 0;
    }

    @Override
    public List<Address> getAll() {
        throw new NotImplementedException();
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
