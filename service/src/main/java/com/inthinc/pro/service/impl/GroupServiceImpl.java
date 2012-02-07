package com.inthinc.pro.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.joda.time.Interval;

import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupScoreWrapper;
import com.inthinc.pro.model.aggregation.GroupTrendWrapper;
import com.inthinc.pro.reports.util.MessageUtil;
import com.inthinc.pro.service.GroupService;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.exceptionMappers.BaseExceptionMapper;
import com.inthinc.pro.service.model.BatchResponse;
import com.inthinc.pro.util.DateUtil;

public class GroupServiceImpl extends AbstractService<Group, GroupDAOAdapter> implements GroupService {
    
	@Override
    public Response getAll() {
        List<Group> list = getDao().getAll();
        return Response.ok(new GenericEntity<List<Group>>(list) {}).build();
    }
	@Override
	public Response update(Group object) {
		Group original = getDao().findByID(object.getGroupID());
		if(!original.getAccountID().equals(object.getAccountID()))
			return Response.status(Status.FORBIDDEN).header(BaseExceptionMapper.HEADER_ERROR_MESSAGE, "Changing the accountID on a group is not allowed").build();
		
		return super.update(object);
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
    		//round about way of getting scores for subgroups that just uses existing hessian methods
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
