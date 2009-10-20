package com.inthinc.pro.service.it;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;
import org.junit.Test;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.BaseTest;
import com.inthinc.pro.service.model.BatchResponse;

public class IntegrationTest extends BaseTest {

    @Test
    public void userTest() throws Exception {
        ClientRequest request = new ClientRequest("http://localhost:8080/service/api/users", clientExecutor);
        ClientResponse<List<User>> response = request.get(new GenericType<List<User>>() {
        });
        List<User> userList = response.getEntity();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void updateAddress() throws Exception {
//        ClientRequest getRequest = new ClientRequest("http://localhost:8080/service/api/address/1031", clientExecutor);
//        ClientResponse<Address> getResponse = getRequest.get(Address.class);
//        Address address = getResponse.getEntity();
//        address.setAddr1("Test Address");

//        ClientRequest updateRequest = new ClientRequest("http://localhost:8080/service/api/address/", clientExecutor);
//        updateRequest.body(MediaType.valueOf("application/xml"), address);
//        ClientResponse updateResponse = updateRequest.put();
//        System.out.println(updateResponse.getStatus());
        

        ClientRequest deleteRequest = new ClientRequest("http://localhost:8080/service/api/address/1031", clientExecutor);
        ClientResponse deleteResponse = deleteRequest.delete();
        System.out.println(deleteResponse.getStatus());

    }

    @Test
    public void createBulkAddress() throws Exception {
        PostMethod postMethod = new PostMethod("http://localhost:8080/service/api/addresses");
        StringRequestEntity rq = new StringRequestEntity(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><collection><address><accountID>1</accountID><addrID>70</addrID><city>South Jordan</city><zip>84095</zip></address></collection>",
                "application/xml", "UTF-8");
        postMethod.setRequestEntity(rq);
        int status = httpClient.executeMethod(postMethod);
        System.out.println("Status: " + status);
        String response = postMethod.getResponseBodyAsString();
        System.out.println(response);
    }

    @Test
    public void createAddress() throws Exception {
        ClientRequest createRequest = new ClientRequest("http://localhost:8080/service/api/addresses", clientExecutor);
        List<Address> addressList = new ArrayList<Address>();
        addressList.add(new Address(null, "address 1", null, "South Jordan", null, "84095", 1));
        addressList.add(new Address(null, "address 2", null, "South Jordan", null, "84095", 1));
        addressList.add(new Address(null, "address 3", null, "South Jordan", null, "84095", 1));
        createRequest.body(MediaType.valueOf("application/xml"), addressList, new GenericType<List<Address>>() {
        });
        ClientResponse<List<BatchResponse>> createResponse = createRequest.post(new GenericType<List<BatchResponse>>() {
        });

        addressList.clear();
        for (BatchResponse br : createResponse.getEntity()) {
            if (br.getStatus().intValue() == Status.CREATED.getStatusCode()) {
                System.out.println("Created: " + br.getUri());
                ClientRequest getRequest = new ClientRequest(br.getUri(), clientExecutor);
                ClientResponse<Address> response = getRequest.get(Address.class);
                addressList.add(response.getEntity());
            }
        }

        for (Address address : addressList) {
            address.setAddr1("3163 W Renegade View Ln");
        }

        ClientRequest updateRequest = new ClientRequest("http://localhost:8080/service/api/addresses/", clientExecutor);
        updateRequest.body(MediaType.valueOf("application/xml"), addressList, new GenericType<List<Address>>() {
        });
        ClientResponse<List<BatchResponse>> updateResponse = createRequest.post(new GenericType<List<BatchResponse>>() {
        });

        for (BatchResponse br : updateResponse.getEntity()) {
            System.out.println("Update Status: " + br.getStatus());
        }

        // System.out.println("\n\n######## Headers ########");
        // for(Map.Entry<String, List<String>> entry: response.getHeaders().entrySet()) {
        // System.out.print("\n" + entry.getKey() + " : ");
        // for(String value : entry.getValue()) {
        // System.out.print(value);
        // }
        // }
        //        
        // System.out.println("\n\n######## Metadata ########");
        // for(Map.Entry<String, List<Object>> entry: response.getMetadata().entrySet()) {
        // System.out.print("\n" + entry.getKey() + " : ");
        // for(Object value : entry.getValue()) {
        // System.out.print(value);
        // }
        // }
    }
}
