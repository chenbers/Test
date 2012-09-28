package com.inthinc.pro.automation.utils;

import com.inthinc.pro.automation.logging.Log;


public class AutomationThread {

    public static void pause(Long timeout_in_milliseconds,
            String reasonForPause, StackTraceElement element) {
        String print = String.format("%s: %3d - Pausing for %d milliseconds. "
                + "Reason for pause: %s\n", element.getFileName().replace(".java", ""), element.getLineNumber(),
                timeout_in_milliseconds, reasonForPause);
        Log.debug(print);
        pause(timeout_in_milliseconds);
    }

    public static void pause(Long timeout_in_milliseconds, String reasonForPause) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        pause(timeout_in_milliseconds, reasonForPause, element);
    }

    public static void pause(Integer timeout_in_seconds, String reasonForPause) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        pause(timeout_in_seconds, reasonForPause, element);
    }

    public static void pause(Integer timeout_in_seconds, String reasonForPause,
            StackTraceElement element) {
        String print = String.format(
                "%s: %3d - Pausing for %d seconds. Reason for pause: %s\n",
                element.getFileName().replace(".java", ""),
                element.getLineNumber(), timeout_in_seconds, reasonForPause);
        Log.debug(print);
        pause(timeout_in_seconds);
    }

    public static void pause(Integer timeout_in_seconds) {
        pause((long) (timeout_in_seconds * 1000));
    }

    public static void pause(Long timeout_in_milliseconds) {
        try {
            Thread.sleep(timeout_in_milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
