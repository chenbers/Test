package com.inthinc.pro.notegen;

import java.util.Date;

import com.inthinc.pro.model.Device;


public interface SendNote {
    public void sendNote(Integer noteType, Date noteTime, byte[] notePackage, Device device) throws Exception;
}
