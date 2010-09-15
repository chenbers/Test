package com.inthinc.pro.reports;
 

import java.util.List;

import javax.faces.context.FacesContext;


public interface ReportRenderer
{
    public void exportSingleReportToPDF(ReportCriteria reportCriteria, FacesContext facesContext);
    
    public void exportReportToPDF(List<ReportCriteria> reportCriteriaList,FacesContext facesContext);
    
    public void exportReportToExcel(ReportCriteria reportCriteria,FacesContext facesContext);
    
    public void exportReportToExcel(List<ReportCriteria> reportCriteriaList,FacesContext facesContext);
    
    public void exportReportToEmail(List<ReportCriteria> reportCriteriaList,String email,String noReplyEmailAddress);
    
    public void exportReportToEmail(ReportCriteria reportCriteria,String email,String noReplyEmailAddress);
    
    public void exportReportToHTML(List<ReportCriteria> reportCriteriaList,FacesContext facesContext);
    
    public String exportReportToString(List<ReportCriteria> reportCriteriaList, FormatType formatType, FacesContext facesContext);

}
