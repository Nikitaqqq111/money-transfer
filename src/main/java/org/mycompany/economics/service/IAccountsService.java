package org.mycompany.economics.service;

import org.mycompany.economics.checks.ResultCheck;
import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;

import java.util.List;

public interface IAccountsService {

    ResultCheck createAccountsWithListInput(List<Account> accounts);

    List<Account> getAccounts();

    ResultCheck transferMoney(TransferInfo transferInfo);

}
