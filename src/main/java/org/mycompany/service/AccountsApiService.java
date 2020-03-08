package org.mycompany.service;

import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;

import javax.ws.rs.core.Response;
import java.util.List;

public abstract class AccountsApiService {
    public abstract Response createAccountsWithListInput(List<Account> body);

    public abstract Response getAccounts();

    public abstract Response transferMoney(TransferInfo body);
}
