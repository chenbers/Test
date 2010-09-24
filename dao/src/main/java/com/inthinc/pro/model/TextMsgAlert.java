package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;

@XmlRootElement
public class TextMsgAlert extends BaseAlert {

    @Column(updateable = false)
    private static final long serialVersionUID = -4336190870525772062L;
    
    private Integer teamGroupID;
    
    public TextMsgAlert() {
        super();
    }

    public Integer getTeamGroupID() {
        return teamGroupID;
    }

    public void setTeamGroupID(Integer teamGroupID) {
        this.teamGroupID = teamGroupID;
    }

}
