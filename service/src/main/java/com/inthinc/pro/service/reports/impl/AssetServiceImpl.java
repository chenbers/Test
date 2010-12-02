package com.inthinc.pro.service.reports.impl;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.inthinc.pro.service.reports.AssetService;

/**
 * TODO COmplete Javadoc
 */
@Component
public class AssetServiceImpl implements AssetService {

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlags(java.lang.Integer)
     */
    @Override
    public Response getRedFlags(Integer groupID) {

        return Response.ok().build();
    }

    /**
     * @see com.inthinc.pro.service.reports.AssetService#getRedFlagCount(java.lang.Integer, java.util.Date, java.util.Date, javax.ws.rs.core.PathSegment)
     */
    @Override
    public Response getRedFlags(Integer groupID, Date startDate, Date endDate) {

        return Response.ok().build();
    }

}
