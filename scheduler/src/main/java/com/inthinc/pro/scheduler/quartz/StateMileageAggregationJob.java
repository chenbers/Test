package com.inthinc.pro.scheduler.quartz;


import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.inthinc.pro.aggregation.db.DBUtil;

public class StateMileageAggregationJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(StateMileageAggregationJob.class);


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//        logger.setLevel(Level.DEBUG);
        logger.info("Start Processing StateMileageAggregation");
        try {
            DBUtil.aggStateMileageByVehicle();
        } catch (Exception e)
        {
            logger.error("Exception: " + e);
            e.printStackTrace();
        }
        logger.info("End Processing StateMileageAggregation");

    }
}
