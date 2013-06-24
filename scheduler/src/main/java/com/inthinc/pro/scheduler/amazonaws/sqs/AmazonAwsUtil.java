package com.inthinc.pro.scheduler.amazonaws.sqs;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: michaelreinicke
 * Date: 6/20/13
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class AmazonAwsUtil {

    public static void amazonWaitRule() {
        DateTime deletedDateTime = new DateTime();
        // Add just a small padding of time.
        DateTime canRecreateQueueDate = deletedDateTime.plusMinutes(1).plusSeconds(10);

            /*
                Amazon has a rule that after deleting a queue you must wait 60 seconds before re-creating a queue
             */
        while(new DateTime().isBefore(canRecreateQueueDate)) {
            // Careful what ya wish for this will print a shiz load!!
            //logger.debug("Waiting to create Amazon Queue ... " + (canRecreateQueueDate.getSecondOfDay() - new DateTime().getSecondOfDay()) ) ;
        }
    }
}
