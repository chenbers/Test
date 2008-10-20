package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class ScoreableEntity extends BaseEntity {
	@ID
	private Integer entityID;
	  
	private String identifier;
	private String score;
	private String style;
	private String colorKey;
	private String goTo;
	
	public Integer getEntityID() {
		return entityID;
	}
	public void setEntityID(Integer entityID) {
		this.entityID = entityID;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getColorKey() {
		return colorKey;
	}
	public void setColorKey(String colorKey) {
		this.colorKey = colorKey;
	}
	public String getGoTo() {
		return goTo;
	}
	public void setGoTo(String goTo) {
		this.goTo = goTo;
	}
	public String goTo() {
		return this.goTo;
	}
}
