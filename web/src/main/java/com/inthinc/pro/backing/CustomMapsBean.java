package com.inthinc.pro.backing;

import java.util.List;

import com.inthinc.pro.dao.CustomMapDAO;
import com.inthinc.pro.model.CustomMap;

public class CustomMapsBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;
    
    private CustomMapDAO customMapDAO;
    private List<CustomMap> customMaps;

    public void init() {
        if (customMaps == null) {
            customMaps = customMapDAO.getCustomMapsByAcctID(getAccountID());
        }
    }
    
    public CustomMapDAO getCustomMapDAO() {
        return customMapDAO;
    }

    public void setCustomMapDAO(CustomMapDAO customMapDAO) {
        this.customMapDAO = customMapDAO;
    }
    
    public List<CustomMap> getCustomMaps() {
        return customMaps;
    }

    public void setCustomMaps(List<CustomMap> customMaps) {
        this.customMaps = customMaps;
    }

}
