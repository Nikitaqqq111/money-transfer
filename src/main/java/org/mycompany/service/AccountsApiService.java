package org.mycompany.service;

import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

public abstract class AccountsApiService {
    public abstract Response createAccountsWithListInput(List<Account> body, SecurityContext securityContext) throws NotFoundException;

    public abstract Response getAccounts(SecurityContext securityContext) throws NotFoundException;

    public abstract Response transferMoney(TransferInfo body, SecurityContext securityContext) throws NotFoundException;
}
