package com.inthinc.pro.model.security;

import java.util.EnumSet;

import com.inthinc.pro.model.app.SiteAccessPoints;

public class AccessPoint {
	
	public enum Mode {READ(1),WRITE(2),UPDATE(4),DELETE(8);
		
		private Integer code;
		Mode(Integer code){
		
			this.code = code;
		}
		public Integer getCode(){
			return code;
		}
	}
	
	private EnumSet<Mode> permissions;
	
	private Integer siteAccessPointID;
	
	
	public AccessPoint(Integer siteAccessPointID, Integer mode ) {
		super();
		
		setMode(mode);
		this.siteAccessPointID = siteAccessPointID;
	}

	public Integer getSiteAccessPointID() {
		return siteAccessPointID;
	}
	public void setSiteAccessPointID(Integer siteAccessPointID) {
		this.siteAccessPointID = siteAccessPointID;
	}
	public EnumSet<Mode> getPermissions() {
		return permissions;
	}
	public void setPermissions(EnumSet<Mode> permissions) {
		this.permissions = permissions;
	}

	public void setMode(Integer mode){
		
		permissions = EnumSet.noneOf(Mode.class);
		
		for(Mode m: Mode.values()){
			
			if ((mode.intValue() & m.getCode()) == m.getCode()) {
				
				permissions.add(m);
			}
		}
	}
	
	public Integer getMode(){
		
		Integer mode = 0;
		
		for(Mode m: permissions){
			
			mode+=m.getCode();
		}
		return mode;
	}
	public boolean isSelected(){
		
		return getMode().intValue() != 0;
	}
	public void setSelected(boolean selected){
		
		setMode(selected?15:0);
	}
	public String getMsgKey(){
		
		return SiteAccessPoints.getAccessPointById(siteAccessPointID).getMsgKey();
	}
	public static EnumSet<Mode> getAllModes(){
		
		return EnumSet.allOf(Mode.class);
	}
}
