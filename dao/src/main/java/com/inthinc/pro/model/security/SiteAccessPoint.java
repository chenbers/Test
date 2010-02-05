package com.inthinc.pro.model.security;

import com.inthinc.pro.dao.annotations.ID;

public class SiteAccessPoint {
	
	@ID
	private Integer accessPtID;
	
	private String msgKey;
	
	public Integer getAccessPtID() {
		return accessPtID;
	}
	public void setAccessPtID(Integer accessPtID) {
		this.accessPtID = accessPtID;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	@Override
	public String toString() {

		return "ROLE_"+msgKey;
	}

}
