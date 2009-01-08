package com.inthinc.pro.reports;
 

import java.util.List;

import javax.faces.context.FacesContext;


public interface ReportRenderer
{
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, FacesContext facesContext);
    
    public void exportMultipleReportsToPDF(List<ReportCriteria> reportCriteriaList,FacesContext facesContext);
    
    public void exportReportToEmail(List<ReportCriteria> reportCriteriaList,String email);
}
