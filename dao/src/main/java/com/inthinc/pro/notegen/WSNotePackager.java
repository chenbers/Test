package com.inthinc.pro.notegen;

import java.io.ByteArrayOutputStream;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;

public class WSNotePackager extends PackageNote{
    public final static int nVersion = 2;
    public final static int duration = 0;

    @Override
    public byte[] packageNote(Event event, Integer noteTypeID) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0);
        longToByte(baos, noteTypeID, 1);
        longToByte(baos, nVersion, 1);
        longToByte(baos, event.getTime().getTime()/1000l, 4);
        longToByte(baos, concatenateTwoInts(event.getHeading(), event.getSats()), 1);
        longToByte(baos, encodeLat(event.getLatitude()), 3);
        longToByte(baos, encodeLng(event.getLongitude()), 3);
        longToByte(baos, event.getSpeed(), 1);
        longToByte(baos, event.getOdometer(), 3);
        longToByte(baos, duration, 2);
        
        // TODO:
        encodeAttributes(baos, event);

        byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        return temp;
        
    }
}
