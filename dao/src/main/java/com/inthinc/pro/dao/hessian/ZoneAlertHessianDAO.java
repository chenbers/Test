package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.ZoneAlertMapper;
import com.inthinc.pro.model.ZoneAlert;

@SuppressWarnings("serial")
public class ZoneAlertHessianDAO extends GenericHessianDAO<ZoneAlert, Integer> implements ZoneAlertDAO
{
    
    
    public ZoneAlertHessianDAO() {
        super();
        super.setMapper(new ZoneAlertMapper());
    }

    @Override
    public List<ZoneAlert> getZoneAlerts(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getZoneAlertsByAcctID(accountID), ZoneAlert.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }

    @Override
    public List<ZoneAlert> getZoneAlertsByUserID(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getZoneAlertsByUserID(userID), ZoneAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ZoneAlert> getZoneAlertsByUserIDDeep(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getZoneAlertsByUserIDDeep(userID), ZoneAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Integer deleteByZoneID(Integer zoneID)
    {
        return getChangedCount(getSiloService().deleteZoneAlertsByZoneID(zoneID));
    }
}
