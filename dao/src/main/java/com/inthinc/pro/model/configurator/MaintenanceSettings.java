package com.inthinc.pro.model.configurator;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public enum MaintenanceSettings implements Serializable {
    ENABLE_MAINTENANCE(10138, "enable_maintenance"),
    MALFUNCTION_INDICATORS(10117, "malfunction_indicators"),
    RED_STOP(10118, "red_stop"),
    AMBER_WARNING(10119, "amber_warning"),
    PROTECT(10120, "protect"),
    BATT_VOLTAGE(10121, "batt_voltage"),
    SET_BATT_VOLTAGE(10122, "set_batt_voltage"),
    ENGINE_TEMP(10123, "engine_temp"),
    SET_ENGINE_TEMP(10124, "set_engine_temp"),
    TRANS_TEMP(10125, "trans_temp"),
    SET_TRANS_TEMP(10126, "set_trans_temp"),
    OIL_PRESSURE(10127, "oil_pressure"),
    SET_OIL_PRESSURE(10128, "set_oil_pressure"),
    DIESEL_EXHAUST(10129, "diesel_exhaust"),
    SET_DIESEL_EXHAUST(10130, "set_diesel_exhaust"),
    DPF_FLOW_RATE(10137, "dpf_flow_rate"),
    ENABLE_DPF_FLOW_RATE(10139, "enable_dpf_flow_rate"),
    MAINT_BY_DIST(10131, "maint_by_dist"),
    MAINT_BY_DIST_INTERVAL(10132, "maint_by_dist_interval"),
    MAINT_BY_DIST_START(10133, "maint_by_dist_start"),
    MAINT_BY_ENGINE_HOURS(10134, "maint_by_engine_hours"),
    MAINT_BY_ENGINE_HOURS_INTERVAL(10135, "maint_by_engine_hours_interval"),
    MAINT_BY_ENGINE_HOURS_START(10136, "maint_by_engine_hours_start")
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
