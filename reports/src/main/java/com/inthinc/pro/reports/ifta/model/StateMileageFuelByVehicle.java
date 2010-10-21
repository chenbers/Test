package com.inthinc.pro.reports.ifta.model;

/**
 * Data Bean for the StateMileageFuelByVehicle report.
 */
public class StateMileageFuelByVehicle {

	private String groupName;
	private String vehicle;
	private String month;
	private String state;
	private Double totalMiles;
	private Double totalTruckGas;
	private Double totalTrailerGas;

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the vehicle
	 */
	public String getVehicle() {
		return vehicle;
	}
	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the totalMiles
	 */
	public Double getTotalMiles() {
		return totalMiles;
	}
	/**
	 * @param totalMiles the totalMiles to set
	 */
	public void setTotalMiles(Double totalMiles) {
		this.totalMiles = totalMiles;
	}
	/**
	 * @return the totalTruckGas
	 */
	public Double getTotalTruckGas() {
		return totalTruckGas;
	}
	/**
	 * @param totalTruckGas the totalTruckGas to set
	 */
	public void setTotalTruckGas(Double totalTruckGas) {
		this.totalTruckGas = totalTruckGas;
	}
	/**
	 * @return the totalTrailerGas
	 */
	public Double getTotalTrailerGas() {
		return totalTrailerGas;
	}
	/**
	 * @param totalTrailerGas the totalTrailerGas to set
	 */
	public void setTotalTrailerGas(Double totalTrailerGas) {
		this.totalTrailerGas = totalTrailerGas;
	}
	
	
}
