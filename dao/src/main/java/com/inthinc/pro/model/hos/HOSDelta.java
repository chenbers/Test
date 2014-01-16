package com.inthinc.pro.model.hos;

import java.util.ArrayList;
import java.util.List;

public class HOSDelta {

    private List<HOSDeltaRecord> deletedList;
    private List<HOSDeltaRecord> addedList;

    public HOSDelta() {
        deletedList = new ArrayList<HOSDeltaRecord>();
        addedList = new ArrayList<HOSDeltaRecord>();
    }

    public List<HOSDeltaRecord> getDeletedList() {
        return deletedList;
    }

    public void setDeletedList(List<HOSDeltaRecord> deletedList) {
        this.deletedList = deletedList;
    }

    public List<HOSDeltaRecord> getAddedList() {
        return addedList;
    }

    public void setAddedList(List<HOSDeltaRecord> addedList) {
        this.addedList = addedList;
    }

    public Integer getTotalCount() {
        return deletedList.size() + addedList.size();
    }
    public Integer getDeletedCount() {
        return deletedList.size();
    }
    public Integer getAddedCount() {
        return addedList.size();
    }
    
    public void addAdded(HOSDeltaRecord rec) {
        addedList.add(rec);
    }
    public void addDeleted(HOSDeltaRecord rec) {
        deletedList.add(rec);
    }
}
