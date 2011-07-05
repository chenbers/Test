package com.inthinc.pro.backing.model;

import java.util.List;

public interface ItemsGetter<V> {

    List<V> getItems();
    boolean isEmpty();
    int getSize();
    void reset();
}
