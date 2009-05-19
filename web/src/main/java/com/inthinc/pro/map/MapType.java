package com.inthinc.pro.map;

public enum MapType
{
    GOOGLE("google",""),
    YAHOO("yahoo",""),
    MICROSOFT("microsoft","");

    private String mapId;
    private String scriptSrc;
    
    private MapType(String mapId,String scriptSrc){
        this.mapId = mapId;
        this.scriptSrc = scriptSrc;
    }
    
    public String getMapId()
    {
        return mapId;
    }
    
    public String getScriptSrc()
    {
        return scriptSrc;
    }
}
