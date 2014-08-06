package com.inthinc.pro.backing.dao.model;

public enum CrudType {

    CREATE("create"),
    READ("findByID"),
    READ_RESTRICTED("findByID"),
    UPDATE("update"),
    DELETE("deleteByID"),
    NOT_AVAILABLE(null);

    String methodName;

    private CrudType(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

}
