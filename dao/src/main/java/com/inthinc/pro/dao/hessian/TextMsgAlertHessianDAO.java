package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.TextMsgAlertDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.MessageItem;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;


public class TextMsgAlertHessianDAO extends GenericHessianDAO<MessageItem, Integer> implements TextMsgAlertDAO{

    @Override
    public List<MessageItem> getTextMsgAlertsByAcctID(Integer acctID) {
        try {
            return getMapper().convertToModelObject(getSiloService().getAlertsByAcctID(acctID), MessageItem.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<MessageItem> getTextMsgPage(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams) {
        try {
            List<MessageItem> messageItemList = getMapper().convertToModelObject(
                    getSiloService().getTextMsgPage(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(DateUtil.getDaysBackDate(endDate, -1)), getMapper().convertList(filterList), getMapper().convertToMap(pageParams)),
                    MessageItem.class);
            return messageItemList;
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }
    
    @Override
    public Integer getTextMsgCount(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList) {
        try {
            if (filterList == null)
                filterList = new ArrayList<TableFilterField>();
            return getChangedCount(getSiloService().getTextMsgCount(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(DateUtil.getDaysBackDate(endDate, -1)), getMapper().convertList(filterList)));

        } catch (EmptyResultSetException e) {
            return 0;
        }
    }

    @Override
    public List<MessageItem> getSentTextMsgsByGroupID(Integer groupID, Date startDate, Date stopDate) {
        try {
            List<MessageItem> messageItemList = getMapper().convertToModelObject(
                    getSiloService().getSentTextMsgsByGroupID(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(DateUtil.getDaysBackDate(stopDate, -1))), MessageItem.class);
            return messageItemList;
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

}
