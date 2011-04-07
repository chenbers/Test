package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.DriverPerformanceDAO;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportType;


public class DriverPerformanceReportCriteriaDataTest extends BasePerformanceUnitTest {
    
    @Test
    public void individualDriverTest() {
        
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_INDIVIDUAL, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(Integer.valueOf(4));
        criteria.init(getMockGroupHierarchy(), GROUP_ID, idList, initInterval());
        
        dump("IndividualDriverPerformance", 1, criteria.getCriteriaList().get(0), FormatType.PDF);
        dump("IndividualDriverPerformance", 1, criteria.getCriteriaList().get(0), FormatType.HTML);

    }
    @Test
    public void teamDriverTest() {
        
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_TEAM, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        criteria.init(getMockGroupHierarchy(), GROUP_ID, initInterval());
        
        dump("TeamDriverPerformance", 1, criteria, FormatType.PDF);
        dump("TeamDriverPerformance", 1, criteria, FormatType.HTML);

    }
    
    class MockDriverPerformanceDAO implements DriverPerformanceDAO{

        Interval interval;
        
        public MockDriverPerformanceDAO(Interval interval)
        {
            this.interval = interval;
        }
        @Override
        public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverID, Interval queryInterval) {
//                String groupName, Integer driverID, String driverName, String employeeID, Integer score, Integer totalMiles, Integer hardAccelCount,
//                Integer hardBrakeCount, Integer hardTurnCount, Integer hardVerticalCount
                return getDriverPerformanceListForGroup(groupID, groupName, queryInterval);
        }

        @Override
        public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval queryInterval) {
            List<DriverPerformance> list = new ArrayList<DriverPerformance>();

            list.add(new DriverPerformance("Group", 100, "Driver NA", "Emp NA", -1, 0, 0,0,0,0,0));
            for (int i = 0; i < 5; i++) {
                list.add(new DriverPerformance("Group", i, "Driver " + i, "Emp " + i, i*10+1, i*1000, i,i,i,i,i));
            }
            return list;
        }
    }

}
