package com.inthinc.pro.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Driver
 *
 */
@Entity
@Table(name="driver")
public class Driver implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer driverID;
	
	public Integer getDriverID() {
		return driverID;
	}

	public void setDriverID(Integer driverID) {
		this.driverID = driverID;
	}

	public Driver() {
		super();
	}
   
}
