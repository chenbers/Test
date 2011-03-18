package com.inthinc.pro.service.test.mock;

import java.util.List;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.RedFlag;

public class AlertMessageDAOStub implements AlertMessageDAO {

    @Override
    public Boolean acknowledgeMessage(Integer msgID) {
        
        return msgID!=null;
    }

    @Override
    public Boolean cancelPendingMessage(Integer msgID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void fillInRedFlagMessageInfo(List<RedFlag> redFlagList) {
    // TODO Auto-generated method stub

    }

    @Override
    public List<AlertMessageBuilder> getMessageBuilders(AlertMessageDeliveryType messageType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AlertMessage> getMessages(AlertMessageDeliveryType messageType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer create(Integer id, AlertMessage entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer deleteByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AlertMessage findByID(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer update(AlertMessage entity) {
        // TODO Auto-generated method stub
        return null;
    }

}
