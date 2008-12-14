package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class RedFlagAlert extends BaseAlert
{
    @Column(updateable = false)
    private static final long serialVersionUID = -1621262257747114161L;

    @ID
    private Integer           redFlagAlertID;
    private RedFlagType       type;
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
// TODO: others

    public Integer getRedFlagAlertID()
    {
        return redFlagAlertID;
    }

    public void setRedFlagAlertID(Integer redFlagAlertID)
    {
        this.redFlagAlertID = redFlagAlertID;
    }

    public RedFlagType getType()
    {
        return type;
    }

    public void setType(RedFlagType type)
    {
        this.type = type;
    }

    public String getSpeedSet()
    {
        return speedSet;
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
            speedSettings = new Integer[speeds.length];
            for (int i = 0; i < speeds.length; i++)
                speedSettings[i] = new Integer(speeds[i]);
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
            if (speedSettings.length != 15)
                throw new IllegalArgumentException("speedSettings.length must be 15");

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

    public boolean isSensitivitiesInverted()
    {
        return sensitivitiesInverted;
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
            return Device.MAX_SENSITIVITY + ((Device.MIN_SENSITIVITY - Device.MAX_SENSITIVITY) - (sensitivity - Device.MAX_SENSITIVITY));
        return null;
    }
}
