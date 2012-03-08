package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.CustomMapDAO;
import com.inthinc.pro.model.CustomMap;
import com.inthinc.pro.model.GoogleMapType;
import com.inthinc.pro.model.User;


public class CustomMapsBean extends BaseBean {
    
    private static final long serialVersionUID = 1L;
    
    private CustomMapDAO customMapDAO;
    private List<CustomMap> customMaps;
    private GoogleMapType googleMapType;
    private String googleMapTypeStr;

    private Integer googleMapLayerID;
    private Boolean googleMapLayerSelected;


    private static final Map<String, GoogleMapType>  googleMapTypes;
    static
    {
        googleMapTypes = new HashMap<String, GoogleMapType>();
        for (GoogleMapType mapType : GoogleMapType.values()) {
            googleMapTypes.put(mapType.name(), mapType);
        }
    }
    
    public void init() {
        if (customMaps == null) {
            customMaps = customMapDAO.getCustomMapsByAcctID(getAccountID());
            for (CustomMap customMap : customMaps)
                    customMap.setSelected(false);
            
            initUserPreferences(getUser());
        }
    }
    
    public void initUserPreferences(User user) {
        googleMapType = user.getMapType();
        if (googleMapType == null || googleMapType == GoogleMapType.NONE)
            googleMapType = GoogleMapType.G_NORMAL_MAP;
        
        List<Integer> selectedMapLayerIDs = user.getSelectedMapLayerIDs();
        if (selectedMapLayerIDs != null)
            for (Integer id : selectedMapLayerIDs) {
                for (CustomMap customMap : customMaps)
                    if (customMap.getCustomMapID().equals(id)) {
                        customMap.setSelected(true);
                        break;
                    }
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

    public GoogleMapType getGoogleMapType() {
        return googleMapType;
    }

    public void setGoogleMapType(GoogleMapType googleMapType) {
        this.googleMapType = googleMapType;
    }

    public String getGoogleMapTypeStr() {
        return googleMapTypeStr;
    }

    public void setGoogleMapTypeStr(String googleMapTypeStr) {
        this.googleMapTypeStr = googleMapTypeStr;
        setGoogleMapType(googleMapTypes.get(googleMapTypeStr));
    }
    public Integer getGoogleMapLayerID() {
        return googleMapLayerID;
    }

    public void setGoogleMapLayerID(Integer googleMapLayerID) {
        this.googleMapLayerID = googleMapLayerID;
    }

    public Boolean getGoogleMapLayerSelected() {
        return googleMapLayerSelected;
    }

    public void setGoogleMapLayerSelected(Boolean googleMapLayerSelected) {
        this.googleMapLayerSelected = googleMapLayerSelected;
    }

    public void saveLayerState() {
        if (googleMapLayerID == null || googleMapLayerSelected == null)
            return;
        
        for (CustomMap customMap : customMaps)
            if (customMap.getCustomMapID().equals(googleMapLayerID)) {
                customMap.setSelected(googleMapLayerSelected);
                break;
            }
    }
    

}
