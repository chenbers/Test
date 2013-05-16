package com.inthinc.pro.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Interval;

import com.inthinc.pro.dao.report.DVIRInspectionRepairReportDAO;
import com.inthinc.pro.model.dvir.DVIRInspectionRepairReport;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.NoteType;

public class DVIRInspectionRepairReportJDBCDAO extends GenericJDBCDAO implements DVIRInspectionRepairReportDAO {
    
    /**
     * Generated serial Version ID
     */
    private static final long serialVersionUID = -4985375162075724392L;
    
    private static Set<Integer> inspectionEventCodeSet = new HashSet<Integer>();
    
    static {
        
        inspectionEventCodeSet.add(EventAttr.ATTR_DVIR_MECHANIC_ID_STR.getIndex());
        inspectionEventCodeSet.add(EventAttr.ATTR_DVIR_INSPECTOR_ID_STR.getIndex());
        inspectionEventCodeSet.add(EventAttr.ATTR_DVIR_SIGNOFF_ID_STR.getIndex());
        inspectionEventCodeSet.add(EventAttr.ATTR_DVIR_COMMENTS.getIndex());
        inspectionEventCodeSet.add(EventAttr.ATTR_DVIR_FORM_ID.getIndex());
        inspectionEventCodeSet.add(EventAttr.ATTR_DVIR_SUBMISSION_TIME.getIndex());
    }
    
    public Collection<DVIRInspectionRepairReport> getDVIRInspectionRepairsForGroup(List<Integer> groupIDs, Interval interval) {
        List<DVIRInspectionRepairReport> retVal = new ArrayList<DVIRInspectionRepairReport>();
        
        Connection conn = null;
        Statement stmnt = null;
        ResultSet resultSet = null;
        
        try {
            conn = this.getConnection();
            stmnt = conn.createStatement();
            
            String sqlStatement = buildInspectionSQL(groupIDs, interval);
            resultSet = stmnt.executeQuery(sqlStatement);
            
            while (resultSet.next()) {
                
                // Logic to parse out the other necessary fields from the returned attributes
                String[] attributeArray = resultSet.getString("attributes").split(";");
                
                Map<EventAttr, String> inspectionAttrMap = buildInspectionAttrMap(attributeArray);
                
                DVIRInspectionRepairReport dVIRInspectionRepairReport = new DVIRInspectionRepairReport(resultSet.getDate("dateTime"), resultSet.getString("driverName"),
                                resultSet.getString("vehicleName"), resultSet.getInt("vehicleID"), resultSet.getString("groupName"), resultSet.getInt("groupID"),
                                inspectionAttrMap.get(EventAttr.ATTR_DVIR_FORM_ID), inspectionAttrMap.get(EventAttr.ATTR_DVIR_SUBMISSION_TIME),
                                inspectionAttrMap.get(EventAttr.ATTR_DVIR_INSPECTOR_ID_STR), inspectionAttrMap.get(EventAttr.ATTR_DVIR_MECHANIC_ID_STR),
                                inspectionAttrMap.get(EventAttr.ATTR_DVIR_SIGNOFF_ID_STR), inspectionAttrMap.get(EventAttr.ATTR_DVIR_COMMENTS));
                
                retVal.add(dVIRInspectionRepairReport);
            }
        } catch (SQLException e) {
            System.out.println(e);
            
        } finally { // clean up and release the connection
            close(resultSet);
            close(stmnt);
            close(conn);
        }
        
        return retVal;
    }
    
    private Map<EventAttr, String> buildInspectionAttrMap(String[] attributeArray) {
        Map<EventAttr, String> retVal = new HashMap<EventAttr, String>();
        
        for (int i = 0; i < attributeArray.length; i++) {
            String keyAndValString = attributeArray[i];
            
            if (StringUtils.isNotBlank(keyAndValString)) {
                String[] keyValSplit = keyAndValString.split("=");
                
                if (keyValSplit != null && keyValSplit.length > 1) {
                    
                    if (inspectionEventCodeSet.contains(Integer.valueOf(keyValSplit[0]))) {
                        EventAttr key = EventAttr.valueOf(Integer.valueOf(keyValSplit[0]));
                        String value = keyValSplit[1] == null ? "" : keyValSplit[1];
                        retVal.put(key, value);
                    }
                }
            }
        }
        
        return retVal;
    }
    
    public static String buildInspectionSQL(List<Integer> groupIDs, Interval interval) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT note.time as 'dateTime', CONCAT(p.first, ' ', p.middle, ' ', p.last) as 'driverName', v.name as 'vehicleName', note.vehicleId as 'vehicleID', g.name as 'groupName', note.groupID as 'groupID', note.attrs as 'attributes'")
                        .append(" FROM ").append("( ");
        
        // Create iterator for unions of the note tables found in the schema.
        for (int i = 0; i < 32; i++) {
            
            sb.append("SELECT deviceID, driverID, vehicleId, groupID, time, attrs").append(" FROM ");
            
            // lil' logic here to increment the note table name.
            StringBuilder noteTableName = new StringBuilder("note");
            if (i < 10) {
                noteTableName.append("0" + String.valueOf(i));
            } else {
                noteTableName.append(String.valueOf(i));
            }
            
            sb.append(noteTableName)
            // Build Clause for the noteTypes to include
                            .append(" WHERE ").append("type IN ( ").append(String.valueOf(NoteType.SAT_EVENT_DVIR_REPAIR.getCode())).append(" ) ");
            
            // Build Clause for group IDS
            if (groupIDs != null && groupIDs.size() > 0) {
                sb.append("AND ").append("groupID IN ( ");
                for (Integer id : groupIDs) {
                    sb.append(String.valueOf(id)).append(",");
                }
                sb.deleteCharAt(sb.length() - 1)// remove extra comma
                                .append(" ) ");
            }
            
            // Build between clause for interval
            if (interval != null) {
                sb.append("AND ").append("time BETWEEN ").append("'").append(interval.getStart()).append("'").append(" AND ").append("'").append(interval.getEnd()).append("'");
            }
            
            // Union this bad boy for all the note tables in the schema.
            if (i < 31) {
                sb.append(" UNION ");
            }
            
        }
        
        sb.append(" ) AS note ").append("left join driver d on d.driverID = note.driverID ").append("left join vehicle v on v.vehicleID = note.vehicleID ")
                        .append("left join groups g on g.groupID = note.groupID ").append("left join person p on p.personID = d.personID ");
        
        // Add the order by clause
        sb.append("order by groupName, dateTime");
        
        return sb.toString();
    }
}
