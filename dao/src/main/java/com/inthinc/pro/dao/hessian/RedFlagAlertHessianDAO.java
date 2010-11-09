package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.RedFlagsAlertMapper;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.RedFlagAlert;


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
            return getMapper().convertToModelObject(getSiloService().getRedFlagAlertsByAcctID(accountID), RedFlagAlert.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserID(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getRedFlagAlertsByUserID(userID), RedFlagAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<RedFlagAlert> getRedFlagAlertsByUserIDDeep(Integer userID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getRedFlagAlertsByUserIDDeep(userID), RedFlagAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
    @Override
    public List<AlertEscalationItem> getAlertEscalationItemsByAlert(Integer alertID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getAlertEscalationItemsByAlert(alertID), AlertEscalationItem.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

}
