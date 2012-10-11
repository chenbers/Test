package com.inthinc.device.cassandras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import com.inthinc.device.emulation.enums.ColumnAttributes;
import com.inthinc.device.emulation.enums.DeviceNoteTypes;
import com.inthinc.device.emulation.enums.NoteColumnFamily;
import com.inthinc.device.emulation.enums.UnitType;
import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;

@SuppressWarnings("serial")
public class EventCassandraDAO extends GenericCassandraDAO {
    private final static int MAX_EVENTS = 1000;


    public static void main(String[] args) {
        /*
         * if(args.length == 0) { System.out.println("Properties File was not passed In"); System.exit(0); }
         * 
         * 
         * String filePath = (String) args[0]; String startTime = (String) args[1]; String endTime = (String) args[2];
         */

        CassandraDB cassandraDB = new CassandraDB("Iridium Archive", "note", "10.0.35.40:9160", 10, false);
        EventCassandraDAO dao = new EventCassandraDAO();
        dao.setCassandraDB(cassandraDB);
        AutomationCalendar endDate = new AutomationCalendar();
        AutomationCalendar startDate = endDate.copy().addToDay(-7);
        List<Integer> noteTypes = new ArrayList<Integer>();

        noteTypes.add(52);
        
        Log.info(dao.fetchEventsForAsset(UnitType.VEHICLE, 53412, startDate, endDate, noteTypes, true));
        
        dao.shutdown();
    }

    public List<DeviceNote> fetchEventsForAsset(UnitType type, Integer ID, AutomationCalendar startDate, AutomationCalendar endDate, List<Integer> eventTypes,
            boolean includeForgiven) {
        List<Composite> keyList = new ArrayList<Composite>();
        if (eventTypes.isEmpty()){
          for (DeviceNoteTypes noteType : EnumSet.allOf(DeviceNoteTypes.class)){
              eventTypes.add(noteType.getCode());
          }
        }
        for (Integer eventType : eventTypes) {

            Composite startRange = new Composite();
            startRange.add(0, eventType);
            startRange.add(1, startDate.toInt());

            Composite endRange = new Composite();
            endRange.add(0, eventType);
            endRange.add(1, endDate.toInt());

            keyList.addAll(fetchRowKeysFromIndex(NoteColumnFamily.NOTE_TYPE_TIME.getColumnDef(type), ID, startRange, endRange, MAX_EVENTS));
        }

        return fetchNotes(keyList, includeForgiven);
    }

    private List<Composite> fetchRowKeysFromIndex(String INDEX_CF, Integer rowKey, Composite startRange, Composite endRange, Integer count) {
        SliceQuery<Composite, Composite, Composite> sliceQuery = HFactory.createSliceQuery(getKeyspace(), compositeSerializer, compositeSerializer, compositeSerializer);

        sliceQuery.setRange(startRange, endRange, false, count);
        sliceQuery.setColumnFamily(INDEX_CF);

        Composite rowKeyComp = new Composite();
        rowKeyComp.add(0, rowKey);
        sliceQuery.setKey(rowKeyComp);
        // rangeSlicesQuery.setReturnKeysOnly();

        QueryResult<ColumnSlice<Composite, Composite>> result = sliceQuery.execute();
        ColumnSlice<Composite, Composite> columnSlice = result.get();

        List<Composite> keyList = new ArrayList<Composite>();
        List<HColumn<Composite, Composite>> columnList = columnSlice.getColumns();
        for (HColumn<Composite, Composite> column : columnList) {
            keyList.add(column.getValue());
        }
        return keyList;
    }

    private List<DeviceNote> fetchNotes(List<Composite> keys, boolean includeForgiven) {
        List<DeviceNote> eventList = new ArrayList<DeviceNote>();
        MultigetSliceQuery<Composite, String, byte[]> sliceQuery = HFactory.createMultigetSliceQuery(getKeyspace(), compositeSerializer, stringSerializer, bytesArraySerializer);

        sliceQuery.setColumnFamily(NoteColumnFamily.NOTE.getColumnDef(null));
        sliceQuery.setRange(ColumnAttributes.ATTRIBS_COL.getValue() + "!", "~", false, 1000); // get all the columns
        sliceQuery.setKeys(keys);

        QueryResult<Rows<Composite, String, byte[]>> result = sliceQuery.execute();

        Rows<Composite, String, byte[]> rows = result.get();
        for (Row<Composite, String, byte[]> row : rows) {
            ColumnSlice<String, byte[]> columnSlice = row.getColumnSlice();
            List<HColumn<String, byte[]>> columnList = columnSlice.getColumns();


            byte[] raw = null;
            boolean forgiven = false;
            for (HColumn<String, byte[]> column : columnList) {
                if (column.getName().equalsIgnoreCase(ColumnAttributes.RAW_COL.getValue()))
                    raw = column.getValue();
                else if (column.getName().equalsIgnoreCase(ColumnAttributes.FORGIVEN_COL.getValue()))
                    forgiven = true;
            }

            DeviceNote note = DeviceNote.unPackageS(raw);

            if ((includeForgiven || (!includeForgiven && forgiven == false)))
                eventList.add(note);
        }
        Collections.sort(eventList);
        return eventList;
    }
}
