package com.inthinc.pro.selenium.util;

public class OverallScoreSummary {
    private Integer zeroToOne;
    private Integer oneToTwo;
    private Integer twoToThree;
    private Integer threeToFour;
    private Integer fourToFive;
    
    public OverallScoreSummary() {
        this.zeroToOne = null;
        this.oneToTwo = null;
        this.twoToThree = null;
        this.threeToFour = null;
        this.fourToFive = null;
    }
    
    public Integer getZeroToOne() {
        return zeroToOne;
    }
    public void setZeroToOne(Integer zeroToOne) {
        this.zeroToOne = zeroToOne;
    }
    public Integer getOneToTwo() {
        return oneToTwo;
    }
    public void setOneToTwo(Integer oneToTwo) {
        this.oneToTwo = oneToTwo;
    }
    public Integer getTwoToThree() {
        return twoToThree;
    }
    public void setTwoToThree(Integer twoToThree) {
        this.twoToThree = twoToThree;
    }
    public Integer getThreeToFour() {
        return threeToFour;
    }
    public void setThreeToFour(Integer threeToFour) {
        this.threeToFour = threeToFour;
    }
    public Integer getFourToFive() {
        return fourToFive;
    }
    public void setFourToFive(Integer fourToFive) {
        this.fourToFive = fourToFive;
    }

}
