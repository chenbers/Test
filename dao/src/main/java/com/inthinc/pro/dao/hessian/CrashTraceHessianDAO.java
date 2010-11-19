package com.inthinc.pro.dao.hessian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.CrashTraceDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.CrashTrace;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class CrashTraceHessianDAO extends GenericHessianDAO<CrashTrace, Integer> implements CrashTraceDAO{

    @Override
    public List<CrashTrace> getCrashTraceByEventID(String eventID) {
        try {
            //List<CrashTrace> results = new ArrayList<CrashTrace>();
            //CrashTrace ct = CrashTrace.getMockObject();
            //results.add(ct);
            //return results;
            //TODO: jwimmer: following line will be put back into play after backend guys deliver a hessian call that sends back a CrashTrace object... may need tweaked based on byte[] vs java.sql.Blob
            return getMapper().convertToModelObject(getSiloService().getCrashTraces(eventID), CrashTrace.class);
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Integer getCrashTraceCount(String eventID, Date startDate, Date endDate, List<TableFilterField> filterList) {
        try {
            if (filterList == null)
                filterList = new ArrayList<TableFilterField>();
            return getChangedCount(getSiloService().getCrashTraceCount(eventID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), getMapper().convertList(filterList)));

        } catch (EmptyResultSetException e) {
            return 0;
        }
    }

    @Override
    public List<CrashTrace> getCrashTracePage(String eventID, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams) {
        try {
            List<CrashTrace> messageItemList = getMapper().convertToModelObject(
                    getSiloService().getCrashTracePage(eventID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), getMapper().convertList(filterList),
                            getMapper().convertToMap(pageParams)), CrashTrace.class);
            return messageItemList;
        } catch (EmptyResultSetException e) {
            return Collections.emptyList();
        }
    }


//    @Override
//    public List<MessageItem> getTextMsgAlertsByAcctID(Integer acctID) {
//        try {
//            return getMapper().convertToModelObject(getSiloService().getTextMsgAlertsByAcctID(acctID), MessageItem.class);
//        } catch (EmptyResultSetException e) {
//            return Collections.emptyList();
//        }
//    }
//    
//    @Override
//    public List<MessageItem> getTextMsgPage(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList, PageParams pageParams) {
//        try {
//            List<MessageItem> messageItemList = getMapper().convertToModelObject(
//                    getSiloService().getTextMsgPage(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), getMapper().convertList(filterList), getMapper().convertToMap(pageParams)),
//                    MessageItem.class);
//            return messageItemList;
//        } catch (EmptyResultSetException e) {
//            return Collections.emptyList();
//        }
//    }
//    
//    @Override
//    public Integer getTextMsgCount(Integer groupID, Date startDate, Date endDate, List<TableFilterField> filterList) {
//        try {
//            if (filterList == null)
//                filterList = new ArrayList<TableFilterField>();
//            return getChangedCount(getSiloService().getTextMsgCount(groupID, DateUtil.convertDateToSeconds(startDate), DateUtil.convertDateToSeconds(endDate), getMapper().convertList(filterList)));
//
//        } catch (EmptyResultSetException e) {
//            return 0;
//        }
//    }

}
