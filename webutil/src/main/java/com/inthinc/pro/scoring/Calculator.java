package com.inthinc.pro.scoring;

import java.util.List;

import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventType;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;
// import com.inthinc.pro.scoring.model.OverallScoreDetails;

public class Calculator {

	// Some constants:
	public final static double a = -10.4888220818923d; // Offset for scores in
														// log scale.
	public final static double b = 1.16563268601352d; // Scale factor for scores
														// in log scale.
	public final static double exp_ab = Math.exp(-a/b);
	
	public final static double seatbelt_a = 0.3d; 
	public final static double seatbelt_b = 0.2d; 
	public final static double speeding_a = 1.0d;
	public final static double speeding_b = 0.2d;

	public final static double agg_bump = 0.270663d; // Percent of scores for
														// bump (scale factor
														// used to compute
														// bump-only scores)
	public final static double agg_turn = 0.321069d; // Percent of scores for
														// turn (scale factor
														// used to compute
														// turn-only scores)
	public final static double agg_brake = 0.382325d; // Percent of scores for
														// brake (scale factor
														// used to compute
														// brake-only scores)
	public final static double agg_accel = 0.0259425d; // Percent of scores for
														// accel (scale factor
														// used to compute
														// accel-only scores)
	
	public final static double MAX_SCORE = 5.0d;
	public final static double MIN_SCORE = 0.0d;

	/*
		The p2s function is solving the problem of linearizing the scores and scaling
		them into a reasonable range.
		The penalties generally are the sum of some quantity squared.
		The sqrt in p2s is roughly taking the rms or quadratic mean of the individual penalties.
		The ln (natural log) is used to shape the scores from their natural
		exponential shape into a straight line.
		The coefficients a and b scale and center the values to get typical scores
		centered between 0.0 and 1.0.
		
			double result = 50.0;
	if (odometer > 0)
	{
		double l = 0.0;
		double sq = sqrt(penalty/((double)odometer));
		if (sq > 1e-10)
			l = a+b*log(sq);
		result = 50.0 * (1-l);
		if (result > 50.0) result = 50.0;
		if (result < 0.0) result = 0.0;
	}
	return result;

	 */

	private double p2s(double aval, double bval, double penalty, double distance)
	{
		double result = 0.0d;
		double penaltyDistance = Math.sqrt(penalty/distance);
		if (penaltyDistance > 1e-10)
			result = (aval + bval * Math.log(penaltyDistance));
		result =  MAX_SCORE * (1.0d - result);
		
		if (result > MAX_SCORE)
			return MAX_SCORE;
		if (result < MIN_SCORE)
			return MIN_SCORE;
		return result;
	}

	public double getDistance(List<Event> eventList) {
		double distance = 0d;
		for (Event event : eventList)
		{
			if (event.getOdometer() != null)// && event.getSats() > 0)
				distance += event.getOdometer().doubleValue();
		}
		return distance;
	}

	//	overallScore =
	//			5.0-sqrt(0.4*(5.0-speedScore)^2+0.4*(5.0-drivingStyleScore)^2+0.2*(5.0-seatbeltScore)^2);
	//		               This is now a weighted RMS of the other scores.
	public double getOverallScore(List<Event> eventList)
	{
		return getOverallScore(eventList, getDistance(eventList));
	}
		

	public double getOverallScore(List<Event> eventList, double distance)
	{
		double speedScore = this.getSpeedingScore(eventList, distance);
		double seatbeltScore = this.getSeatbeltScore(eventList, distance);
		double drivingStyleScore = this.getDrivingStyleScore(eventList, distance);
		
		double speedComponent = 0.4 * squared(MAX_SCORE - speedScore); 
		double styleComponent = 0.4 * squared(MAX_SCORE - drivingStyleScore); 
		double seatbeltComponent = 0.2 * squared(MAX_SCORE - seatbeltScore); 

		return MAX_SCORE - Math.sqrt(speedComponent + styleComponent + seatbeltComponent);
	}
/*	
	public OverallScoreDetails getOverallScoreDetails(List<Event> eventList, double distance)
	{
		OverallScoreDetails details = new OverallScoreDetails();
		details.setDistance(distance);
		
		details.setSpeedScore(this.getSpeedingScore(eventList, distance));
		details.setSeatbeltScore(this.getSeatbeltScore(eventList, distance));
		details.setDrivingStyleScore(this.getDrivingStyleScore(eventList, distance));
		
		details.setSpeedComponent(0.4 * squared(MAX_SCORE - details.getSpeedScore())); 
		details.setStyleComponent(0.4 * squared(MAX_SCORE - details.getDrivingStyleScore())); 
		details.setSeatbeltComponent(0.2 * squared(MAX_SCORE - details.getSeatbeltScore())); 

		details.setOverallScore(MAX_SCORE - Math.sqrt(details.getSpeedComponent() + details.getStyleComponent() + details.getSeatbeltComponent()));
		
		return details;
	}
*/	
	//speedingScore = p2s(1.0,0.2,speedingPenalty,mileage);
	public double getSpeedingScore(List<Event> eventList)
	{
		return getSpeedingScore(eventList, getDistance(eventList));
	}
	
