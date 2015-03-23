package com.inthinc.pro.scheduler.quartz;


import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Base email report bean.
 */
public abstract class BaseEmailReportJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(BaseEmailReportJob.class);

    private String enabled;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // read property, see if it's enabled
        if (enabled != null && enabled.equals("true")){

            // if enabled, execute the job code
            executeIfEnabled(context);
        }else{
            logger.trace("Not executed scheduler "+this.getClass().toString()+" because it's not enabled in the property file.");
        }
    }

    /**
     * Version of {@link QuartzJobBean#executeInternal} that executes only if the property <b>scheduler.enabled</b> is <b>true</b>.
     *
     * @param context job execution context
     * @throws JobExecutionException job execution exception
     */
    protected abstract void executeIfEnabled(JobExecutionContext context) throws JobExecutionException;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}

