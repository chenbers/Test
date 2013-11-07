package com.inthinc.pro.reports;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.jasper.JasperReportCreator;
import com.inthinc.pro.reports.tabular.Result;

public class BaseUnitTest {
    /* Windows path */
    public static final String BASE_PATH = "c:/reportDump/";
    /* Mac/Linux path */
	//public static final String BASE_PATH = "/reportDump/";
    
	// switch to true to get a file dump of the report for debug/test
    public static final boolean DUMP_TO_FILE = false;
    //public static final boolean DUMP_TO_FILE = true;
    
    @Test
    public void dummy() {
        assertTrue(true);
    }
    
    protected void dump(String prefix, int testCaseCnt, ReportCriteria reportCriteria, FormatType formatType) {
        // remove comments to get pdf or xls dump of report
        ReportCreator<JasperReport> reportCreator = new JasperReportCreator(null);
        Report report = reportCreator.getReport(reportCriteria);
        
        genReport(prefix, testCaseCnt, formatType, report);
    }

    
    protected void dump(String prefix, int testCaseCnt, List<ReportCriteria> reportCriteriaList, FormatType formatType) {
        // remove comments to get pdf or xls dump of report
        ReportCreator<JasperReport> reportCreator = new JasperReportCreator(null);
        Report report = reportCreator.getReport(reportCriteriaList);
        genReport(prefix, testCaseCnt, formatType, report);

    }

    private void genReport(String prefix, int testCaseCnt, FormatType formatType, Report report) {

        if (!DUMP_TO_FILE)
            return;
//        String ext = ((formatType == FormatType.PDF) ? ".pdf" : ((formatType == FormatType.EXCEL) ? ".xls" : ".html"));
        
        OutputStream out = null;
        try {
            out = new FileOutputStream(BASE_PATH + prefix + testCaseCnt + formatType.getSuffix());
            report.exportReportToStream(formatType, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected String genReportToString(ReportCriteria reportCriteria, FormatType formatType) {
        ReportCreator<JasperReport> reportCreator = new JasperReportCreator(null);
        Report report = reportCreator.getReport(reportCriteria);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            report.exportReportToStream(formatType, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out.toString();
    }
    protected void dumpTabularData(List<List<Result>> tablularData) {
        
        for (List<Result> row : tablularData) {
            System.out.print("  {");
                for (Result result : row) {
                    System.out.print("\"" + result.getDisplay() + "\", "); 
                }
            System.out.println("  },");
        }
        
    }
    
    private static final String AMPERSAND = "&";
    private static final String XML_AMPERSAND = "&amp;";
    private static final String LESS_THAN = "<";
    private static final String XML_LESS_THAN = "&lt;";
    private static final String GREATER_THAN = ">";
    private static final String XML_GREATER_THAN = "&gt;";
    private static final String QUOTE = "\"";
    private static final String XML_QUOTE = "&quot;";
    private static final String APOSTROPHE = "'";
    private static final String XML_APOSTROPHE = "&apos;";

    public String escapeXMLChars(String input) {
        return (input == null) ? "" : input.replace(AMPERSAND, XML_AMPERSAND).replace(LESS_THAN, XML_LESS_THAN).replace(GREATER_THAN, XML_GREATER_THAN).replace(QUOTE, XML_QUOTE)
                .replace(APOSTROPHE, XML_APOSTROPHE);
    }

}
