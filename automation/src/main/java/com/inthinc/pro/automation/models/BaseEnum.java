package com.inthinc.pro.automation.models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface BaseEnum extends Serializable
{
    public Integer getCode();
}
