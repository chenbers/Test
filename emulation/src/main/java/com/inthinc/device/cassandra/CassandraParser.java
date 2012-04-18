package com.inthinc.device.cassandra;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.query.QueryResult;

public class CassandraParser {
    
    public CassandraParser(CassandraDB db){
        if (db == null){
            throw new NullPointerException("Cassandra needs to be set before we can execute");
        }
    }

    public Map<Map<String, Object>, Map<String, Long>> getAggMonth(Long driverID, Long vehicleID) {
        CqlQuery<Composite, String, Long> cqlQuery = new CqlQuery<Composite, String, Long>(
                CassandraDB.getKeyspace(), CassandraDB.cs, CassandraDB.ss, CassandraDB.ls);
        
        cqlQuery.setQuery("SELECT * FROM aggMonth");
        QueryResult<CqlRows<Composite, String, Long>> result = cqlQuery.execute();
        Map<Map<String, Object>, Map<String, Long>> values = new HashMap<Map<String, Object>, Map<String, Long>>();
        for (Row<Composite, String, Long> row : result.get().getList()) {
            Composite key = row.getKey();
            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("date", key.get(0, CassandraDB.ss));
            keyValue.put("driverID", key.get(1, CassandraDB.ls));
            keyValue.put("vehicleID", key.get(2, CassandraDB.ls));
            Map<String, Long> columns = new HashMap<String, Long>();
            ColumnSlice<String, Long> slice = row.getColumnSlice();
            for (HColumn<String, Long> column : slice.getColumns()) {
                columns.put(column.getName(), column.getValue());
            }
            values.put(keyValue, columns);
        }
        return values;
    }

}
