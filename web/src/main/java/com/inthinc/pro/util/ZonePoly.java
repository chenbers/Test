package com.inthinc.pro.util;

import java.util.List;

import com.inthinc.pro.model.LatLng;

public class ZonePoly {
    List<LatLng> zonePolyPoints;

    public ZonePoly(List<LatLng> geogate_points) {
        this.zonePolyPoints = geogate_points;
    }

    public boolean contains(double lat, double lon) {
        boolean c = false;
        int i = 0;
        int j = zonePolyPoints.size() - 1;
        for (LatLng point : zonePolyPoints) {
            double lat_i = point.getLat();
            double lon_i = point.getLng();
            double lat_j = zonePolyPoints.get(j).getLat();
            double lon_j = zonePolyPoints.get(j).getLng();
            if (((lat_i > lat != (lat_j > lat)) && (lon < (lon_j - lon_i) * (lat - lat_i) / (lat_j - lat_i) + lon_i)))
                c = !c;
            j = i++;
        }
        return c;
    }
}
