package com.inthinc.pro.reports.jasper;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.mail.ReportAttatchment;
import com.inthinc.pro.reports.mail.ReportMailer;
import com.inthinc.pro.reports.mail.ReportMailerImpl;

public class JasperReport implements Report
{

    private static final String FILE_NAME = "tiwiPRO_Report";
    private static final Logger logger = Logger.getLogger(JasperReport.class);
    private JasperReportBuilder reportBuilder;
    private ReportMailer reportMailer;
    private List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();

    public static JasperReport getInstance(ReportCriteria reportCriteria)
    {
        JasperReport report = new JasperReport();
        report.setReportBuilder(new JasperReportBuilder());
        report.setReportMailer(new ReportMailerImpl());
        List<ReportCriteria> reportCriteriaList = new ArrayList<ReportCriteria>();
        reportCriteriaList.add(reportCriteria);
        report.setReportCriteriaList(reportCriteriaList);
        return report;
    }

    public static JasperReport getInstance(List<ReportCriteria> reportCriteriaList)
    {
        JasperReport report = new JasperReport();
        report.setReportBuilder(new JasperReportBuilder());
        report.setReportMailer(new ReportMailerImpl());
        report.setReportCriteriaList(reportCriteriaList);
        return report;
    }

    @Override
    public void exportReportToStream(FormatType formatType, OutputStream outputStream) 
    {
        byte[] data = null;
        JasperPrint jp = reportBuilder.buildReport(reportCriteriaList,formatType);
        if(jp != null)
        {
            try
            {
            switch (formatType) {
            case EXCEL:
                exportToExcelStream(outputStream,jp);
                break;
            case HTML:
                exportToHtmlStream(outputStream, jp);
                break;
            default:
                exportToPdfStream(outputStream,jp);
                break;
            }
            }catch(JRException e){
                logger.error(e);
            }
        }else{
            logger.error("Jasper Print is null");
        }
    }

    @Override
    public void exportReportToEmail(String email, FormatType formatType)
    {
        exportReportToEmail(email, formatType,null,null);
    } 
    
    @Override
    public void exportReportToEmail(String email, FormatType formatType, String subject, String message)
    {
        JasperPrint jp = reportBuilder.buildReport(reportCriteriaList,formatType);
        byte[] bytes;
        try
        {
            bytes = JasperExportManager.exportReportToPdf(jp);
            ReportAttatchment reportAttatchment = new ReportAttatchment(FILE_NAME + ".pdf", bytes);
            List<ReportAttatchment> attachments = new ArrayList<ReportAttatchment>();
            attachments.add(reportAttatchment);
            String[] emails = email.split(",");
            List<String> emailList = Arrays.asList(emails);
            reportMailer.emailReport(emailList, attachments,subject,message);
        }
        catch (JRException e)
        {
            // We want to know why the report isn't sent but because this is used in the scheduler, we don't want to push it up.
            logger.error(e);
        }
    }

    private void exportToPdfStream(OutputStream out,JasperPrint jasperPrint) throws JRException
    {
        
         JasperExportManager.exportReportToPdfStream(jasperPrint, out);
       
    }

    private void exportToExcelStream(OutputStream out,JasperPrint jasperPrint) throws JRException
    {   
        JRXlsExporter jexcelexporter = new JRXlsExporter();
        jexcelexporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        jexcelexporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
        jexcelexporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        
        jexcelexporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING, "UTF-8");

        jexcelexporter.exportReport();
    }
    
    private void exportToHtmlStream(OutputStream out,JasperPrint jasperPrint) throws JRException
    {   
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();
    }

    public void setReportMailer(ReportMailer reportMailer)
    {
        this.reportMailer = reportMailer;
    }

    public ReportMailer getReportMailer()
    {
        return reportMailer;
    }

    public JasperReportBuilder getReportBuilder()
    {
        return reportBuilder;
    }

    public void setReportBuilder(JasperReportBuilder reportBuilder)
    {
        this.reportBuilder = reportBuilder;
    }

    public void setReportCriteriaList(List<ReportCriteria> reportCriteriaList)
    {
        this.reportCriteriaList = reportCriteriaList;
    }

    public List<ReportCriteria> getReportCriteriaList()
    {
        return reportCriteriaList;
    }

}
