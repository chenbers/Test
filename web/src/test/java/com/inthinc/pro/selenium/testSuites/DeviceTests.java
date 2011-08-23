package com.inthinc.pro.selenium.testSuites;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.automation.device_emulation.TiwiProDevice;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.Locales;
import com.inthinc.pro.automation.utils.CreateHessian;
import com.inthinc.pro.automation.utils.DownloadFile;
import com.inthinc.pro.automation.utils.MD5Checksum;
import com.inthinc.pro.dao.hessian.proserver.SiloService;


public class DeviceTests extends WebRallyTest {
    
    @Test
    public void forwardCommandLimit(){
        setBuildNumber("");
        String imei = "483548625738283";   
        int vehicleID=5096;
        int deviceID=364;
        Addresses server = Addresses.QA;
        try{
            for (int i=0; i<20; i++) {
                Map<String, Object> fwdMap = new HashMap<String, Object>();
                fwdMap.put("cmd", 2011);
                fwdMap.put("data", 900+i);
                fwdMap.put("status", 1);
                fwdMap.put("cmd", System.currentTimeMillis()/1000);
                SiloService portalProxy = new CreateHessian().getPortalProxy(server);
                portalProxy.queueFwdCmd(deviceID, fwdMap);
            }
            TiwiProDevice tiwi = new TiwiProDevice(imei, server);
            tiwi.nonTripNote(System.currentTimeMillis()/1000, 9, 8, 55.0, 55.0, 5, 5);
            tiwi.add_location();
            tiwi.flushNotes();
        } catch (Exception e){
            
        }
    }
    
    
    @Test
    public void audioFilesFromHessianMatchSVN(){
        StringBuilder destPath = new StringBuilder("target/test/resources/audioFiles/");
        set_test_case("TC5798");
        File svnFolder = new File(destPath.append("svnVersion/").toString());
        svnFolder.mkdir();
        
        File hessianFolder = new File(destPath.append("hessianVersion/").toString());
        hessianFolder.mkdir();
        
        TiwiProDevice tiwi = new TiwiProDevice("javadeviceindavidsaccount", Addresses.QA);
        tiwi.set_WMP(17207);
        
        for (int i=1;i<=33;i++){
            for (Locales locale: EnumSet.allOf(Locales.class)){
                int fileNumber = i;
                
                String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"+locale.getFolder();
                String fileName = String.format("%02d.pcm", fileNumber);
                File dest = new File(destPath.append("svnVersion/").append(fileName).toString());
                
                if (!DownloadFile.downloadSvnDirectory(url, fileName, dest)){
                    addError("SVN File not found", ErrorLevel.FATAL_ERROR);
                }
                tiwi.getAudioFile(destPath.append("hessianVersion/").append(fileName).toString(),fileNumber, locale);
        
                String svn = MD5Checksum.getMD5Checksum(destPath.append("svnVersion").append(fileName).toString());
                String hessian = MD5Checksum.getMD5Checksum(destPath.append("hessianVersion").append(fileName).toString());
                validateEquals(svn, hessian);
                
            }
        }
    }

}
