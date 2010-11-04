package com.inthinc.pro.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.StateMileage;

public class AlertMessageJDBCDAO  extends GenericJDBCDAO  implements AlertMessageDAO{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AlertMessageJDBCDAO.class);


    @Override
    public void acknowledgeMessage(Integer msgID) {
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
