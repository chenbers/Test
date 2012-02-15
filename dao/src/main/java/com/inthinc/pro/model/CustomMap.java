package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomMap {
    
    private Integer customMapID;
    private Integer acctID;
    private String url;
    private String name;
    private Integer minZoom;
    private Integer maxZoom;
    private Double opacity;
    private Boolean pngFormat;
    private GoogleMapType bottomLayer;
    
    public Integer getCustomMapID() {
        return customMapID;
    }
    public void setCustomMapID(Integer customMapID) {
        this.customMapID = customMapID;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getMinZoom() {
        return minZoom;
    }
    public void setMinZoom(Integer minZoom) {
        this.minZoom = minZoom;
    }
    public Integer getMaxZoom() {
        return maxZoom;
    }
    public void setMaxZoom(Integer maxZoom) {
        this.maxZoom = maxZoom;
    }
    public Double getOpacity() {
        return opacity;
    }
    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }
    public Boolean getPngFormat() {
        return pngFormat;
    }
    public void setPngFormat(Boolean pngFormat) {
        this.pngFormat = pngFormat;
    }
    public GoogleMapType getBottomLayer() {
        return bottomLayer;
    }
    public void setBottomLayer(GoogleMapType bottomLayer) {
        this.bottomLayer = bottomLayer;
    }

    public Integer getAcctID() {
        return acctID;
    }
    public void setAcctID(Integer acctID) {
        this.acctID = acctID;
    }


    @Override
    public String toString() {
        
    return "acctID: " + acctID +
            "customMapID: " + customMapID +
            " url: " +  url +
            " name: " +  name +
            " minResolution: " +  minZoom +
            " maxResolution: " + maxZoom +
            " opacity: " +  opacity +
            " pngFormat: " +  pngFormat +
            " bottomLayer: " +  bottomLayer;
    }
}
