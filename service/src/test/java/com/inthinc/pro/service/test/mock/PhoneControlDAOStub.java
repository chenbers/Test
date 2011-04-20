package com.inthinc.pro.service.test.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.phone.CellStatusType;

@Component
public class PhoneControlDAOStub implements PhoneControlDAO{
    
    private Cellblock cellblock;
    
    @Override
    public Cellblock findByPhoneNumber(String phoneID) {
        cellblock.setCellPhone(phoneID);
        cellblock.setCellStatus(CellStatusType.DISABLED);
        return cellblock;
    }

    @Override
    public List<Cellblock> getDriversWithDisabledPhones() {
        return new ArrayList<Cellblock>();
    }

    @Override
    public Integer create(Integer id, Cellblock entity) {
        this.cellblock = entity;
        return id;
    }

    @Override
    public Integer deleteByID(Integer id) {
        return id;
    }

    @Override
    public Cellblock findByID(Integer id) {
        return this.cellblock;
    }

    @Override
    public Integer update(Cellblock entity) {

        this.cellblock = entity;
        return cellblock.getDriverID();
    }

    @Override
    public List<Cellblock> getCellblocksByAcctID(Integer acctID) {
        return new ArrayList<Cellblock>();
    }
}
