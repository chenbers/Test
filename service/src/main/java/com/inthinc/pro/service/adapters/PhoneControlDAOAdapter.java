package com.inthinc.pro.service.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;

@Component
public class PhoneControlDAOAdapter extends BaseDAOAdapter<Cellblock>{
    @Autowired
    private PhoneControlDAO phoneControlDAO;

    /**
     * Get Driver object by cell phone number.
     * @param phoneNumber the cell phone number.
     * @return the Driver having the cell phone number.
     */
    public Cellblock findByPhoneNumber(String phoneNumber){
        return phoneControlDAO.findByPhoneNumber(phoneNumber);
    }
    
    @Override
    public List<Cellblock> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected GenericDAO<Cellblock, Integer> getDAO() {
        return phoneControlDAO;
    }

    @Override
    protected Integer getResourceID(Cellblock resource) {
         return resource.getDriverID();
    }

    public void setPhoneControlDAO(PhoneControlDAO phoneControlDAO) {
        this.phoneControlDAO = phoneControlDAO;
    }

    public List<Cellblock> getCellblocksByAcctID(Integer acctID) {
        return phoneControlDAO.getCellblocksByAcctID(acctID);
    }

    public List<Cellblock> getDriversWithDisabledPhones() {

        return phoneControlDAO.getDriversWithDisabledPhones();
    }

}
