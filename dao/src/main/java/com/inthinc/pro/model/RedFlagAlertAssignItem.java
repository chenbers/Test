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

    public RedFlagAlertAssignItem(Integer id) {
        this(id, null, null);
    }

    public RedFlagAlertAssignItem(Integer redFlagId, Integer itemId) {
        this(null, redFlagId, itemId);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedFlagAlertAssignItem that = (RedFlagAlertAssignItem) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
