package com.inthinc.pro.reports;

import java.util.List;

import com.inthinc.pro.reports.model.ReportAttatchment;

public interface ReportMailer
{
    void emailReport(List<String> toAddress,List<ReportAttatchment> attachments);
    
    void emailReport(List<String> toAddress,String fromAddress,List<ReportAttatchment> attachments);
    
    void emailReport(List<String> toAddress,String fromAddress,List<ReportAttatchment> attachments,String message,String subject);
}
