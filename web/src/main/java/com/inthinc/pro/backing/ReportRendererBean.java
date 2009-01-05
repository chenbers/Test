package com.inthinc.pro.backing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.reports.ProReportCompiler;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ReportRendererBean extends BaseBean implements ReportRenderer
{
    private static final Logger logger = Logger.getLogger(ReportRendererBean.class);
    private static final String PDF_CONTENT_TYPE = "application/pdf";
    private static final String EXCEL_CONTENT_TYPE = "application/xls";

    // Spring Managed Beans
    private NavigationBean navigationBean;
    private AccountDAO accountDAO;

    @Override
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, HttpServletResponse response)
    {
        Map<String,List> mainDatasets = new HashMap<String, List>();
        mainDatasets.put(reportCriteria.getReportType().toString(), reportCriteria.getMainDataset());
        ProReportCompiler proCompiler = new ProReportCompiler(reportCriteria.getReportType());
        JasperPrint jp = proCompiler.compileReport(mainDatasets, reportCriteria.getPramMap());
        exportToPdf(jp,(HttpServletResponse)response);
    }

    private void exportToPdf(JasperPrint jasperPrint,HttpServletResponse response)
    {
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
                getFacesContext().responseComplete();
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

    private void exportToExcel(JasperPrint jasperPrint)
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
                getFacesContext().responseComplete();
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

    public void setNavigationBean(NavigationBean navigationBean)
    {
        this.navigationBean = navigationBean;
    }

    public NavigationBean getNavigationBean()
    {
        return navigationBean;
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }
}
