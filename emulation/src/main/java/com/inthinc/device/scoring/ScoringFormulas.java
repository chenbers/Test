package com.inthinc.device.scoring;

import java.util.Iterator;
import java.util.Map;

import android.util.Log;



public class ScoringFormulas {
	
	public final static Double xSeverity( Double deltaX, Double speed){
		Double severity = Math.pow(Math.abs(deltaX), 2.0) * Math.pow(speed+Math.abs(deltaX)/10.0, 2.0);
		Log.i("deltaX = "+deltaX);
		Log.i("speed = " + speed);
		if (deltaX>0) {
			Log.d("Accell Severity: "+severity);
		}else Log.d("Brake Severity: "+severity);
		return severity;
	}
	

	
	public final static Double ySeverity( Double deltaY, Double speed){
		speed = Math.max(speed, 5.0);
		Double severity = Math.pow(Math.abs(deltaY), 2.0) * Math.pow(speed, 2.0);
		Log.d("Turn severity = " + severity);
		return severity;
	}
	
	public final static Double zSeverity( Double deltaZ, Double speed){
		speed = Math.max(speed, 5.0);
		Double severity = Math.pow(Math.abs(deltaZ), 2.0) * Math.pow(speed / 3.0, 2.0);
		Log.d("Bump/Dip severity = " + severity);
		return severity;
	}
	

	
	public final static Double p2s(Double a, Double b, Double penalty, Double distance) {
		Double score;
		try {
			if (distance == 0) score = 5.0;
			else if ((penalty/distance) == 0) score = 5.0;
			else {
				score = 5.0*(1.0-(a+b*Math.log(Math.sqrt(penalty/distance))));
				Log.d("p2s raw score = " + score);
			}
		}catch(ArithmeticException error) {
			score = 5.0;
		}
		if (score<0.0)score=0.0;
		if (score>5.0)score=5.0;
		score = Math.floor(score*10.0)/10.0;
		return score;
	}
	
	public final static Double ap2s( Double a, Double b, Double penalty, Double mileage, Double scale) {
		Double score;
		try {
			if (mileage==0)score=5.0;
			else if (((penalty/scale)/(mileage/100.0))<Math.exp(-a/b)) {
				score=5.0;
			}
			else {
				score = 5.0-(a+b*Math.log((penalty/scale)/(mileage/100.0)));
				Log.d("a="+a	);
				Log.d("b="+b);
				Log.d("penalty="+penalty);
				Log.d("mileage="+mileage);
				Log.d("scale="+scale);
				Log.d("ap2s raw score = " + score);
			}
		}catch(ArithmeticException e) {
			Log.wtf("%s",e);
			score=5.0;
		}
		if (score<0.0)score=0.0;
		if (score>5.0)score=5.0;
		score = Math.floor(score*10.0)/10.0;
		return score;
	}
	
	public final static Double speedingPenalty(Map<Long, Map<String, Integer>> speeding) {
		Iterator<Long> itr = speeding.keySet().iterator();
		Double grounded = 0.0;
		while (itr.hasNext()) {
			Map<String, Integer> signs = speeding.get(itr.next());
			grounded += speedingPenalty(signs.get("topSpeed").doubleValue(),
					signs.get("limit").doubleValue(),signs.get("distance").doubleValue());
		}
		Log.d("Speeding Penalty en mass: "+grounded);
		return grounded;
	}
	
	public final static Double speedingPenalty(Double youWentHowFast, Double theSkys, Double longJump) {
		Double grounded = 0.0;
		if (theSkys==0)return 0.0;
		else if (theSkys >= youWentHowFast) return 0.0;
		else {
			grounded = Math.pow(( youWentHowFast - theSkys) / theSkys, 2) * longJump;
		}
		Log.d("Speeding penalty: "+grounded);
		return grounded;
	}
	
	public final static Double overallScore(Double speed, Double style, Double seatB) {
		Double overall = Math.floor((5-Math.sqrt( 
				0.4 * Math.pow(5-speed, 2) + 
				0.4 * Math.pow(5-style, 2) + 
				0.2 * Math.pow(5-seatB,2)))*10)/10;
		Log.d("Overall Score: "+overall);
		return overall;
	}
	
}
