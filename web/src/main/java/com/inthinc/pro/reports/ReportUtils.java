package com.inthinc.pro.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

public class ReportUtils
{
    private static final Logger logger = Logger.getLogger(ReportUtils.class);

    public static JasperReport loadReport(Report reportSection)
    {
        InputStream in = null;
        try
        {
            in = new FileInputStream(loadFile(reportSection.getFilename()));
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

    private static File loadFile(String fileName)
    {
        File file = new File(ReportUtils.class.getResource(fileName).getFile());

        return file;
    }

}
