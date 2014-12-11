package com.inthinc.pro.reports.service.impl;

import java.util.Locale;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.reports.hos.HosDailyDriverLogReportCriteria;
import com.inthinc.pro.reports.service.ReportCriteriaService;

@ContextConfiguration(locations={"classpath:spring/applicationContext-reports.xml"})
public class ReportCriteriaServiceImplTest {
    
    private static final Logger logger = Logger.getLogger(ReportCriteriaServiceImplTest.class);

    
    @Autowired
    ReportCriteriaService reportCriteriaService = new ReportCriteriaServiceImpl();
    Integer driverID = null;
    Interval interval = null;
    Locale locale = null;
    Boolean defaultUseMetric = null;
    DateTimeZone dateTimeZone = null;
    boolean includeInactiveDrivers = true;
    final Boolean hosDriversOnly = true;
    GroupHierarchy accountGroupHierarchy = null;
    
    @Mocked
    HosDailyDriverLogReportCriteria hosDailyDriverLogReportCriteria;
    
    @Test
    public void testGetHosDailyDriverLogReportCriteriaGroupHierarchyIntegerIntervalLocaleBooleanDateTimeZone() {
        final Boolean trueBoolean = true;
        final Boolean falseBoolean = false;

        new NonStrictExpectations() {{
            hosDailyDriverLogReportCriteria.setLocale((Locale)any);
        }};
        
        reportCriteriaService.getHosDailyDriverLogReportCriteria(accountGroupHierarchy, driverID, interval, locale, defaultUseMetric, dateTimeZone, includeInactiveDrivers, null);
        
        new Verifications() {{
            hosDailyDriverLogReportCriteria.setHosDriversOnly(false);
        }};
    }
    
}
