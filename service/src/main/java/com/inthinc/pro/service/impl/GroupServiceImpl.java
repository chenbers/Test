package com.inthinc.pro.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.BadRequestException;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;

import com.inthinc.pro.dao.hessian.exceptions.GenericHessianException;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupStatus;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.exceptionMappers.BaseExceptionMapper;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.service.validation.annotations.ValidParams;
import com.inthinc.pro.util.DateUtil;

public class GroupServiceImpl extends AbstractService<Group, GroupDAOAdapter> implements GroupService {

    @Autowired
    PersonService personService;

    @Override
    public Response getAll() {
        List<Group> list = getDao().getAll();
        checkManagersExist(list);
        return Response.ok(new GenericEntity<List<Group>>(list) {}).build();
    }

    private void checkManagersExist(List<Group> list) {
        for (Group group : list) {
            group.setManagerID(adjustForDeletedManager(group.getManagerID()));
        }
    }

    @Override
    @ValidParams
    public Response create(Group object, UriInfo uriInfo) {
        return super.create(object, uriInfo);
    }

    @Override
    @ValidParams
    public Response update(Group object) {
        return super.update(object);
    }

    @Override
    public Response delete(Integer id) {
        if (groupHasActiveAssets(id))
            throw new BadRequestException("Cannot delete a group that contains a subordinate group, driver, or vehicle.");

        return super.delete(id);
    }

    public boolean groupHasActiveAssets(Integer groupID) {
        boolean groupHasActiveChildren = false;
        boolean groupHasActiveDrivers = false;
        boolean groupHasActiveVehicles = false;
        Group original = getDao().findByID(groupID);
        groupHasActiveDrivers = !getDao().getGroupDriverNames(groupID).isEmpty();
        groupHasActiveVehicles = !getDao().getGroupVehicleNames(groupID).isEmpty();

        List<Group> subGroups = getDao().getSubGroups(original.getGroupID());
        if (!subGroups.isEmpty()) {
            for (Group group : subGroups) {
                if (group.getStatus() != GroupStatus.GROUP_DELETED) {
                    groupHasActiveChildren = true;// can stop as soon as true
                    break;
                } else
                    groupHasActiveChildren |= groupHasActiveAssets(group.getGroupID());// don't return false until all have
                                                                                       // been checked
            }
        }
        return groupHasActiveChildren || groupHasActiveDrivers || groupHasActiveVehicles;
    }

    @Override
    public Response getGroupDriverNames(Integer groupID) {
        List<DriverName> list = getDao().getGroupDriverNames(groupID);
        if (list.isEmpty())
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(new GenericEntity<List<DriverName>>(list) {}).build();
    }

    @Override
    public Response getGroupVehicleNames(Integer groupID) {
        List<VehicleName> list = getDao().getGroupVehicleNames(groupID);
        if (list.isEmpty())
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(new GenericEntity<List<VehicleName>>(list) {}).build();
    }

    @Override
    public Response get(Integer id) {
        // TODO Auto-generated method stub
        Response response = super.get(id);
        Group group = (Group) response.getEntity();
        if (group != null) {
            group.setManagerID(adjustForDeletedManager(group.getManagerID()));
        }
        return response;
    }

    private Integer adjustForDeletedManager(Integer managerID) {
        if (managerID == null)
            return managerID;
        Person manager = (Person) personService.get(managerID).getEntity();
        return manager.getStatus().equals(com.inthinc.pro.model.Status.DELETED) ? null : managerID;

    }

    @Override
    public Response getDriverScores(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<DriverVehicleScoreWrapper> list = getDao().getDriverScores(groupID, duration);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<DriverVehicleScoreWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getDriverScoresByMonth(Integer groupID, String month) {

        try {
            Interval interval = DateUtil.getIntervalFromMonth(month);
            List<DriverVehicleScoreWrapper> list = getDao().getDriverScores(groupID, interval);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<DriverVehicleScoreWrapper>>(list) {}).build();

        } catch (ParseException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getDriverScoresByMonth(Integer groupID) {

        return getDriverScoresByMonth(groupID, "");
    }

    @Override
    public Response getSubGroupsDriverScores(Integer groupID, String month) {
        try {
            // round about way of getting scores for subgroups that just uses
            // existing hessian methods
            Interval interval = DateUtil.getIntervalFromMonth(month);

            List<GroupScoreWrapper> list = getDao().getChildGroupsDriverScores(groupID, interval);
            if (!list.isEmpty())
                return Response.ok(new GenericEntity<List<GroupScoreWrapper>>(list) {}).build();

        } catch (ParseException e) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getSubGroupsDriverScores(Integer groupID) {
        return getSubGroupsDriverScores(groupID, "");
    }

    @Override
    public Response getVehicleScores(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<DriverVehicleScoreWrapper> list = getDao().getVehicleScores(groupID, duration);
            if (list != null && !list.isEmpty())
                return Response.ok(new GenericEntity<List<DriverVehicleScoreWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getSubGroupsDriverTrends(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<GroupTrendWrapper> list = getDao().getChildGroupsDriverTrends(groupID, duration);
            if (list != null && !list.isEmpty())
                return Response.ok(new GenericEntity<List<GroupTrendWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response getSubGroupsDriverScores(Integer groupID, Integer numberOfDays) {
        Duration duration = Duration.getDurationByDays(numberOfDays);
        if (duration != null) {
            List<GroupScoreWrapper> list = getDao().getChildGroupsDriverScores(groupID, duration);
            if (list != null && !list.isEmpty())
                return Response.ok(new GenericEntity<List<GroupScoreWrapper>>(list) {}).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Group> list, UriInfo uriInfo) {
        List<BatchResponse> responseList = new ArrayList<BatchResponse>();
        for (Group group : list) {
            BatchResponse batchResponse = new BatchResponse();
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("group");
            Integer id = getDao().create(group);
            if (id == null) {
                batchResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
            } else {
                batchResponse.setStatus(Status.CREATED.getStatusCode());
                batchResponse.setUri(uriBuilder.path(id.toString()).build().toString());
            }
            responseList.add(batchResponse);
        }
        return Response.ok(new GenericEntity<List<BatchResponse>>(responseList) {}).build();
    }
}
