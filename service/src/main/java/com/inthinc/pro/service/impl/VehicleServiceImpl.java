package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.VehicleService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecureVehicleDAO;

public class VehicleServiceImpl extends AbstractService<Vehicle, SecureVehicleDAO> implements VehicleService {

    @Override
    public Response getAll() {
        List<Vehicle> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Vehicle>>(list) {
        }).build();
    }

    @Override
    public Response findByVIN(String vin) {
        Vehicle vehicle = getDao().findByVIN(vin);
        if (vehicle != null)
            return Response.ok(vehicle).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Vehicle> vehicles) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Vehicle vehicle : vehicles) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("vehicle");
            Integer id = getDao().create(vehicle);
            if (id == null) {
                batchResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
            } else {
                batchResponse.setStatus(Status.CREATED.getStatusCode());
                batchResponse.setUri(uriBuilder.path(id.toString()).build().toString());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }

    @Override
    public Response getLastLocation(Integer vehicleID) {
        LastLocation location = getDao().getLastLocation(vehicleID);
        if (location != null) {
            return Response.ok(location).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    // TODO do we implement these?
    // getDao().getLastTrip(driverID);
    // getDao().getVehiclesNearLoc(groupID, numof, lat, lng);
    // getDao().getVehiclesInGroup(groupID);

    @Override
    public Response getTrips(Integer vehicleID, String day) {
        List<Trip> list = getDao().getTrips(vehicleID, day);
        return Response.ok(new GenericEntity<List<Trip>>(list) {
        }).build();
    }

    @Override
    public Response getTrips(Integer vehicleID) {
        List<Trip> list = getDao().getTrips(vehicleID);
        return Response.ok(new GenericEntity<List<Trip>>(list) {
        }).build();
    }

    // fuel consumption for vehicle (parameter:day)"
    @Override
    public Response getVehicleMPG(Integer id) {
        List<MpgEntity> list = getDao().getVehicleMPG(id);
        return Response.ok(new GenericEntity<List<MpgEntity>>(list) {
        }).build();
    }

    @Override
    public Response assignDevice(Integer id, Integer deviceID) {
        Vehicle vehicle = getDao().assignDevice(id, deviceID);
        if (vehicle != null) {
            return Response.ok(vehicle).build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response assignDriver(Integer id, Integer driverID) {
        Vehicle vehicle = getDao().assignDriver(id, driverID);
        if (vehicle != null) {
            return Response.ok(vehicle).build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }
}
