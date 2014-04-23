package com.inthinc.pro.model;

/**
 * Defines an item to be assigned to a red flag alert.
 */
public class RedFlagAlertAssignItem {
    private Integer id;
    private Integer redFlagId;
    private Integer itemId;

    public RedFlagAlertAssignItem(Integer id, Integer redFlagId, Integer itemId) {
        this.id = id;
        this.redFlagId = redFlagId;
        this.itemId = itemId;
    }

    public RedFlagAlertAssignItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRedFlagId() {
        return redFlagId;
    }

    public void setRedFlagId(Integer redFlagId) {
        this.redFlagId = redFlagId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
