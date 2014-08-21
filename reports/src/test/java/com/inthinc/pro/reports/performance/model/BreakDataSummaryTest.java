package com.inthinc.pro.reports.performance.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.hos.model.DayData;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.reports.performance.model.BreakDataSummary;

public class BreakDataSummaryTest {
    
    @Mocked Interval mockInterval;
    @Mocked GroupHierarchy mockGroupHierarchy;
    @Mocked Driver mockDriver;
    @Mocked DayData mockDayData;
    @Mocked Person mockPerson;
    
    @Test
    public void testBreakDataSummaryConstructorWithSingleDay() {
        
        // Values mocked days should return
        new NonStrictExpectations() {{
            mockDayData.getOnDutyMinutes(); result = 10;
            mockDayData.getOffDutyMinutes(); result = 11;
            mockDayData.getNumberOfBreaks(); result = 1;
            mockDayData.getBreakMinutes(); result = 12;
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Populate it with a single mock day
        DayData mockDayData = new DayData();
        mockDayDataList.add(mockDayData);
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);

        // Test that our constructor is working as expected
        assertTrue(sut.getOnDutyMinutes() == 10);
        assertTrue(sut.getOffDutyMinutes() == 11);
        assertTrue(sut.getBreakCount() == 1);
        assertTrue(sut.getBreakMinutes() == 12);
    }
    
    @Test
    public void testBreakDataSummaryConstructorWithNoDays() {
        
        // Create a real day list, but don't add any days to it
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);

        // Make sure our constructor is working as expected
        assertTrue(sut.getOnDutyMinutes() == 0);
        assertTrue(sut.getOffDutyMinutes() == 0);
        assertTrue(sut.getBreakCount() == 0);
        assertTrue(sut.getBreakMinutes() == 0);
    }
    
    @Test
    public void testBreakDataSummaryConstructorWithMultipleDays() {
        
        // Values mocked days should return
        new NonStrictExpectations() {{
            mockDayData.getOnDutyMinutes(); result = 10;
            mockDayData.getOffDutyMinutes(); result = 11;
            mockDayData.getNumberOfBreaks(); result = 1;
            mockDayData.getBreakMinutes(); result = 12;
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Populate it with two mocked days
        DayData mockDayData1 = new DayData();
        mockDayDataList.add(mockDayData1);
        DayData mockDayData2 = new DayData();
        mockDayDataList.add(mockDayData2);
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);

        // Make sure our constructor is correctly summing the values of multiple days
        assertTrue(sut.getOnDutyMinutes() == 20);
        assertTrue(sut.getOffDutyMinutes() == 22);
        assertTrue(sut.getBreakCount() == 2);
        assertTrue(sut.getBreakMinutes() == 24);
    }
    
    @Test
    public void testGetDriverName() {
        
        new NonStrictExpectations() {{
            mockDriver.getPerson(); result = mockPerson;
            mockPerson.getFullNameLastFirst(); result = "User, Test";
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);
        
        assertTrue(sut.getDriverName() == "User, Test");
        assertFalse(sut.getDriverName() == "Something, Else");
        
    }
    
    @Test
    public void testGetDriverID() {
        
        new NonStrictExpectations() {{
            mockDriver.getPerson(); result = mockPerson;
            mockPerson.getEmpid(); result = "123456";
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);
        
        assertTrue(sut.getEmployeeID() == "123456");
        assertFalse(sut.getEmployeeID() == "Something else");
        
    }
    
    @Test
    public void testGetGroupName() {
        
        new NonStrictExpectations() {{
            mockGroupHierarchy.getFullGroupName(anyInt); result = "Group Name";
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);
        
        assertTrue(sut.getGroupName() == "Group Name");
        assertFalse(sut.getGroupName() == "Something else");
    }
    
    @Test
    public void testGetGroupID() {
        
        new NonStrictExpectations() {{
            mockDriver.getGroupID(); result = 123456;
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);
        
        assertTrue(sut.getGroupID() == 123456);
        assertFalse(sut.getGroupID() == 987654);
    }
    
    @Test
    public void testGetOnDutyDecimalHours() {
        
        // Values mocked days should return
        new NonStrictExpectations() {{
            mockDayData.getOnDutyMinutes(); result = 60;
            mockDayData.getOffDutyMinutes(); result = 11;
            mockDayData.getNumberOfBreaks(); result = 1;
            mockDayData.getBreakMinutes(); result = 12;
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Populate it with a single mock day
        DayData mockDayData = new DayData();
        mockDayDataList.add(mockDayData);
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);
        
        assertTrue(sut.getOnDutyDecimalHours() == 1.0);
        assertFalse(sut.getOnDutyDecimalHours() == 2.0);
    }
    
    @Test
    public void testGetOffDutyDecimalHours() {
        
        // Values mocked days should return
        new NonStrictExpectations() {{
            mockDayData.getOnDutyMinutes(); result = 60;
            mockDayData.getOffDutyMinutes(); result = 30;
            mockDayData.getNumberOfBreaks(); result = 1;
            mockDayData.getBreakMinutes(); result = 12;
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Populate it with a single mock day
        DayData mockDayData = new DayData();
        mockDayDataList.add(mockDayData);
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);
        
        assertTrue(sut.getOffDutyDecimalHours() == 0.5);
        assertFalse(sut.getOffDutyDecimalHours() == 2.0);
    }
    
    @Test
    public void testGetBreakDecimalHours() {
        
        // Values mocked days should return
        new NonStrictExpectations() {{
            mockDayData.getOnDutyMinutes(); result = 60;
            mockDayData.getOffDutyMinutes(); result = 11;
            mockDayData.getNumberOfBreaks(); result = 1;
            mockDayData.getBreakMinutes(); result = 45;
        }};
        
        // Create a real day list
        List<DayData> mockDayDataList = new ArrayList<DayData>();
        
        // Populate it with a single mock day
        DayData mockDayData = new DayData();
        mockDayDataList.add(mockDayData);
        
        // Instantiate with a bunch of mocked stuff
        BreakDataSummary sut = new BreakDataSummary(mockInterval, mockGroupHierarchy, mockDriver, mockDayDataList);
        
        assertTrue(sut.getBreakDecimalHours() == 0.75);
        assertFalse(sut.getBreakDecimalHours() == 2.0);
    }
    
}
