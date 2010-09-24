package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.TextMsgAlert;
import com.inthinc.pro.model.ZoneAlert;


public class TextMsgAlertHessianDAO extends GenericHessianDAO<TextMsgAlert, Integer> implements TextMsgAlertDAO{

    @Override
    public List<TextMsgAlert> getTextMsgAlertsByAcctID(Integer acctID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getTextMsgAlertsByAcctID(acctID), TextMsgAlert.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

}
