package com.inthinc.pro.reports.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.form.SubmissionData;
import com.inthinc.pro.model.form.SubmissionDataItem;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;

public class DVIRReportTest extends BaseUnitTest{

    public List<SubmissionData> setupSubmissions() {
        List<SubmissionData> submissions = new ArrayList<SubmissionData>();
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
        List<SubmissionDataItem> dataList = new ArrayList<SubmissionDataItem>();
        for (int i = 0; i < 4; i++) {
            SubmissionDataItem submissionDataItem = new SubmissionDataItem();
            submissionDataItem.setTag("" + i);
            submissionDataItem.setQuestion("question" + i);
            submissionDataItem.setAnswer("answer" + i);
            dataList.add(submissionDataItem);
        }
        submissionData.setDataList(dataList);
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
        for (int i = 0; i < 4; i++) {
            SubmissionDataItem submissionDataItem = new SubmissionDataItem();
            submissionDataItem.setTag("" + i);
            submissionDataItem.setQuestion("question" + i);
            submissionDataItem.setAnswer("answer" + i);
            dataList.add(submissionDataItem);
        }
        submissionData.setDataList(dataList);
        submissions.add(submissionData);
        return submissions;
    }

    @Test
    public void testPreTripCriteriaTest(){
        
        
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(12);
        List<ReportCriteria> criteriaList = new ArrayList<ReportCriteria>();
        ReportCriteria reportCriteria = new DVIRPreTripReportCriteria(Locale.ENGLISH);
        reportCriteria.setTimeFrame(TimeFrame.ONE_DAY_AGO);
        reportCriteria.setGroupID(4917);
        List<SubmissionData> submissions = setupSubmissions();
        reportCriteria.setMainDataset(submissions);
        criteriaList.add(reportCriteria);
        
        

        dump("preTrip", 1, criteriaList, FormatType.PDF);
        dump("preTrip", 2, criteriaList, FormatType.CSV);
        dump("preTrip", 3, criteriaList, FormatType.HTML);
        dump("preTrip", 4, criteriaList, FormatType.EXCEL);
   }
    @Test
    public void testPostTripCriteriaTest(){
        
        
        List<Integer> groupIDs = new ArrayList<Integer>();
        groupIDs.add(12);
        List<ReportCriteria> criteriaList = new ArrayList<ReportCriteria>();
        ReportCriteria reportCriteria = new DVIRPostTripReportCriteria(Locale.ENGLISH);
        reportCriteria.setTimeFrame(TimeFrame.PAST_SEVEN_DAYS);
        reportCriteria.setGroupID(4917);
        List<SubmissionData> submissions = setupSubmissions();
        reportCriteria.setMainDataset(submissions);
        criteriaList.add(reportCriteria);
        
        

        dump("postTrip", 5, criteriaList, FormatType.PDF);
        dump("postTrip", 6, criteriaList, FormatType.CSV);
        dump("postTrip", 7, criteriaList, FormatType.HTML);
        dump("postTrip", 8, criteriaList, FormatType.EXCEL);
    }
}
