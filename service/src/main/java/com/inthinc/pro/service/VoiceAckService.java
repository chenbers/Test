package com.inthinc.pro.service;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/")
@Produces("application/xml")
public interface VoiceAckService {

    @GET
    @Path("/voiceack/{msgID}")

    public Response get(@PathParam("msgID") Integer id) throws IOException;
}
