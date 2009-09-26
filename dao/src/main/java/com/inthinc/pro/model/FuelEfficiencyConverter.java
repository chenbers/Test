package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface FuelEfficiencyConverter {
        
    public  Double convert(Number mpg);
  
 }
