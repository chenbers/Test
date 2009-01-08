package com.inthinc.pro.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;

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
    public void exportMultipleReportsToPDF(List<ReportCriteria> reportCriteriaList, FacesContext facesContext)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteriaList);
        exportToPdf(jp,facesContext); 
    }
    
    @Override
    public void exportReportToEmail(List<ReportCriteria> reportCriteriaList, String email)
    {
        ProReportCompiler proCompiler = new ProReportCompiler();
        JasperPrint jp = proCompiler.compileReport(reportCriteriaList);
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
//                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//                mimeMessage.setFrom(new InternetAddress("noreply@inthinc.com"));
//                mimeMessage.setRecipients(Message.RecipientType.TO, email);
//                mimeMessage.setSubject("Tiwi Pro Report");
//                mimeMessage.setText("Tiwi Pro Report");
//                File file = new File("Tiwi Pro Report.pdf");
//                FileOutputStream out = new FileOutputStream(file);
//                out.write(bytes);
//                MimeBodyPart pdfAttatchment = new MimeBodyPart();
//                FileDataSource fds = new FileDataSource(file);
//                pdfAttatchment.setFileName("Tiwi Pro Report.pdf");
//                pdfAttatchment.setDataHandler(new DataHandler(fds));
//                
//                Multipart mp = new MimeMultipart();
//                mp.addBodyPart(pdfAttatchment);
//                
//                mimeMessage.setContent(mp);
//                mimeMessage.setSentDate(new Date());
//                
//                
//               javaMailSender.send(mimeMessage);
////                mailSender.setHost("https://webmail.iwiglobal.com");
////                mailSender.setPort("43");
                
                
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

    public void setReportMailer(ReportMailer reportMailer)
    {
        this.reportMailer = reportMailer;
    }

    public ReportMailer getReportMailer()
    {
        return reportMailer;
    }
}
