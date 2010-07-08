package com.inthinc.pro.model.configurator;

import java.util.List;

import com.inthinc.pro.dao.annotations.ID;

public class DeviceSettingDefinition implements Comparable{
    
    public enum SettingCategory{VEHICLE(0, "Vehicle"), HOS(1, "HOS"), THRESHOLDS(2, "Thresholds"),
                                NOTIFICATIONS(3,"Notifications"), GEOFENCE(4, "Geofence"), ALARM(5,"Alarm"),
                                WITNESS_TRIAX(6, "Witness Triax"), REMOTEUPDATE(7, "Remote Update"), COMM(8, "Comm"),
                                MCM(9,"MCM");
 

        private int code;
        private String name;
        
        SettingCategory(int code, String name){
            
            this.code = code;
            this.name = name;
        }
        public int getCode(){
            
            return code;
        }
        public String getName(){
            
            return name;
        }
    }
    
    public enum VarType{ VTBOOLEAN(0), VTINTEGER(1), VTDOUBLE(2), VTSTRING(3);
    
        private int code;
        
        VarType(int code){
            this.code = code;
        }
        public int getCode(){
            
            return code;
        }
      
    }
    @ID
    private Integer settingID;
    private String description;
    private String name;
    private SettingCategory category;
    private VarType varType;
    private List<String> choices;
    
    
    public Integer getSettingID() {
        return settingID;
    }
    public void setSettingID(Integer settingID) {
        this.settingID = settingID;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public SettingCategory getCategory() {
        return category;
    }
    public void setCategory(SettingCategory category) {
        this.category = category;
    }
    public VarType getVarType() {
        return varType;
    }
    public void setVarType(VarType varType) {
        this.varType = varType;
    }
    public List<String> getChoices() {
        return choices;
    }
    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
    @Override
    public int compareTo(Object o) {
        
        if (o != null && o instanceof DeviceSettingDefinition) {
            Integer rhsID = ((DeviceSettingDefinition) o).getSettingID();
            
            if (rhsID == null) {
                return -1;
            }
            
            return settingID.compareTo(rhsID);
        }
        return -1;
    }
}
