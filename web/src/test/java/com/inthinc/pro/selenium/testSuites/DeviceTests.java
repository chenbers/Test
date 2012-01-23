package com.inthinc.pro.selenium.testSuites;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.enums.Locales;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.emulation.hessian.tcp.AutomationHessianFactory;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.ErrorLevel;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.objects.AutomationCalendar.WebDateFormat;
import com.inthinc.pro.automation.utils.AutomationFileHandler;
import com.inthinc.pro.automation.utils.StackToString;

public class DeviceTests extends WebDeviceOnlyRallyTest {
    

    @Test
    public void forwardCommandLimit() {
        set_test_case("TC875");
        setBuildNumber(AutomationCalendar.now(WebDateFormat.NOTE_DATE_TIME).toString());
        if (runToday()){
            String imei = "483548625738283";
            // int vehicleID=5096;
            int deviceID = 364;
            Addresses server = Addresses.QA;
            try {
                for (int i = 0; i < 20; i++) {
                    Map<String, Object> fwdMap = new HashMap<String, Object>();
                    fwdMap.put("cmd", 2011);
                    fwdMap.put("data", 900 + i);
                    fwdMap.put("status", 1);
                    fwdMap.put("modified", System.currentTimeMillis() / 1000);
                    SiloService portalProxy = new AutomationHessianFactory()
                            .getPortalProxy(server);
                    portalProxy.queueFwdCmd(deviceID, fwdMap);
                }
                TiwiProDevice tiwi = new TiwiProDevice(imei, server);
                tiwi.nonTripNote(new AutomationCalendar(), 9, Heading.NORTH_EAST, new GeoPoint(55.0,
                        55.0), 5, 5);
                tiwi.addLocation();
                tiwi.flushNotes();
            } catch (Exception e) {
                logger.info(StackToString.toString(e));
            }    
        }
    }

    @Test
    public void audioFilesFromHessianMatchSVN() {
        set_test_case("TC5798");
        setBuildNumber(AutomationCalendar.now(WebDateFormat.NOTE_DATE_TIME).toString());
        if (runToday()){
            StringBuilder destPath = new StringBuilder(
                    "target/test/resources/audioFiles/");
            String svnPath = destPath.append("svnVersion/").toString();
            String hessianPath = destPath.append("hessianVersion/").toString();
    
            TiwiProDevice tiwi;
//            for (Addresses silo : EnumSet.allOf(Addresses.class)) {
            Addresses silo = Addresses.QA;
                try{
                    tiwi = new TiwiProDevice("FAKEIMEIDEVICE", silo);
                    tiwi.getState().setWMP(17207);
                    for (int i = 1; i <= 33; i++) {
        
                        String fileName = String.format("%02d.pcm", i);
                        String svnFile = svnPath + "/" + fileName;
                        String hessianFile = hessianPath + "/" + fileName;
        
                        for (Locales locale : EnumSet.allOf(Locales.class)) {
                            String url = "https://svn.iwiglobal.com/iwi/map_image/trunk/audio/"
                                    + locale.getFolder();
                            File dest = new File(svnFile);
        
                            if (!AutomationFileHandler.downloadSvnDirectory(url, fileName, dest)) {
                                addError("SVN File not found", ErrorLevel.FATAL_ERROR);
                            }
                            tiwi.getAudioFile(hessianFile, i, locale);
                            validateTrue(AutomationFileHandler.filesEqual(svnFile, hessianFile),
                                    "The Downloaded files didn't match:\n" + fileName);
                        }
                    }
                } catch(Exception e){
//                    continue;
                }
//            }
        }
    }
}
