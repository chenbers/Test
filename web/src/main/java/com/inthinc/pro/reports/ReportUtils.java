package com.inthinc.pro.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportUtils
{
    private static Logger logger = Logger.getLogger(ReportUtils.class);

//    public static JasperPrint loadReport(ReportType reportType)
//    {
//        JasperPrint jasperPrint = null;
//
//        for (int i = 0; i < reportType.getReportSections().length; i++)
//        {
//            JasperPrint jp = null;
//            try
//            {
//                jp = JasperFillManager.fillReport(loadReport(reportType.getReportSections()[i]), null);
//                if (jasperPrint == null)
//                {
//                    jasperPrint = jp;
//                }
//                else
//                {
//                    for (Object o : jp.getPages())
//                    {
//                        jasperPrint.addPage((JRPrintPage) o);
//                    }
//                }
//
//            }
//            catch (JRException e)
//            {
//                logger.error(e.getMessage());
//            }
//
//        }
//
//        return jasperPrint;
//    }

    public static JasperReport loadReport(ReportType reportSection)
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
        File file = new File(ReportUtils.class.getResource(fileName + ".jrxml").getFile());

        return file;
    }

}
