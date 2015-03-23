package com.inthinc.pro.model;

import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Speed;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.TimeZone;

/**
 * Holds person data and score data.
 */
@XmlRootElement
public class PersonScoresView {

    @JsonIgnore
    @XmlTransient
    private Person person;

    @JsonIgnore
    @XmlTransient
    private com.inthinc.pro.model.aggregation.Score score;

    @JsonIgnore
    @XmlTransient
    private Speed speed;

    public PersonScoresView(Person person, com.inthinc.pro.model.aggregation.Score score, Speed speed) {
        this.person = person;
        this.score = score;
        this.speed = speed;
    }

    public PersonScoresView() {
    }

    @XmlElement
    public Date getModified() {
        if (person == null)
            return null;

        return person.getModified();
    }

    @XmlElement
    public Integer getAcctID() {
        if (person == null)
            return null;

        return person.getAccountID();
    }

    @XmlElement
    public Driver getDriver() {
        if (person == null)
            return null;

        return person.getDriver();
    }

    @XmlElement
    public String getEmpid() {
        if (person == null)
            return null;

        return person.getEmpid();
    }

    @XmlElement
    public String getFirst() {
        if (person == null)
            return null;

        return person.getFirst();
    }

    @XmlElement
    public FuelEfficiencyType getFuelEfficiencyType() {
        if (person == null)
            return null;

        return person.getFuelEfficiencyType();
    }

    @XmlElement
    public Integer getHeight() {
        if (person == null)
            return null;

        return person.getHeight();
    }

    @XmlElement
    public Integer getInfo() {
        if (person == null)
            return null;

        return person.getInfo();
    }

    @XmlElement
    public String getLast() {
        if (person == null)
            return null;

        return person.getLast();
    }

    @XmlElement
    public String getLocale() {
        if (person == null)
            return null;

        return person.getLocale().getDisplayName();
    }

    @XmlElement
    public MeasurementType getMeasurementType() {
        if (person == null)
            return null;

        return person.getMeasurementType();
    }

    @XmlElement
    public String getMiddle() {
        if (person == null)
            return null;

        return person.getMiddle();
    }

    @XmlElement
    public Integer getPersonID() {
        if (person == null)
            return null;

        return person.getPersonID();
    }

    @XmlElement
    public String getPriEmail() {
        if (person == null)
            return null;

        return person.getPriEmail();
    }

    @XmlElement
    public String getPriPhone() {
        if (person == null)
            return null;

        return person.getPriPhone();
    }

    @XmlElement
    public String getReportsTo() {
        if (person == null)
            return null;

        return person.getReportsTo();
    }

    @XmlElement
    public String getSecEmail() {
        if (person == null)
            return null;

        return person.getSecEmail();
    }

    @XmlElement
    public String getSecPhone() {
        if (person == null)
            return null;

        return person.getSecPhone();
    }

    @XmlElement
    public String getSecText() {
        if (person == null)
            return null;

        return person.getSecText();
    }

    @XmlElement
    public Status getStatus() {
        if (person == null)
            return null;

        return person.getStatus();
    }

    @XmlElement
    public String getSuffix() {
        if (person == null)
            return null;

        return person.getSuffix();
    }

    @XmlElement
    public TimeZone getTimeZone() {
        if (person == null)
            return null;

        return person.getTimeZone();
    }

    @XmlElement
    public String getTitle() {
        if (person == null)
            return null;

        return person.getTitle();
    }

    @XmlElement
    public User getUser() {
        if (person == null)
            return null;

        return person.getUser();
    }

    @XmlElement
    public Integer getWarn() {
        if (person == null)
            return null;

        return person.getWarn();
    }

    @XmlElement
    public Integer getWeight() {
        if (person == null)
            return null;

        return person.getWeight();
    }

    @XmlElement
    public Integer getAddressID() {
        if (person == null)
            return null;

        return person.getAddressID();
    }

    @XmlElement
    public Double getMilesDriven() {
        return score != null && score.getMilesDriven() != null ? score.getMilesDriven().doubleValue() : 0d;
    }

    @XmlElement
    public Double getSpeeding() {
        return score != null && score.getSpeeding() != null ? score.getSpeeding().doubleValue() : 0d;
    }

    @XmlElement
    public Double getAggressiveAccel() {
        return score != null && score.getAggressiveAccel() != null ? score.getAggressiveAccel().doubleValue() : 0d;
    }

    @XmlElement
    public Double getAggressiveBrake() {
        return score != null && score.getAggressiveBrake() != null ? score.getAggressiveBrake().doubleValue() : 0d;
    }

    @XmlElement
    public Double getOverall() {
        return score != null && score.getOverall() != null ? score.getOverall().doubleValue() : 0d;
    }

    @XmlElement
    public Double getAggressiveAccelEvents() {
        return score != null && score.getAggressiveAccelEvents() != null ? score.getAggressiveAccelEvents().doubleValue() : 0d;
    }

    @XmlElement
    public Double getAggressiveBrakeEvents() {
        return score != null && score.getAggressiveBrakeEvents() != null ? score.getAggressiveBrakeEvents().doubleValue() : 0d;
    }

    @XmlElement
    public Double getAggressiveBumpEvents() {
        return score != null && score.getAggressiveBumpEvents() != null ? score.getAggressiveBumpEvents().doubleValue() : 0d;
    }

    @XmlElement
    public Double getAggressiveTurnsEvents() {
        if (score == null)
            return 0d;

        Number numAgressiveLeftEvents = score.getAggressiveLeftEvents();
        Number numAgressiveRightEvents = score.getAggressiveRightEvents();
        Double aggressiveLeftEvents = numAgressiveLeftEvents != null ? numAgressiveLeftEvents.doubleValue() : 0d;
        Double aggressiveRightEvents = numAgressiveRightEvents != null ? numAgressiveRightEvents.doubleValue() : 0d;
        return aggressiveLeftEvents + aggressiveRightEvents;
    }

    @XmlElement
    public Integer getSpeedTime() {
        return speed != null && speed.getSpeedTime() != null ? speed.getSpeedTime() : 0;
    }

    @XmlElement
    public Integer getMaxSpeed() {
        return speed != null && speed.getMaxSpeed() != null ? speed.getMaxSpeed() : 0;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }
}


