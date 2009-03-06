package com.inthinc.pro.reports.jasper;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportType;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

public class ReportUtils
{
    private static final Logger logger = Logger.getLogger(ReportUtils.class);
    private static final String PACKAGE_PATH = "com/inthinc/pro/reports/jasper/";

    public static JasperReport loadReport(ReportType reportType,FormatType formatType)
    {
        InputStream in = null;
        try
        {
            if(formatType != null && formatType.equals(FormatType.EXCEL) && reportType.getRawTemplate() != null)
                in = loadFile(reportType.getRawTemplate());
            else
                in = loadFile(reportType.getPrettyTemplate());
            
            
            JasperReport jasperReport = JasperCompileManager.compileReport(in);

            return jasperReport;
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    public static InputStream loadFile(String fileName)
    {
        String path = PACKAGE_PATH + fileName;
        InputStream inputStream = ReportUtils.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }

}
