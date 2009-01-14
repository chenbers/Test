package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;

public class AlertMessageHessianDAO extends GenericHessianDAO<AlertMessage, Integer> implements AlertMessageDAO
{
    private static final Logger logger = Logger.getLogger(AlertMessageHessianDAO.class);
    private Integer MAX_SILO_ID = 1;
    @Override
    public List<AlertMessage> getMessages(AlertMessageDeliveryType messageType)
    {
        try
        {
            List<AlertMessage> messages = new ArrayList<AlertMessage>();
            
            for (Integer siloID =0; siloID < MAX_SILO_ID; siloID++)
            {
                try
                {
                    messages.addAll(getMapper().convertToModelObject(getSiloService().getMessages(siloID << 24, messageType.getCode()), AlertMessage.class));
                }
                catch (ProxyException e)
                {
                    logger.debug("getMessages proxy exception" );
                }
            }
            return messages;
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
