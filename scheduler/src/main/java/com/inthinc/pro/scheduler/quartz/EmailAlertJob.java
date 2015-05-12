package com.inthinc.pro.scheduler.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.scheduler.data.EzCrmMessageData;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class EmailAlertJob extends BaseAlertJob
{
    private static final Logger logger = Logger.getLogger(EmailAlertJob.class);

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.error("EmailAlertJob: THROWAWAY BUILD DOES NOT SEND EMAILALERTS");


    }
}
