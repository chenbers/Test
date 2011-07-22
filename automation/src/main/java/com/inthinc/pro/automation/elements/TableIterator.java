package com.inthinc.pro.automation.elements;

import java.util.Iterator;

import com.inthinc.pro.automation.elements.ElementInterface.TableBased;

public class TableIterator<T> implements Iterator<T> {
    
    private final TableBased<T> table;

    public TableIterator(TableBased<T> table) {
        this.table = table;
        rowNumber = 1;
    }

    private int rowNumber;

    @Override
    public boolean hasNext() {
        return ((ElementInterface) table.row(rowNumber)).isPresent();
    }

    @Override
    public T next() {
        return table.row(rowNumber++);
    }

    @Override
    @Deprecated
    public void remove() {
        throw new UnsupportedOperationException("There is nothing to remove");
    }
}
