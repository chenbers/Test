package com.inthinc.pro.model;

public class DriverScore implements Comparable<DriverScore>
{
    Driver driver;
    Vehicle vehicle;
    Integer milesDriven;
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
    public Integer getMilesDriven()
    {
        return milesDriven;
    }
    public void setMilesDriven(Integer milesDriven)
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
        Integer scoreResult = score.compareTo(o.getScore());
        
        if(scoreResult != 0 )
            return score.compareTo(o.getScore());
        else
            return driver.getPerson().getLast().compareTo(o.getDriver().getPerson().getLast());
        
        //return score.compareTo(o.getScore());
    }

}
