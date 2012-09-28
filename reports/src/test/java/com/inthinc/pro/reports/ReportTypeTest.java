package com.inthinc.pro.reports;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.inthinc.hos.ddl.HosDailyDriverLog;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.DriverStopReport;
import com.inthinc.pro.model.EventReportItem;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.RedFlagReportItem;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.reports.hos.model.DotHoursRemaining;
import com.inthinc.pro.reports.hos.model.DriverDOTLog;
import com.inthinc.pro.reports.hos.model.DrivingTimeViolationsSummary;
import com.inthinc.pro.reports.hos.model.HosEdit;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;
import com.inthinc.pro.reports.hos.model.NonDOTViolationsSummary;
import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.reports.hos.model.ViolationsDetailRaw;
import com.inthinc.pro.reports.ifta.model.MileageByVehicle;
import com.inthinc.pro.reports.ifta.model.StateMileageByMonth;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;
import com.inthinc.pro.reports.ifta.model.StateMileageCompareByGroup;
import com.inthinc.pro.reports.ifta.model.StateMileageFuelByVehicle;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.reports.performance.model.DriverHours;
import com.inthinc.pro.reports.performance.model.PayrollData;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;
import com.inthinc.pro.reports.performance.model.VehicleUsage;

//
//  Compare fields in the jrxml against the model objects.  this will insure that the jasper isn't referencing
//  a field that no longer exists.
//

public class ReportTypeTest {

    private static final String BASE_REPORT_PATH = "src/main/resources/com/inthinc/pro/reports/jasper/";    

