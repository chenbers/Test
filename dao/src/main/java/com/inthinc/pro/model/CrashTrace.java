package com.inthinc.pro.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

@XmlRootElement
public class CrashTrace extends BaseEntity {
    /**
     * 
     */
    @Column(updateable = false)
    private static final long serialVersionUID = -2232308206099636851L;//TODO: jwimmer: regenerate after class complete-ish
    @ID
    @Column(name = "eventID")    
    private String eventID;
    private Blob binaryTraceData;
    private byte[] binaryTraceData_ba;

    public CrashTrace() {
    }

    public CrashTrace(String eventID, Blob binaryTraceData, byte[] binaryTraceData_ba) {
        super();
        this.eventID = eventID;
        this.binaryTraceData = binaryTraceData;
        this.binaryTraceData_ba = binaryTraceData_ba;
    }
    
    
    public CrashTrace getMockObject(String path) throws IOException{
        CrashTrace result = new CrashTrace();
        result.setEventID("D6FB4857-E6B6-4813-9DB6-2E41C5392D9D");//example of an actual eventID
        result.setBinaryTraceData(getMockBlob(path));
        result.setBinaryTraceData_ba(getMockByteArray(path));
           
        return result;
    }
    
    public static byte[] getMockByteArray(String path) throws IOException {
        InputStream is = null;
        byte[] result = null; 
        try{
            is = new URL(path+"/applets/mockCrashTrace.bin").openStream(); //TODO: needs replaced with ?hessian stuff? to get BLOB out of DB (not media server)

            byte[] buf = new byte[32 * 1024]; // 32k buffer
            int nRead = 0;
            while( (nRead=is.read(buf)) != -1 ) {
                //o.write(buf, 0, nRead);
                System.out.print(".");
            }
            result = buf; 
            
        }catch(Exception e){
            //IF the data is mal-formatted.
        }finally{
            is.close();
        } 
        return result;
    }
    
    public static Blob getMockBlob(String path) throws IOException {
        InputStream is = null;
        Blob result = null;
        try{
            is = new URL(path+"/applets/mockCrashTrace.bin").openStream(); //TODO: needs replaced with ?hessian stuff? to get BLOB out of DB (not media server)

            byte[] buf = new byte[32 * 1024]; // 32k buffer
            int nRead = 0;
            while( (nRead=is.read(buf)) != -1 ) {
                //o.write(buf, 0, nRead);
                System.out.print(".");
            }
            result.setBytes(0, buf); 
            
        }catch(Exception e){
            //IF the data is mal-formatted.
        }finally{
            is.close();
        } 
        return result;
    }

    @Override
    public String toString() {
        return "CrashTrace [eventID=" + eventID + ", ]"; //TODO: jwimmer: finish implementation after fields established
    }

    public Blob getBinaryTraceData() {
        return binaryTraceData;
    }

    public void setBinaryTraceData(Blob binaryTraceData) {
        this.binaryTraceData = binaryTraceData;
    }

    public byte[] getBinaryTraceData_ba() {
        return binaryTraceData_ba;
    }

    public void setBinaryTraceData_ba(byte[] binaryTraceDataBa) {
        binaryTraceData_ba = binaryTraceDataBa;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
