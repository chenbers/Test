package com.inthinc.pro.scheduler;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportManagerDeliveryType;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.User;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCategory;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.reports.jasper.JasperReportBuilder;
import com.inthinc.pro.reports.service.ReportCriteriaService;
import junit.framework.Assert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Test that exports all reports to a specified disk location
 * in all available formats for visual inspection.
 */

@ContextConfiguration(locations = {"classpath:/applicationContext-beans.xml",
        "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-daoBeans.xml",
        "classpath:/applicationContext-daoJDBCBeans.xml",
        "classpath:/applicationContext-reports.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class MassReportExporterTest {
    @Autowired
    ReportCriteriaService reportCriteriaService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    GroupDAO groupDAO;

    private List<ReportSchedule> testData = new ArrayList<ReportSchedule>();
    private final Integer ACCOUNT_ID = 1;
    private final Integer USER_ID = 3980;
    private final Integer GROUP_ID = 1;
    private final Integer EXCEL_MAX_ROWS = Integer.valueOf(65535);
    private DateTime today = new DateTime();
    private JasperReportBuilder reportBuilder = new JasperReportBuilder();
    private Properties testProperties = new Properties();
    private final Logger logger = Logger.getLogger(MassReportExporterTest.class);
    private User user;
    private Person person;
    private boolean useRawTemplateForTabularFormats = false;
    private GroupHierarchy accountGroupHierarchy;

    @Test
    public void massExportTest() {
        String baseFilePath = testProperties.getProperty("massReportExporterTest.baseDir");
        if (baseFilePath == null && !baseFilePath.trim().isEmpty()) {
            logger.fatal("Cannot find base dir property for exporter.");
            Assert.fail();
        }

        File baseDirForExporter = new File(baseFilePath.trim());
        if (!baseDirForExporter.exists()) {
            logger.fatal("Base dir for exporter does not exist.");
            Assert.fail();
        }

        for (ReportSchedule reportSchedule : testData) {
            try {
                List<ReportCriteria> reportCriteriaList = reportCriteriaService.getReportCriteria(reportSchedule, accountGroupHierarchy, person);
                if (reportCriteriaList.isEmpty())
                    continue;

                Set<FormatType> formats = EnumSet.allOf(FormatType.class);
                for (FormatType format : formats) {
                    JasperPrint jp = null;
                    if (useRawTemplateForTabularFormats)
                        jp = reportBuilder.buildReport(reportCriteriaList, format);
                    else
                        jp = reportBuilder.buildReport(reportCriteriaList, FormatType.PDF);

                    byte[] bytes;
                    String ext = ".pdf";

                    if (format == FormatType.EXCEL) {
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        exportToExcelStream(os, jp);
                        bytes = os.toByteArray();
                        ext = ".xls";
                    } else if (format == FormatType.CSV) {
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        exportToCsvStream(os, jp);
                        bytes = os.toByteArray();
                        ext = ".csv";
                    } else {
                        bytes = JasperExportManager.exportReportToPdf(jp);
                    }

                    long milis = System.currentTimeMillis();
                    File reportFile = new File(baseDirForExporter.getAbsolutePath() + File.separator + reportSchedule.getName() + "_" + milis + ext);
                    if (reportFile.exists())
                        reportFile.delete();

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(reportFile);
                        fos.write(bytes);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (Throwable t2) {
                            t2.printStackTrace();
                        }
                    }
                }
            } catch (Throwable t) {
               logger.error("Exception when creating report: "+reportSchedule.getName());
               t.printStackTrace();
            }
        }
    }


    @Before
    public void loadTestData() throws IOException {
        testProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("it.properties"));
        user = userDAO.findByID(USER_ID);
        person = user.getPerson();
        String useRawTemplateStr = testProperties.getProperty("massReportExporterTest.useRawTemplateForTabularFormats");
        useRawTemplateForTabularFormats = useRawTemplateStr != null && useRawTemplateStr.trim().equals("true");

        accountGroupHierarchy = getAccountGroupHierarchy(ACCOUNT_ID);
        Map<Integer, String> items = new HashMap<Integer, String>();

        for (ReportCategory cat : ReportCategory.values()) {
            items.putAll(getItemsByCategory(cat));
        }

        int i = 0;
        for (Map.Entry<Integer, String> entry : items.entrySet()) {
            ReportSchedule reportSchedule = new ReportSchedule();
            reportSchedule.setReportScheduleID(i);
            reportSchedule.setReportID(entry.getKey());
            reportSchedule.setName(entry.getValue());
            reportSchedule.setStatus(Status.ACTIVE);
            reportSchedule.setAccountID(ACCOUNT_ID);
            reportSchedule.setUserID(USER_ID);
            reportSchedule.setGroupIDList(Arrays.asList(GROUP_ID));
            reportSchedule.setReportTimeFrame(TimeFrame.TODAY);
            reportSchedule.setStartDate(today.minusHours(1).withMinuteOfHour(0).toDate());
            reportSchedule.setTimeOfDay(today.getMinuteOfDay() + 30);
            reportSchedule.setOccurrence(Occurrence.DAILY);
            reportSchedule.setLastDate(today.minusDays(3).toDate());
            reportSchedule.setDeliverToManagers(false);
            reportSchedule.setLastDate(today.plusDays(4).withMinuteOfHour(0).toDate());
            reportSchedule.setDriverID(6321);
            reportSchedule.setIncludeInactiveDrivers(false);
            reportSchedule.setIncludeZeroMilesDrivers(false);
            reportSchedule.setLastDate(today.minusDays(2).toDate());
            reportSchedule.setManagerDeliveryType(ReportManagerDeliveryType.ALL);
            reportSchedule.setIftaOnly(false);

            testData.add(reportSchedule);
            i++;
        }
    }

    private void exportToExcelStream(OutputStream out, JasperPrint jasperPrint) throws JRException {
        JRXlsExporter jexcelexporter = new JRXlsExporter();
        jexcelexporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        jexcelexporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BACKGROUND, Boolean.FALSE);
        jexcelexporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, EXCEL_MAX_ROWS);
        jexcelexporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");
        jexcelexporter.exportReport();
    }

    private void exportToCsvStream(OutputStream outputStream, JasperPrint jp) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
        exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.exportReport();
    }

    private Map<Integer, String> getItemsByCategory(ReportCategory category) {
        Map<Integer, String> items = new HashMap<Integer, String>();
        for (ReportGroup rt : EnumSet.allOf(ReportGroup.class)) {

            String label = rt.getLabel().replace("\\", "_").replace("/", "_").replace(" ", "_");
            if (label==null)
                label = "unknown";
            if (category == null && rt.getReportCategory() == null) {
                items.put(rt.getCode(), label);
                continue;
            }
            if (!rt.isCategory(category))
                continue;

            items.put(rt.getCode(), label);
        }
        return items;
    }

    public GroupHierarchy getAccountGroupHierarchy(Integer accountID) {
        Map<Integer, GroupHierarchy> map = new HashMap<Integer, GroupHierarchy>();
        if (map.get(accountID) == null) {
            List<Group> groupList = groupDAO.getGroupsByAcctID(accountID);
            map.put(accountID, new GroupHierarchy(groupList));
        }

        return map.get(accountID);
    }
}
