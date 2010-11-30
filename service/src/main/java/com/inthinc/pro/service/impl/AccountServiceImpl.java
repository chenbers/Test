package com.inthinc.pro.service.impl;

import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.service.AccountService;
import com.inthinc.pro.service.adapters.AccountDAOAdapter;

public class AccountServiceImpl extends AbstractService<Account, AccountDAOAdapter> implements AccountService {

    public Response get() {
        return get(getDao().getAccountID());
    }

    @Override
    public Response getAll() {
        List<Account> list = getDao().getAll();
        if (list != null)
            return Response.ok(new GenericEntity<List<Account>>(list) {}).build();
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(List<Account> list, UriInfo uriInfo) {
        return Response.status(501).build();
    }

    @Override
    public Response delete(List<Integer> list) {
        return Response.status(501).build();
    }

    @Override
    public Response update(List<Account> list) {
        return Response.status(501).build();
    }

}
