package com.inthinc.pro.reports;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Test;

import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.jasper.JasperReportCreator;
import com.inthinc.pro.reports.tabular.Result;
import com.inthinc.pro.reports.util.MessageUtil;

public class BaseUnitTest {
    
    public static final String BASE_PATH = "c:/reportDump/";
    
    // switch to true to get a file dump of the report for debug/test
    public static final boolean DUMP_TO_FILE = true;
    
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
        
        String ext = ((formatType == FormatType.PDF) ? ".pdf" : ((formatType == FormatType.EXCEL) ? ".xls" : ".html"));
        
        OutputStream out = null;
        try {
            out = new FileOutputStream(BASE_PATH + prefix + testCaseCnt + ext);
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

    protected void dumpTabularData(List<List<Result>> tablularData) {
        
        for (List<Result> row : tablularData) {
            System.out.print("  {");
                for (Result result : row) {
                    System.out.print("\"" + result.getDisplay() + "\", "); 
                }
            System.out.println("  },");
        }
        
    }
    
    /**
     * Retrieve the list of missing keys within a resource bundle.
     * @param keys  The list of keys to check within the resource bundle
     * @param resourceBundleName The resource bundle name
     * @param locale the user locale settings
     * 
     * @return List of missing keys or null if no keys are missing within the resource bundle
     */
    protected List checkResourceBundleKeys(String[] keys, String resourceBundleName, Locale locale){
        List missingKeys = new ArrayList(); 
     
        if(locale == null || keys == null | resourceBundleName == null) {
            missingKeys.add("invalid parameters");
        }
        if (missingKeys.isEmpty()){
            ResourceBundle rb = MessageUtil.getBundle(locale, resourceBundleName);
            if(rb == null){
                missingKeys.add("invalid resource bundle");
            }
            else {
                for(String key : keys){
                    try{ 
                        rb.getString(key);                  
                    }
                    catch(MissingResourceException e){
                        missingKeys.add(key);
                    }                  
                }
            }
        }    
        return missingKeys;
    }
    
}
