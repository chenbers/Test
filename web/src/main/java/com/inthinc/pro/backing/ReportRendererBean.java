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

    public void exportDriverReport(List<DriverReportItem> driverlist)
    {
        ProReportCompiler proCompiler = new ProReportCompiler(ReportType.DRIVER_TABULAR);
        Map<String, List> source = new HashMap<String, List>();
        source.put(ReportType.DRIVER_TABULAR.toString(), driverlist);
        JasperPrint jp = proCompiler.compileReport(source, null);
        exportToPdf(jp);
    }

    public void exportOverallScoreToPDF(List<PieScoreData> overallScoreData, List<PieScoreData> drivingStyleData, List<PieScoreData> seatbeltData, List<PieScoreData> speedData,
            String overallscore)
    {
//        ProReportCompiler proCompiler = new ProReportCompiler(ReportType.OVERALL_SCORE);
//
//        // Set the list of main datasources for each of the report templates
//        Map<String, List> source = new HashMap<String, List>();
//        source.put(ReportType.OVERALL_SCORE.toString(), overallScoreData);
//
//        // Set the parameter map
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("OVERALL_SCORE", overallscore);
//        paramMap.put("ENTITY_NAME", this.navigationBean.getGroup().getName());
//        paramMap.put("DURATION","30 Days");
//
//        // Set the sub Datasets
//        paramMap.put("DRIVER_STYLE_DATA", drivingStyleData);
//        paramMap.put("SEATBELT_USE_DATA", seatbeltData);
//        paramMap.put("SPEED_DATA", speedData);
//
//        paramMap.put("ACCOUNT_NAME",getAccountName());
//        JasperPrint jp = proCompiler.compileReport(source, paramMap);
//        exportToPdf(jp);
    }

    @Override
    public void exportTrendReportToPDF(List<CategorySeriesData> trendChartData, List<ScoreableEntityPkg> scoreableEntityData)
    {
//        ProReportCompiler proCompiler = new ProReportCompiler(ReportType.TREND);
//
//        // Set the list of master datasources for each of the report templates
//        Map<String, List> source = new HashMap<String, List>();
//        source.put(ReportType.TREND.toString(), scoreableEntityData);
//
//        // Set the parameter map
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("ENTITY_NAME", this.navigationBean.getGroup().getName());
//
//        // Set the sub Datasets
//        paramMap.put("TREND_CHART_DATA", trendChartData);
//        paramMap.put("ACCOUNT_NAME", getAccountName());
//        JasperPrint jp = proCompiler.compileReport(source, paramMap);
//        exportToPdf(jp);
    }
    
    @Override
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, ServletResponse response)
    {
        Map<String,List> mainDatasets = new HashMap<String, List>();
        mainDatasets.put(reportCriteria.getReportType().toString(), reportCriteria.getMainDataset());
        ProReportCompiler proCompiler = new ProReportCompiler(reportCriteria.getReportType());
        JasperPrint jp = proCompiler.compileReport(mainDatasets, reportCriteria.getPramMap());
        exportToPdf(jp);
    }

//    private String getAccountName()
//    {
//        Account account = getAccountDAO().findByID(getAccountID());
//        String name = account.getAcctName();
//        return name;
//    }

    private void exportToPdf(JasperPrint jasperPrint)
    {
        if (jasperPrint != null && jasperPrint.getPages().size() > 0)
        {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
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
