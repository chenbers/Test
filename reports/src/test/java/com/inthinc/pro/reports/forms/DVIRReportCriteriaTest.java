package com.inthinc.pro.reports.forms;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.service.FormsServiceDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.form.SubmissionData;
import com.inthinc.pro.model.form.SubmissionDataItem;
import com.inthinc.pro.model.form.TriggerType;

public class DVIRReportCriteriaTest {

    @Mocked FormsServiceDAO formsDAO;
    @Mocked @Cascading TimeFrame timeFrame;
    private List<SubmissionData> submissions;
    @Test
    public void creationTest() {
        
        DVIRReportCriteria dvirPreTripReportCriteria = new DVIRPreTripReportCriteria(Locale.getDefault());
        assertNotNull(dvirPreTripReportCriteria);
        assertTrue(dvirPreTripReportCriteria instanceof DVIRPreTripReportCriteria);

        DVIRReportCriteria dvirPostTripReportCriteria = new DVIRPostTripReportCriteria(Locale.getDefault());
        assertNotNull(dvirPostTripReportCriteria);
        assertTrue(dvirPostTripReportCriteria instanceof DVIRPostTripReportCriteria);
    }
    @Before
    public void setupSubmissions(){
        submissions = new ArrayList<SubmissionData>();
        SubmissionData submissionData = new SubmissionData();
        submissionData.setApproved(false);
        submissionData.setDriverID(1);
        submissionData.setDriverName("Jacquie Howard");
        submissionData.setFormID(100);
        submissionData.setFormTitle("Form Title");
        submissionData.setGroupID(4917);
        submissionData.setTimestamp(102030423L);
        submissionData.setVehicleID(200);
        submissionData.setVehicleName("Jacquie's Car");
        List<SubmissionDataItem>dataList = new ArrayList<SubmissionDataItem>();
        for (int i=0; i<4; i++){
            SubmissionDataItem submissionDataItem = new SubmissionDataItem();
            submissionDataItem.setTag(""+i);
            submissionDataItem.setQuestion("question"+i);
            submissionDataItem.setAnswer("answer"+i);
            dataList.add(submissionDataItem);
        }
        submissionData.setDataList(dataList );
        submissions.add(submissionData);
        
        submissionData = new SubmissionData();
        submissionData.setApproved(false);
        submissionData.setDriverID(1);
        submissionData.setDriverName("Colleen Jennings");
        submissionData.setFormID(100);
        submissionData.setFormTitle("Pre Trip Inspection version 2");
        submissionData.setGroupID(4917);
        submissionData.setTimestamp(102030423L);
        submissionData.setVehicleID(200);
        submissionData.setVehicleName("Colleen's Car");
        dataList = new ArrayList<SubmissionDataItem>();
        for (int i=0; i<4; i++){
            SubmissionDataItem submissionDataItem = new SubmissionDataItem();
            submissionDataItem.setTag(""+i);
            submissionDataItem.setQuestion("question"+i);
            submissionDataItem.setAnswer("answer"+i);
            dataList.add(submissionDataItem);
        }
        submissionData.setDataList(dataList );
        submissions.add(submissionData);
    }
    @Ignore
    @Test
    public void buildTest() {
        GroupHierarchy accountGroupHierarchy = new GroupHierarchy();
        final Integer groupID = 4917;

        DVIRReportCriteria dvirPreTripReportCriteria = new DVIRPreTripReportCriteria(Locale.getDefault());
        new Expectations() {
            {
            formsDAO.getSubmissions(TriggerType.PRE_TRIP,timeFrame.getInterval().getStart().toDate(), timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), groupID);returns(submissions);
            timeFrame.getInterval().getStart().toDate();returns(new Date(1345939200L));
            timeFrame.getInterval().getEnd().minusSeconds(1).toDate(); returns (new Date(1346112000L));       
        }};
        dvirPreTripReportCriteria.build(accountGroupHierarchy,formsDAO,groupID,timeFrame);
        assertNotNull(dvirPreTripReportCriteria);
        assertTrue(dvirPreTripReportCriteria instanceof DVIRPreTripReportCriteria);

        DVIRReportCriteria dvirPostTripReportCriteria = new DVIRPostTripReportCriteria(Locale.getDefault());
        new Expectations() {
            {
            formsDAO.getSubmissions(TriggerType.POST_TRIP,timeFrame.getInterval().getStart().toDate(), timeFrame.getInterval().getEnd().minusSeconds(1).toDate(), groupID);returns(submissions);
            timeFrame.getInterval().getStart().toDate();returns(new Date(1345939200L));
            timeFrame.getInterval().getEnd().minusSeconds(1).toDate(); returns (new Date(1346112000L));       
        }};
        dvirPostTripReportCriteria.build(accountGroupHierarchy,formsDAO,groupID,timeFrame);
        assertNotNull(dvirPostTripReportCriteria);
        assertTrue(dvirPostTripReportCriteria instanceof DVIRPostTripReportCriteria);
    }
    
}
