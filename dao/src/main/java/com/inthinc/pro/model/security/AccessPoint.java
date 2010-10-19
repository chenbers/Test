package com.inthinc.pro.model.security;

import java.io.Serializable;


public class AccessPoint implements Serializable{
		
    private static final long serialVersionUID = 1L;
    private Integer accessPtID;
	private Integer mode;
	
	public AccessPoint() {
		super();
	}
	public AccessPoint(Integer siteAccessPointID, Integer mode ) {
		super();
		
		setMode(mode);
		this.accessPtID = siteAccessPointID;
	}

	public void setMode(Integer mode){
		
		this.mode = mode;
	}
	
	public Integer getMode(){
		
		return mode;
	}
	public Integer getAccessPtID() {
		return accessPtID;
	}

	public void setAccessPtID(Integer accessPtID) {
		this.accessPtID = accessPtID;
	}
	@Override
	public boolean equals(Object obj) {

		return (accessPtID.intValue() == ((AccessPoint)obj).accessPtID.intValue());
	}
	
}
