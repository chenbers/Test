package com.inthinc.pro.notegen;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;

public class NoteGenerator {
    
    private static final Logger logger = Logger.getLogger(NoteGenerator.class);
    
    private WSNotePackager wsNotePackager = new WSNotePackager();
    
    private WSNoteSender wsNoteSender;

    private TiwiProNotePackager tiwiProNotePackager = new TiwiProNotePackager();
    
    private TiwiProNoteSender tiwiProNoteSender;


    public NoteGenerator()
    {
    }
    
    public void genEvent(Event event, Device device) throws Exception {
        
        NoteType noteType = event.getType();
        if (device.getProductVersion() == ProductType.WAYSMART) {
            byte[] notePackage = wsNotePackager.packageNote(event, noteType.getCode());
            wsNoteSender.sendNote(noteType.getCode(), event.getTime(), notePackage, device);
        }
        else if (device.getProductVersion() == ProductType.TIWIPRO_R74) {
            byte[] notePackage = tiwiProNotePackager.packageNote(event, noteType.getCode());
            tiwiProNoteSender.sendNote(noteType.getCode(), event.getTime(), notePackage, device);
            
        }
        else {
            logger.error("genEvent failed for unsupported product type " + device.getProductVersion());
        }
        
    }
    public void genTrip(List<Event> eventList, Device device) throws Exception {
        
        PackageNote notePackager = null;
        SendNote noteSender = null;
        if (device.getProductVersion() == ProductType.WAYSMART) {
            notePackager = wsNotePackager;
            noteSender = wsNoteSender;
        }
        else if (device.getProductVersion() == ProductType.TIWIPRO_R74) {
            notePackager = tiwiProNotePackager;
            noteSender = tiwiProNoteSender;
        }
        else {
            logger.error("genTrip failed for unsupported product type " + device.getProductVersion());
            return;
        }
        
        
        // TODO: fix sender to send multiples
        for (Event event : eventList) {
            NoteType noteType = event.getType();
            byte[] notePackage = notePackager.packageNote(event, noteType.getCode());
            noteSender.sendNote(noteType.getCode(), event.getTime(), notePackage, device);
        }
        
    }
    
    
    public WSNoteSender getWsNoteSender() {
        return wsNoteSender;
    }

    public void setWsNoteSender(WSNoteSender wsNoteSender) {
        this.wsNoteSender = wsNoteSender;
    }

    public TiwiProNoteSender getTiwiProNoteSender() {
        return tiwiProNoteSender;
    }

    public void setTiwiProNoteSender(TiwiProNoteSender tiwiProNoteSender) {
        this.tiwiProNoteSender = tiwiProNoteSender;
    }
}
