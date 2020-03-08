package org.mycompany.service;

import javax.ws.rs.core.Response;
import java.util.List;

import org.mycompany.economics.checks.ResultCheck;
import org.mycompany.economics.service.AccountService;
import org.mycompany.economics.service.IAccountsService;
import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;

public class AccountsApiServiceImpl extends AccountsApiService {

    private final IAccountsService accountsService = new AccountService();

    @Override
    public Response createAccountsWithListInput(List<Account> accounts) {
        accountsService.createAccountsWithListInput(accounts);
        return Response.ok().build();
    }

    @Override
    public Response getAccounts() {
        List<Account> accounts = accountsService.getAccounts();
        return Response.ok().entity(accounts).build();
    }

    @Override
    public Response transferMoney(TransferInfo transferInfo) {
        ResultCheck resultCheck = accountsService.transferMoney(transferInfo);
        if (resultCheck.ok) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), resultCheck.rejectReason).build();
        }

    }
}

