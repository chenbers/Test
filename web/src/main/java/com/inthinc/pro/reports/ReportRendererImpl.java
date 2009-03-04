package com.inthinc.pro.reports;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ReportRendererBean;

public class ReportRendererImpl implements ReportRenderer
{
    private static final Logger logger = Logger.getLogger(ReportRendererBean.class);
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
    public void exportReportToEmail(List<ReportCriteria> reportCriteriaList, String email)
    {
        Report report = reportCreator.getReport(reportCriteriaList);
        report.exportReportToEmail(email, FormatType.PDF);
    }
    
    @Override
    public void exportReportToEmail(ReportCriteria reportCriteria, String email)
    {
        Report report = reportCreator.getReport(reportCriteria);
        report.exportReportToEmail(email, FormatType.PDF);
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void exportReportToHTML(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {

        Report report = reportCreator.getReport(reportCriteriaList);
        exportToHTML(report, facesContext);
        
    }
    
    private void exportToHTML(Report report,FacesContext facesContext)
    {
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        if (report != null)
        {
            response.setContentType(FormatType.PDF.getContentType());
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
