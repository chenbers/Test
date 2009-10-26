package com.inthinc.pro.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.service.AccountService;
import com.inthinc.pro.util.SecureAccountDAO;

public class AccountServiceImpl extends AbstractService<Account, SecureAccountDAO> implements AccountService {

    public Response get() {
        return get(getDao().getAccountID());
    }

    @Override
    public Response getAll() {
        return Response.status(501).build();
    }

    @Override
    public Response create(Account object, UriInfo uriInfo) {
        return Response.status(501).build();
    }

    @Override
    public Response create(List<Account> list, UriInfo uriInfo) {
        return Response.status(501).build();
    }

    @Override
    public Response delete(Integer id) {
        return Response.status(501).build();
    }

    @Override
    public Response delete(List<Integer> list) {
        return Response.status(501).build();
    }

    @Override
    public Response update(Account object) {
        return Response.status(501).build();
    }

    @Override
    public Response update(List<Account> list) {
        return Response.status(501).build();
    }


}
