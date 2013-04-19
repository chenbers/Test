package it.util;

import it.com.inthinc.pro.dao.Util;
import it.com.inthinc.pro.dao.model.GroupData;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ReportTestConst;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;

public class DataGenForZonesTesting extends DataGenForTesting {

public String xmlPath;
public String importPath;

@Override
protected void createTestData() {
    itData = new ITData();
    Date assignmentDate = new Date(DateUtil.getTodaysDate());
    ((ITData)itData).createTestData(siloService, xml, assignmentDate, false, false);
}

@Override
protected boolean parseTestData() {
    itData = new ITData();
    InputStream stream;
    try {
        stream = new FileInputStream(xmlPath);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
    }
    if (!((ITData)itData).parseTestData(stream, siloService, false, false))
    {
        System.out.println("Parse of xml data file failed.  File: " + xmlPath);
        return false;
    }
    
    return true;
}

private void createZones(String importPath) {

    List<Zone> list = new ArrayList<Zone>();

    BufferedReader in;
    try {
        in = new BufferedReader(new FileReader(importPath));

        String line = in.readLine();
        line = in.readLine();
        while (line != null) {
           Zone rec = parseLine(line);
           if (rec == null) {
               System.out.println("error parsing line: " + line);
            } 
           else {
               list.add(rec);
            }
    
            line = in.readLine();
        }
    
        in.close();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
    zoneDAO.setSiloService(siloService);

    for (Zone zone : list) {
        Integer zoneID = zoneDAO.create(itData.account.getAccountID(), zone);
        zone.setZoneID(zoneID);
    }
}

    
    
private Zone parseLine(String line) {
    String[] token = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");//split(",");

    String name = token[0].trim();
    String coords = token[1].trim();
    System.out.println("coords: " + coords);
    coords = coords.substring(1, coords.length()-1);
    System.out.println("coords after: " + coords);
    

    Zone zone = new Zone(0, itData.account.getAccountID(), Status.ACTIVE, name, "123 Street, Salt Lake City, UT 84107", itData.fleetGroup.getGroupID());
    
    List points = new ArrayList<LatLng>();

    if (coords != null && !coords.isEmpty())
    {
        String[] cToken = coords.split(",");
        for (int i = 0; i < cToken.length; i+=2) {
            float lng = new Float(cToken[i]);
            float lat = new Float(cToken[i+1]);
            points.add(new LatLng(lat, lng));
        }
    }
    zone.setPoints(points);

    
    return zone;
}

private void parseArguments(String[] args) {
    // Arguments:
    //      required
    //          0:      full path to xml file for storage/retrieval of current data set
    //      optional
    //          1:      full path to csv file for zones to import zone name, coord list
    
    String usageErrorMsg = "Usage: DataGenForZonesTesting <xml file path> [zone import csv]";
    
    if (args.length < 1)
    {
        System.out.println(usageErrorMsg);
        System.exit(1);
    }
    
    xmlPath = args[0];
    
    if (args.length > 1) {
        importPath = args[1];
        if (!new File(importPath).exists()) {
            System.out.println("The zone import data file that you specified does not exist. " + importPath);
            System.exit(1);
        }

        
    }
    
    
}

protected void createZones(int zoneCount) {
    ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
    zoneDAO.setSiloService(siloService);

    for (int i = 0; i < zoneCount; i++) {
        Zone zone = new Zone(0, itData.account.getAccountID(), Status.ACTIVE, "Zone " + i, "123 Street, Salt Lake City, UT 84107", itData.fleetGroup.getGroupID());
        List<LatLng> points = new ArrayList<LatLng>();
        int numPoints = Util.randomInt(3, 10);
        LatLng startPt = new LatLng(randomLat(), randomLng()); 
        points.add(startPt);
        for (int p = 0; p < numPoints; p++) {
            points.add(new LatLng(startPt.getLat()+randomDelta(), startPt.getLng()+randomDelta()));
        }
        points.add(startPt);
        zone.setPoints(points);
        Integer zoneID = zoneDAO.create(itData.account.getAccountID(), zone);
        zone.setZoneID(zoneID);
    }
//    xml.writeObject(zone);

}

// uses lat lng for US
private double randomLng() {
    return Util.randomFloat(-127.0f, -79.0f);
}
private double randomLat() {
    return Util.randomFloat(28.0f, 48.0f);
}
private double randomDelta() {
    return Util.randomFloat(-1f, 1f);
}
public static void main(String[] args)
{
    
    DataGenForZonesTesting  testData = new DataGenForZonesTesting();
    testData.parseArguments(args);
    
    try {
        initServices();
    } catch (MalformedURLException e1) {
        e1.printStackTrace();
        System.exit(1);
    }
    try
    {
        System.out.println(" -- test data generation start -- ");
        xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(testData.xmlPath)));
        System.out.println(" saving output to " + testData.xmlPath);
        testData.createTestData();
        if (testData.importPath != null) {
            testData.createZones(testData.importPath);
        }
        
        if (xml != null)
        {
            xml.close();
        }
        
        System.out.println(" -- test data generation complete -- ");
    }
    catch (Exception e)
    {
        e.printStackTrace();
        System.exit(1);
    }
    System.exit(0);
    
}

}


