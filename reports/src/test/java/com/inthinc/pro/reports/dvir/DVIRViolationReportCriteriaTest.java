package com.inthinc.pro.reports.dvir;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.report.DVIRViolationReportDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.dvir.DVIRViolationReport;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.dvir.DVIRViolationReportCriteria.ViolationTypeListWharehouse;

public class DVIRViolationReportCriteriaTest extends BaseUnitTest {
    // private static Logger logger = Logger.getLogger(DVIRViolationReportCriteriaTest.class);
    
    @Mocked
    private DVIRViolationReportDAO dvirViolationDao;
    
    private GroupHierarchy groupHierarchy = new GroupHierarchy();
    private List<DVIRViolationReport> violationReportList;
    
    @Before
    public void setup() {
        List<Group> groups = new ArrayList<Group>();
        Group rootGroup = new Group();
        rootGroup.setAccountID(1);
        rootGroup.setGroupID(0);
        rootGroup.setName("Root Group");
        rootGroup.setPath("0");
        groups.add(rootGroup);
        
        Group parentGroup = new Group();
        parentGroup.setAccountID(1);
        parentGroup.setGroupID(1);
        parentGroup.setName("Parent Group");
        parentGroup.setPath("0,1");
        parentGroup.setParentID(0);
        groups.add(parentGroup);
        
        Group childGroup = new Group();
        childGroup.setAccountID(1);
        childGroup.setGroupID(2);
        childGroup.setName("Team Group");
        childGroup.setPath("0,1,2");
        childGroup.setParentID(1);
        groups.add(childGroup);
        
        groupHierarchy.setGroupList(groups);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testDVIRViolationReportCriteriaBuilder_allViolations() {
        DVIRViolationReportCriteria.ViolationTypeListWharehouse wharehouse = null;
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 1, 20, false, false, false);
        Assert.assertNotNull(reportCriterias);
        Assert.assertNotNull(reportCriterias.get(0).getMainDataset());
        
        wharehouse = (ViolationTypeListWharehouse) reportCriterias.get(0).getMainDataset().get(0);
        Assert.assertNotNull(wharehouse);
        
        Assert.assertNotNull(wharehouse.getUnsafeViolationList());
        Assert.assertNotNull(wharehouse.getNoPreTripInspectionList());
        Assert.assertNotNull(wharehouse.getNoPostTripInspectionList());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testDVIRViolationReportCriteriaBuilder_allViolationsManyResults() {
        DVIRViolationReportCriteria.ViolationTypeListWharehouse wharehouse = null;
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 2, 200, false, false, false);
        Assert.assertNotNull(reportCriterias);
        Assert.assertNotNull(reportCriterias.get(0).getMainDataset());
        
        wharehouse = (ViolationTypeListWharehouse) reportCriterias.get(0).getMainDataset().get(0);
        Assert.assertNotNull(wharehouse);
        
        Assert.assertNotNull(wharehouse.getUnsafeViolationList());
        Assert.assertNotNull(wharehouse.getNoPreTripInspectionList());
        Assert.assertNotNull(wharehouse.getNoPostTripInspectionList());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testDVIRViolationReportCriteriaBuilder_allViolationsNoResults() {
        DVIRViolationReportCriteria.ViolationTypeListWharehouse wharehouse = null;
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 3, 0, false, false, false);
        Assert.assertNotNull(reportCriterias);
        Assert.assertNotNull(reportCriterias.get(0).getMainDataset());
        
        wharehouse = (ViolationTypeListWharehouse) reportCriterias.get(0).getMainDataset().get(0);
        Assert.assertNotNull(wharehouse);
        
        Assert.assertNull(wharehouse.getUnsafeViolationList());
        Assert.assertNull(wharehouse.getNoPreTripInspectionList());
        Assert.assertNull(wharehouse.getNoPostTripInspectionList());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testDVIRViolationReportCriteriaBuilder_NoUnsafeViolations() {
        DVIRViolationReportCriteria.ViolationTypeListWharehouse wharehouse = null;
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 4, 20, true, false, false);
        Assert.assertNotNull(reportCriterias);
        Assert.assertNotNull(reportCriterias.get(0).getMainDataset());
        
        wharehouse = (ViolationTypeListWharehouse) reportCriterias.get(0).getMainDataset().get(0);
        Assert.assertNotNull(wharehouse);
        
        Assert.assertNull(wharehouse.getUnsafeViolationList());
        Assert.assertNotNull(wharehouse.getNoPreTripInspectionList());
        Assert.assertNotNull(wharehouse.getNoPostTripInspectionList());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testDVIRViolationReportCriteriaBuilder_NoPreTripViolations() {
        DVIRViolationReportCriteria.ViolationTypeListWharehouse wharehouse = null;
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 5, 20, false, true, false);
        Assert.assertNotNull(reportCriterias);
        Assert.assertNotNull(reportCriterias.get(0).getMainDataset());
        
        wharehouse = (ViolationTypeListWharehouse) reportCriterias.get(0).getMainDataset().get(0);
        Assert.assertNotNull(wharehouse);
        
        Assert.assertNotNull(wharehouse.getUnsafeViolationList());
        Assert.assertNull(wharehouse.getNoPreTripInspectionList());
        Assert.assertNotNull(wharehouse.getNoPostTripInspectionList());
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testDVIRViolationReportCriteriaBuilder_NoPostTripViolations() {
        DVIRViolationReportCriteria.ViolationTypeListWharehouse wharehouse = null;
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 6, 20, false, false, true);
        Assert.assertNotNull(reportCriterias);
        Assert.assertNotNull(reportCriterias.get(0).getMainDataset());
        
        wharehouse = (ViolationTypeListWharehouse) reportCriterias.get(0).getMainDataset().get(0);
        Assert.assertNotNull(wharehouse);
        
        Assert.assertNotNull(wharehouse.getUnsafeViolationList());
        Assert.assertNotNull(wharehouse.getNoPreTripInspectionList());
        Assert.assertNull(wharehouse.getNoPostTripInspectionList());
    }
    
