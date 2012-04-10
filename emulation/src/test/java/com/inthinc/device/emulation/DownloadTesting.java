package com.inthinc.device.emulation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import android.util.Log;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;

public class DownloadTesting {
    
    private final static Set<ProductType> tiwiSet = EnumSet.of(ProductType.TIWIPRO_R71, ProductType.TIWIPRO_R74, ProductType.TIWIPRO_R747);
    private final static Set<ProductType> tiwiProSet = EnumSet.of(ProductType.TIWIPRO_R74, ProductType.TIWIPRO_R747);
    private final static Set<Addresses> serverSet = EnumSet.of(Addresses.TEEN_QA, Addresses.QA, Addresses.DEV);
    private final static String tiwiImei = "500000000000000";
    
    private final static int[] fw = {17210, 17302, 17303, 
                                     17304, 17305, 17401, 
                                     17404, 17603, 17604};
    
    
    private final static int uploadFW = 17604;
    
    @Test
    public void audioFilesFromHessianMatchSVN() {

        TiwiProDevice tiwi;
        Addresses silo = Addresses.QA;
        List<String> errors = new ArrayList<String>();

        boolean result = true;
        for (ProductType type : tiwiSet){
            tiwi = new TiwiProDevice(tiwiImei, type, silo);
            tiwi.getState().setWMP(17207);
            for (int i = 1; i <= 33; i++) {
                for (Locales locale : EnumSet.allOf(Locales.class)) {
                    boolean thisOne = tiwi.compareAudio(i, locale); 
                    result &= thisOne;
                    if (!thisOne){
                        errors.add(String.format("Compare failed for %02d.pcm, locale: %s, ProductType: %s", i, locale, type));
                    }
                }
            }
        }
        assertTrue("Unable to verify all audio versions: " + errors, result);
    }
    
    @Test
    public void firmwareDownloadTest(){
        boolean results = true;
        List<String> errors = new ArrayList<String>();
        for (ProductType type : tiwiSet){
            TiwiProDevice tiwi = new TiwiProDevice(tiwiImei, type, Addresses.QA);
            for (Integer version : fw){
                try {
                    boolean thisOne = tiwi.firmwareCompare(version); 
                    results &= thisOne;
                    if (!thisOne){
                        errors.add(String.format("Version compare failed for: %d, productType = %s", version, type));
                    }
                } catch (HessianException e){
                    if (e.getErrorCode() == 304){
                        Log.i("Version: %d, for product: %s cannot be retrieved via Hessian", version, type);
                    } else {
                        results = false;
                        errors.add(String.format("Got error for version: %d, productType = %s\n%s", version, type, e));
                    }
                    continue;
                }
            }
        }
        assertTrue("Firmware versions didn't all match: " + errors, results);
    }
    
    
    @Test
    @Ignore
    public void uploadFirmware(){
        for (ProductType type : tiwiProSet){
            for (Addresses server : serverSet){
                try {
                    TiwiProDevice tiwi = new TiwiProDevice(type, server);
                    tiwi.uploadFirmware(uploadFW);
                } catch (HessianException e ){
                    Log.wtf("%s", e);
                }
            }
        }
    }

}
