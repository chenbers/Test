package com.inthinc.pro.model.phone;

/**
 * Cell phone control Service Provider enum.
 */
public enum ServiceProviderType {
    CELL_CONTROL ("Cell Control"),
    ZOOM_SAFER ("Zoom Safer");
    
    private String name;

    private ServiceProviderType(String providerName) {
        this.name = providerName;
    }

    /**
     * The name getter.
     * @return the name
     */
    public String getName() {
        return this.name;
    }
}
