package com.inthinc.pro.model.security;

import java.io.Serializable;

import com.inthinc.pro.dao.annotations.ID;

public class SiteAccessPoint implements Serializable {
	
    private static final long serialVersionUID = 1L;
    @ID
	private Integer accessPtID;
	private Integer sortValue;
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

		return "ROLE_"+msgKey.toUpperCase();
	}
	public Integer getSortValue() {
		return sortValue;
	}
	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}

}
