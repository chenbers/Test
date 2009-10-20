package com.inthinc.pro.util;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;

public class SecureAddressDAO extends BaseSecureDAO {

    private AddressDAO addressDAO;

    public boolean isAuthorized(Address address) {
        if (address != null) {
            if (getAccountID().equals(address.getAccountID()))
                return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer addressID) {
        return isAuthorized(findByID(addressID));
    }

    public Address findByID(Integer addressID) {
        Address address = addressDAO.findByID(addressID);
        if (isAuthorized(address))
            return address;
        return null;
    }

    public Integer create(Address address) {
        if (isAuthorized(address))
            return addressDAO.create(getAccountID(), address);

        return null;
    }

    public Integer update(Address address) {
        if (isAuthorized(address))
            return addressDAO.update(address);

        return 0;
    }

    public Integer delete(Integer addressID) {
        if (isAuthorized(addressID))
            return addressDAO.deleteByID(addressID);

        return 0;
    }

    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
