package com.inthinc.pro.model;

public class OverallScore
{
    // score is 0-50 so we need to divide by 10
    Integer score;
    
    Integer userID;
    Integer date;
    

    public OverallScore(Integer userID, Integer score, Integer date)
    {
        super();
        this.score = score;
        this.userID = userID;
        this.date = date;
    }

    public Integer getUserID()
    {
        return userID;
    }

    public void setUserID(Integer userID)
    {
        this.userID = userID;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public Integer getDate()
    {
        return date;
    }

    public void setDate(Integer date)
    {
        this.date = date;
    }
    
    
}
