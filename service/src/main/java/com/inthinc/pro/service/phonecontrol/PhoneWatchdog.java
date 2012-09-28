package com.inthinc.pro.service.phonecontrol;

/**
 * Watchdog to enable phones in case the communication is lost.
 * 
 * @author dcueva
 *
 */
public interface PhoneWatchdog {

	/**
	 * Enable phones when there are no communication events.
	 */
	public void enablePhonesWhenLostComm();
	
}
