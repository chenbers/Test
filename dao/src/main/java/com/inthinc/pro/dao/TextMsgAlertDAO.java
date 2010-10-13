package com.inthinc.pro.dao;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public interface TextMsgAlertDAO extends GenericDAO<MessageItem, Integer>{
    
    List<MessageItem> getTextMsgAlertsByAcctID(Integer acctID);
    
    /**
     * Retrieve the total count of filtered list of text messages for all of the drivers under the specified group and time frame.
     * 
     * @param groupID
     * @return
     */
    Integer getTextMsgCount(Integer groupID, Date startDate, Date endDate,  List<TableFilterField> filterList);


    /**
     * Retrieve the sorted/filtered sublist of text messages for all of the drivers under the specified group and time frame.
     * 
     * @param groupID
     * @return
     */
    List<MessageItem> getTextMsgPage(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams);

}
