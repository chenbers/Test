package com.inthinc.pro.dao.util;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;

public class GeoUtil {
    /**
     * Calculates the bearing from the <code>origin</code> to the <code>destination</code>
     * @param origin the origin point LatLng
     * @param destination the destination point LatLng
     * @return a String (english) representation of the bearing (compass point)
     */
    public static String headingBetween(LatLng origin, LatLng destination) {
        String results = " * ";
        double deltaLong = Math.abs(origin.getLng() - destination.getLng());
        double y = Math.sin(deltaLong) * Math.cos(destination.getLat());
        double x = Math.cos(origin.getLat()) * Math.sin(destination.getLat()) - Math.sin(origin.getLat()) * Math.cos(origin.getLat()) * Math.cos(deltaLong);
        double bearing = Math.toDegrees(Math.atan2(y, x));
        double north = 360; // or full circle in degrees
        String[] directionals = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };
        double bearingDegreesDelta = 360; // starting at zero and the farthest bearing couldn't be more than 360 degrees away
        for (int i = 0; i < directionals.length; i++) {
            double degrees = (north / directionals.length) * i;
            double tempDelta = Math.abs(bearing - degrees);
            if (tempDelta < bearingDegreesDelta) {
                bearingDegreesDelta = tempDelta;
                results = directionals[i];
            } else {
                break;
            }
        }
        return results;
    }

    /**
     * Calculates the distance between <code>orig</code> and <code>dest</code in Miles or Kilometers.
     * @param orig the origin point LatLng
     * @param dest the destination point LatLng
     * @param units specify English or Metric units
     * @return a float representation of the distance between the given points in Miles or Kilometers depending on english/metric units.
     */
    public static float distBetween(LatLng orig, LatLng dest, MeasurementType units) {
        double earthRadiusMiles = 3958.75;
        double earthRadiusKilo = 6371;
        double earthRadius = (MeasurementType.ENGLISH.equals(units)) ? earthRadiusMiles : earthRadiusKilo;

        double dLat = Math.toRadians(dest.getLat() - orig.getLat());
        double dLng = Math.toRadians(dest.getLng() - orig.getLng());
        double halfChordSquared = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(orig.getLat())) * Math.cos(Math.toRadians(dest.getLat())) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double radianDistance = 2 * Math.atan2(Math.sqrt(halfChordSquared), Math.sqrt(1 - halfChordSquared));
        double dist = earthRadius * radianDistance;
        return new Float(dist).floatValue();
    }

}
