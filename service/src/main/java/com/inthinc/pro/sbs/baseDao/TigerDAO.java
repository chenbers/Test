package com.inthinc.pro.sbs.baseDao;

import javax.xml.parsers.ParserConfigurationException;

import com.inthinc.pro.sbs.SBSChangeRequest;


public interface TigerDAO {

    public  SBSChangeRequest getCompleteChains(double lat, double lng, int address) throws ParserConfigurationException;
}
