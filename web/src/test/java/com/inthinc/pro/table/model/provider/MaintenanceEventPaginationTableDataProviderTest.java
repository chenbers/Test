package com.inthinc.pro.table.model.provider;

import com.inthinc.pro.model.pagination.FilterOp;
import com.inthinc.pro.model.pagination.TableFilterField;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MaintenanceEventPaginationTableDataProviderTest {

    private MaintenanceEventPaginationTableDataProvider maintenance = new MaintenanceEventPaginationTableDataProvider();

    @Test
    public void testGetRowCount(){
        List<TableFilterField> listTableFilterFields = new ArrayList<TableFilterField>();
        maintenance.setFilters(listTableFilterFields);
        maintenance.treatCustomFilters();
        List<TableFilterField> customFilter=maintenance.getFilters();
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);

        assertEquals(customFilter.get(0).getField().toString(),"aggType");
        assertEquals(customFilter.get(0).getFilter(),intList);
        assertEquals(customFilter.get(0).getFilterOp(), FilterOp.IN);
    }
}
