package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.Zone;

public class ZoneHessianDAO extends GenericHessianDAO<Zone, Integer> implements ZoneDAO
{
    @Override
    public List<Zone> getZones(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getZonesByAcctID(accountID), Zone.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }
}
