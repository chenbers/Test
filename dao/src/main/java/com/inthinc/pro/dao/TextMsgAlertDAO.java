package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.TextMsgAlert;
import com.inthinc.pro.model.ZoneAlert;

public interface TextMsgAlertDAO extends GenericDAO<TextMsgAlert, Integer>{
    
    List<TextMsgAlert> getTextMsgAlertsByAcctID(Integer acctID);

}
