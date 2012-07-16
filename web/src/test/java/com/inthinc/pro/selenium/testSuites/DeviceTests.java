package com.inthinc.pro.selenium.testSuites;

import java.io.File;
import java.util.EnumSet;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.AutomationFileHandler;

public class DeviceTests {
    

//    @Test
//    public void forwardCommandLimit() {
//        set_test_case("TC875");
//        setBuildNumber(AutomationCalendar.now(WebDateFormat.NOTE_DATE_TIME).toString());
//        if (runToday()){
//            String imei = "483548625738283";
//            // int vehicleID=5096;
//            int deviceID = 364;
//            Addresses server = Addresses.QA;
//            try {
//                for (int i = 0; i < 20; i++) {
//                    Map<String, Object> fwdMap = new HashMap<String, Object>();
//                    fwdMap.put("cmd", 2011);
//                    fwdMap.put("data", 900 + i);
//                    fwdMap.put("status", 1);
//                    fwdMap.put("modified", System.currentTimeMillis() / 1000);
//                    SiloService portalProxy = new AutomationHessianFactory()
//                            .getPortalProxy(server);
//                    portalProxy.queueFwdCmd(deviceID, fwdMap);
//                }
//                TiwiProDevice tiwi = new TiwiProDevice(imei, server);
//                tiwi.nonTripNote(new AutomationCalendar(), 9, Heading.NORTH_EAST, new GeoPoint(55.0,
//                        55.0), 5, 5);
//                tiwi.addLocation();
//                tiwi.flushNotes();
//            } catch (Exception e) {
//                logger.info(StackToString.toString(e));
//            }    
//        }
//    }

    @Test
    @Ignore
    public void audioFilesFromHessianMatchSVN() {
//        set_test_case("TC5798");
//        setBuildNumber(AutomationCalendar.now(WebDateFormat.NOTE_DATE_TIME).toString());
//        if (runToday()){
            StringBuilder destPath = new StringBuilder(
                    "target/test/resources/audioFiles/");
            String svnPath = destPath.append("svnVersion/").toString();
            String hessianPath = destPath.append("hessianVersion/").toString();
            String fileName="", svnFile="", hessianFile="";
    
            TiwiProDevice tiwi;
//            for (Addresses silo : EnumSet.allOf(Addresses.class)) {
            AutoSilos silo = AutoSilos.QA;
                try{
                    tiwi = new TiwiProDevice("FAKEIMEIDEVICE");
                    tiwi.set_server(silo);
                    tiwi.getState().setWMP(17207);
                    for (int i = 1; i <= 33; i++) {
        
                        fileName = String.format("%02d.pcm", i);
                        svnFile = svnPath + "/" + fileName;
                        hessianFile = hessianPath + "/" + fileName;
                        Log.info(fileName);
        
                        for (Locales locale : EnumSet.allOf(Locales.class)) {
                            try {
                                Log.info(locale);
                                String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"
                                        + locale.getFolder();
                                File dest = new File(svnFile);
            
                                if (!AutomationFileHandler.downloadSvnDirectory(url, fileName, dest)) {
                                    Log.info("SVN File not found");
                                }
                                tiwi.getAudioFile(hessianFile, i, locale);
                                Log.info(AutomationFileHandler.filesEqual(svnFile, hessianFile));
                            } catch (HessianException e){
                                Log.info("Failed for " + fileName + "." + locale);
                                continue;
                            }
                                        
                        }
                    }
                } catch(Exception e){
                    Log.info(e);
                    
//                    continue;
                }
//            }
//        }
    }
}
