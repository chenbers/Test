package com.inthinc.pro.model;

public class DeviceReportItem extends BaseEntity
{
    private Device device;
    private Vehicle vehicle;

    public Device getDevice()
    {
        return device;
    }

    public void setDevice(Device device)
    {
        this.device = device;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
}
