package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;

public interface AlertMessageDAO extends GenericDAO<AlertMessage, Integer>
{
    List<AlertMessage> getMessages(AlertMessageDeliveryType messageType);
    
    /**
     * Returns a list of qued message that need to be sent out. Once this method is called, all the message are 
     * automatically removed from the message queue.
     * 
     * @param messageType (Email,Text, Or Phone)
     * @return AlertMessageBuilder - class that creates friendly class to be used with a resource properties file
     */
    List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageType);
    
    void acknowledgeMessage(Integer msgID);
}
