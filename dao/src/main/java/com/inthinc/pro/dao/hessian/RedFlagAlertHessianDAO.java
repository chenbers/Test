package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.RedFlagsAlertMapper;
import com.inthinc.pro.model.RedFlagAlert;


@SuppressWarnings("serial")
public class RedFlagAlertHessianDAO extends GenericHessianDAO<RedFlagAlert, Integer> implements RedFlagAlertDAO
{
    public RedFlagAlertHessianDAO ()
    {
        super();
        super.setMapper(new RedFlagsAlertMapper());
    }
    @Override
    public List<RedFlagAlert> getRedFlagAlerts(Integer accountID)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getAlertsByAcctID(accountID), RedFlagAlert.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserID(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getAlertsByUserID(userID), RedFlagAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserIDDeep(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getAlertsByUserIDDeep(userID), RedFlagAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
    @Override
    public Integer deleteAlertsByZoneID(Integer zoneID)
    {
        return getChangedCount(getSiloService().deleteAlertsByZoneID(zoneID));
    }
    @Override
    public List<RedFlagAlert> getAlertsByTeamGroupID(Integer groupID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getAlertsByTeamGroupID(groupID), RedFlagAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
   }
}
