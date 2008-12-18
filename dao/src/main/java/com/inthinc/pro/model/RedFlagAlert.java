package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class RedFlagAlert extends BaseAlert
{
    @Column(updateable = false)
    private static final long serialVersionUID = -1621262257747114161L;

    @ID
    private Integer           redFlagAlertID;
    private String            speedSet;
    @Column(updateable = false)
    private Integer[]         speedSettings;
    private RedFlagLevel[]    speedLevels;
    @Column(name = "accel")
    private Integer           hardAcceleration;
    @Column(name = "brake")
    private Integer           hardBrake;
    @Column(name = "turn")
    private Integer           hardTurn;
    @Column(name = "vert")
    private Integer           hardVertical;
    private RedFlagLevel      hardAccelerationLevel;
    private RedFlagLevel      hardBrakeLevel;
    private RedFlagLevel      hardTurnLevel;
    private RedFlagLevel      hardVerticalLevel;
    private RedFlagLevel      seatBeltLevel;

    public Integer getRedFlagAlertID()
    {
        return redFlagAlertID;
    }

    public void setRedFlagAlertID(Integer redFlagAlertID)
    {
        this.redFlagAlertID = redFlagAlertID;
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

    public RedFlagLevel[] getSpeedLevels()
    {
        return speedLevels;
    }

    public void setSpeedLevels(RedFlagLevel[] speedLevels)
    {
        this.speedLevels = speedLevels;
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

    public RedFlagLevel getHardAccelerationLevel()
    {
        return hardAccelerationLevel;
    }

    public void setHardAccelerationLevel(RedFlagLevel hardAccelerationLevel)
    {
        this.hardAccelerationLevel = hardAccelerationLevel;
    }

    public RedFlagLevel getHardBrakeLevel()
    {
        return hardBrakeLevel;
    }

    public void setHardBrakeLevel(RedFlagLevel hardBrakeLevel)
    {
        this.hardBrakeLevel = hardBrakeLevel;
    }

    public RedFlagLevel getHardTurnLevel()
    {
        return hardTurnLevel;
    }

    public void setHardTurnLevel(RedFlagLevel hardTurnLevel)
    {
        this.hardTurnLevel = hardTurnLevel;
    }

    public RedFlagLevel getHardVerticalLevel()
    {
        return hardVerticalLevel;
    }

    public void setHardVerticalLevel(RedFlagLevel hardVerticalLevel)
    {
        this.hardVerticalLevel = hardVerticalLevel;
    }

    public RedFlagLevel getSeatBeltLevel()
    {
        return seatBeltLevel;
    }

    public void setSeatBeltLevel(RedFlagLevel seatBeltLevel)
    {
        this.seatBeltLevel = seatBeltLevel;
    }
}
