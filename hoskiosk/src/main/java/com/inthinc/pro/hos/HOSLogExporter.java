package com.inthinc.pro.hos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;

public class HOSLogExporter extends HOSBase {

    public HOSLogExporter(HOSDAO hosDAO) {
        super(hosDAO);
    }


    public void writeDriverStateHistoryToStream(OutputStream os, Driver driver) throws IOException {
        DateTime currentDate = new DateTime();
        List<HOSRecord> hosRecordList = fetchHosRecordList(currentDate, driver);
        if (hosRecordList == null)
            return;
        

        writeHOSDriverStateHistoryToStream(os, hosRecordList);
    }

    void writeHOSDriverStateHistoryToStream(OutputStream os, List<HOSRecord> hosRecordList) throws IOException {

    
        DataOutputStream out = new DataOutputStream(os);
        for (HOSRecord hosRecord : hosRecordList) {
            addRecordToStream(out, hosRecord);
        }
        // gain adds this record to the end of the list on export
        addRecordToStream(out, new HOSRecord(0, 0, RuleSetType.NON_DOT, 0, "", true, 14l, new Date(948842477000l), null, null, HOSStatus.OFF_DUTY, HOSOrigin.PORTAL, "", 0f, 0f, 0l, "","",false, false, "", false, 0f, 0f));
    }


    private void addRecordToStream(DataOutputStream out, HOSRecord hosRecord) throws IOException {
        out.writeShort((short) 120); // packet length
        byte driverState = (byte) (hosRecord.getStatus().getCode() & 0x003f);
        out.writeInt(driverState);
        out.writeByte((byte) 1); // version
        out.writeInt(Long.valueOf(hosRecord.getLogTime().getTime() / 1000l).intValue());
        out.writeByte((byte) 0x01); // flags - hardcode to always having gps lock

        double position = (hosRecord.getLng() == null) ? 0.0 : ((hosRecord.getLng() < 0.0) ? (hosRecord.getLng() + 360.0) / 360.0 : hosRecord.getLng() / 360.0);
        writePosition(out, position);

        position = (hosRecord.getLat() == null) ? 0.0 : (-(hosRecord.getLat() - 90.0) / 180.0);
        writePosition(out, position);

        out.writeInt(hosRecord.getDistance() == null ? 0 : hosRecord.getDistance().intValue());

        // TODO: GAIN unitID -- which field is same?
        writePaddedString(out, hosRecord.getVehicleName(), 18);
        writePaddedString(out, hosRecord.getTrailerID(), 16);
        writePaddedString(out, hosRecord.getServiceID(), 32);
        writePaddedString(out, hosRecord.getLocation(), 32);
    }

    private void writePosition(DataOutputStream out, double position) throws IOException {
        long position_encoded = (long) (position * 0x00ffffff) & 0x00ffffff;
        position_encoded = position_encoded & 0x00ffffff;
        out.writeByte((byte) ((position_encoded & 0x00ff0000) >> 16));
        out.writeByte((byte) ((position_encoded & 0x0000ff00) >> 8));
        out.writeByte((byte) (position_encoded & 0x000000ff));
    }

    private void writePaddedString(DataOutputStream out, String str, int len) throws IOException {
        if (str == null)
            str = "";
        if (str.length() >= len)
          str = str.substring(0, len);
        out.writeBytes(str);
        for (int i = str.length(); i < len; i++)
        {
            out.writeByte((byte) 0);
        }
        
    }
}
