package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
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
        // TODO: remove once this is implemented on the back end
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }
        
    }

    @Override
    public Integer deleteByZoneID(Integer zoneID)
    {
        return getChangedCount(getSiloService().deleteZoneAlertsByZoneID(zoneID));
    }
}
