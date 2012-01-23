package com.inthinc.pro.notegen;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;

public class NoteGenerator {
    
    private static final Logger logger = Logger.getLogger(NoteGenerator.class);
    
    private WSNotePackager wsNotePackager = new WSNotePackager();
    
    private WSNoteSender wsNoteSender;

    public NoteGenerator()
    {
    }
    
    public void genEvent(NoteType noteType, Event event, Device device) {
        
        if (device.getProductVersion() == ProductType.WAYSMART) {
            byte[] notePackage = wsNotePackager.packageNote(event, noteType.getCode());
            wsNoteSender.sendNote(noteType.getCode(), event.getTime(), notePackage, device);
        }
        else if (device.getProductVersion() == ProductType.TIWIPRO_R74) {
            
        }
        else {
            logger.error("genEvent failed for unsupported product type " + device.getProductVersion());
        }
        
    }

    public WSNoteSender getWsNoteSender() {
        return wsNoteSender;
    }

    public void setWsNoteSender(WSNoteSender wsNoteSender) {
        this.wsNoteSender = wsNoteSender;
    }

}