    ReportTypeData[] reportTypeDataList = new ReportTypeData[] {
      
            new ReportTypeData(ReportType.DEVICES_REPORT, DeviceReportItem.class),
            new ReportTypeData(ReportType.DOT_HOURS_REMAINING, DotHoursRemaining.class),
            new ReportTypeData(ReportType.DRIVER_HOURS, DriverHours.class),
            new ReportTypeData(ReportType.DRIVER_REPORT, DriverReportItem.class),
            new ReportTypeData(ReportType.DRIVER_SUMMARY_P1, CategorySeriesData.class),
            new ReportTypeData(ReportType.DRIVER_SUMMARY_P2, CategorySeriesData.class),
            new ReportTypeData(ReportType.DRIVING_TIME_VIOLATIONS_DETAIL_REPORT, ViolationsDetail.class, ViolationsDetailRaw.class),
            new ReportTypeData(ReportType.DRIVING_TIME_VIOLATIONS_SUMMARY_REPORT, DrivingTimeViolationsSummary.class),
            new ReportTypeData(ReportType.EMERGENCY_REPORT, EventReportItem.class),
            new ReportTypeData(ReportType.EVENT_REPORT, EventReportItem.class),
            new ReportTypeData(ReportType.HOS_DAILY_DRIVER_LOG_REPORT, HosDailyDriverLog.class),
            new ReportTypeData(ReportType.HOS_DRIVER_DOT_LOG_REPORT, DriverDOTLog.class),
            new ReportTypeData(ReportType.HOS_EDITS,HosEdit.class),
            new ReportTypeData(ReportType.HOS_VIOLATIONS_DETAIL_REPORT, ViolationsDetail.class, ViolationsDetailRaw.class),
            new ReportTypeData(ReportType.HOS_VIOLATIONS_SUMMARY_REPORT, HosViolationsSummary.class),
            new ReportTypeData(ReportType.HOS_ZERO_MILES, HosZeroMiles.class),
            new ReportTypeData(ReportType.IDLE_PERCENTAGE, CategorySeriesData.class),
            new ReportTypeData(ReportType.IDLING_REPORT, IdlingReportItem.class),
            new ReportTypeData(ReportType.MILEAGE_BY_VEHICLE, MileageByVehicle.class),
            new ReportTypeData(ReportType.MPG_GROUP, MpgEntity.class),
            new ReportTypeData(ReportType.NON_DOT_VIOLATIONS_DETAIL_REPORT, ViolationsDetail.class, ViolationsDetailRaw.class),
            new ReportTypeData(ReportType.NON_DOT_VIOLATIONS_SUMMARY_REPORT, NonDOTViolationsSummary.class),
            new ReportTypeData(ReportType.OVERALL_SCORE, PieScoreData.class),
            new ReportTypeData(ReportType.PAYROLL_DETAIL, PayrollData.class),
            new ReportTypeData(ReportType.PAYROLL_SIGNOFF, PayrollData.class),
            new ReportTypeData(ReportType.PAYROLL_SUMMARY, PayrollData.class),
            new ReportTypeData(ReportType.RED_FLAG_REPORT, RedFlagReportItem.class),
            new ReportTypeData(ReportType.SPEED_PERCENTAGE, CategorySeriesData.class),  
            new ReportTypeData(ReportType.STATE_MILEAGE_BY_MONTH, StateMileageByMonth.class),
            new ReportTypeData(ReportType.STATE_MILEAGE_BY_VEHICLE, MileageByVehicle.class),
            new ReportTypeData(ReportType.STATE_MILEAGE_BY_VEHICLE_ROAD_STATUS, StateMileageByVehicleRoadStatus.class),
            new ReportTypeData(ReportType.STATE_MILEAGE_COMPARE_BY_GROUP, StateMileageCompareByGroup.class),
            new ReportTypeData(ReportType.STATE_MILEAGE_FUEL_BY_VEHICLE, StateMileageFuelByVehicle.class),
            new ReportTypeData(ReportType.TEAM_STATISTICS_REPORT, DriverVehicleScoreWrapper.class),
            new ReportTypeData(ReportType.TEAM_STOPS_REPORT, DriverStopReport.class),
            new ReportTypeData(ReportType.TEN_HOUR_DAY_VIOLATIONS, TenHoursViolation.class),
            new ReportTypeData(ReportType.TREND, ScoreableEntity.class),
            new ReportTypeData(ReportType.VEHICLE_REPORT, VehicleReportItem.class),
            new ReportTypeData(ReportType.VEHICLE_SUMMARY_P1, CategorySeriesData.class),
            new ReportTypeData(ReportType.VEHICLE_SUMMARY_P2, CategorySeriesData.class),
            new ReportTypeData(ReportType.VEHICLE_USAGE, VehicleUsage.class),
            new ReportTypeData(ReportType.WARNING_REPORT, EventReportItem.class),
            
            // excluding these because there model objects are in the web project
//          new ReportTypeData(ReportType.CRASH_HISTORY_REPORT, CrashHistoryReportItem.class),
//          new ReportTypeData(ReportType.DRIVER_SEATBELT, EventReportItem.class), 
//          new ReportTypeData(ReportType.DRIVER_SPEED, EventReportItem.class), 
//          new ReportTypeData(ReportType.DRIVER_STYLE, EventReportItem.class),
//          new ReportTypeData(ReportType.VEHICLE_SEATBELT, EventReportItem.class), 
//          new ReportTypeData(ReportType.VEHICLE_SPEED, EventReportItem.class),
//          new ReportTypeData(ReportType.VEHICLE_STYLE, EventReportItem.class),
    };
    
    
    @Test
    public void jasperFieldsTest() {
        
        for (ReportTypeData reportTypeData : reportTypeDataList) {
            String jrxmlPath = getReportPath(reportTypeData.reportType, false);
            checkJasperReport(reportTypeData, jrxmlPath, reportTypeData.reportType.getPrettyTemplate(), reportTypeData.modelObject);

            if (reportTypeData.reportType.getRawTemplate() != null) {
                String jrxmlRawPath = getReportPath(reportTypeData.reportType, true);
                checkJasperReport(reportTypeData, jrxmlRawPath, reportTypeData.reportType.getRawTemplate(),
                        reportTypeData.rawModelObject != null ? reportTypeData.rawModelObject : reportTypeData.modelObject);
            }
            
        }
    }
    private void checkJasperReport(ReportTypeData reportTypeData, String jrxmlPath, String jrxml, Class<?> modelObject) {
  
        List<String> jasperFieldList = getFieldList(jrxmlPath);
        for (String jasperField : jasperFieldList) {
            String rptField = jasperField;

            Class<?> fieldType = modelObject;
            int idx = jasperField.indexOf(".");
            while (idx != -1) {
                String subField = rptField.substring(0, idx);
                fieldType = checkField(subField, fieldType);
                assertTrue(reportTypeData.reportType + "(" + jrxml + ") references field: " + jasperField + " subfield: " + subField + " but not found in model object: " + reportTypeData.getModelObject().getName(), fieldType != null);
                rptField = rptField.substring(idx+1);
                idx = rptField.indexOf(".");
            }
            fieldType = checkField(rptField, fieldType);
            assertTrue(reportTypeData.reportType + "(" + jrxml + ") references field: " + jasperField + " but not found in model object: " + reportTypeData.getModelObject().getName(), fieldType != null);
        }
    }

