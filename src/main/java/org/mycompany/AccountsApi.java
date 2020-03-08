package org.mycompany;

import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;
import org.mycompany.service.AccountsApiService;
import org.mycompany.service.AccountsApiServiceImpl;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
@Singleton
public class AccountsApi {

    private final AccountsApiService delegate = new AccountsApiServiceImpl();

    @POST
    @Path("/createWithList")
    @Produces({"application/json"})
    public Response createAccountsWithListInput(List<Account> body) {
        return delegate.createAccountsWithListInput(body);
    }

    @GET
    @Produces({"application/json"})
    public Response getAccounts() {
        return delegate.getAccounts();
    }

    @POST
    @Path("/transfer")
    @Produces({"application/json"})
    public Response transferMoney(TransferInfo body) {
        return delegate.transferMoney(body);
    }

}
