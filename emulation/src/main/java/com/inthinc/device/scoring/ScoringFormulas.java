package com.inthinc.device.scoring;

import java.util.Iterator;
import java.util.Map;

import com.inthinc.pro.automation.logging.Log;


/**
 * 
 * 
 * Units for mileage are 1/100th of a mile so the value 500 is 5 miles.
 * @author dtanner
 *
 */
public class ScoringFormulas {
	
    public final static double agg_a = -10.4888220818923;
    public final static double agg_b = 1.16563268601352;
    public final static double MAX_SCORE = 5.0;
    public final static double MIN_SCORE = 0.0;

    /**
     * Typical Aggressive events have deltaX/Y/Z values between 70 and 200. <br />
     * 220 = 1G.
     * @param deltaX
     * @param speed
     * @return
     */
	public final static Double xSeverity( Double deltaX, Double speed){
		Double severity = Math.pow(Math.abs(deltaX), 2.0) * Math.pow(speed+Math.abs(deltaX)/10.0, 2.0);
		Log.debug("deltaX = "+deltaX);
		Log.debug("speed = " + speed);
		if (deltaX>0) {
			Log.debug("Accell Severity: "+severity);
		}else 
			Log.debug("Brake Severity: "+severity);
		return severity;
	}
	

	/**
	 * Typical Aggressive events have deltaX/Y/Z values between 70 and 200. <br />
     * 220 = 1G.
	 * @param deltaY
	 * @param speed
	 * @return
	 */
	public final static Double ySeverity( Double deltaY, Double speed){
		speed = Math.max(speed, 5.0);
		Double severity = Math.pow(Math.abs(deltaY), 2.0) * Math.pow(speed, 2.0);
		Log.debug("Turn severity = " + severity);
		return severity;
	}
	
	/**
	 * Typical Aggressive events have deltaX/Y/Z values between 70 and 200. <br />
     * 220 = 1G.
	 * @param deltaZ
	 * @param speed
	 * @return
	 */
	public final static Double zSeverity( Double deltaZ, Double speed){
		speed = Math.max(speed, 5.0);
		Double severity = Math.pow(Math.abs(deltaZ), 2.0) * Math.pow(speed / 3.0, 2.0);
		Log.debug("Bump/Dip severity = " + severity);
		return severity;
	}
	

	/**
	 * The p2s function is solving the problem of linearizing the scores and scaling<br />
	 * them into a reasonable range.<br />
	 * The penalties generally are the sum of some quantity squared.<br />
	 * The sqrt in p2s is roughly taking the rms or quadratic mean of the individual<br />
	 * penalties.<br />
	 * The ln (natural log) is used to shape the scores from their natural<br />
	 * exponential shape into a straight line.<br />
	 * The coefficients a and b scale and center the values to get typical scores<br />
	 * centered between 0.0 and 1.0.<br />
	 * <br />
	 * p2s(a,b,penalty,distance) = 5.0*(1-(a+b*ln(sqrt(penalty/distance))));
	 * 
	 * @param a
	 * @param b
	 * @param penalty
	 * @param distance
	 * @return
	 */
	public final static Double p2s(Double a, Double b, Double penalty, Double distance) {
		Double score;
		try {
			if (distance == 0) score = 5.0;
			else if ((penalty/distance) == 0) score = 5.0;
			else {
				score = 5.0*(1.0-(a+b*Math.log(Math.sqrt(penalty/distance))));
				Log.debug("p2s raw score = " + score);
			}
		}catch(ArithmeticException error) {
			score = 5.0;
		}
		if (score<0.0)score=0.0;
		if (score>5.0)score=5.0;
		score = Math.floor(score*10.0)/10.0;
		return score;
	}
	
	/**
	 * The ap2s is used to scale aggressive driving events into a reasonable range.
	 * @param a
	 * @param b
	 * @param penalty
	 * @param mileage
	 * @param scale
	 * @return
	 */
	public final static Double ap2s( Double a, Double b, Double penalty, Double mileage, Double scale) {
		Double score;
		try {
			if (mileage==0)score=5.0;
			else if (((penalty/scale)/(mileage/100.0))<Math.exp(-a/b)) {
				score=5.0;
			}
			else {
				score = 5.0-(a+b*Math.log((penalty/scale)/(mileage/100.0)));
				Log.debug("a="+a	);
				Log.debug("b="+b);
				Log.debug("penalty="+penalty);
				Log.debug("mileage="+mileage);
				Log.debug("scale="+scale);
				Log.debug("ap2s raw score = " + score);
			}
		}catch(ArithmeticException e) {
			Log.error("%s",e);
			score=5.0;
		}
		if (score<0.0)score=0.0;
		if (score>5.0)score=5.0;
		score = Math.floor(score*10.0)/10.0;
		return score;
	}
	
	public final static Double speedingPenalty(Map<Long, Map<String, Integer>> speeding) {
		Iterator<Long> itr = speeding.keySet().iterator();
		Double penalty = 0.0;
		while (itr.hasNext()) {
			Map<String, Integer> map = speeding.get(itr.next());
			penalty += speedingPenalty(map.get("topSpeed").doubleValue(),
					map.get("speedLimit").doubleValue(),map.get("odometer").doubleValue());
		}
		Log.debug("Speeding Penalty en mass: "+penalty);
		return penalty;
	}
	
	public final static Double speedingPenalty(Double topSpeed, Double speedLimit, Double distance) {
		Double penalty = 0.0;
		if (speedLimit==0)return 0.0;
		else if (speedLimit >= topSpeed) return 0.0;
		else {
			penalty = Math.pow(( topSpeed - speedLimit) / speedLimit, 2) * distance;
		}
		Log.debug("Speeding penalty: "+penalty);
		return penalty;
	}
	
	
	
    public static double getSpeedingScore(double totalMiles, double speedPenalty) {
        return p2s(1.0, 0.2, speedPenalty, totalMiles);
    }
    public static double getStyleScore(double totalMiles, double stylePenalty) {
        return ap2s(agg_a, agg_b, stylePenalty, totalMiles, 1.0);
    }
    public static double getSeatBeltScore(double totalMiles, double seatbeltPenalty) {
        return p2s(0.3d ,0.2d, seatbeltPenalty, totalMiles);
    }

    public static double getOverallFromPenalty(double totalMiles, double seatbeltPenalty, double stylePenalty, double speedingPenalty) {
        double seatbelt = getSeatBeltScore(totalMiles, seatbeltPenalty);
        double style = getStyleScore(totalMiles, stylePenalty);
        double speeding = getSpeedingScore(totalMiles, speedingPenalty);
        return overallScore(speeding, style, seatbelt);
    }
    
    /**
	 * This is now a weighted RMS of the other scores.
	 * @param speed
	 * @param style
	 * @param seatBelt
	 * @return
	 */
	public final static Double overallScore(Double speed, Double style, Double seatBelt) {
		Double overall = Math.ceil((MAX_SCORE-Math.sqrt( 
				0.4 * Math.pow(MAX_SCORE-speed, 2) + 
				0.4 * Math.pow(MAX_SCORE-style, 2) + 
				0.2 * Math.pow(MAX_SCORE-seatBelt,2)))*10.0)/10.0;
		Log.debug("Overall Score: "+overall);
		return overall;
	}

}
