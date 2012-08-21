package com.inthinc.pro.scheduler.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.scheduler.dispatch.MailDispatcher;
import com.inthinc.pro.scheduler.dispatch.PhoneDispatcher;
import com.inthinc.pro.scheduler.i18n.LocalizedMessage;

public class BaseAlertJob extends QuartzJobBean
{
    private static final Logger logger = Logger.getLogger(BaseAlertJob.class);
    
    private AlertMessageDAO alertMessageDAO;
    private MailDispatcher mailDispatcher;
    private PhoneDispatcher phoneDispatcher;
    
    
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException
    {
//      SimpleDateFormat formatter = new SimpleDateFormat("E, MMM d, h:mm a (z)");
//      formatter.setTimeZone(TimeZone.getTimeZone("US/Mountain"));
//      
//      logger.debug("TEXT MESSAGE JOB: Triggered At: " + formatter.format(new Date()) );
        
    }
    
    protected List<AlertMessage>getMessages(AlertMessageDeliveryType messageType) throws JobExecutionException
    {
        try
        {
            return alertMessageDAO.getMessages(messageType);
        }
        catch (Exception e)
        {
            logger.error("Error getting messages from dataAccess: ", e);
            // repackage exception
            throw new JobExecutionException(e);
        }
    }
    
    protected List<AlertMessageBuilder>getMessageBuilders(AlertMessageDeliveryType messageType) throws JobExecutionException
    {
        try
        {
            return alertMessageDAO.getMessageBuilders(messageType);
        }
        catch (Exception e)
        {
            logger.error("Error getting messages from dataAccess: ", e);
            // repackage exception
            throw new JobExecutionException(e);
        }
    }

    protected String getSubject(AlertMessageBuilder message)
    {
        return LocalizedMessage.getString("SUBJECT_" + message.getAlertMessageType().name(),message.getLocale());
    }

    public AlertMessageDAO getAlertMessageDAO()
    {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO)
    {
        this.alertMessageDAO = alertMessageDAO;
    }

    public MailDispatcher getMailDispatcher()
    {
        return mailDispatcher;
    }

    public void setMailDispatcher(MailDispatcher mailDispatcher)
    {
        this.mailDispatcher = mailDispatcher;
    }

    public PhoneDispatcher getPhoneDispatcher()
    {
        return phoneDispatcher;
    }

    public void setPhoneDispatcher(PhoneDispatcher phoneDispatcher)
    {
        this.phoneDispatcher = phoneDispatcher;
    }
    
}

