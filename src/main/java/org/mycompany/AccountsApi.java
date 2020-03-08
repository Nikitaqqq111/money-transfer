package org.mycompany;

import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;
import org.mycompany.service.AccountsApiService;
import org.mycompany.service.AccountsApiServiceImpl;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/accounts")
@Singleton
public class AccountsApi {

    private final AccountsApiService delegate = new AccountsApiServiceImpl();

    @POST
    @Path("/createWithList")
    @Produces({"application/json"})
    public Response createAccountsWithListInput(List<Account> body, @Context SecurityContext securityContext)
            throws NotFoundException {
        return delegate.createAccountsWithListInput(body, securityContext);
    }

    @GET
    @Produces({"application/json"})
    public Response getAccounts(@Context SecurityContext securityContext)
            throws NotFoundException {
        return delegate.getAccounts(securityContext);
    }

    @POST
    @Path("/transfer")
    @Produces({"application/json"})
    public Response transferMoney(TransferInfo body, @Context SecurityContext securityContext)
            throws NotFoundException {
        return delegate.transferMoney(body, securityContext);
    }

}