    private List<ReportCriteria> runTestReport(Integer groupId, int caseCount, final int recCount, final boolean nullOutUnsafeList, final boolean nullOutPreTripList,
                    final boolean nullOutPostTripViolation) {
        // creatViolationReportTestData(recCount, nullOutUnsafeList, nullOutPreTripList, nullOutPostTripViolation);
        
        new NonStrictExpectations() {
            {
                dvirViolationDao.getDVIRViolationsForGroup((List) any, (Interval) any);
                returns(creatViolationReportTestData(recCount, nullOutUnsafeList, nullOutPreTripList, nullOutPostTripViolation));
            }
        };
        
        List<Integer> groupIds = new ArrayList<Integer>();
        groupIds.addAll(groupHierarchy.getGroupIDList(groupId));
        
        List<DVIRViolationReport> smallList = new ArrayList<DVIRViolationReport>();
        
        DVIRViolationReportCriteria.Builder builder = new DVIRViolationReportCriteria.Builder(groupHierarchy, groupId, dvirViolationDao, groupIds, TimeFrame.MONTH);
        
        List<ReportCriteria> reportCriterias = new ArrayList<ReportCriteria>();
        reportCriterias.add(builder.build());
        
        dump("DvirViolation", caseCount, reportCriterias, FormatType.PDF);
        
        return reportCriterias;
    }
    
    private List<DVIRViolationReport> creatViolationReportTestData(int recCount, boolean nullOutUnsafeList, boolean nullOutPreTripList, boolean nullOutPostTripViolation) {
        List<DVIRViolationReport> violationReportList = new ArrayList<DVIRViolationReport>();
        
        for (int i = 0; i < recCount; i++) {
            DVIRViolationReport report = null;
            
            if (i % 2 == 0 && !nullOutPostTripViolation) {
                report = new DVIRViolationReport();
                report.setDateTime(new Date(new Date().getTime() + 1800000));
                report.setDriverName("TestDriverName" + String.valueOf(i));
                report.setVehicleName("TestVehicleName" + String.valueOf(i));
                report.setNoteType(NoteType.DVIR_DRIVEN_NOPOSTINSPEC);
                report.setGroupName("TestGroup1");
            } else if (i % 3 == 0 && !nullOutPreTripList) {
                report = new DVIRViolationReport();
                report.setDateTime(new Date(new Date().getTime() + 1800000));
                report.setDriverName("TestDriverName" + String.valueOf(i));
                report.setVehicleName("TestVehicleName" + String.valueOf(i));
                report.setNoteType(NoteType.DVIR_DRIVEN_NOPREINSPEC);
                report.setGroupName("TestGroup2");
            } else if (!nullOutUnsafeList) {
                report = new DVIRViolationReport();
                report.setDateTime(new Date(new Date().getTime() + 1800000));
                report.setDriverName("TestDriverName" + String.valueOf(i));
                report.setVehicleName("TestVehicleName" + String.valueOf(i));
                report.setNoteType(NoteType.DVIR_DRIVEN_UNSAFE);
                report.setGroupName("TestGroup3");
            }
            
            if (report != null) {
                violationReportList.add(report);
            }
        }
        return violationReportList;
    }
}
