package com.inthinc.device.cassandra;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import android.util.Log;


public class CassandraParser {
    
    public CassandraParser(CassandraDB db){
        if (db == null){
            throw new NullPointerException("Cassandra needs to be set before we can execute");
        }
    }

    public Map<Map<String, Object>, Map<String, Long>> getAggMonth() {
        CqlQuery<Composite, String, Long> cqlQuery = new CqlQuery<Composite, String, Long>(
                CassandraDB.getKeyspace(), CassandraDB.cs, CassandraDB.ss, CassandraDB.ls);
        
        cqlQuery.setQuery("SELECT * FROM note.aggMonth WHERE KEY>='2012-05'");
        QueryResult<CqlRows<Composite, String, Long>> result = cqlQuery.execute();
        Map<Map<String, Object>, Map<String, Long>> values = new HashMap<Map<String, Object>, Map<String, Long>>();
        for (Row<Composite, String, Long> row : result.get().getList()) {
            Log.i("%s", row);
            Composite key = row.getKey();
            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("date", key.get(0, CassandraDB.ss));
            keyValue.put("driverID", key.get(1, CassandraDB.ls));
            keyValue.put("vehicleID", key.get(2, CassandraDB.ls));
            for (int bits : key.serialize().array()){
                System.out.print(String.format("%2s", Integer.toHexString(bits & 0xff)).replace(" ", "0"));
            }
            System.out.println();
            Log.i("%s", keyValue);
            Map<String, Long> columns = new HashMap<String, Long>();
            ColumnSlice<String, Long> slice = row.getColumnSlice();
            for (HColumn<String, Long> column : slice.getColumns()) {
                columns.put(column.getName(), column.getValue());
            }
            values.put(keyValue, columns);
        }
        return values;
    }
    
    public Map<Map<String, Object>, Map<String, Long>> getTrip(){
        CqlQuery<UUID, String, Long> cqlQuery = new CqlQuery<UUID, String, Long>(
                CassandraDB.getKeyspace(), CassandraDB.us, CassandraDB.ss, CassandraDB.ls);

        cqlQuery.setQuery("SELECT * FROM trip");
        QueryResult<CqlRows<UUID, String, Long>> result = cqlQuery.execute();
        for (Row<UUID, String, Long> row : result.get().getList()){
            System.out.println(row.getKey());
            
            if (true){
                return null;
            }
        }
        return null;
    }
    
    public void getDriverAggIndex(){
        CqlQuery<Long, String, Long> cqlQuery = new CqlQuery<Long, String, Long>(
                CassandraDB.getKeyspace(), CassandraDB.ls, CassandraDB.ss, CassandraDB.ls);
        
        cqlQuery.setQuery("SELECT * FROM note.driverAggIndex");
        QueryResult<CqlRows<Long, String, Long>> result = cqlQuery.execute();
        for (Row<Long, String, Long> row : result.get().getList()){
            Log.i("%s", row);
        }
    }
    
    public void getDriverAddIndex(String clusterName, String address, String keyspaceName){
        
    }

    public static void main(String[] args){

        String address = "10.0.35.40";
        String clusterName = "inthinc";
        String keyspaceName = "note";
        CassandraParser cp = new CassandraParser(new CassandraDB(clusterName, keyspaceName, address, 10, true));
        cp.getDriverAggIndex();
    }
}
