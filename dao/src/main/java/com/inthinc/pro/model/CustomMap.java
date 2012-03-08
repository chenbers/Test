package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;

@XmlRootElement
public class CustomMap {
    
    private Integer customMapID;
    private Integer acctID;
    private String url;
    private String name;
    private String layer;
    private Integer minZoom;
    private Integer maxZoom;
    private Double opacity;
    private Boolean pngFormat;
    
    @Column(updateable = false)
    private Boolean selected;
    
    public Boolean getSelected() {
        return selected;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
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
    public Integer getAcctID() {
        return acctID;
    }
    public void setAcctID(Integer acctID) {
        this.acctID = acctID;
    }
    public String getLayer() {
        return layer;
    }
    public void setLayer(String layer) {
        this.layer = layer;
    }

    @Override
    public String toString() {
        
    return "acctID: " + acctID +
            " customMapID: " + customMapID +
            " url: " +  url +
            " layer: " +  layer +
            " name: " +  name +
            " minResolution: " +  minZoom +
            " maxResolution: " + maxZoom +
            " opacity: " +  opacity +
            " pngFormat: " +  pngFormat +
            " selected: " +  selected;
    }
}
