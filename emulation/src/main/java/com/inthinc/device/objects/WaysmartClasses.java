package com.inthinc.device.objects;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.pro.automation.utils.MasterTest;

public final class WaysmartClasses {

    public class MultiForwardCmd {
        public final Integer m_ID;
        public final Byte m_version;
        public final Integer m_nCount;
        public List<ForwardCommandEventInterface> events;

        public static final int SIZE = (Integer.SIZE * 2 + Byte.SIZE)
                / Byte.SIZE;

        public MultiForwardCmd(ByteArrayInputStream bais) {
            this.m_ID = DeviceNote.byteToInt(bais, 4);
            this.m_version = (byte) bais.read();
            this.m_nCount = DeviceNote.byteToInt(bais, 4);
            MasterTest.print(
                    String.format("Got %d forward commands", m_nCount),
                    Level.INFO);
            events = new ArrayList<ForwardCommandEventInterface>();
        }

    }

    public class HttpHeader {
        public final byte nForwardCommand;
        public final byte[] m_data;
        public final Integer exFwdCmdID;
        public final byte nVersion;
        public final int nLength;

        public static final int SIZE = (Byte.SIZE * 2 + Integer.SIZE * 2)
                / Byte.SIZE;

        public HttpHeader(ByteArrayInputStream bais) {
            nForwardCommand = (byte) bais.read();
            m_data = new byte[] { (byte) bais.read(), (byte) bais.read(),
                    (byte) bais.read(), (byte) bais.read() };
            exFwdCmdID = DeviceNote.byteToInt(m_data, 0, 4);
            if (exFwdCmdID == -1){
                nVersion = 0;
                nLength = 0;
            } else {            
                nVersion = (byte) bais.read();
                nLength = DeviceNote.byteToInt(bais, 4);
            }

            MasterTest.print(toString(), Level.INFO);
        }

        @Override
        public String toString() {
            return String.format(
                    "ForwardCommand:%d, FwdCmdID:%d, Version:%d, Length:%d",
                    nForwardCommand, exFwdCmdID, nVersion, nLength);
        }
    }
    
    public interface ForwardCommandEventInterface{
        public EventHeader getHeader();
        public byte getRequest();
        public List<Byte> getData();
    }

    public class ForwardCommandEvent implements ForwardCommandEventInterface{
        public final EventHeader m_header;
        public final byte m_nRequest;
        public final List<Byte> m_data;

        public static final int SIZE = EventHeader.SIZE + (Byte.SIZE * 5)
                / Byte.SIZE;

        public ForwardCommandEvent(byte m_nRequest) {
            m_header = new EventHeader();
            this.m_nRequest = m_nRequest;
            m_data = new ArrayList<Byte>();
        }
        
        public void addData(ByteArrayInputStream bais){
            m_data.add((byte)bais.read());
        }
        
        public void addData(byte[] list){
            for (byte bits : list){
                m_data.add(bits);
            }
        }
        
        public void addData(ByteArrayInputStream bais, int length){
            for (;length>0;length--){
                m_data.add((byte)bais.read());
            }
            MasterTest.print(m_data.get(m_data.size()-1));
        }
        
        @Override
        public String toString(){
            return String.format("\nRequest: %d\nData: %s", m_nRequest, m_data);
        }

        @Override
        public EventHeader getHeader() {
            return m_header;
        }

        @Override
        public byte getRequest() {
            return m_nRequest;
        }

        @Override
        public List<Byte> getData() {
            return m_data;
        }
    }

    public class ForwardCommandEventEx implements ForwardCommandEventInterface{
        public final EventHeader m_header;
        public final byte m_nRequest;
        public final List<Byte> m_pData;

        public static final int SIZE = EventHeader.SIZE + (Byte.SIZE * 2)
                / Byte.SIZE;

        public ForwardCommandEventEx(byte m_nRequest) {
            m_header = new EventHeader();
            this.m_nRequest = m_nRequest;
            m_pData = new ArrayList<Byte>();
            
        }

        public void addData(ByteArrayInputStream bais) {
            m_pData.add((byte)bais.read());
        }

        public void addData(byte[] list) {
            for (byte bits : list){
                m_pData.add(bits);
            }
        }
        
        @Override
        public String toString(){
            return String.format("\nRequest: %d\nData: %s", m_nRequest, m_pData);
        }

        @Override
        public EventHeader getHeader() {
            return m_header;
        }

        @Override
        public byte getRequest() {
            return m_nRequest;
        }

        @Override
        public List<Byte> getData() {
            return m_pData;
        }
    }

    public class EventHeader {
        public byte m_nSender;
        public byte m_nEventType;
        public byte m_nSeverity;
        public long m_timeToSend;
        public int m_nLength;

        public static final int SIZE = (Byte.SIZE * 3 + Integer.SIZE * 2)
                / Byte.SIZE;


        @Override
        public String toString() {
            return String
                    .format("Sender:%d, EventType:%d, Severity:%d, TimeToSend:%d, Length:%d",
                            m_nSender, m_nEventType, m_nSeverity, m_timeToSend,
                            m_nLength);
        }
    }

}
