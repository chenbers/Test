package com.inthinc.pro.reports.util.wrapper;

import com.inthinc.pro.model.ScoreableEntity;

public class ScoreableEntityPkg {
	private ScoreableEntity se;
	
	private String style;
	private String colorKey;
	private String goTo;
	private Integer position;
	
    public ScoreableEntityPkg() {
		this.se = new ScoreableEntity();
		this.style = "";
		this.colorKey = "";
		this.goTo = "";
	}
	
	
	public ScoreableEntity getSe() {
		return se;
	}
	public void setSe(ScoreableEntity se) {
		this.se = se;
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
    public Integer getPosition()
    {
        return position;
    }


    public void setPosition(Integer position)
    {
        this.position = position;
    }



}
