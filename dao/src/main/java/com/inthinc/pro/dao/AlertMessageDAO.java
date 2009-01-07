package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;

public interface AlertMessageDAO extends GenericDAO<AlertMessage, Integer>
{
    List<AlertMessage> getMessages(AlertMessageDeliveryType messageType);
}
