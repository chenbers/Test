package com.inthinc.pro.notegen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.model.Device;

public class TiwiProNoteSender implements SendNote {

    
    private MCMSimulator mcmSimulator;

    @Override
    public void sendNote(Integer noteType, Date noteTime, byte[] notePackage, Device device) throws Exception {
        List<byte[]> noteList = new ArrayList<byte[]>();
        noteList.add(notePackage);
        int retryCnt = 0;
        for (; retryCnt < 10; retryCnt++) {
            try {
                mcmSimulator.note(device.getImei(), noteList);
                break;
            }
            catch (HessianException e) {
                System.out.println("Exception inserting notes: " + e.getErrorCode() + " retrying...");
            }
            catch (Throwable t) {
                System.out.println("Exception inserting notes: " + t.getMessage() + " retrying...");
            }
        }
        if (retryCnt == 10)
            throw new Exception("Error inserting notes even after 10 retry attempts.");

    }
    
    public MCMSimulator getMcmSimulator() {
        return mcmSimulator;
    }

    public void setMcmSimulator(MCMSimulator mcmSimulator) {
        this.mcmSimulator = mcmSimulator;
    }

}
