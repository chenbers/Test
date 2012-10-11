package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.SimpleName;

@XmlRootElement
@SimpleName(simpleName="FwdCmdDef")
public class ForwardCommandDef extends BaseEntity {

	private static final long serialVersionUID = -663110688473083198L;
	private Integer fwdCmd;
	private String name;
	private String description;
	private ForwardCommandParamType paramType;
	private Boolean accessAllowed;
	
	public ForwardCommandDef()
	{
		
	}
	public ForwardCommandDef(Integer fwdCmd, String name, String description,
			ForwardCommandParamType paramType, Boolean accessAllowed) {
		super();
		this.fwdCmd = fwdCmd;
		this.name = name;
		this.description = description;
		this.paramType = paramType;
		this.accessAllowed = accessAllowed;
	}
	
	public Integer getFwdCmd() {
		return fwdCmd;
	}
	public void setFwdCmd(Integer fwdCmd) {
		this.fwdCmd = fwdCmd;
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
	public ForwardCommandParamType getParamType() {
		return paramType;
	}
	public void setParamType(ForwardCommandParamType paramType) {
		this.paramType = paramType;
	}
	public Boolean getAccessAllowed() {
		return accessAllowed;
	}
	public void setAccessAllowed(Boolean accessAllowed) {
		this.accessAllowed = accessAllowed;
	}
	
}
