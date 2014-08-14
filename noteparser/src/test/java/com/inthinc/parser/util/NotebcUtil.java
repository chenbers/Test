package com.inthinc.parser.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inthinc.pro.comm.parser.attrib.Attrib;
import com.inthinc.pro.comm.parser.attrib.AttribParser;
import com.inthinc.pro.comm.parser.attrib.AttribParserFactory;
import com.inthinc.pro.comm.parser.attrib.StringVarLengthParser;
import com.inthinc.pro.comm.parser.note.NoteParser;
import com.inthinc.pro.comm.parser.note.NotebcParser;
import com.inthinc.pro.comm.parser.util.LatLngUtil;


public class NotebcUtil {
    private static Logger logger = LoggerFactory.getLogger(NotebcUtil.class.getName());

    
    public static byte[] addAttrib(byte[] data, Attrib attrib, Object value)
    {
        Integer code = attrib.getCode();
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try {
            dataStream.write(data);
            dataStream.write(convertShortToBytes(code.shortValue()));
            if (code <= 127) {
                dataStream.write(convertByteToBytes(((Number) value).byteValue()));
            }
            if (code >= 128 && 191 >= code)
                dataStream.write(convertShortToBytes(((Number) value).shortValue()));
    
            if (code >= 192 && 254 >= code) 
                dataStream.write(convertIntToBytes(((Number) value).intValue()));
            
            if (code >= 8000 && code < 9000)
            {
                dataStream.write(convertByteToBytes(((Number) value).byteValue()));
            }
            
            if (code >= 16000 && code < 17000)
                dataStream.write(convertShortToBytes(((Number) value).shortValue()));
            
            if (code >= 32000 && code < 33000)
                dataStream.write(convertIntToBytes(((Number) value).intValue()));
            
            if (value instanceof String){
                AttribParser parser = AttribParserFactory.getParserForParserType(attrib.getAttribParserType());
                dataStream.write(((String) value).getBytes());
                
                if (parser instanceof StringVarLengthParser)
                    dataStream.write(convertByteToBytes((byte) 0));
            }

            if (value instanceof byte[]){
                dataStream.write((byte[])value);
            }

            dataStream.flush();
        } catch (IOException e)
        {
            logger.error("Error adding Attrib: " + e);
        }
            
//        if (code >= 40960 && code < 40964)
//            ho.writeDouble((Double) value);
        
        return dataStream.toByteArray();
        
    }
    
    public static byte[] createHeader(Integer noteType)
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try {
            dataStream.write(convertByteToBytes(noteType.byteValue()));
            dataStream.write(convertByteToBytes((byte)3)); //version
            Date currentTime = new Date();
            dataStream.write(convertIntToBytes((int)(currentTime.getTime()/1000)));
            short flags = 0;
            dataStream.write(convertShortToBytes(flags));
            double latitude = 40.709736;
            double longitude = -112.005844;
            dataStream.write(LatLngUtil.encodeLat(latitude));
            dataStream.write(LatLngUtil.encodeLng(longitude));
            byte speed = 50;
            dataStream.write(convertByteToBytes(speed));
            byte speedLimit = 60;
            dataStream.write(convertByteToBytes(speedLimit));
            int linkId = 0;
            dataStream.write(convertIntToBytes(linkId)); //LinkID
            int odometer = 111100;
            dataStream.write(convertIntToBytes(odometer));
            short state = 1;
            dataStream.write(convertShortToBytes(state));
            int driverID = 12345;
            dataStream.write(convertIntToBytes(driverID));

            dataStream.flush();
            dataStream.toByteArray();
        } catch (IOException e)
        {
            logger.error("Error creating note header: " + e);
        }
            
        return dataStream.toByteArray();
    }

    public static byte[] createHeaderV4(Integer noteType)
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try {
            byte flags = 5;
            dataStream.write(convertByteToBytes(flags));
            dataStream.write(convertByteToBytes(noteType.byteValue()));
            dataStream.write(convertByteToBytes((byte)4)); //version
            Date currentTime = new Date();
            dataStream.write(convertIntToBytes((int)(currentTime.getTime()/1000)));
            double latitude = 40.7777;
            double longitude = -112.3333;
            dataStream.write(LatLngUtil.encodeLat3(latitude));
            dataStream.write(LatLngUtil.encodeLng3(longitude));
            int odometer = 111100;
            dataStream.write(convertIntToBytes(odometer));
            short noteEnum = 1001;
            dataStream.write(convertShortToBytes(noteEnum));

            dataStream.flush();
            dataStream.toByteArray();
        } catch (IOException e)
        {
            logger.error("Error creating note header: " + e);
        }
            
        return dataStream.toByteArray();
    }

    public static byte[] createHeaderV5(Integer noteType)
    {
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        try {
            byte flags = 5;
            dataStream.write(convertByteToBytes(flags));
            dataStream.write(convertByteToBytes(noteType.byteValue()));
            dataStream.write(convertByteToBytes((byte)4)); //version
            Date currentTime = new Date();
            dataStream.write(convertIntToBytes((int)(currentTime.getTime()/1000)));
            int odometer = 111100;
            dataStream.write(convertIntToBytes(odometer));
            short noteEnum = 1001;
            dataStream.write(convertShortToBytes(noteEnum));

            dataStream.flush();
            dataStream.toByteArray();
        } catch (IOException e)
        {
            logger.error("Error creating note header: " + e);
        }
            
        return dataStream.toByteArray();
    }
    
    public static Map<String, Object> parseNote(byte[] data)
    {
        NoteParser parser = new NotebcParser(); 
        Map<String, Object> attribMap = parser.parseNote(data);
        return attribMap;
    }
    
    public static byte[] convertIntToBytes(int intData)
    {
        byte[] dataBytes = new byte[4];
        dataBytes[3] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[2] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[1] = (byte) (intData & 0x000000FF);
        intData = intData >>> 8;
        dataBytes[0] = (byte) (intData & 0x000000FF);
        return dataBytes;
    }

    public static byte[] convertShortToBytes(short data)
    {
        byte[] dataBytes = new byte[2];
        dataBytes[1] = (byte) (data & 0x000000FF);
        data = (short) (data >>> 8);
        dataBytes[0] = (byte) (data & 0x000000FF);
        return dataBytes;
    }

    public static byte[] convertByteToBytes(byte data)
    {
        byte[] dataBytes = new byte[1];
        dataBytes[0] = (byte) (data);
        return dataBytes;
    }

    private byte[] convertLongToBytes(long data) {
        return new byte[] {
            (byte)((data >> 56) & 0xff),
            (byte)((data >> 48) & 0xff),
            (byte)((data >> 40) & 0xff),
            (byte)((data >> 32) & 0xff),
            (byte)((data >> 24) & 0xff),
            (byte)((data >> 16) & 0xff),
            (byte)((data >> 8) & 0xff),
            (byte)((data >> 0) & 0xff),
        };
    }

    

}
