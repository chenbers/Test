package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class AlertContact extends BaseEntity
{
    @ID
    private Integer userID;
    private Integer info;
    private Integer warn;
    private Integer crit;
    private String priEmail;
    private String secEmail;
    private String priPhone;
    private String secPhone;
    private String cellPhone;
    private String priText;
    private String secText;

    public AlertContact()
    {
        super();
    }

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getInfo() {
		return info;
	}

	public void setInfo(Integer info) {
		this.info = info;
	}

	public Integer getWarn() {
		return warn;
	}

	public void setWarn(Integer warn) {
		this.warn = warn;
	}

	public Integer getCrit() {
		return crit;
	}

	public void setCrit(Integer crit) {
		this.crit = crit;
	}

	public String getPriEmail() {
		return priEmail;
	}

	public void setPriEmail(String priEmail) {
		this.priEmail = priEmail;
	}

	public String getSecEmail() {
		return secEmail;
	}

	public void setSecEmail(String secEmail) {
		this.secEmail = secEmail;
	}

	public String getPriPhone() {
		return priPhone;
	}

	public void setPriPhone(String priPhone) {
		this.priPhone = priPhone;
	}

	public String getSecPhone() {
		return secPhone;
	}

	public void setSecPhone(String secPhone) {
		this.secPhone = secPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getPriText() {
		return priText;
	}

	public void setPriText(String priText) {
		this.priText = priText;
	}

	public String getSecText() {
		return secText;
	}

	public void setSecText(String secText) {
		this.secText = secText;
	}

}
