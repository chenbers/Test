package com.inthinc.pro.reports.hos.testData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeZone;

import com.inthinc.pro.model.hos.HOSVehicleMileage;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class ZeroMilesDataSet extends BaseDataSet {
    
    public List<HOSVehicleMileage> groupUnitNoDriverMileageList;

    public ZeroMilesDataSet(String basePath, String baseFilename) {
        String values[] = baseFilename.split("_");
        readInGroupHierarchy(basePath + baseFilename + "_groups.csv", values[1]);
        groupUnitNoDriverMileageList = readInNoDriverMileage(basePath + baseFilename + "_mileageZero.csv");
        // "vtest_00_07012010_07072010",
        interval = DateTimeUtil.getStartEndInterval(values[2], values[3], "MMddyyyy", DateTimeZone.getDefault());
        numDays = interval.toPeriod().toStandardDays().getDays();
    }


    private List<HOSVehicleMileage> readInNoDriverMileage(String filename) {
        List<HOSVehicleMileage> mileageList = new ArrayList<HOSVehicleMileage>();
        BufferedReader in;
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (stream != null) {
                in = new BufferedReader(new InputStreamReader(stream));
                String str;
                while ((str = in.readLine()) != null) {
                    String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                    for (int i = 0; i < values.length; i++)
                        if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                            values[i] = values[i].substring(1, values[i].length() - 1);
                        }
                    String groupId = values[0];
                    String unitID = values[1];
                    long mileage = Long.valueOf(values[2].replace(",", "")).longValue() * 100;
                    mileageList.add(new HOSVehicleMileage(calcGroupID(groupIDMap, groupId), unitID, mileage));
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mileageList;
    }

    
}
