package com.inthinc.pro.reports.jasper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.ReportType;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

public class ReportUtils
{
    private static final Logger logger = Logger.getLogger(ReportUtils.class);
    private static final String PACKAGE_PATH = "com/inthinc/pro/reports/jasper/";

    public static JasperReport loadReport(ReportType reportSection)
    {
        InputStream in = null;
        try
        {
            in = loadFile(reportSection.getFilename());
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

    private static InputStream loadFile(String fileName)
    {
        String path = PACKAGE_PATH + fileName;
        InputStream inputStream = ReportUtils.class.getClassLoader().getResourceAsStream(path);
        //File file = new File(ReportUtils.class.getResource(fileName).getFile());
        return inputStream;
    }

}
