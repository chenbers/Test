package com.inthinc.pro.reports;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ReportRendererBean;

public class ReportRendererImpl implements ReportRenderer
{
    private static final Logger logger = Logger.getLogger(ReportRendererBean.class);
    private static final String PDF_CONTENT_TYPE = "application/pdf";
    private static final String EXCEL_CONTENT_TYPE = "application/xls";


    @Override
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, FacesContext facesContext)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteria);
        exportToPdf(jp,facesContext);
    }
    
    @Override
    public void exportMultipleReportsToPDF(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteriaList);
        exportToPdf(jp,facesContext); 
    }

    private void exportToPdf(JasperPrint jasperPrint,FacesContext facesContext)
    {
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        if (jasperPrint != null && jasperPrint.getPages().size() > 0)
        {
            response.setContentType(PDF_CONTENT_TYPE);
            response.addHeader("Content-Disposition", "attachment; filename=\"Tiwi_Pro_Report.pdf\"");

            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush();
               facesContext.responseComplete();
            }
            catch (IOException e)
            {
                logger.warn(e);
            }
            catch (JRException e)
            {
                logger.warn(e);
            }
            finally
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    logger.warn(e);
                }
            }
        }
    }

    private void exportToExcel(JasperPrint jasperPrint,FacesContext facesContext)
    {
        if (jasperPrint != null && jasperPrint.getPages().size() > 0)
        {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType(EXCEL_CONTENT_TYPE);

            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush();
                facesContext.responseComplete();
            }
            catch (IOException e)
            {
                logger.warn(e);
            }
            catch (JRException e)
            {
                logger.warn(e);
            }
            finally
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    logger.warn(e);
                }
            }
        }
    }
}
