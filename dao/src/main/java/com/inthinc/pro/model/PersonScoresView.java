package com.inthinc.pro.model;

import org.apache.tools.ant.types.resources.First;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Holds person data and score data.
 */
@XmlRootElement
public class PersonScoresView {

    @JsonIgnore
    @XmlTransient
    private Person person;

    private Double milesDriven;

    private Double speeding;

    private Double speedTime;

    private Double aggressiveAccel;

    private Double aggressiveBrake;

    private Double overall;

    private Double aggressiveAccelEvents;

    private Double aggressiveBrakeEvents;

    private Integer maxSpeed;

    private Double aggressiveTurnsEvents;
    private Double aggressiveBumpEvents;

    public PersonScoresView(Person person) {
        this.person = person;
    }

    public PersonScoresView() {
    }

    @XmlElement
    public Date getModified() {
        if (person == null)
            return null;

        return person.getModified();
    }

    public void setVehicleID(Date modified) {
        if (person != null)
            this.person.setModified(modified);
    }

    @XmlElement
    public Integer getAcctID() {
        if (person == null)
            return null;

        return person.getAccountID();
    }

    public void setAcctID(Integer acctID) {
        if (person != null)
            this.person.setAcctID(acctID);
    }

    @XmlElement
    public Driver getDriver() {
        if (person == null)
            return null;

        return person.getDriver();
    }

    public void setDriver(Driver driver) {
        if (person != null)
            this.person.setDriver(driver);
    }


    @XmlElement
    public String getEmpid() {
        if (person == null)
            return null;

        return person.getEmpid();
    }

    public void setEmpid(String empid) {
        if (person != null)
            this.person.setEmpid(empid);
    }

    @XmlElement
    public String getFirst() {
        if (person == null)
            return null;

        return person.getFirst();
    }

    public void setFirst(String first) {
        if (person != null)
            this.person.setFirst(first);
    }

    @XmlElement
    public FuelEfficiencyType getFuelEfficiencyType() {
        if (person == null)
            return null;

        return person.getFuelEfficiencyType();
    }

    public void setFuelEfficiencyType(FuelEfficiencyType fuelEfficiencyType) {
        if (person != null)
            this.person.setFuelEfficiencyType(fuelEfficiencyType);
    }

    @XmlElement
    public Integer getHeight() {
        if (person == null)
            return null;

        return person.getHeight();
    }

    public void setHeight(Integer height) {
        if (person != null)
            this.person.setHeight(height);
    }

   @XmlElement
   public Integer getInfo()    {
       if (person == null)
           return null;

       return person.getInfo();
   }

    public void setInfo(Integer info)   {
        if (person != null)
            this.person.setInfo(info);
    }

    @XmlElement
    public String getLast() {
        if (person == null)
            return null;

        return person.getLast();
    }

    public void setLast(String last)    {
        if (person != null)
            this.person.setLast(last);
    }


    @XmlElement
    public String getLocale() {
        if (person == null)
            return null;

        return person.getLocale().getDisplayName();
    }

    public void setLocale(Locale locale) {
        if (person != null)
            this.person.setLocale(locale);
    }


    @XmlElement
    public MeasurementType getMeasurementType() {
        if (person == null)
            return null;

        return person.getMeasurementType();
    }

    public void setMeasurementType(MeasurementType measurementType) {
        if (person != null)
            this.person.setMeasurementType(measurementType);
    }


    @XmlElement
    public String getMiddle() {
        if (person == null)
            return null;

        return person.getMiddle();
    }

    public void setMiddle(String middle) {
        if (person != null)
            this.person.setMiddle(middle);
    }


    @XmlElement
    public Integer getPersonID() {
        if (person == null)
            return null;

        return person.getPersonID();
    }

    public void setPersonID(Integer personID) {
        if (person != null)
            this.person.setPersonID(personID);
    }


    @XmlElement
    public String getPriEmail() {
        if (person == null)
            return null;

        return person.getPriEmail();
    }

    public void setPriEmail(String priEmail) {
        if (person != null)
            this.person.setPriEmail(priEmail);
    }


    @XmlElement
    public String getPriPhone() {
        if (person == null)
            return null;

        return person.getPriPhone();
    }

    public void setPriPhone(String priPhone) {
        if (person != null)
            this.person.setPriPhone(priPhone);
    }





    @XmlElement
    public String getReportsTo() {
        if (person == null)
            return null;

        return person.getReportsTo();
    }

    public void setReportsTo(String reportsTo) {
        if (person != null)
            this.person.setReportsTo(reportsTo);
    }



