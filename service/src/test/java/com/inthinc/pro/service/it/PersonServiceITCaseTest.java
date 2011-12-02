package com.inthinc.pro.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClientExecutor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Person;
@Ignore
public class PersonServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static final Logger logger = Logger.getLogger(PersonServiceITCaseTest.class);
    private static final int PERSON_ID = 30;

    @Before
    public void before() {

        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(/*
                                                                    * TiwiproPrincipal . ADMIN_BACKDOOR_USERNAME
                                                                    */"jhoward", "password");
        httpClient.getState().setCredentials(new AuthScope(getDomain(), getPort(), AuthScope.ANY_REALM), defaultcreds);
        clientExecutor = new ApacheHttpClientExecutor(httpClient);

        client = ProxyFactory.create(ServiceClient.class, "http://localhost:" + getPort(), clientExecutor);
    }

    @Test
    public void getPerson() throws Exception {

        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/person/" + PERSON_ID);
        ClientResponse<Person> response = request.get();
        Person person = response.getEntity(Person.class);

        assertEquals(Response.Status.OK, response.getResponseStatus());
        MultivaluedMap<String, String> map = response.getHeaders();
        List<String> mediaTypes = map.get("Content-Type");
        assertEquals(mediaTypes.get(0), MediaType.APPLICATION_XML);
        assertNotNull(person);
        assertEquals(person.getPersonID(), new Integer(PERSON_ID));
        logger.info("Driver retrieved successfully in the default XML");
    }

    @Test
    public void updatePerson() throws Exception {
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/person/" + PERSON_ID);
        ClientResponse<Person> response = request.get();
        Person person = response.getEntity(Person.class);

        person.setFirst("test");

        request = clientExecutor.createRequest("http://localhost:8080/service/api/person/");

        request.accept("application/xml").body(MediaType.APPLICATION_XML, person);

        ClientResponse<String> responseString = request.put(String.class); // get response and automatically unmarshall to a string.
        System.out.println(responseString);
    }

}
