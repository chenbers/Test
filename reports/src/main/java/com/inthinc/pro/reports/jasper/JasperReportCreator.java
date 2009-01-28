package com.inthinc.pro.reports.jasper;

import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.mail.ReportMailer;

public class JasperReportCreator extends ReportCreator<JasperReport>
{

    public JasperReportCreator(ReportMailer reportMailer)
    {
        super(reportMailer);
    }
}
