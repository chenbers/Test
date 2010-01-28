package com.inthinc.pro.backing;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.model.aggregation.Score;

public class TeamBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    private List<Score> driverStatistics = Collections.emptyList();

    public List<Score> getDriverStatistics() {
        return driverStatistics;
    }

    public void setDriverStatistics(List<Score> driverStatistics) {
        this.driverStatistics = driverStatistics;
    }
    
    
}
