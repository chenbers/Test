package com.inthinc.parser.attrib;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import com.inthinc.parser.util.NotebcUtil;
import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.note.NoteType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AttribParserTests {
    private static Logger logger = LoggerFactory.getLogger(AttribParserTests.class);
    
     @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parseStringPrefacedLengthTest() throws Exception {
        String textMessage = "This is a test message";
        
        byte[] note = NotebcUtil.createHeader(NoteType.TEXT_MSG.getCode());
        note = NotebcUtil.addAttrib(note, Attrib.TEXTLENGTH, textMessage.length());
        note = NotebcUtil.addAttrib(note, Attrib.TEXTMESSAGE, textMessage);
        
        Map<String, Object> attribMap = NotebcUtil.parseNote(note);
        
        assertEquals((String)attribMap.get(Attrib.TEXTMESSAGE.getFieldName()), textMessage);
    }


    @Test
    public void parseByteArray() throws Exception {
        String textMessage = "This is a test of a byte array";
        byte[] byteMessage = textMessage.getBytes();
                
        byte[] note = NotebcUtil.createHeader(NoteType.CRASH_DATA_HI_RES.getCode());
        note = NotebcUtil.addAttrib(note, Attrib.SPEEDDATAHIRES, byteMessage);
        
        Map<String, Object> attribMap = NotebcUtil.parseNote(note);
        
        String fetchedTextMessage = new String((byte[]) attribMap.get(Attrib.SPEEDDATAHIRES.getFieldName()));
        
        assertEquals(fetchedTextMessage, textMessage);
    }
    
    @Test
    public void parseVarLengthString() throws Exception {
        
        String comments = "This a test comment. Less than 60";
        String mechanicID = "Length  10";
        String inspectID = "Length   11";
        String signOff = "Length 11";
               
        byte[] note = NotebcUtil.createHeader(NoteType.DVIR_REPAIR.getCode());
        note = NotebcUtil.addAttrib(note, Attrib.DVIR_COMMENTS, comments);
        note = NotebcUtil.addAttrib(note, Attrib.DVIR_INSPECTOR_ID, inspectID);
        note = NotebcUtil.addAttrib(note, Attrib.DVIR_SIGNOFF_ID, signOff);
        note = NotebcUtil.addAttrib(note, Attrib.DVIR_MECHANIC_ID, mechanicID);
        
        Map<String, Object> attribMap = NotebcUtil.parseNote(note);
        
        
        assertEquals((String)attribMap.get(Attrib.DVIR_COMMENTS.getFieldName()), comments);
        assertEquals((String)attribMap.get(Attrib.DVIR_MECHANIC_ID.getFieldName()), mechanicID);
        assertEquals((String)attribMap.get(Attrib.DVIR_INSPECTOR_ID.getFieldName()), inspectID);
        assertEquals((String)attribMap.get(Attrib.DVIR_SIGNOFF_ID.getFieldName()), signOff);
    }

    @Test
    public void parseLocation() throws Exception {
        
        String location = "Location";
        String comments = "This a test comment. Less than 60";
        Integer lowIdle = 7;
        
        //var length test
        byte[] note = NotebcUtil.createHeader(NoteType.HOS_CHANGE_STATE_NO_GPS_LOCK.getCode());
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        dataStream.write(note);
        dataStream.write(NotebcUtil.convertShortToBytes((short)Attrib.LOCATION.getCode()));
        dataStream.write(((String) location).getBytes());
        dataStream.write((byte)0);
        note = dataStream.toByteArray();
        note = NotebcUtil.addAttrib(note, Attrib.DVIR_COMMENTS, comments);
        Map<String, Object> attribMap = NotebcUtil.parseNote(note);
        assertEquals((String)attribMap.get(Attrib.DVIR_COMMENTS.getFieldName()), comments);
        assertEquals((String)attribMap.get(Attrib.LOCATION.getFieldName()), location);
        
        //Fixed length test
        note = NotebcUtil.createHeader(NoteType.HOS_CHANGE_STATE_NO_GPS_LOCK.getCode());
        dataStream = new ByteArrayOutputStream();
        dataStream.write(note);
        dataStream.write(NotebcUtil.convertShortToBytes((short)Attrib.LOCATION.getCode()));
        dataStream.write(((String) location).getBytes());
        for (int i=0; i < (30-location.length()); i++)
        	dataStream.write((byte)0);
        
        note = dataStream.toByteArray();
        note = NotebcUtil.addAttrib(note, Attrib.DVIR_COMMENTS, comments);
        note = NotebcUtil.addAttrib(note, Attrib.LOWIDLE, lowIdle);
        attribMap = NotebcUtil.parseNote(note);
        assertEquals((String)attribMap.get(Attrib.DVIR_COMMENTS.getFieldName()), comments);
        assertEquals((Integer)attribMap.get(Attrib.LOWIDLE.getFieldName()), lowIdle);
        assertEquals((String)attribMap.get(Attrib.LOCATION.getFieldName()), location);

        //Fixed length test 2
        note = NotebcUtil.createHeader(NoteType.HOS_CHANGE_STATE_NO_GPS_LOCK.getCode());
        dataStream = new ByteArrayOutputStream();
        dataStream.write(note);
        dataStream.write(NotebcUtil.convertShortToBytes((short)Attrib.LOCATION.getCode()));
        dataStream.write(((String) location).getBytes());
        for (int i=0; i < (30-location.length()); i++)
        	dataStream.write((byte)0);
        
        note = dataStream.toByteArray();
        note = NotebcUtil.addAttrib(note, Attrib.LOWIDLE, lowIdle);
        note = NotebcUtil.addAttrib(note, Attrib.DVIR_COMMENTS, comments);
        attribMap = NotebcUtil.parseNote(note);
        assertEquals((String)attribMap.get(Attrib.DVIR_COMMENTS.getFieldName()), comments);
        assertEquals((Integer)attribMap.get(Attrib.LOWIDLE.getFieldName()), lowIdle);
        assertEquals((String)attribMap.get(Attrib.LOCATION.getFieldName()), location);
    }
}
