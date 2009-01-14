package com.inthinc.pro.reports;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import com.inthinc.pro.backing.ReportRendererBean;
import com.inthinc.pro.reports.model.ReportAttatchment;

public class ReportRendererImpl implements ReportRenderer
{
    private static final Logger logger = Logger.getLogger(ReportRendererBean.class);
    private static final String PDF_CONTENT_TYPE = "application/pdf";
    private static final String EXCEL_CONTENT_TYPE = "application/xls";
    
    private ReportMailer reportMailer;

    @Override
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, FacesContext facesContext)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteria);
        exportToPdf(jp,facesContext);
    }
    
    @Override
    public void exportReportToPDF(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteriaList);
        exportToPdf(jp,facesContext); 
    }
    
    @Override
    public void exportReportToExcel(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteriaList);
        exportToExcel(jp,facesContext); 
    }
    
    @Override
    public void exportReportToExcel(ReportCriteria reportCriteria, FacesContext facesContext)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteria);
        exportToExcel(jp,facesContext); 
    }
    
    @Override
    public void exportReportToEmail(List<ReportCriteria> reportCriteriaList, String email)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteriaList);
        exportToPdfViaEmail(jp, email);
    }
    
    @Override
    public void exportReportToEmail(ReportCriteria reportCriteria, String email)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteria);
        exportToPdfViaEmail(jp, email);
        
    }
    
    private void exportToPdfViaEmail(JasperPrint jasperPrint,String email)
    {
        if (jasperPrint != null && jasperPrint.getPages().size() > 0)
        {
            
            try
            {
                byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
                ReportAttatchment reportAttatchment = new ReportAttatchment("Tiwi Pro Report.pdf",bytes);
                List<ReportAttatchment> attachments = new ArrayList<ReportAttatchment>();
                attachments.add(reportAttatchment);
                String[] emails = email.split(",");
                List<String> emailList =  Arrays.asList(emails);
                reportMailer.emailReport(emailList, attachments);                
            }
            catch(JRException e)
            {
                logger.error(e);
            }catch(Exception e)
            {
                logger.error(e);
            }
        }
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
        HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
        if (jasperPrint != null && jasperPrint.getPages().size() > 0)
        {
            response.setContentType(EXCEL_CONTENT_TYPE);
            response.addHeader("Content-Disposition", "attachment; filename=\"Tiwi_Pro_Report.xls\"");

            OutputStream out = null;
            try
            {
                out = response.getOutputStream();
                JExcelApiExporter jexcelexporter = new JExcelApiExporter();
                jexcelexporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                jexcelexporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
                //jexcelexporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                jexcelexporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
                jexcelexporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                jexcelexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
                jexcelexporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
                jexcelexporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
                jexcelexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                jexcelexporter.exportReport();
                
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

    public void setReportMailer(ReportMailer reportMailer)
    {
        this.reportMailer = reportMailer;
    }

    public ReportMailer getReportMailer()
    {
        return reportMailer;
    }
}
