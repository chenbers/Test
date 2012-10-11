package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssetReportItem extends BaseEntity
{
    //Minimalist class from another.  Done this way to 
    //  accommodate additions of other items not related to an Asset.
    private Asset asset;

    public Asset getAsset()
    {
        return asset;
    }

    public void setAsset(Asset asset)
    {
        this.asset = asset;
    }
}
