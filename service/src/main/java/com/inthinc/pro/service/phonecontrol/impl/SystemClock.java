package com.inthinc.pro.service.phonecontrol.impl;

import java.util.Date;

import com.inthinc.pro.service.phonecontrol.Clock;

/**
 * Default implementation of the {@link Clock} interface which returns current dates by creating new {@link Date} objects.
 */
public class SystemClock implements Clock {

    /**
     * @see com.inthinc.pro.service.phonecontrol.Clock#getNow()
     */
    @Override
    public Date getNow() {
        return new Date();
    }
}