    @XmlElement
    public String getSecEmail() {
        if (person == null)
            return null;

        return person.getSecEmail();
    }

    public void setSecEmail(String secEmail) {
        if (person != null)
            this.person.setSecEmail(secEmail);
    }


    @XmlElement
    public String getSecPhone() {
        if (person == null)
            return null;

        return person.getSecPhone();
    }

    public void setSecPhone(String secPhone) {
        if (person != null)
            this.person.setSecPhone(secPhone);
    }




    @XmlElement
    public String getSecText() {
        if (person == null)
            return null;

        return person.getSecText();
    }

    public void setSecText(String secText) {
        if (person != null)
            this.person.setSecText(secText);
    }



    @XmlElement
    public Status getStatus() {
        if (person == null)
            return null;

        return person.getStatus();
    }

    public void setStatus(Status status) {
        if (person != null)
            this.person.setStatus(status);
    }




    @XmlElement
    public String getSuffix() {
        if (person == null)
            return null;

        return person.getSuffix();
    }

    public void setSuffix(String suffix) {
        if (person != null)
            this.person.setSuffix(suffix);
    }



    @XmlElement
    public TimeZone getTimeZone() {
        if (person == null)
            return null;

        return person.getTimeZone();
    }

    public void setTimeZone(TimeZone timeZone) {
        if (person != null)
            this.person.setTimeZone(timeZone);
    }



    @XmlElement
    public String getTitle() {
        if (person == null)
            return null;

        return person.getTitle();
    }

    public void setTitle(String title) {
        if (person != null)
            this.person.setTitle(title);
    }


    @XmlElement
    public User getUser() {
        if (person == null)
            return null;

        return person.getUser();
    }

    public void setUser(User user) {
        if (person != null)
            this.person.setUser(user);
    }


    @XmlElement
    public Integer getWarn() {
        if (person == null)
            return null;

        return person.getWarn();
    }

    public void setWarn(Integer warn) {
        if (person != null)
            this.person.setWarn(warn);
    }



    @XmlElement
    public Integer getWeight() {
        if (person == null)
            return null;

        return person.getWeight();
    }

    public void setWeight(Integer weight) {
        if (person != null)
            this.person.setWeight(weight);
    }



    @XmlElement
    public Integer getAddressID() {
        if (person == null)
            return null;

        return person.getAddressID();
    }

    public void setAddressID(Integer addressID) {
        if (person != null)
            this.person.setAddressID(addressID);
    }

    public Double getMilesDriven() {
        return milesDriven;
    }

    public void setMilesDriven(Double milesDriven) {
        this.milesDriven = milesDriven;
    }

    public Double getSpeeding() {
        return speeding;
    }

    public void setSpeeding(Double speeding) {
        this.speeding = speeding;
    }

    public Double getSpeedTime() {
        return speedTime;
    }

    public void setSpeedTime(Double speedTime) {
        this.speedTime = speedTime;
    }

    public Double getAggressiveAccel() {
        return aggressiveAccel;
    }

    public void setAggressiveAccel(Double aggressiveAccel) {
        this.aggressiveAccel = aggressiveAccel;
    }

    public Double getAggressiveBrake() {
        return aggressiveBrake;
    }

    public void setAggressiveBrake(Double aggressiveBrake) {
        this.aggressiveBrake = aggressiveBrake;
    }

    public Double getOverall() {
        return overall;
    }

    public void setOverall(Double overall) {
        this.overall = overall;
    }

    public Double getAggressiveAccelEvents() {
        return aggressiveAccelEvents;
    }

    public void setAggressiveAccelEvents(Double aggressiveAccelEvents) {
        this.aggressiveAccelEvents = aggressiveAccelEvents;
    }

    public Double getAggressiveBrakeEvents() {
        return aggressiveBrakeEvents;
    }

    public void setAggressiveBrakeEvents(Double aggressiveBrakeEvents) {
        this.aggressiveBrakeEvents = aggressiveBrakeEvents;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getAggressiveTurnsEvents() {
        return aggressiveTurnsEvents;
    }

    public void setAggressiveTurnsEvents(Double aggressiveTurnsEvents) {
        this.aggressiveTurnsEvents = aggressiveTurnsEvents;
    }

    public Double getAggressiveBumpEvents() {
        return aggressiveBumpEvents;
    }

    public void setAggressiveBumpEvents(Double aggressiveBumpEvents) {
        this.aggressiveBumpEvents = aggressiveBumpEvents;
    }
}


