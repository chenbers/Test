package com.inthinc.pro.sbs.baseDao;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.inthinc.pro.backing.SBSChangeRequest;

public interface TigerDAO {

    public  List<SBSChangeRequest> getCompleteChains(double lat, double lng, int address, int limit) throws ParserConfigurationException;
}
