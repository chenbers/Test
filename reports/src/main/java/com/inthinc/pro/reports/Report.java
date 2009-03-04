package com.inthinc.pro.reports;

import java.io.OutputStream;

public interface Report
{
  
    public void exportReportToEmail(String email, FormatType formatType);
    public void exportReportToEmail(String email, FormatType formatType,String subject,String message);
    public void exportReportToStream(FormatType formatType, OutputStream outputStream);

}   
