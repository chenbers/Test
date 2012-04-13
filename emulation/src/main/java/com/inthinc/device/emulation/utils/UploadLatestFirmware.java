package com.inthinc.device.emulation.utils;

import java.util.EnumSet;
import java.util.Set;

import android.util.Log;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;

public class UploadLatestFirmware {

    private final static Set<ProductType> tiwiProSet = EnumSet.of(ProductType.TIWIPRO_R74, ProductType.TIWIPRO_R747);
    private final static Set<Addresses> serverSet = EnumSet.of(Addresses.TEEN_QA, Addresses.QA, Addresses.DEV);
    private final static String svnBaseURl = "https://svn.iwiglobal.com/iwi/release/tiwi/pro/wmp";

    public void uploadFirmware(){
        AutomationFileHandler afh = new AutomationFileHandler();
        for (ProductType type : tiwiProSet){
            String svnUrl = svnBaseURl + type.getTiwiVersion() + "/";

            String fileName = afh.getLatestFileFromSVN(svnUrl);
            String[] split = fileName.split("[.]");
            int uploadFW = ((Double)Double.parseDouble(split[2])).intValue();
            for (Addresses server : serverSet){
                try {
                    TiwiProDevice tiwi = new TiwiProDevice(type, server);
                    tiwi.uploadFirmware(uploadFW);
                } catch (HessianException e ){
                    if (e.getErrorCode()==322){
                        Log.i("Already uploaded " + fileName);
                        continue;
                    }
                    Log.wtf("%s", e);
                }
            }
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        UploadLatestFirmware ulf = new UploadLatestFirmware();
        ulf.uploadFirmware();

    }

}
