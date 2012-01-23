package com.inthinc.device.emulation.enums;

import java.util.Map;

public class MapSection {
    

    private final String name;
    
    private byte[] file;
    
    private Integer version;
    private Integer baseVersion;
    
    
    public MapSection(Map<String, Object> mapSegment){
        name = (String) mapSegment.get("n");
        file = (byte[]) mapSegment.get("f");
        version = (Integer) mapSegment.get("v");
        baseVersion = (Integer) mapSegment.get("b");
    }
    
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getBaseVersion() {
        return baseVersion;
    }

    public void setBaseVersion(Integer baseVersion) {
        this.baseVersion = baseVersion;
    }

    public String getName() {
        return name;
    }

    public boolean setBase(Map<String, Object> mapBase){
        if (!mapBase.get("n").equals(getName())){
            return false;
        }
        setBaseVersion((Integer) mapBase.get("b"));
        setVersion((Integer) mapBase.get("v"));
        setFile((byte[]) mapBase.get("f"));
        return true;
    }
    
    public boolean setEdit(Map<String, Object> mapBase){
        if (mapBase == null){
            return true;
        }
        if (!mapBase.get("n").equals(name)){
            return false;
        }
        baseVersion = (Integer) mapBase.get("b");
        version = (Integer) mapBase.get("v");
        file = (byte[]) mapBase.get("f");
        return true;
    }
    

}
