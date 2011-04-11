package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.CellblockMapper;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.Silo;

public class PhoneControlHessianDAO extends GenericHessianDAO<Cellblock, Integer> implements PhoneControlDAO {

    private static final long serialVersionUID = 1L;
    private CellblockMapper cellblockMapper;
    private static final Logger logger = Logger.getLogger(PhoneControlHessianDAO.class);

    public PhoneControlHessianDAO() {
        super();
        cellblockMapper = new CellblockMapper();
        super.setMapper(cellblockMapper);

    }
    
    @Override
    public Cellblock findByPhoneNumber(String phoneID) {
        return new Cellblock();
    }

    @Override
    public List<Cellblock> getDriversWithDisabledPhones() {
        Silo silo = getMapper().convertToModelObject(getSiloService().getNextSilo(), Silo.class);
        Integer siloID = silo.getSiloID();

        try
        {
            List<Cellblock> cellblockList = getMapper().convertToModelObject(this.getSiloService().getDriversWithDisabledPhones(siloID<<24), Cellblock.class);
            return cellblockList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    @Override
    public List<Cellblock> getCellblocksForAcctID(Integer acctID) {
        logger.debug("getCellblocksForAcctID acctID = " + acctID);
        try
        {
            List<Cellblock> cellblockList = getMapper().convertToModelObject(this.getSiloService().getCellblocksForAcctID(acctID), Cellblock.class);
            return cellblockList;
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
}
