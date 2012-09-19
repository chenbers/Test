package com.inthinc.pro.backing;

import org.junit.Test;

public class UnsubscribeBeanTest extends BaseBeanTest {

    @Test
    public void spring() {
        UnsubscribeBean bean = (UnsubscribeBean) applicationContext.getBean("unsubscribeBean");

        // Check the injected beans
        assertNotNull(bean.getPersonDAO());
        assertNotNull(bean.getReportScheduleDAO());
        String emailAddress = "01CA4370CBE4A0C227D378833A2F33B57990EBFF2F4861B1BEEE36CA0CFD47DB";
        Integer reportScheduleID = 41961;
        bean.setEmailAddress(emailAddress);
        bean.setReportScheduleID(reportScheduleID);
        bean.prepareToUnsubscribeToReportSchedule();
    }

}
