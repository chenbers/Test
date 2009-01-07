package com.inthinc.pro.reports;
 

import javax.faces.context.FacesContext;


public interface ReportRenderer
{
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, FacesContext facesContext);
}
