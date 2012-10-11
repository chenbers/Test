package com.inthinc.pro.backing.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;

public class ReportRenderer {

    private static final Logger logger = Logger.getLogger(ReportRenderer.class);
    private static final String FILE_NAME = "tiwiPRO_DDLReport";
    
    @SuppressWarnings("unchecked")
    private ReportCreator reportCreator;
    
    @SuppressWarnings("unchecked")
    public void exportReportToPDF(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {
        Report report = reportCreator.getReport(reportCriteriaList);
        exportToPdf(report,facesContext);
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
    public ReportCreator getReportCreator() {
        return reportCreator;
    }

    public void setReportCreator(ReportCreator reportCreator) {
        this.reportCreator = reportCreator;
    }

    

}
