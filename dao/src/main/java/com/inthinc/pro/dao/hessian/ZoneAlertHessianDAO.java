package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.ZoneAlert;

public class ZoneAlertHessianDAO extends GenericHessianDAO<ZoneAlert, Integer> implements ZoneAlertDAO
{
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
    public Integer deleteByZoneID(Integer zoneID)
    {
        return getChangedCount(getSiloService().deleteZoneAlertsByZoneID(zoneID));
    }
}
