package com.inthinc.pro.service.phonecontrol;

import java.util.Date;

/**
 * Date and time abstraction utility interface.
 */
public interface Clock {

    /**
     * Returns the current date/time in the default locale and timezone.
     */
    Date getNow();
}
