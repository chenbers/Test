package com.inthinc.pro.comm.parser.note;

import static org.junit.Assert.assertEquals;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.parser.util.NotebcUtil;
import com.inthinc.pro.comm.parser.attrib.Attrib;

public class NoteVersionParseTest {

    @Test
    public void parseVersion3() throws Exception {
        String textMessage = "This is a test of a byte array";
        byte[] byteMessage = textMessage.getBytes();
                
        byte[] note = NotebcUtil.createHeader(NoteType.CRASH_DATA_HI_RES.getCode());
        note = NotebcUtil.addAttrib(note, Attrib.SPEEDDATAHIRES, byteMessage);
        
        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC_METHOD);
        Map<String, Object> attribMap = parser.parseNote(note);
        
        
        assertEquals(NoteType.CRASH_DATA_HI_RES.getCode(), attribMap.get(Attrib.NOTETYPE.getFieldName()));
        assertEquals(new Double(-112.00584), (Double)attribMap.get("longitude"));
        assertEquals(new Double(40.70974), (Double)attribMap.get("latitude"));
        assertEquals(new Integer(111100), (Integer)attribMap.get("odometer"));

        String fetchedTextMessage = new String((byte[]) attribMap.get(Attrib.SPEEDDATAHIRES.getFieldName()));
        assertEquals(fetchedTextMessage, textMessage);
    }


    @Test
    public void parseVersion4() throws Exception {
        String textMessage = "This is a test of a byte array";
        byte[] byteMessage = textMessage.getBytes();
                
        byte[] note = NotebcUtil.createHeaderV4(NoteType.CRASH_DATA_HI_RES.getCode());
        note = NotebcUtil.addAttrib(note, Attrib.SPEEDDATAHIRES, byteMessage);
        
        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC4_METHOD);
        Map<String, Object> attribMap = parser.parseNote(note);
        
        assertEquals(NoteType.CRASH_DATA_HI_RES.getCode(), attribMap.get(Attrib.NOTETYPE.getFieldName()));
        DecimalFormat df = new DecimalFormat("###.###");
        df.setRoundingMode(RoundingMode.DOWN);
        assertEquals(df.format(40.777d), df.format((Double)attribMap.get("latitude")));
        assertEquals(df.format(-112.333d), df.format((Double)attribMap.get("longitude")));
        assertEquals(new Integer(5), (Integer)attribMap.get(Attrib.NOTEFLAGS.getFieldName()));
        assertEquals(new Integer(111100), (Integer)attribMap.get("odometer"));
        assertEquals(new Integer(1001), (Integer)attribMap.get(Attrib.NOTIFICATION_ENUM.getFieldName()));

        String fetchedTextMessage = new String((byte[]) attribMap.get(Attrib.SPEEDDATAHIRES.getFieldName()));
        assertEquals(fetchedTextMessage, textMessage);
    }

    @Test
    public void parseVersion5() throws Exception {
        String textMessage = "This is a test of a byte array";
        byte[] byteMessage = textMessage.getBytes();
                
        byte[] note = NotebcUtil.createHeaderV5(NoteType.CRASH_DATA_HI_RES.getCode());
        note = NotebcUtil.addAttrib(note, Attrib.SPEEDDATAHIRES, byteMessage);
        
        NoteParser parser = NoteParserFactory.getParserForMethod(NoteParserFactory.NOTEBC5_METHOD);
        Map<String, Object> attribMap = parser.parseNote(note);
        
        assertEquals(NoteType.CRASH_DATA_HI_RES.getCode(), attribMap.get(Attrib.NOTETYPE.getFieldName()));
        assertEquals(new Integer(5), (Integer)attribMap.get(Attrib.NOTEFLAGS.getFieldName()));
        assertEquals(new Integer(111100), (Integer)attribMap.get("odometer"));
        assertEquals(new Integer(1001), (Integer)attribMap.get(Attrib.NOTIFICATION_ENUM.getFieldName()));
        
        String fetchedTextMessage = new String((byte[]) attribMap.get(Attrib.SPEEDDATAHIRES.getFieldName()));
        assertEquals(fetchedTextMessage, textMessage);
    }

}
