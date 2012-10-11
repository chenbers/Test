package com.inthinc.pro.wrapper;

import java.io.Serializable;

import com.inthinc.pro.model.ScoreableEntity;

public class ScoreableEntityPkg implements Serializable{
    
    
	/**
     * 
     */
    private static final long serialVersionUID = 7147474007659185096L;

    private ScoreableEntity se;
	
	private String style;
	private String colorKey;
//	private String goTo;
	private Integer position;
	private Boolean show = Boolean.TRUE;
	
    public ScoreableEntityPkg() {
		this.se = new ScoreableEntity();
		this.style = "";
		this.colorKey = "";
//		this.goTo = "";
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
//	public String getGoTo() {
//		return goTo;
//	}
//	public void setGoTo(String goTo) {
//		this.goTo = goTo;
//	}
//	public String goTo() {
//		return this.goTo;
//	}
    public Integer getPosition()
    {
        return position;
    }


    public void setPosition(Integer position)
    {
        this.position = position;
    }


    public Boolean getShow()
    {
    	return show;
    }


    public void setShow(Boolean show)
    {
        this.show = show;
    }



}
