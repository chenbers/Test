package com.inthinc.pro.model;

import java.text.DecimalFormat;

public class Score
{
    private Double   score;
    
    public Score(Integer Score)
    {
        setScore(Score == null ? -1.0D : Score / 10D);
    }
    
    public Double getScore()
    {
        return score;
    }

    public void setScore(Double score)
    {
        this.score = score;
    }

    public String toString()
    {
        if(score < 0)
            return "N/A";
        else
            return new DecimalFormat("0.#").format(score);
    }
}
