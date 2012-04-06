package com.inthinc.device.emulation;

import java.io.File;
import java.util.EnumSet;

import org.apache.log4j.spi.ErrorCode;
import org.junit.Ignore;
import org.junit.Test;

import android.util.Log;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.utils.AutomationFileHandler;
import com.inthinc.pro.automation.utils.MasterTest;

public class DownloadTesting {
    
    @Test
    @Ignore
    public void audioFilesFromHessianMatchSVN() {
        StringBuilder destPath = new StringBuilder(
                "target/test/resources/audioFiles/");
        String svnPath = destPath.append("svnVersion/").toString();
        String hessianPath = destPath.append("hessianVersion/").toString();
        String fileName="", svnFile="", hessianFile="";

        TiwiProDevice tiwi;
        Addresses silo = Addresses.QA;
        for (ProductType type : EnumSet.of(ProductType.TIWIPRO_R71, ProductType.TIWIPRO_R74, ProductType.TIWIPRO_R747)){
            try{
                tiwi = new TiwiProDevice("FAKEIMEIDEVICE", type, silo);
                tiwi.getState().setWMP(17207);
                for (int i = 1; i <= 33; i++) {
    
                    fileName = String.format("%02d.pcm", i);
                    svnFile = svnPath + "/" + fileName;
                    hessianFile = hessianPath + "/" + fileName;
                    MasterTest.print(fileName);
    
                    for (Locales locale : EnumSet.allOf(Locales.class)) {
                        try {
                            MasterTest.print(locale);
                            String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"
                                    + locale.getFolder();
                            File dest = new File(svnFile);
        
                            if (!AutomationFileHandler.downloadSvnDirectory(url, fileName, dest)) {
                                MasterTest.print("SVN File not found");
                            }
                            tiwi.getAudioFile(hessianFile, i, locale);
                            MasterTest.print(AutomationFileHandler.filesEqual(svnFile, hessianFile));
                        } catch (HessianException e){
                            MasterTest.print("Failed for " + fileName + "." + locale);
                            continue;
                        }
                                    
                    }
                }
            } catch(Exception e){
                MasterTest.print(e);
            }
        }
    }
    
    @Test
    @Ignore
    public void firmwareDownloadTest(){
        boolean results = false;
        for (ProductType type : EnumSet.of(ProductType.TIWIPRO_R71, ProductType.TIWIPRO_R74, ProductType.TIWIPRO_R747)){
            TiwiProDevice tiwi = new TiwiProDevice("500000000000000", type, Addresses.QA);
            
                for (Integer version : new int[]{17210,17302,17303,17304,17305,17401,17404}){
                    try {
                        results &= tiwi.firmwareCompare(version);
                    } catch (HessianException e){
                        if (e.getErrorCode() == 304){
                            
                        }
                        Log.i("%s", e);
                        continue;
                    }
                    assert(results);
                }
            
        }
    }

}
