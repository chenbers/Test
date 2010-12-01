package com.inthinc.pro.service.reports.impl;

import java.util.Date;

import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.inthinc.pro.service.reports.AssetService;

/**
 * TODO COmplete Javadoc
 */
@Component
public class AssetServiceImpl implements AssetService {

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer)
     */
    @Override
    public Response getRedFlagCount(Integer groupID) {
        System.out.println(groupID);

        return Response.ok().build();
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer, java.util.Date, java.util.Date, javax.ws.rs.core.PathSegment)
     */
    @Override
    public Response getRedFlagCount(Integer groupID, Date startDate, Date endDate, PathSegment optionalParams) {
        System.out.println(groupID);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(optionalParams);
        System.out.println(optionalParams.getPath());
        System.out.println(optionalParams.getMatrixParameters());

        return Response.ok().build();
    }

}
