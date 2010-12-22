package com.inthinc.pro.model.configurator;

public enum ProductName {
    
    UNKNOWN("Unknown"),TEEN("Teen"),WAYSMART("waySmart"),TIWIPRO("tiwiPro");
    
    private String productName;

    private ProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
