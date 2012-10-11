package com.inthinc.pro.service.phonecontrol.impl;

/**
 * The update strategy to be used for the cell phone status once the request to enable/disable the device is sent to the remote provider service.
 */
public enum UpdateStrategy {
    /**
     * Synchronous update strategy. Cell phone status is updated as soon as a OK response is received from the service invokation.
     */
    SYNCHRONOUS,
    /**
     * Asynchronous update strategy. Cell phone status is updated once ACK callback is received from service provider.
     */
    ASYNCHRONOUS
}
