package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Fleet extends BaseEntity 
{
	private Integer companyID;
	private String name;
		
	public Fleet()
	{
		
	}
	
}
