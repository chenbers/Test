package com.inthinc.pro.model;

import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.Speed;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Holds person score views.
 */
@XmlRootElement
public class PersonScoresViewList {
    public PersonScoresViewList() {
    }

    public PersonScoresViewList(List<PersonScoresView> personScores) {
        this.personScores = personScores;
    }

    private List<PersonScoresView> personScores;

    public List<PersonScoresView> getPersonScores() {
        return personScores;
    }

    public void setPersonScores(List<PersonScoresView> personScores) {
        this.personScores = personScores;
    }
}