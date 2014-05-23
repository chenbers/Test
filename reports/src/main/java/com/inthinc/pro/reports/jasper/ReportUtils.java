package com.inthinc.pro.reports.jasper;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.exception.ReportRuntimeException;

public class ReportUtils
{
    private static final Logger logger = Logger.getLogger(ReportUtils.class);
    private static final String PACKAGE_PATH = "com/inthinc/pro/reports/jasper/";

    public static JasperReport loadReport(ReportType reportType,FormatType formatType) throws IOException
    {
        InputStream in = null;
        try
        {
            if(formatType != null && (formatType.equals(FormatType.EXCEL) || formatType.equals(FormatType.CSV)) && reportType.getRawTemplate() != null)
                in = getRawOrPrettyJasper(reportType);
            else
                in = getPrettyOrRawJasper(reportType);
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(in);

//            if(formatType != null && formatType.equals(FormatType.EXCEL) && reportType.getRawTemplate() != null)
//                in = loadFile(reportType.getRawTemplate());
//            else
//                in = loadFile(reportType.getPrettyTemplate());
//            
//
//            JasperReport jasperReport = JasperCompileManager.compileReport(in);

            return jasperReport;
        }
        catch (JRException e) {
            throw new ReportRuntimeException(e);
        }
        finally
        {
            if(in != null)
                in.close();
        }
    }

    /**
     * Tries to get raw jasper but defaults to pretty jasper if it fails.
     *
     * @param reportType report type
     * @return raw or pretty
     * @throws IOException
     */
  private static InputStream getRawOrPrettyJasper(ReportType reportType) throws IOException {
       if (reportType.getRawJasper()!=null){
           return loadFile(reportType.getRawJasper());
       }else{
           return loadFile(reportType.getPrettyJasper());
       }
   }


    /**
     * Tries to get pretty jasper but defaults to raw jasper if it fails.
     *
     * @param reportType report type
     * @return pretty or raw
     * @throws IOException
     */
    private static InputStream getPrettyOrRawJasper(ReportType reportType) throws IOException {
        if (reportType.getPrettyJasper()!=null){
            return loadFile(reportType.getPrettyJasper());
        }else{
            return loadFile(reportType.getRawJasper());
        }
    }

    public static InputStream loadFile(String fileName) throws IOException
    {
        String path = PACKAGE_PATH + fileName;
        InputStream inputStream = ReportUtils.class.getClassLoader().getResourceAsStream(path);
        if(inputStream == null){
            throw new IOException("Could not find file: " + fileName);
        }
        return inputStream;
    }

    public static Object getLogoPath(String fileName) {
        
        String path = PACKAGE_PATH + fileName;
        URL url = ReportUtils.class.getClassLoader().getResource(path);
        return url.getPath();
        
    }
    
    public static Image getLogoImage(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(ReportUtils.loadFile(fileName));
        } catch (IOException e) {
            logger.error(e);
        }
        
        return img;
    }
    
    public static Object getSubReportURL(String subDirectory) {
        
        String path = PACKAGE_PATH + (subDirectory == null ? "" : (subDirectory + "/"));
        URL url = ReportUtils.class.getClassLoader().getResource(path);
        return url;
        
    }

    public static Object getImagesURI() {
        
        String path = PACKAGE_PATH;
        try {
            return ReportUtils.class.getClassLoader().getResource(path).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
