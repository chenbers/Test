package com.inthinc.pro.automation.interfaces;


public interface HasCustomUrl {
	
	public <T extends CustomUrl> void setCustomUrl(T custom);
	
	public String getCustomUrl();

}