	public double getSpeedingScore(List<Event> eventList, double distance)
	{
		return p2s(speeding_a, speeding_b, getSpeedingPenalty(eventList), distance);
	}
	
	// speedingPenalty = sum(((topSpeed-speedLimit)/speedLimit)^2*distance);
	private double getSpeedingPenalty(List<Event> eventList)
	{
		double sum = 0d;
		for (Event event : eventList)
		{
			if (event.getEventType().equals(EventType.SPEEDING))
			{
				SpeedingEvent speedingEvent = (SpeedingEvent)event;
				double topSpeed = (speedingEvent.getTopSpeed() == null) ? 0d : speedingEvent.getTopSpeed().doubleValue();
				double speedLimit = (speedingEvent.getSpeedLimit() == null) ? 0d : speedingEvent.getSpeedLimit().doubleValue();
				if (speedLimit == 0d)
					continue;
				double distance = (speedingEvent.getDistance() == null) ? 0.0d : speedingEvent.getDistance().doubleValue();
				
				sum += (squared((topSpeed-speedLimit)/speedLimit) * distance);
// System.out.println(topSpeed + " " + speedLimit + " " + distance + " " + sum);				
				
			}
		}
		
		return sum;
	}

	// seatbeltScore = p2s(0.3,0.2,seatbeltPenalty,mileage);
	public double getSeatbeltScore(List<Event> eventList)
	{
		return getSeatbeltScore(eventList, getDistance(eventList));
	}
	public double getSeatbeltScore(List<Event> eventList, double distance)
	{
		return p2s(seatbelt_a, seatbelt_b, getSeatbeltPenalty(eventList), distance);
	}
	
	// seatbeltPenalty = sum((topSpeed^2)*distance*2);
	public double getSeatbeltPenalty(List<Event> eventList)
	{
		double sum = 0d;
		for (Event event : eventList)
		{
			if (event.getEventType().equals(EventType.SEATBELT))
			{
				SeatBeltEvent seatBeltEvent = (SeatBeltEvent)event;
				double topSpeed = (seatBeltEvent.getTopSpeed() == null) ? 0.0d : seatBeltEvent.getTopSpeed().doubleValue();
				double distance = (seatBeltEvent.getDistance() == null) ? 0.0d : seatBeltEvent.getDistance().doubleValue();
				sum += (squared(topSpeed) * distance * 2);
			}
		}
		
		return sum;
	}
	
	

	// drivingStyleScore = ap2s(a,b,aggressiveBrakePenalty+aggressiveAccelPenalty+aggressiveTurnPenalty+aggressiveBumpPenalty, distance, 1.0);
	public double getDrivingStyleScore(List<Event> eventList)
	{
		return getDrivingStyleScore(eventList, getDistance(eventList));
	}
	public double getDrivingStyleScore(List<Event> eventList, double distance)
	{
		double penalty = 	getAggressiveBrakePenalty(eventList) + 
							getAggressiveAccelPenalty(eventList) + 
							getAggressiveTurnPenalty(eventList) + 
							getAggressiveBumpPenalty(eventList); 
		return ap2s(penalty, distance, 1.0);
	}
	
	//	aggressiveBrakeScore = ap2s(a,b,aggressiveBrakePenalty,distance,agg_brake);
	public double getAggressiveBrakeScore(List<Event> eventList, double distance)
	{
		return ap2s(getAggressiveBrakePenalty(eventList),distance,agg_brake);
	}
	
	//	aggressiveAccelScore = ap2s(a,b,aggressiveAccelPenalty,distance,agg_accel);
	public double getAggressiveAccelScore(List<Event> eventList, double distance)
	{
		return ap2s(getAggressiveAccelPenalty(eventList),distance,agg_accel);
	}
	
	//	aggressiveTurnScore = ap2s(a,b,aggressiveTurnPenalty,distance,agg_turn);
	public double getAggressiveTurnScore(List<Event> eventList, double distance)
	{
		return ap2s(getAggressiveTurnPenalty(eventList),distance,agg_turn);
	}
	
