package com.inthinc.pro.reports.mail;

import java.util.List;



public interface ReportMailer
{
    void emailReport(List<String> toAddress,List<ReportAttatchment> attachments, String noReplayEmailAddres);
    
    void emailReport(List<String> toAddress,String fromAddress,List<ReportAttatchment> attachments);
    
    void emailReport(List<String> toAddress,String fromAddress,List<ReportAttatchment> attachments,String message,String subject);
    
    void emailReport(List<String> toAddress,List<ReportAttatchment> attachments,String message,String subject,  String noReplayEmailAddres);
}
