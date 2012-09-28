package com.inthinc.pro.backing.paging;

public enum PhoneEscalationStatus {
    NOT_ATTEMPTED,
    IN_PROGRESS,
    FAILED,
    SUCCESS,
    CANCELED;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
}
