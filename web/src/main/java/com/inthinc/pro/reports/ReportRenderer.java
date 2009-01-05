package com.inthinc.pro.reports;
 

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public interface ReportRenderer
{
    void exportSingleReportToPDF(ReportCriteria reportCriteria,HttpServletResponse response);
}
