package it.util.hos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.hos.model.HOSRec;

public class TestCase {

    String description;
    String id;
    List<HOSRec> driverLog;
    ArrayList<TestCaseResults> results;
    int drivingCnt;
    boolean isSingleDriver = true; // used for Canada 2007 rulesets
    Date currentTime;
    Double homeLat;
    Double homeLng;
    TimeZone timeZone;

    public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public Double getHomeLat() {
		return homeLat;
	}

	public void setHomeLat(Double homeLat) {
		this.homeLat = homeLat;
	}

	public Double getHomeLng() {
		return homeLng;
	}

	public void setHomeLng(Double homeLng) {
		this.homeLng = homeLng;
	}

	public boolean isSingleDriver() {
        return isSingleDriver;
    }

    public void setSingleDriver(boolean isSingleDriver) {
        this.isSingleDriver = isSingleDriver;
    }

    public int getDrivingCnt() {
        return drivingCnt;
    }

    public void setDrivingCnt(int drivingCnt) {
        this.drivingCnt = drivingCnt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<HOSRec> getDriverLog() {
        return driverLog;
    }

    public void setDriverLog(List<HOSRec> driverLog) {
        this.driverLog = driverLog;
    }

    public ArrayList<TestCaseResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<TestCaseResults> results) {
        this.results = results;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
