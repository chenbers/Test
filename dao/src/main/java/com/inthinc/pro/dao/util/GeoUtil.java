package com.inthinc.pro.dao.util;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;

public class GeoUtil {
    private static double EARTH_RADIUS_MI = 3958.75;
    private static double EARTH_RADIUS_KM = 6371;
    /**
     * Calculates the bearing from the <code>origin</code> to the <code>destination</code>
     * @param origin the origin point LatLng
     * @param destination the destination point LatLng
     * @return a String (english) representation of the bearing (compass point)
     */
    public static String headingBetween(LatLng origin, LatLng destination) {
        String results = " * ";
        double bearing = bearing(origin, destination);
        
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
    public static double bearing(LatLng latLng1, LatLng latLng2) {
        double deltaLong = Math.toRadians(latLng2.getLng() - latLng1.getLng());

        double lat1 = Math.toRadians(latLng1.getLat());
        double lat2 = Math.toRadians(latLng2.getLat());

        double y = Math.sin(deltaLong) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLong);
        double result = Math.toDegrees(Math.atan2(y, x));
        return (result + 360.0) % 360.0;
       }
    /**
     * Calculates the distance between <code>orig</code> and <code>dest</code in Miles or Kilometers.
     * @param orig the origin point LatLng
     * @param dest the destination point LatLng
     * @param units specify English or Metric units
     * @return a float representation of the distance between the given points in Miles or Kilometers depending on english/metric units.
     */
    public static float distBetween(LatLng orig, LatLng dest, MeasurementType units) {
        double earthRadius = (MeasurementType.ENGLISH.equals(units)) ? EARTH_RADIUS_MI : EARTH_RADIUS_KM;

        double dLat = Math.toRadians(dest.getLat() - orig.getLat());
        double dLng = Math.toRadians(dest.getLng() - orig.getLng());
        double halfChordSquared = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(orig.getLat())) * Math.cos(Math.toRadians(dest.getLat())) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double radianDistance = 2 * Math.atan2(Math.sqrt(halfChordSquared), Math.sqrt(1 - halfChordSquared));
        double dist = earthRadius * radianDistance;
        return new Float(dist).floatValue();
    }

}
