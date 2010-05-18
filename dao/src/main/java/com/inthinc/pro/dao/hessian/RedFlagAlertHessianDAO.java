package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.mapper.RedFlagsAlertMapper;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.User;

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
        // TODO: THIS IS A KLUDGE SO THIS WILL WORK UNTIL DS GETS BACK AND CAN FIX IT!!!!!!
        catch (RemoteServerException ex) {
            if (ex.getErrorCode() == 307) {
                User user = getMapper().convertToModelObject(getSiloService().getUser(userID), User.class);
                return getRedFlagAlerts(user.getPerson().getAcctID());
            }
            return Collections.emptyList();
        }
    }
}
