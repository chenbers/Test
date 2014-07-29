package com.inthinc.pro.model.configurator;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public enum MaintenanceSettings implements Serializable {
    ENABLE_MAINTENANCE(190, "enable_maintenance"),
    MALFUNCTION_INDICATORS(191, "malfunction_indicators"),
    RED_STOP(192, "red_stop"),
    AMBER_WARNING(193, "amber_warning"),
    PROTECT(194, "protect"),
    BATT_VOLTAGE(195, "batt_voltage"),
    SET_BATT_VOLTAGE(196, "set_batt_voltage"),
    ENGINE_TEMP(197, "engine_temp"),
    SET_ENGINE_TEMP(198, "set_engine_temp"),
    TRANS_TEMP(199, "trans_temp"),
    SET_TRANS_TEMP(200, "set_trans_temp"),
    OIL_PRESSURE(201, "oil_pressure"),
    SET_OIL_PRESSURE(202, "set_oil_pressure"),
    //    DIESEL_EXHAUST(10129, "diesel_exhaust"),
//    SET_DIESEL_EXHAUST(10130, "set_diesel_exhaust"),
    DPF_FLOW_RATE(209, "dpf_flow_rate"),
    ENABLE_DPF_FLOW_RATE(210, "enable_dpf_flow_rate"),
    MAINT_BY_DIST(203, "maint_by_dist"),
    MAINT_BY_DIST_INTERVAL(204, "maint_by_dist_interval"),
    MAINT_BY_DIST_START(205, "maint_by_dist_start"),
    MAINT_BY_ENGINE_HOURS(206, "maint_by_engine_hours"),
    MAINT_BY_ENGINE_HOURS_INTERVAL(207, "maint_by_engine_hours_interval"),
    MAINT_BY_ENGINE_HOURS_START(208, "maint_by_engine_hours_start"),
    SETTINGS_MASK(189,"maintenance_bitmask")
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
