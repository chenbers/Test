package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;

public class AlertMessageHessianDAO extends GenericHessianDAO<AlertMessage, Integer> implements AlertMessageDAO
{

    @Override
    public List<AlertMessage> getMessages(AlertMessageDeliveryType messageType)
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getMessages(messageType.getCode()), AlertMessage.class);
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
