package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.ZoneAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.model.User;
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
        // TODO: THIS IS A KLUDGE SO THIS WILL WORK UNTIL DS GETS BACK AND CAN FIX IT!!!!!!
        catch (RemoteServerException ex) {
            if (ex.getErrorCode() == 307) {
                User user = getMapper().convertToModelObject(getSiloService().getUser(userID), User.class);
                return getZoneAlerts(user.getPerson().getAcctID());
            }
            return Collections.emptyList();
        }
    }

    @Override
    public Integer deleteByZoneID(Integer zoneID)
    {
        return getChangedCount(getSiloService().deleteZoneAlertsByZoneID(zoneID));
    }
}