    private Class<?> checkField(String rptField, Class<?> modelObject) {
        List<Field> fieldList = getAllFields(modelObject);
        Field field = fieldExistsInObject(fieldList, rptField);
        if (field == null) {
            // check for a getter
            return checkForGetter(modelObject, rptField);
        }
        
        return field.getType();
    }

    private Class<?> checkForGetter(Class<?> modelObject, String rptField) {
        try {
            Method getter = modelObject.getMethod("get" + rptField.substring(0,1).toUpperCase() + rptField.substring(1));
            return getter.getReturnType();
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private Field fieldExistsInObject(List<Field> fieldList, String rptField) {
        for (Field field : fieldList) {
            if (field.getName().compareTo(rptField) == 0) {
                return field;
            }
        }
        return null;
    }
    
    private List<Field> getAllFields(Class<?> modelObject) {
        List<Field> allFieldList = new ArrayList<Field>();
        Field[] fieldList = modelObject.getDeclaredFields();
        
        allFieldList.addAll(Arrays.asList(fieldList));
        
        if (modelObject.getSuperclass() != null)
            allFieldList.addAll(getAllFields(modelObject.getSuperclass()));

        return allFieldList;
    }

    private List<String> getFieldList(String path) {
        List<String> fieldList = new ArrayList<String>();
        try
        {
            File file = new File(path);
            //System.out.println("path is " + file.getAbsoluteFile() + " exists: " + file.exists());
            assertTrue(file.getAbsoluteFile() + " exists is FALSE ", file.exists());
            Document doc = parse(file);
            
            NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeName().equalsIgnoreCase("field")) {
                    NamedNodeMap attribs = node.getAttributes();
                
//                    System.out.println(attribs.getNamedItem("name").getNodeValue());
                    String idItem = attribs.getNamedItem("name").getNodeValue();
                    fieldList.add(idItem);
                    
                }
            }
                
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return fieldList;
    }

    public static Document parse(File file) throws Exception
    {
        return createDocumentBuilder().parse(file);
    }

    public static DocumentBuilder createDocumentBuilder() throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setIgnoringComments(true);

        try
        {
            return dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            throw new Exception("Failed to create a document builder factory", e);
        }
    }



    
    private String getReportPath(ReportType reportType, boolean isRaw) {

        StringBuffer path = new StringBuffer(BASE_REPORT_PATH);
        if (reportType.getSubDirectory() != null)
            path.append(reportType.getSubDirectory() + "/");
        
        if (isRaw)
            path.append(reportType.getRawTemplate());
        else path.append(reportType.getPrettyTemplate());
        
        return path.toString();
    }


    class ReportTypeData {
        
        public ReportType reportType;
        public Class<?> modelObject;
        public Class<?> rawModelObject;
        
        public ReportTypeData(ReportType reportType, Class<?> modelObject) {
            this.reportType = reportType;
            this.modelObject = modelObject;
        }
        public ReportTypeData(ReportType reportType, Class<?> modelObject, Class<?> rawModelObject) {
            this.reportType = reportType;
            this.modelObject = modelObject;
            this.rawModelObject = rawModelObject;
        }
        
        
        public Class<?> getRawModelObject() {
            return rawModelObject;
        }
        public void setRawModelObject(Class<?> rawModelObject) {
            this.rawModelObject = rawModelObject;
        }
        public ReportType getReportType() {
            return reportType;
        }
        public void setReportType(ReportType reportType) {
            this.reportType = reportType;
        }
        public Class<?> getModelObject() {
            return modelObject;
        }
        public void setModelObject(Class<?> modelObject) {
            this.modelObject = modelObject;
        }
        
        
    }
    
}
