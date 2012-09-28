package com.inthinc.pro.model;

import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverScore implements Comparable<DriverScore>
{
    private static final Logger logger = Logger.getLogger(DriverScore.class);
    Driver driver;
    Vehicle vehicle;
    Number milesDriven;
    Integer score;

    public DriverScore()
    {

    }

    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public Number getMilesDriven()
    {
        return milesDriven;
    }

    public void setMilesDriven(Number milesDriven)
    {
        this.milesDriven = milesDriven;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    @Override
    public int compareTo(DriverScore o)
    {

        // check if score fields are null. if so, handle appropriately
        if (this.score == null && o.getScore() != null)
            return -1;
        if (o.getScore() == null && this.score != null)
            return 1;
        // if neither score is null, compare score
        if (this.score != null && o.getScore() != null)
        {            
            int thisScore = this.score.intValue();
            int thatScore = o.getScore().intValue();
            int scoreResult = (thisScore < thatScore ? 1 : (thisScore == thatScore ? 0 : -1));
            // if scores are NOT the same, return outcome
            if (scoreResult != 0)
                return scoreResult;
            // if scores are the same, do not return. allow for further comparison
        }

        // at this point, either both score values are null or the scores are equal
        // compare driver objects (note: Driver should never be null. If it is, there is a big problem)

        return this.driver.compareTo(o.getDriver());

    }
}
