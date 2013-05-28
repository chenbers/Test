package com.inthinc.pro.reports.dvir;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.FormsDAO;
import com.inthinc.pro.dao.report.DVIRInspectionRepairReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.dvir.DVIRInspectionRepairReport;
import com.inthinc.pro.model.form.SubmissionData;
import com.inthinc.pro.model.form.SubmissionDataItem;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;

public class DVIRInspectionRepairCompleteCriteriaTest extends BaseUnitTest {
    @Mocked
    private DVIRInspectionRepairReportDAO mockedDvirInspectionRepairReportDAO;
    
    @Mocked
    private DriverDAO mockedDriverDAO;
    
    @Mocked
    private FormsDAO formsDAO;
    
    @Mocked
    private Driver mockedDriver;
    
    @Mocked
    private Person mockedPerson;
    
    private int submissionDataCount;
    
    private GroupHierarchy groupHierarchy = new GroupHierarchy();
    
    private static String PERSON_FULL_NAME = "Testty Tester Test";
    private static String DRIVER_NAME = "Drivers Name";
    private static String VEHICLE_NAME = "Vehicle Name";
    private static String INSPECTOR_ID = "1234";
    private static String SIGN_OFF_ID = "5678";
    private static String MECHANIC_ID = "9123";
    private static String BAD_INSPECTOR_ID = "1234A";
    private static String BAD_SIGN_OFF_ID = "5678A";
    private static String BAD_MECHANIC_ID = "9123A";
    
    private static Integer SUBMISSION_DATA_ITEM_COUNT = 2;
    
