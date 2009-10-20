package com.inthinc.pro.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BatchResponse {

    private Integer status;
    private String uri;
//    private T entity;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "BatchResponse [status=" + status + ", uri=" + uri + "]";
    }
    
//    public T getEntity() {
//        return entity;
//    }

//    public void setEntity(T entity) {
//        this.entity = entity;
//    }

}
