package com.inthinc.pro.scheduler.quartz;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.inthinc.pro.aggregation.TripWS;

public class AggregationJob extends QuartzJobBean {
    private static final Logger logger = Logger.getLogger(AggregationJob.class);


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//        logger.setLevel(Level.DEBUG);
        logger.info("START PROCESSING WAYSMART TRIP AGGREGATION");
        processWSTripAggegation();
        logger.info("END PROCESSING WAYSMART TRIP AGGREGATION");

    }
    

    private void processWSTripAggegation() {
		TripWS tripWS = new TripWS();
		try {
			tripWS.updateTripsWS();
		} catch (Exception e)
		{
			logger.error("Exception: " + e);
			e.printStackTrace();
		}
    }
}
