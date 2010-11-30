package com.inthinc.pro.service.impl;

import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Device;
import com.inthinc.pro.service.DeviceService;
import com.inthinc.pro.service.adapters.DeviceDAOAdapter;

public class DeviceServiceImpl extends AbstractService<Device, DeviceDAOAdapter> implements DeviceService {

    @Override
    public Response getAll() {
        List<Device> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Device>>(list) {
        }).build();
    }
    public Response findByIMEI(String imei) {
        Device device = getDao().findByIMEI(imei);
        if (device != null)
            return Response.ok(device).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    public Response findBySerialNum(String serialNum) {
        Device device = getDao().findBySerialNum(serialNum);
        if (device != null)
            return Response.ok(device).build();
        return Response.status(Status.NOT_FOUND).build();
    }
    @Override
    public Response create(List<Device> list, UriInfo uriInfo) {
        return Response.status(501).build();
    }
    @Override
    public Response update(List<Device> list) {
        return Response.status(501).build();
    }
    @Override
    public Response delete(List<Integer> list) {
        return Response.status(501).build();
    }

}
