package com.inthinc.pro.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.jasper.JasperReport;


public class ReportRendererImpl implements ReportRenderer
{
    private static final Logger logger = Logger.getLogger(ReportRendererImpl.class);
    private static final String FILE_NAME = "tiwiPRO_Report";
    
    @SuppressWarnings("unchecked")
    private ReportCreator reportCreator;

    @Override
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, FacesContext facesContext)
    {
        Report report = reportCreator.getReport(reportCriteria);
        exportToPdf(report,facesContext);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void exportReportToPDF(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {
        Report report = reportCreator.getReport(reportCriteriaList);
        exportToPdf(report,facesContext);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void exportReportToExcel(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {
        Report report = reportCreator.getReport(reportCriteriaList);
        exportToExcel(report,facesContext);
    }
    
    @Override
    public void exportReportToExcel(ReportCriteria reportCriteria, FacesContext facesContext)
    {
        Report report = reportCreator.getReport(reportCriteria);
        exportToExcel(report,facesContext); 
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void exportReportToEmail(List<ReportCriteria> reportCriteriaList, String email, String noReplyEmailAddress)
    {
        Report report = reportCreator.getReport(reportCriteriaList);
        FormatType format = FormatType.PDF;
        for (ReportCriteria reportCriteria : reportCriteriaList)
            if (reportCriteria.getReport().getPrettyTemplate() == null)
                format = FormatType.EXCEL;
        report.exportReportToEmail(email, format, noReplyEmailAddress);
    }
    
    @Override
    public void exportReportToEmail(ReportCriteria reportCriteria, String email, String noReplyEmailAddress)
    {
        Report report = reportCreator.getReport(reportCriteria);
        FormatType format = (reportCriteria.getReport().getPrettyTemplate() == null) ? FormatType.EXCEL : FormatType.PDF;
        report.exportReportToEmail(email, format, noReplyEmailAddress);
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void exportReportToHTML(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {

        Report report = reportCreator.getReport(reportCriteriaList);
        exportToHTML(report, facesContext);
        
    }
    
    @Override
    public String exportReportToString(List<ReportCriteria> reportCriteriaList, FormatType formatType, FacesContext facesContext) {
        
        Report report = reportCreator.getReport(reportCriteriaList);
        OutputStream out = null;
        try {
              out = new ByteArrayOutputStream();
              if (formatType == FormatType.HTML)
                  exportHTMLReportToStream(report, out, facesContext);
              else report.exportReportToStream(formatType, out);
              out.flush();
              out.close();
              return ((ByteArrayOutputStream)out).toString("UTF-8");
        } catch (IOException e) {
            logger.error(e);
        } catch (JRException e) {
            logger.error(e);
        }
        
        return null;

    }

    private String exportHTMLReportToStream(Report report, OutputStream out, FacesContext facesContext) throws JRException {
        if (!(report instanceof JasperReport))
            return null;
        
        JasperReport jasperReport = (JasperReport)report;
        
        JasperPrint jp = jasperReport.getReportBuilder().buildReport(jasperReport.getReportCriteriaList(), FormatType.HTML);
        if(jp != null)
        {
            HttpServletRequest request = ((HttpServletRequest)facesContext.getExternalContext().getRequest()); 
            request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jp);
            String imagesURIStr = request.getContextPath()+"/images?x=" + System.identityHashCode(jp) + "&image=";
            jasperReport.exportToHtmlStream(out, jp, imagesURIStr);
        }
        
        return null;

    }

    
    private void exportToHTML(Report report,FacesContext facesContext)
    {
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        if (report != null)
        {
            response.setContentType(FormatType.HTML.getContentType());
            response.addHeader("Content-Disposition", "attachment; filename=\"" + FILE_NAME + ".html\"");

            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                report.exportReportToStream(FormatType.HTML, out);
                out.flush();
                facesContext.responseComplete();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
            finally
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    logger.error(e);
                }
            }
        }
    }

    private void exportToPdf(Report report,FacesContext facesContext)
    {
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        if (report != null)
        {
            response.setContentType(FormatType.PDF.getContentType());
            response.addHeader("Content-Disposition", "attachment; filename=\"" + FILE_NAME + ".pdf\"");

            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                report.exportReportToStream(FormatType.PDF, out);
                out.flush();
                facesContext.responseComplete();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
            finally
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    logger.error(e);
                }
            }
        }
    }
    
    

    private void exportToExcel(Report report,FacesContext facesContext)
    {
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        if (report != null)
        {
            response.setContentType(FormatType.EXCEL.getContentType());
            response.addHeader("Content-Disposition", "attachment; filename=\"" + FILE_NAME + ".xls\"");

            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                report.exportReportToStream(FormatType.EXCEL, out);
                
                out.flush();
                facesContext.responseComplete();
            }
            catch (IOException e)
            {
                logger.error(e);
            }
           
            finally
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    logger.error(e);
                }
            }
        }
    }
    
    public void setReportCreator(ReportCreator reportCreator)
    {
        this.reportCreator = reportCreator;
    }

    public ReportCreator getReportCreator()
    {
        return reportCreator;
    }
}
