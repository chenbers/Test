package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Device extends BaseEntity
{
    @Column(updateable = false)
    public static final int NUM_SPEEDS = 15;

    @Column(updateable = false)
    private static final long serialVersionUID = 2865030663439253720L;

    // yep, a high number means a low sensitivity; a low number means a high sensitivity
    @Column(updateable = false)
    public static final int   MIN_SENSITIVITY  = 90;
    @Column(updateable = false)
    public static final int   MAX_SENSITIVITY  = 20;

    @ID
    private Integer           deviceID;
    @Column(name = "acctID")
    private Integer           accountID;
    @Column(updateable = false)
    private Integer           vehicleID;
    @Column(name = "baseID")
    private Integer           baselineID;
    private DeviceStatus      status;
    private String            name;
    @Column(name = "mcmid")
    private String            imei;
    private String            sim;
    private String            phone;
    private String            ephone;
    private Date              activated;
    private String            speedSet;
    @Column(updateable = false)
    private Integer[]         speedSettings;
    @Column(updateable = false)
    private boolean           sensitivitiesInverted;
    @Column(name = "accel")
    private Integer           hardAcceleration;
    @Column(name = "brake")
    private Integer           hardBrake;
    @Column(name = "turn")
    private Integer           hardTurn;
    @Column(name = "vert")
    private Integer           hardVertical;

    public Device()
    {
        super();
    }
    public Device(Integer deviceID, Integer accountID, Integer baselineID, DeviceStatus status, String name, String imei, String sim, String phone, String ephone)
    {
        super();
        this.deviceID = deviceID;
        this.accountID = accountID;
        this.baselineID = baselineID;
        this.status = status;
        this.name = name;
        this.imei = imei;
        this.sim = sim;
        this.phone = phone;
        this.ephone = ephone;
    }

    public Integer getDeviceID()
    {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID)
    {
        this.deviceID = deviceID;
    }

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public Integer getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public Integer getBaselineID()
    {
        return baselineID;
    }

    public void setBaselineID(Integer baseID)
    {
        this.baselineID = baseID;
    }

    public DeviceStatus getStatus()
    {
        return status;
    }

    public void setStatus(DeviceStatus status)
    {
        this.status = status;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public String getSim()
    {
        return sim;
    }

    public void setSim(String sim)
    {
        this.sim = sim;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEphone()
    {
        return ephone;
    }

    public void setEphone(String ephone)
    {
        this.ephone = ephone;
    }

    public Date getActivated()
    {
        return activated;
    }

    public void setActivated(Date activated)
    {
        this.activated = activated;
    }

    public String getSpeedSet()
    {
        return speedSet;
    }

    public boolean isSensitivitiesInverted()
    {
        return sensitivitiesInverted;
    }

    public Integer getHardAcceleration()
    {
        return hardAcceleration;
    }

    public void setHardAcceleration(Integer hardAcceleration)
    {
        this.hardAcceleration = hardAcceleration;
    }

    public Integer getHardBrake()
    {
        return hardBrake;
    }

    public void setHardBrake(Integer hardBrake)
    {
        this.hardBrake = hardBrake;
    }

    public Integer getHardTurn()
    {
        return hardTurn;
    }

    public void setHardTurn(Integer hardTurn)
    {
        this.hardTurn = hardTurn;
    }

    public Integer getHardVertical()
    {
        return hardVertical;
    }

    public void setHardVertical(Integer hardVertical)
    {
        this.hardVertical = hardVertical;
    }

    /**
     * Inverts all the sensitivity levels. Useful for the UI, where a low number means a low sensitivity level.
     */
    public void invertSensitivities()
    {
        sensitivitiesInverted = !sensitivitiesInverted;
        hardAcceleration = invertSensitivity(hardAcceleration);
        hardBrake = invertSensitivity(hardBrake);
        hardTurn = invertSensitivity(hardTurn);
        hardVertical = invertSensitivity(hardVertical);
    }

    /**
     * Inverts the given sensitivity level. Null-safe.
     * 
     * @param sensitivity
     *            The sensitivity level to invert.
     * @return The inverted sensitivity level.
     */
    public static Integer invertSensitivity(Integer sensitivity)
    {
        if (sensitivity != null)
            return MAX_SENSITIVITY + ((MIN_SENSITIVITY - MAX_SENSITIVITY) - (sensitivity - MAX_SENSITIVITY));
        return null;
    }

    public void setSpeedSet(String speedSet)
    {
        this.speedSet = speedSet;
        this.speedSettings = null;
    }

    public Integer[] getSpeedSettings()
    {
        if ((speedSettings == null) && (speedSet != null))
        {
            final String[] speeds = speedSet.split(" ");
            speedSettings = new Integer[NUM_SPEEDS];
            for (int i = 0; i < speeds.length; i++)
            {
                try
                {
                    speedSettings[i] = new Integer(speeds[i]);
                }
                catch (NumberFormatException e)
                {
                    speedSettings[i] = 0;
                }
            }
            for (int i = speeds.length; i < NUM_SPEEDS; i++)
            {
                speedSettings[i] = 0;
            }
        }
        return speedSettings;
    }

    public void setSpeedSettings(Integer[] speedSettings)
    {
        this.speedSettings = speedSettings;
        if ((speedSettings == null) || (speedSettings.length == 0))
            this.speedSet = null;
        else
        {
            if (speedSettings.length != NUM_SPEEDS)
                throw new IllegalArgumentException("speedSettings.length must be " + NUM_SPEEDS);

            final StringBuilder sb = new StringBuilder();
            for (final Integer speed : speedSettings)
            {
                if (sb.length() > 0)
                    sb.append(' ');
                sb.append(speed);
            }
            this.speedSet = sb.toString();
        }
    }
    public String getImei()
    {
        return imei;
    }
    public void setImei(String imei)
    {
        this.imei = imei;
    }

}
