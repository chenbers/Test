package com.inthinc.pro.reports.hos.model;

import java.awt.Image;
import java.util.List;

import com.inthinc.hos.ddl.Recap;
import com.inthinc.hos.ddl.RecapType;
import com.inthinc.hos.model.DayTotals;
import com.inthinc.hos.model.HOSRecAdjusted;
import com.inthinc.hos.model.RuleSetType;


public class HosDailyDriverLog {
    
    private String day;
    private Boolean edited;
    private List<HOSRecAdjusted> correctedGraphList;
    private List<HOSRecAdjusted> originalGraphList;
    private Image correctedGraph;
    private Image originalGraph;
    private RuleSetType ruleSetType;
    private String driverName;
    private String carrierName;
    private String codrivers;
    private String mainAddress;
    private String terminalAddress;
    private List<VehicleInfo> vehicles;
    private Number milesDriven;
    private String shipping;
    private String trailers;
    private List<RemarkLog> remarksList;
    private RecapType recapType;
    private List<Recap> recap;
    private DayTotals correctedDayTotals;
    private DayTotals originalDayTotals;
    
    
    
    public List<Recap> getRecap() {
        return recap;
    }

    public void setRecap(List<Recap> recap) {
        this.recap = recap;
    }

    public RecapType getRecapType() {
        return recapType;
    }

    public void setRecapType(RecapType recapType) {
        this.recapType = recapType;
    }

    public List<RemarkLog> getRemarksList() {
        return remarksList;
    }

    public void setRemarksList(List<RemarkLog> remarksList) {
        this.remarksList = remarksList;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getTrailers() {
        return trailers;
    }

    public void setTrailers(String trailers) {
        this.trailers = trailers;
    }

    public List<VehicleInfo> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleInfo> vehicles) {
        this.vehicles = vehicles;
    }

    public Image getCorrectedGraph() {
        return correctedGraph;
    }

    public void setCorrectedGraph(Image correctedGraph) {
        this.correctedGraph = correctedGraph;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<HOSRecAdjusted> getCorrectedGraphList() {
        return correctedGraphList;
    }

    public void setCorrectedGraphList(List<HOSRecAdjusted> correctedGraphList) {
        this.correctedGraphList = correctedGraphList;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public List<HOSRecAdjusted> getOriginalGraphList() {
        return originalGraphList;
    }

    public void setOriginalGraphList(List<HOSRecAdjusted> originalGraphList) {
        this.originalGraphList = originalGraphList;
    }

    public Image getOriginalGraph() {
        return originalGraph;
    }

    public void setOriginalGraph(Image originalGraph) {
        this.originalGraph = originalGraph;
    }

    public RuleSetType getRuleSetType() {
        return ruleSetType;
    }

    public void setRuleSetType(RuleSetType ruleSetType) {
        this.ruleSetType = ruleSetType;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Number getMilesDriven() {
        return milesDriven;
    }

    public void setMilesDriven(Number milesDriven) {
        this.milesDriven = milesDriven;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCodrivers() {
        return codrivers;
    }

    public void setCodrivers(String codrivers) {
        this.codrivers = codrivers;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public DayTotals getCorrectedDayTotals() {
        return correctedDayTotals;
    }

    public void setCorrectedDayTotals(DayTotals correctedDayTotals) {
        this.correctedDayTotals = correctedDayTotals;
    }

    public DayTotals getOriginalDayTotals() {
        return originalDayTotals;
    }

    public void setOriginalDayTotals(DayTotals originalDayTotals) {
        this.originalDayTotals = originalDayTotals;
    }

}
