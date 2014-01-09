package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.inthinc.pro.backing.BaseBeanTest;
import com.inthinc.pro.model.EventReportItem;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.HOSNoHoursEvent;
import com.inthinc.pro.model.event.HOSNoHoursState;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.table.model.provider.EventPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;

public class PagingHOSEventsBeanTest extends BaseBeanTest {
    

    @Test
    public void testHOSNoHoursState() {
        
        // login
        loginUser("custom101");

        PagingHOSEventsBean pagingHOSEventsBean = new PagingHOSEventsBean();
        pagingHOSEventsBean.setTableDataProvider(new MockEventPaginationTableDataProvider());
        
        
        List<EventReportItem> eventReportItems = pagingHOSEventsBean.getReportTableData();
        
        assertEquals("expected 1 event for each status type", EnumSet.allOf(HOSNoHoursState.class).size(), eventReportItems.size());

        int itemCnt = 0;
        for (HOSNoHoursState status : EnumSet.allOf(HOSNoHoursState.class)) {
            EventReportItem item = eventReportItems.get(itemCnt++);
            String msgKey = status.toString();
            String msg = MessageUtil.getMessageString(msgKey);
            assertTrue("Expected message for status " + status.name(), !msg.equalsIgnoreCase(msgKey));
            assertTrue("Expected details for status " + status.name(), item.getDetail().contains(msg));
        }
        
    }

    
    public class MockEventPaginationTableDataProvider extends EventPaginationTableDataProvider {
        @Override
        public List<Event> getItemsByRange(int firstRow, int endRow) {
            DateTime eventTime = new DateTime().minusHours(1);
            Integer odometer = 1000;
            //Integer vehicleID = 1;
            List<Event> eventList = new ArrayList<Event>();
            for (HOSNoHoursState status : EnumSet.allOf(HOSNoHoursState.class)) {
                eventList.add(new HOSNoHoursEvent(1l, null, NoteType.HOS_NO_HOURS, eventTime.toDate(), 60,
                        odometer, 0.0, 0.0, status));
                
                eventTime = eventTime.plusMinutes(1);
                odometer += 10;
                
                
            }
            return eventList;
        }

        @Override
        public int getRowCount() {
            return EnumSet.allOf(HOSNoHoursState.class).size();
        }
    }
}
