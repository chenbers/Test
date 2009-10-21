package com.inthinc.pro.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.service.VehicleService;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.SecureVehicleDAO;

public class VehicleServiceImpl implements VehicleService {

    private SecureVehicleDAO vehicleDAO;

    @Override
    public Response getAll() {
        List<Vehicle> list = vehicleDAO.getAll();
        return Response.ok(new GenericEntity<List<Vehicle>>(list) {
        }).build();
    }

    @Override
    public Response get(Integer vehicleID) {
        Vehicle vehicle = vehicleDAO.findByID(vehicleID);
        if (vehicle != null)
            return Response.ok(vehicle).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response findByVIN(String vin) {
        Vehicle vehicle = vehicleDAO.findByVIN(vin);
        if (vehicle != null)
            return Response.ok(vehicle).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(Vehicle vehicle, UriInfo uriInfo) {
        Integer id = vehicleDAO.create(vehicle);
        if (id != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            URI uri = uriBuilder.path(id.toString()).build();
            return Response.created(uri).build();
        }
        return Response.serverError().build();
    }

    @Override
    public Response update(Vehicle vehicle) {
        if (vehicleDAO.update(vehicle).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response delete(Integer vehicleID) {
        if (vehicleDAO.delete(vehicleID).intValue() != 0) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response create(List<Vehicle> vehicles, UriInfo uriInfo) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Vehicle vehicle : vehicles) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("vehicle");
            Integer id = vehicleDAO.create(vehicle);
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
    public Response update(List<Vehicle> vehicles) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Vehicle vehicle : vehicles) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = vehicleDAO.update(vehicle);
            if (changeCount == 0) {
                batchResponse.setStatus(Status.NOT_MODIFIED.getStatusCode());
            } else {
                batchResponse.setStatus(Status.OK.getStatusCode());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }

    @Override
    public Response delete(List<Integer> vehicleIDs) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Integer vehicleID : vehicleIDs) {
            BatchResponse batchResponse = new BatchResponse();
            Integer changeCount = vehicleDAO.delete(vehicleID);
            if (changeCount == 0) {
                batchResponse.setStatus(Status.NOT_MODIFIED.getStatusCode());
            } else {
                batchResponse.setStatus(Status.OK.getStatusCode());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {
        }).build();
    }

    @Override
    public Response getLastLocation(Integer vehicleID) {
        LastLocation location = vehicleDAO.getLastLocation(vehicleID);
        if(location != null)  {
            return Response.ok(location).build();
        }            
        return Response.status(Status.NOT_FOUND).build();
    }

    // TODO do we implement these?
    // vehicleDAO.getLastTrip(driverID);
    // vehicleDAO.getVehiclesNearLoc(groupID, numof, lat, lng);
    // vehicleDAO.getVehiclesInGroup(groupID);

    @Override
    public Response getTrips(Integer vehicleID, String day) {
        List<Trip> list = vehicleDAO.getTrips(vehicleID, day);
        return Response.ok(new GenericEntity<List<Trip>>(list) {
        }).build();
    }

    @Override
    public Response getTrips(Integer vehicleID) {
        List<Trip> list = vehicleDAO.getTrips(vehicleID);
        return Response.ok(new GenericEntity<List<Trip>>(list) {
        }).build();
    }

    // fuel consumption for vehicle (parameter:day)"
    @Override
    public Response getVehicleMPG(Integer id) {
        List<MpgEntity> list = vehicleDAO.getVehicleMPG(id);
        return Response.ok(new GenericEntity<List<MpgEntity>>(list) {
        }).build();
    }

    @Override
    public Response assignDevice(Integer id, Integer deviceID) {
        Vehicle vehicle = vehicleDAO.assignDevice(id, deviceID);
        if (vehicle != null) {
            return Response.ok(vehicle).build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    @Override
    public Response assignDriver(Integer id, Integer driverID) {
        Vehicle vehicle = vehicleDAO.assignDriver(id, driverID);
        if (vehicle != null) {
            return Response.ok(vehicle).build();
        }
        return Response.status(Status.NOT_MODIFIED).build();
    }

    public void setVehicleDAO(SecureVehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public SecureVehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
}
