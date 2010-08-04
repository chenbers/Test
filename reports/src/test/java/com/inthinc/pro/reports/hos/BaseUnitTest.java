package com.inthinc.pro.reports.hos;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.junit.Test;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.jasper.JasperReportCreator;

public class BaseUnitTest {
    
    @Test
    public void dummy() {
        assertTrue(true);
    }
    
    protected void dump(String prefix, int testCaseCnt, ReportCriteria reportCriteria, FormatType formatType) {
        // remove comments to get pdf or xls dump of report
        ReportCreator<JasperReport> reportCreator = new JasperReportCreator(null);
        Report report = reportCreator.getReport(reportCriteria);
        OutputStream out = null;
        try {
            out = new FileOutputStream("c:\\" + prefix + testCaseCnt + ((formatType == FormatType.PDF) ? ".pdf" : ".xls"));
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
    
    protected void dump(String prefix, int testCaseCnt, List<ReportCriteria> reportCriteriaList, FormatType formatType) {
        // remove comments to get pdf or xls dump of report
        ReportCreator<JasperReport> reportCreator = new JasperReportCreator(null);
        Report report = reportCreator.getReport(reportCriteriaList);
        OutputStream out = null;
        try {
            out = new FileOutputStream("c:\\" + prefix + testCaseCnt + ((formatType == FormatType.PDF) ? ".pdf" : ".xls"));
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


}
