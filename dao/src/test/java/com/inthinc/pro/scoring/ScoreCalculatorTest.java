package com.inthinc.pro.scoring;

import static org.junit.Assert.*;

import org.junit.Test;


public class ScoreCalculatorTest {
    
    private double DELTA = 0.1;
    
    @Test 
    public void speedScoreTest() {
        
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        assertEquals("0 miles score is always 50", 50.0, scoreCalculator.getSpeedingScore(0, 100.0), DELTA);
        assertEquals("0 penalty score is always 50", 50.0, scoreCalculator.getSpeedingScore(100.0, 0.0), DELTA);
        
        assertEquals("penalty with mileage ex 1", 29.8, scoreCalculator.getSpeedingScore(9700.0, 25.0), DELTA);
        assertEquals("penalty with mileage ex 2", 22.1, scoreCalculator.getSpeedingScore(10500.0, 125.0), DELTA);
    }

    @Test 
    public void styleScoreTest() {
        
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        assertEquals("0 miles score is always 50", 50.0, scoreCalculator.getStyleScore(0, 100.0), DELTA);
        assertEquals("0 penalty score is always 50", 50.0, scoreCalculator.getStyleScore(100.0, 0.0), DELTA);
        
        assertEquals("penalty with mileage ex 1", 50.0, scoreCalculator.getStyleScore(9700.0, 435600.0), DELTA);
        assertEquals("penalty with mileage ex 2", 39.0, scoreCalculator.getStyleScore(10500.0, 2178000.0), DELTA);
    }

    @Test 
    public void seatbeltScoreTest() {
        
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        assertEquals("0 miles score is always 50", 50.0, scoreCalculator.getSeatBeltScore(0, 100.0), DELTA);
        assertEquals("0 penalty score is always 50", 50.0, scoreCalculator.getSeatBeltScore(100.0, 0.0), DELTA);
        
        assertEquals("penalty with mileage ex 1", 17.1, scoreCalculator.getSeatBeltScore(9700.0, 348100.0), DELTA);
        assertEquals("penalty with mileage ex 2", 9.4, scoreCalculator.getSeatBeltScore(10500.0, 1740500.0), DELTA);
    }


    @Test 
    public void overallTest() {
        
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        assertEquals("overall using penalty", 23.8, scoreCalculator.getOverallFromPenalty(10500, 1740500.0, 2178000.0, 125.0), DELTA);

        assertEquals("overall using score", 23.8, scoreCalculator.getOverall(scoreCalculator.getSeatBeltScore(10500, 1740500.0), 
                scoreCalculator.getStyleScore(10500, 2178000.0), scoreCalculator.getSpeedingScore(10500, 125.0)), DELTA);

    }
}
