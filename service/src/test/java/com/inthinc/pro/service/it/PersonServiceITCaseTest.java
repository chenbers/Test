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
import com.inthinc.pro.model.Vehicle;
@Ignore
public class PersonServiceITCaseTest extends BaseEmbeddedServerITCase {
    private static final Logger logger = Logger.getLogger(PersonServiceITCaseTest.class);
    private static final int PERSON_ID = 30;
    private static final int UPDATE_PERSON_ID=16491;

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
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/person/" + UPDATE_PERSON_ID);
        ClientResponse<Person> response = request.get();
        Person person = response.getEntity(Person.class);
        System.out.println(person.toString());
        System.out.println(person.getDriver().toString());
        System.out.println(person.getUser().toString());
       
//        person.setFirst("test");

        Person updatePerson = new Person();
        updatePerson.setFirst("test");
        updatePerson.setDriver(person.getDriver());

        updatePerson.setPersonID(UPDATE_PERSON_ID);
        request = clientExecutor.createRequest("http://localhost:8080/service/api/person/");

        request.accept("application/xml").body(MediaType.APPLICATION_XML, updatePerson);

        ClientResponse<String> responseString = request.put(String.class); // get response and automatically unmarshall to a string.
        System.out.println(responseString.getEntity().toString());
    }
    @Test 
    public void updatePersonDriverTest() throws Exception{
        ClientRequest request = clientExecutor.createRequest("http://localhost:8080/service/api/person/");
        //removed <dot>US_OIL</dot> from person
        String xmlText = 
        "<person><acctID>1</acctID><addressID>3989</addressID><crit>0</crit><driver><certifications></certifications><driverID>10936</driverID><expiration>1969-12-31T17:00:00-07:00</expiration><groupID>2</groupID><personID>16491</personID><status>ACTIVE</status></driver><empid>ROMANIAN</empid><first>test</first><fuelEfficiencyType>KMPL</fuelEfficiencyType><height>0</height><info>0</info><last>Jennings</last><locale>ro</locale><measurementType>METRIC</measurementType><middle></middle><personID>16491</personID><priEmail>romanian@test.com</priEmail><priPhone></priPhone><priText></priText><reportsTo></reportsTo><secEmail></secEmail><secPhone></secPhone><secText></secText><status>ACTIVE</status><suffix></suffix><timeZone>US/Mountain</timeZone><title></title><warn>0</warn><weight>0</weight></person>";

        request.accept("application/xml").body( MediaType.APPLICATION_XML, xmlText);

        ClientResponse<Person> updatedPerson = request.put(Person.class); //get response.
        Person  person = updatedPerson.getEntity();
        System.out.println(person.toString());
  }

}
