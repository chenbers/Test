package com.inthinc.pro.scoring;

// ported from driveq.c in c code
/**
 * @see {@link com.inthinc.pro.scoring.ScoringFormulas}
 *
 */
@Deprecated 

public class ScoreCalculator {
    
    public static double agg_a = -10.4888220818923;
    public static double agg_b = 1.16563268601352;
//    public static double agg_bump  = 0.36351;   /* 0.270663 */
//    public static double agg_turn  = 0.412991;  /* 0.321069 */
//    public static double agg_brake = 0.164130;  /* 0.382325 */
//    public static double agg_accel = 0.0593694; /* 0.0259425 */
    
    
    private double penaltyToScore(double penalty, double odometer, double a, double b) {
        double result = 50.0;
        if (odometer > 0)
        {
            double l = 0.0;
            double sq = Math.sqrt(penalty/odometer);
            if (sq > 1e-10)
                l = a+b*Math.log(sq);
            result = 50.0 * (1-l);
            if (result > 50.0) result = 50.0;
            if (result < 0.0) result = 0.0;
        }
        return result;
    }

    private double scaledPenaltyToScore(double penalty, double odometer, double a, double b, double pct) {
        double result = 50.0;
        if (odometer > 0)
        {
            double l = 0.0;
            double scaledPenaltyPerMile = (penalty/pct)/(odometer/100.0); 
            if (scaledPenaltyPerMile > (Math.exp(-a/b)))
                l = a+b*Math.log(scaledPenaltyPerMile);
            result = 10*(5-l);
            if (result > 50.0) result = 50.0;
            if (result < 0.0) result = 0.0;
        }
        return result;
    }

    
    public double getStyleScore(double totalMiles, double bumpPenalty, double turnPenalty, double accelPenalty, double brakePenalty) {
        return scaledPenaltyToScore(bumpPenalty+turnPenalty+accelPenalty+brakePenalty, totalMiles, agg_a, agg_b, 1.0);
    }

    
    public double getSpeedingScore(double totalMiles, Double... speedPenaltyList) {
        Double totalPenalty = 0.0;
        for (Double speedPenalty : speedPenaltyList) {
            totalPenalty += speedPenalty;
        }
        return penaltyToScore(totalPenalty, totalMiles, 1.0, 0.2);
    }

    
    public double getSpeedingScore(double totalMiles, double speedPenalty) {
        return penaltyToScore(speedPenalty, totalMiles, 1.0, 0.2);
    }
    public double getStyleScore(double totalMiles, double stylePenalty) {
        return scaledPenaltyToScore(stylePenalty, totalMiles, agg_a, agg_b, 1.0);
    }
    public double getSeatBeltScore(double totalMiles, double seatbeltPenalty) {
        return penaltyToScore(seatbeltPenalty, totalMiles, 0.3d ,0.2d);
    }
    
    public double getOverall(double seatbelt, double style, double speeding) {
        return 50-Math.sqrt(0.4*Math.pow(50-speeding,2)+0.4*Math.pow(50-style,2)+0.2*Math.pow(50-seatbelt,2));
    }


    public double getOverallFromPenalty(double totalMiles, double seatbeltPenalty, double stylePenalty, double speedingPenalty) {
        double seatbelt = getSeatBeltScore(totalMiles, seatbeltPenalty);
        double style = getStyleScore(totalMiles, stylePenalty);
        double speeding = getSpeedingScore(totalMiles, speedingPenalty);
        return getOverall(seatbelt, style, speeding);
    }
}
