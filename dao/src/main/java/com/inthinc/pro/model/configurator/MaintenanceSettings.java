package com.inthinc.pro.model.configurator;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public enum MaintenanceSettings implements Serializable {
    ENABLE_MAINTENANCE(189, "enable_maintenance"),
    SET_ENGINE_TEMP(190, "set_engine_temp"),
    SET_TRANS_TEMP(191, "set_trans_temp"),
    DPF_FLOW_RATE(192, "dpf_flow_rate"),
    SET_OIL_PRESSURE(193, "set_oil_pressure"),
    SET_BATT_VOLTAGE(194, "set_batt_voltage"),
    MAINT_BY_ENGINE_HOURS_INTERVAL(195, "maint_by_engine_hours_threshold"),
    MAINT_BY_DIST_START(196, "maint_by_dist_start"),
    MAINT_BY_DIST_INTERVAL(197, "maint_by_dist_threshold"),
    SETTINGS_MASK(198,"maintenance_bitmask"),
    MALFUNCTION_INDICATORS(199, "malfunction_indicators"),
    RED_STOP(200, "red_stop"),
    AMBER_WARNING(201, "amber_warning"),
    PROTECT(202, "protect"),
    ;

    private String description;
    private int code;

    private MaintenanceSettings(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, MaintenanceSettings> lookup = new HashMap<Integer, MaintenanceSettings>();
    static
    {
        for (MaintenanceSettings p : EnumSet.allOf(MaintenanceSettings.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static MaintenanceSettings valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    public String getDescription()
    {
        return description;
    }

}
