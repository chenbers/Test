package com.inthinc.pro.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="accountProp")
public class AccountProperty {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer acctPropID;
	private Integer acctID;
	public Integer getAcctID() {
		return acctID;
	}
	public void setAcctID(Integer acctID) {
		this.acctID = acctID;
	}
	private String name;
	private String value;

	public Integer getAcctPropID() {
		return acctPropID;
	}
	public void setAcctPropID(Integer acctPropID) {
		this.acctPropID = acctPropID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
