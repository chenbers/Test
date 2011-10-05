package com.inthinc.pro.configurator.model;

public enum ProductName {
    
    UNKNOWN("UNKNOWN","Unknown"),TEEN("Teen"),WAYSMART("waySmart"),TIWIPRO("tiwiPro");
    
    private String messageKey;
    private String productName;

    private ProductName(String productName) {
        this.productName = productName;
    }
    private ProductName(String messageKey, String productName){
    	this.messageKey = messageKey;
        this.productName = productName;
    }
    public String getProductName() {
        return productName;
    }
	public String getMessageKey() {
		return messageKey;
	}
}
