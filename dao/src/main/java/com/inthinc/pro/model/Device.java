package com.inthinc.pro.model;

import java.util.Date;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Device extends BaseEntity
{
    @Column(updateable = false)
    private static final long serialVersionUID = 2865030663439253720L;

    @Column(updateable = false)
    public static final int NUM_SPEEDS = 15;

    @Column(updateable = false)
    public static final Integer DEFAULT_SPEED_SETTING = 0;
    
    @Column(updateable = false)
    public static final String DEFAULT_SPEED_SET = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";


    // yep, a high number means a low sensitivity; a low number means a high sensitivity
//    @Column(updateable = false)
//    public static final int   MIN_SENSITIVITY  = 90;
//    @Column(updateable = false)
//    public static final int   MAX_SENSITIVITY  = 20;

    @ID
    private Integer           deviceID;
    @Column(name = "acctID")
    private Integer           accountID;
    @Column(updateable = false)
    private Integer           vehicleID;
    private DeviceStatus      status;
    private String            name;
    @Column(name = "mcmid")
    private String            imei;
    private String            sim;
    private String			  serialNum;
    private String            phone;
    private String            ephone;
    private Date              activated;
    private String            speedSet;
    @Column(updateable = false)
    private Integer[]         speedSettings;
//    @Column(updateable = false)
//    private boolean           sensitivitiesInverted;
    
    private String  accel;
    private String  brake;
    private String  turn;
    private String  vert;
    
    @Column(updateable = false)
    private Integer           hardAcceleration;
    @Column(updateable = false)
    private Integer           hardBrake;
    @Column(updateable = false)
    private Integer           hardTurn;
    @Column(updateable = false)
    private Integer           hardVertical;
    private Integer           baseID;

    public Device()
    {
        super();
    }
    public Device(Integer deviceID, Integer accountID, DeviceStatus status, String name, String imei, String sim, String serialNum, String phone, String ephone)
    {
        super();
        this.deviceID = deviceID;
        this.accountID = accountID;
        this.status = status;
        this.name = name;
        this.imei = imei;
        this.sim = sim;
        this.serialNum = serialNum;
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
        if (speedSet == null)
        {
            speedSet = new String(DEFAULT_SPEED_SET);
        }
        return speedSet;
    }
/*
    public boolean isSensitivitiesInverted()
    {
        return sensitivitiesInverted;
    }
*/
    public Integer getHardAcceleration()
    {
        if (hardAcceleration == null)
            return SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting();
        return hardAcceleration;
    }

    public void setHardAcceleration(Integer hardAcceleration)
    {
        this.hardAcceleration = hardAcceleration;
    }

    public Integer getHardBrake()
    {
        if (hardBrake == null)
            return SensitivityType.HARD_BRAKE_SETTING.getDefaultSetting();
        return hardBrake;
    }

    public void setHardBrake(Integer hardBrake)
    {
        this.hardBrake = hardBrake;
    }

    public Integer getHardTurn()
    {
        if (hardTurn == null)
            return SensitivityType.HARD_TURN_SETTING.getDefaultSetting();
        return hardTurn;
    }

    public void setHardTurn(Integer hardTurn)
    {
        this.hardTurn = hardTurn;
    }

    public Integer getHardVertical()
    {
        if (hardVertical == null)
            return SensitivityType.HARD_VERT_SETTING.getDefaultSetting();
        return hardVertical;
    }

    public void setHardVertical(Integer hardVertical)
    {
        this.hardVertical = hardVertical;
    }

    /**
     * Inverts all the sensitivity levels. Useful for the UI, where a low number means a low sensitivity level.
     */
/*    
    public void invertSensitivities()
    {
        sensitivitiesInverted = !sensitivitiesInverted;
        hardAcceleration = invertSensitivity(hardAcceleration);
        hardBrake = invertSensitivity(hardBrake);
        hardTurn = invertSensitivity(hardTurn);
        hardVertical = invertSensitivity(hardVertical);
    }
*/
    /**
     * Inverts the given sensitivity level. Null-safe.
     * 
     * @param sensitivity
     *            The sensitivity level to invert.
     * @return The inverted sensitivity level.
     */
/*    
    public static Integer invertSensitivity(Integer sensitivity)
    {
        if (sensitivity != null)
            return MAX_SENSITIVITY + ((MIN_SENSITIVITY - MAX_SENSITIVITY) - (sensitivity - MAX_SENSITIVITY));
        return null;
    }
*/
    public void setSpeedSet(String speedSet)
    {
        this.speedSet = speedSet;
    }

    public Integer[] getSpeedSettings()
    {
        if (speedSettings == null)
        {
            speedSettings = new Integer[NUM_SPEEDS];
            for (int i = 0; i < NUM_SPEEDS; i++)
                speedSettings[i] = DEFAULT_SPEED_SETTING; 
                
        }
        return speedSettings;
    }

    public void setSpeedSettings(Integer[] speedSettings)
    {
        this.speedSettings = speedSettings;
    }
    public String getImei()
    {
        return imei;
    }
    public void setImei(String imei)
    {
        this.imei = imei;
    }
    public String getSerialNum()
    {
        return serialNum;
    }
    public void setSerialNum(String serialNum)
    {
        this.serialNum = serialNum;
    }
    public Integer getBaseID()
    {
        return baseID;
    }
    public void setBaseID(Integer baseID)
    {
        this.baseID = baseID;
    }
    public String getAccel()
    {
        return accel;
    }
    public void setAccel(String accel)
    {
        this.accel = accel;
    }
    public String getBrake()
    {
        return brake;
    }
    public void setBrake(String brake)
    {
        this.brake = brake;
    }
    public String getTurn()
    {
        return turn;
    }
    public void setTurn(String turn)
    {
        this.turn = turn;
    }
    public String getVert()
    {
        return vert;
    }
    public void setVert(String vert)
    {
        this.vert = vert;
    }

}
