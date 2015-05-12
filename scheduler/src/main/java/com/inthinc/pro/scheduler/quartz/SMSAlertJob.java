package com.inthinc.pro.scheduler.quartz;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class SMSAlertJob extends BaseAlertJob
{
    private static final Logger logger = Logger.getLogger(SMSAlertJob.class);

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
        logger.error("BaseAlertJob: THROWAWAY BUILD DOES NOT SEND ALERTS");

    }

}
