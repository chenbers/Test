package com.inthinc.pro.dao;

import com.inthinc.pro.model.LatLng;

public interface FipsDAO {

    String getClosestTown(LatLng latLng);
}
