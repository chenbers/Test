package com.inthinc.pro.model;

public class ForwardCommandDef extends BaseEntity {

	private static final long serialVersionUID = -663110688473083198L;
	private Integer cmdID;
	private String name;
	private String description;
	private ForwardCommandParamType expectedParamType;
	private Boolean allAccess;
	
	public ForwardCommandDef()
	{
		
	}
	public ForwardCommandDef(Integer cmdID, String name, String description,
			ForwardCommandParamType expectedParamType, Boolean allAccess) {
		super();
		this.cmdID = cmdID;
		this.name = name;
		this.description = description;
		this.expectedParamType = expectedParamType;
		this.allAccess = allAccess;
	}
	
	public Integer getCmdID() {
		return cmdID;
	}
	public void setCmdID(Integer cmdID) {
		this.cmdID = cmdID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ForwardCommandParamType getExpectedParamType() {
		return expectedParamType;
	}
	public void setExpectedParamType(ForwardCommandParamType expectedParamType) {
		this.expectedParamType = expectedParamType;
	}
	public Boolean getAllAccess() {
		return allAccess;
	}
	public void setAllAccess(Boolean allAccess) {
		this.allAccess = allAccess;
	}
	
}
