package com.inthinc.pro.backing;

import static org.junit.Assert.*;

import java.util.List;

import javax.faces.model.SelectItemGroup;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Test;

import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.util.MessageUtil;

public class ReportScheduleBeanTest {
    
    final ReportScheduleBean reportScheduleBean = new ReportScheduleBean();
    List<SelectItemGroup> reportGroups;
    
    @Test
    public void testGetReportGroupsIncludesMaintenanceWhenEnabled() {
        new NonStrictExpectations(reportScheduleBean){{
            
            // Account permissions we aren't testing here
            invoke(reportScheduleBean, "getAccountIsWaysmart"); result = true;
            invoke(reportScheduleBean, "getAccountIsHOS"); result = true;
            invoke(reportScheduleBean, "getAccountIsDriveTimeViolationsReportEnabled"); result = true;
            
            // Permission under test
            invoke(reportScheduleBean, "getAccountIsMaintenance"); result = true;
        }};
        
        reportGroups = reportScheduleBean.getReportGroups();
        
        SelectItemGroup maintenanceGroup = null;
        
        for(SelectItemGroup group: reportScheduleBean.getReportGroups()){
            if("ReportCategory.Maintenance".equals(group.getDescription())){
                maintenanceGroup = group;
            }
        }
        
        assertNotNull(maintenanceGroup);
        assertEquals(maintenanceGroup.getDescription(), "ReportCategory.Maintenance");
        
    }
    
    @Test
    public void testGetReportGroupsExcludesMaintenanceWhenDisabled() {
        new NonStrictExpectations(reportScheduleBean){{
            
            // Account permissions we aren't testing here
            invoke(reportScheduleBean, "getAccountIsWaysmart"); result = true;
            invoke(reportScheduleBean, "getAccountIsHOS"); result = true;
            invoke(reportScheduleBean, "getAccountIsDriveTimeViolationsReportEnabled"); result = true;
            
            // Permission under test
            invoke(reportScheduleBean, "getAccountIsMaintenance"); result = false;
        }};
        
        reportGroups = reportScheduleBean.getReportGroups();
        
        SelectItemGroup maintenanceGroup = null;
        
        for(SelectItemGroup group: reportScheduleBean.getReportGroups()){
            if("ReportCategory.Maintenance".equals(group.getDescription())){
                maintenanceGroup = group;
            }
        }
        
        assertNull(maintenanceGroup);
        
    }
    
}
