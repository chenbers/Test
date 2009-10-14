package com.inthinc.pro.util;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;

public class SecureAddressDAO extends BaseSecureDAO{

    private AddressDAO addressDAO;


    public boolean isAuthorized(Address address) {
        if (address != null) {
          if (getAccountID().equals(address.getAccountID()))
                return true;

        }
        throw new UnauthorizedException("Address not found");
    }
    
    public boolean isAuthorized(Integer addressID) {
        return isAuthorized(findByID(addressID));
    }

    public Address findByID(Integer addressID) {
        Address address = addressDAO.findByID(addressID);
        if (address == null || !address.getAccountID().equals(getAccountID()))
            throw new NotFoundException("addressID not found: " + addressID);
        return address;
    }    
    
    public Integer create(Address address) {
        if (isAuthorized(address))
            return addressDAO.create(getAccountID(), address);

        return -1;
    }

    public Integer update(Address address) {
        if (isAuthorized(address))
            return addressDAO.update(address);

        return -1;
    }

    public Integer delete(Integer addressID) {
        if (isAuthorized(addressID))
            return addressDAO.deleteByID(addressID);

        return -1;
    }
    
    public AddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
