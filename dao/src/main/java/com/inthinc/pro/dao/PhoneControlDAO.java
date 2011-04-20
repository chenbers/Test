package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Cellblock;

public interface PhoneControlDAO extends GenericDAO<Cellblock, Integer> {
    /**
     * {@inheritDoc}
     * @see com.inthinc.pro.dao.DriverDAO#findByPhoneNumber(java.lang.String)
     */
    public Cellblock findByPhoneNumber(String phoneID);

    public List<Cellblock> getDriversWithDisabledPhones();

    public List<Cellblock> getCellblocksByAcctID(Integer acctID);
}
