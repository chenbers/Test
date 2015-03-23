package com.inthinc.pro.table.model.provider;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.TableFilterField;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class MaintenanceEventPaginationTableDataProvider extends EventPaginationTableDataProvider {
    private static final long serialVersionUID = -2324384773462992656L;
    private static final Logger logger = Logger.getLogger(MaintenanceEventPaginationTableDataProvider.class);

    @Override
    public List<Event> getItemsByRange(int firstRow, int endRow) {
        treatCustomFilters();
        return super.getItemsByRange(firstRow, endRow);
    }


    @Override
    public int getRowCount() {
        treatCustomFilters();
        return super.getRowCount();
    }

    protected void treatCustomFilters() {
        List<TableFilterField> currentFilters = getFilters();
        boolean exists = false;
        for (TableFilterField tf : currentFilters) {
            if (tf.getField().equals("aggType")) {
                exists = true;
                break;
            }
        }

        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);

        if (!exists) {
            TableFilterField customFilter = new TableFilterField("aggType", intList, FilterOp.IN);
            currentFilters.add(customFilter);
            setFilters(currentFilters);
        }
    }
}
