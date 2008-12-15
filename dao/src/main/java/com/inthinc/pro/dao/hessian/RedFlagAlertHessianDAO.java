package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.RedFlagAlert;

public class RedFlagAlertHessianDAO extends GenericHessianDAO<RedFlagAlert, Integer> implements RedFlagAlertDAO
{
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
        // TODO: remove once this is implemented on the back end
        catch (ProxyException e)
        {
            if (e.getErrorCode() == 422)
                return Collections.emptyList();
            else
                throw e;
        }
    }
}
