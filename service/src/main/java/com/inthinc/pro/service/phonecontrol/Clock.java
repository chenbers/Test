package com.inthinc.pro.service.phonecontrol;

import java.util.Date;

/**
 * Date and time abstraction utility interface.
 * <p/>
 * Querying time through this interface allow for better testable code and avoid the use of calling <tt>new Date()</tt>, practically allowing the code to have time objects to be
 * injected instead of creating new objects when the current time is required.
 */
public interface Clock {

    /**
     * Returns the current date/time in the default locale and timezone.
     */
    Date getNow();
}
