package org.mycompany.service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

import org.mycompany.economics.checks.ResultCheck;
import org.mycompany.economics.service.AccountService;
import org.mycompany.economics.service.IAccountsService;
import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;

public class AccountsApiServiceImpl extends AccountsApiService {

    final IAccountsService accountsService = new AccountService();

    @Override
    public Response createAccountsWithListInput(List<Account> accounts, SecurityContext securityContext) throws NotFoundException {
        accountsService.createAccountsWithListInput(accounts);
        return Response.ok().entity("createAccountsWithListInput").build();
    }

    @Override
    public Response getAccounts(SecurityContext securityContext) throws NotFoundException {
        List<Account> accounts = accountsService.getAccounts();
        return Response.ok().entity("getAccounts").build();
    }

    @Override
    public Response transferMoney(TransferInfo transferInfo, SecurityContext securityContext) throws NotFoundException {
        ResultCheck resultCheck = accountsService.transferMoney(transferInfo);
        return Response.ok().entity("transferMoney").build();
    }
}