	//	aggressiveBumpScore = ap2s(a,b,aggressiveBumpPenalty,distance,agg_bump);
	public double getAggressiveBumpScore(List<Event> eventList, double distance)
	{
		return ap2s(getAggressiveBumpPenalty(eventList),distance,agg_bump);
	}

	
	//	The ap2s is used to scale aggressive driving events into a reasonable range.
	//
	//	ap2s(a,b,penalty,distance,scale) = 5-(a+b*ln((penalty/scale)/(odometer/100)));
	//
	//	if (odometer == 0) it will return 5.0
	//	if (penalty/scale)/(odometer/100) < exp(-a/b) it will return 5.0
	//	if the result of 5-(a+b*ln((penalty/scale)/(odometer/100))) < 0, it will return 0.0
	
	private double ap2s(double penalty, double distance, double scale)
	{
		if (distance == 0d)
			return MAX_SCORE;
		
		double prelim = (penalty/scale)/(distance/100d);
		
		if (prelim < exp_ab)
			return MAX_SCORE;
		
		double score = MAX_SCORE-(a+b*Math.log(prelim));
		
		if (score < 0d)
			return MIN_SCORE;
		
		return score;
	}
	

	// aggressiveBrakePenalty = sum(xseverity(aggressiveBrakeEvents));
	private double getAggressiveBrakePenalty(List<Event> eventList)
	{
		double sum = 0d;
		for (Event event : eventList)
		{
			if (event.getEventType().equals(EventType.HARD_BRAKE))
			{
				sum += getXSeverity((AggressiveDrivingEvent)event);
			}
		}
		
		return sum;
	}
	
	// aggressiveAccelPenalty = sum(xseverity(aggressiveAccelEvents));
	private double getAggressiveAccelPenalty(List<Event> eventList)
	{
		double sum = 0d;
		for (Event event : eventList)
		{
			if (event.getEventType().equals(EventType.HARD_ACCEL))
			{
				sum += getXSeverity((AggressiveDrivingEvent)event);
			}
		}
		
		return sum;
		
	}
	
	// aggressiveTurnPenalty = sum(yseverity(aggressiveTurnEvents));
	private double getAggressiveTurnPenalty(List<Event> eventList)
	{
		double sum = 0d;
		for (Event event : eventList)
		{
			if (event.getEventType().equals(EventType.HARD_TURN))
			{
				sum += getYSeverity((AggressiveDrivingEvent)event);
			}
		}
		
		return sum;
		
	}
	
	// aggressiveBumpPenalty = sum(zseverity(aggressiveBumpEvents));
	private double getAggressiveBumpPenalty(List<Event> eventList)
	{
		double sum = 0d;
		for (Event event : eventList)
		{
			if (event.getEventType().equals(EventType.HARD_VERT))
			{
				sum += getZSeverity((AggressiveDrivingEvent)event);
			}
		}
		
		return sum;
		
	}

	// xseverity = (deltaX)^2 * (speed+deltaX/10)^2;
	private double getXSeverity(AggressiveDrivingEvent event) {
		double deltaX = getDeltaValue(event.getDeltaX());

		double deltaXSpeed = ((event.getSpeed() == null) ? 0d : event
				.getSpeed().doubleValue()
				+ deltaX / 10d);

		return squared(deltaX) * squared(deltaXSpeed);
	}

	// yseverity = (deltaY)^2 * (min(speed,5))^2;
	private double getYSeverity(AggressiveDrivingEvent event) {
		double deltaY = getDeltaValue(event.getDeltaY());

		double deltaYSpeed = Math.min(((event.getSpeed() == null) ? 0d : event
				.getSpeed().doubleValue()), 5d);

		return squared(deltaY) * squared(deltaYSpeed);
	}

	// zseverity = (deltaZ/3)^2 * (min(speed,5))^2;
	private double getZSeverity(AggressiveDrivingEvent event) {
		double deltaZ = getDeltaValue(event.getDeltaZ());

		double deltaZSpeed = Math.min(((event.getSpeed() == null) ? 0d : event
				.getSpeed().doubleValue()), 5d);

		return squared(deltaZ) * squared(deltaZSpeed);
	}

	private double squared(double val) {
		return val * val;
	}

	// deltas store as Integer, divide by 10 for float value
	private double getDeltaValue(Integer delta) {
		if (delta == null)
			return 0d;

		return delta.doubleValue() / 10d;
	}
}