    @Before
    public void setup() {
        submissionDataCount = 0;
        
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
    
    @SuppressWarnings({ "unchecked" })
    @Test
    public void testGetDVIRInspectionRepairsForGroup_Normal() {
        List<ReportCriteria> reportCriterias = runTestReport(0, 1, 10, INSPECTOR_ID, SIGN_OFF_ID, MECHANIC_ID, false, false);
        
        ReportCriteria criteria = reportCriterias.get(0);
        
        List<DVIRInspectionRepairReport> inspectionList = criteria.getMainDataset();
        Assert.assertNotNull(inspectionList);
        
        DVIRInspectionRepairReport report = inspectionList.get(0);
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_inspectorName());
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_signOffName());
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_mechanicName());
    }
    
    @SuppressWarnings({ "unchecked" })
    @Test
    public void testGetDVIRInspectionRepairsForGroup_BadInspectorDriverID() {
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 2, 10, BAD_INSPECTOR_ID, SIGN_OFF_ID, MECHANIC_ID, false, false);
        
        ReportCriteria criteria = reportCriterias.get(0);
        
        List<DVIRInspectionRepairReport> inspectionList = criteria.getMainDataset();
        Assert.assertNotNull(inspectionList);
        
        DVIRInspectionRepairReport report = inspectionList.get(0);
        Assert.assertEquals(BAD_INSPECTOR_ID, report.getAttr_inspectorName());
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_signOffName());
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_mechanicName());
    }
    
    @SuppressWarnings({ "unchecked" })
    @Test
    public void testGetDVIRInspectionRepairsForGroup_BadSignOffDriverID() {
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 3, 10, INSPECTOR_ID, BAD_SIGN_OFF_ID, MECHANIC_ID, false, false);
        
        ReportCriteria criteria = reportCriterias.get(0);
        
        List<DVIRInspectionRepairReport> inspectionList = criteria.getMainDataset();
        Assert.assertNotNull(inspectionList);
        
        DVIRInspectionRepairReport report = inspectionList.get(0);
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_inspectorName());
        Assert.assertEquals(BAD_SIGN_OFF_ID, report.getAttr_signOffName());
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_mechanicName());
    }
    
    @SuppressWarnings({ "unchecked" })
    @Test
    public void testGetDVIRInspectionRepairsForGroup_BadMechanicDriverID() {
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 4, 10, INSPECTOR_ID, SIGN_OFF_ID, BAD_MECHANIC_ID, false, false);
        
        ReportCriteria criteria = reportCriterias.get(0);
        
        List<DVIRInspectionRepairReport> inspectionList = criteria.getMainDataset();
        Assert.assertNotNull(inspectionList);
        
        DVIRInspectionRepairReport report = inspectionList.get(0);
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_inspectorName());
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_signOffName());
        Assert.assertEquals(BAD_MECHANIC_ID, report.getAttr_mechanicName());
    }
    
    @SuppressWarnings({ "unchecked" })
    @Test
    public void testGetDVIRInspectionRepairsForGroup_BadMechanicDriverID_WithDetail() {
        
        List<ReportCriteria> reportCriterias = runTestReport(0, 5, 10, INSPECTOR_ID, SIGN_OFF_ID, BAD_MECHANIC_ID, true, false);
        
        ReportCriteria criteria = reportCriterias.get(0);
        
        List<DVIRInspectionRepairReport> inspectionList = criteria.getMainDataset();
        Assert.assertNotNull(inspectionList);
        
        DVIRInspectionRepairReport report = inspectionList.get(0);
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_inspectorName());
        Assert.assertEquals(PERSON_FULL_NAME, report.getAttr_signOffName());
        Assert.assertEquals(BAD_MECHANIC_ID, report.getAttr_mechanicName());
    }
    
    @SuppressWarnings("unchecked")
    private List<ReportCriteria> runTestReport(Integer groupId, int caseCount, final int recCount, final String inspectorID, final String signOffID, final String mechanicID, boolean isDetailed,
                    final boolean doUseNullSubmissionData) {
        
        new NonStrictExpectations() {
            {
                mockedDvirInspectionRepairReportDAO.getDVIRInspectionRepairsForGroup((List<Integer>) any, (Interval) any);
                returns(creatInspectionRepairReportTestData(recCount, inspectorID, signOffID, mechanicID));
            }
        };
        
        new NonStrictExpectations() {
            {
                mockedDriverDAO.findByID((Integer) any);
                returns(mockedDriver);
            }
        };
        
        new NonStrictExpectations() {
            {
                mockedDriver.getPerson();
                returns(mockedPerson);
            }
        };
        
        new NonStrictExpectations() {
            {
                mockedPerson.getFullName();
                returns(PERSON_FULL_NAME);
            }
        };
        
        new NonStrictExpectations() {
            {
                formsDAO.getSingleSubmission((Integer) any, (Integer) any, (Date) any, (Integer) any);
                if (doUseNullSubmissionData) {
                    returns(createSubmissionData(true));
                } else {
                    returns(createSubmissionData(false));
                }
            }
        };
        
        List<Integer> groupIds = new ArrayList<Integer>();
        groupIds.addAll(groupHierarchy.getGroupIDList(groupId));
        
        DVIRInspectionRepairCompleteCriteria.Builder builder = new DVIRInspectionRepairCompleteCriteria.Builder(groupHierarchy, groupId, mockedDvirInspectionRepairReportDAO, mockedDriverDAO,
                        formsDAO, groupIds, TimeFrame.MONTH, isDetailed);
        
        List<ReportCriteria> reportCriterias = new ArrayList<ReportCriteria>();
        reportCriterias.add(builder.build());
        
        dump("DvirInspection", caseCount, reportCriterias, FormatType.PDF);
        
        return reportCriterias;
    }
    
    private List<DVIRInspectionRepairReport> creatInspectionRepairReportTestData(int recCount, String inspectorID, String signOffID, String mechanicID) {
        List<DVIRInspectionRepairReport> inspectionReportList = new ArrayList<DVIRInspectionRepairReport>();
        
        for (int i = 0; i < recCount; i++) {
            DVIRInspectionRepairReport report = null;
            
            Integer vehicleID = 57535;
            Integer formDefinitionID = 57535;
            Date submissionDate = new Date(1366661302);
            Integer groupID = 7823;
            
            report = new DVIRInspectionRepairReport(new Date(new Date().getTime() + 14400000), DRIVER_NAME, VEHICLE_NAME, vehicleID, "groupName", groupID, String.valueOf(formDefinitionID),
                            "1366661302", inspectorID, mechanicID, signOffID, "Some Repair Comments" + i);
            
            if (report != null) {
                inspectionReportList.add(report);
            }
        }
        return inspectionReportList;
    }
    
    private SubmissionData createSubmissionData(Boolean doUseNull) {
        if (doUseNull) {
            return null;
        }
        
        Integer driverID = 1;
        String driverName = "Driver Sub Name";
        Integer vehicleID = 1;
        String vehicleName = "Vehicle Sub Name";
        Integer groupID = 1;
        Integer formID = 1;
        Long timestamp = 1L;
        Boolean approved = true;
        String formTitle = "Form Title long long long long long long long long long long ";
        
        SubmissionData retVal = new SubmissionData(driverID, driverName, vehicleID, vehicleName, groupID, formID, timestamp, approved, formTitle);
        
        retVal.setDataList(createSubmissionDataItems(SUBMISSION_DATA_ITEM_COUNT));
        
        return retVal;
    }
    
    private List<SubmissionDataItem> createSubmissionDataItems(Integer count) {
        List<SubmissionDataItem> retVal = new ArrayList<SubmissionDataItem>();
        
        for (int i = 0; i < count; i++) {
            SubmissionDataItem dataItem = new SubmissionDataItem("Tag " + i,
                            "This is a really long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long  question "
                                            + i, "This is answer a really long long long long long!!! " + i);
            retVal.add(dataItem);
        }
        
        return retVal;
    }
}
