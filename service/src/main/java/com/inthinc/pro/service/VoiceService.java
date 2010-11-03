package com.inthinc.pro.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;


@Path("/")
@Produces("application/xml")
public interface VoiceService  {

    @GET
    @Path("/vxml")

    public Response get(@Context() HttpServletRequest context, @QueryParam("msgID") Integer msgID, @QueryParam("msg") String msg) throws IOException;
}
