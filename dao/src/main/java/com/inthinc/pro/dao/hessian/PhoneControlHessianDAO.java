package com.inthinc.pro.dao.hessian;

import com.inthinc.pro.dao.PhoneControlDAO;
import com.inthinc.pro.dao.hessian.mapper.CellblockMapper;
import com.inthinc.pro.model.Cellblock;

public class PhoneControlHessianDAO extends GenericHessianDAO<Cellblock, Integer> implements PhoneControlDAO {

    private static final long serialVersionUID = 1L;
    private CellblockMapper cellblockMapper;

    public PhoneControlHessianDAO() {
        super();
        cellblockMapper = new CellblockMapper();
        super.setMapper(cellblockMapper);

    }
    
    
}
