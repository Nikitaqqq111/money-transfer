package org.mycompany.economics.service;

import org.mycompany.economics.checks.ResultCheck;
import org.mycompany.economics.core.MoneyTransfer;
import org.mycompany.economics.model.Balance;
import org.mycompany.economics.model.IAccount;
import org.mycompany.economics.model.IBalance;
import org.mycompany.economics.model.ZeroOverdraftAccount;
import org.mycompany.economics.storage.RuntimeAccountStorage;
import org.mycompany.model.Account;
import org.mycompany.model.TransferInfo;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccountService implements IAccountsService {

    private final Function<Account, IAccount> accountInConverter = (account) -> {
        Currency accountCurrency = Currency.getInstance(account.getCur());
        IBalance accountBalance = new Balance(accountCurrency, new BigDecimal(account.getBalance()));
        return new ZeroOverdraftAccount(account.getId(), accountCurrency, accountBalance);
    };

    private final Function<IAccount, Account> accountOutConverter = (account) -> {
        Account outAccount = new Account();
        outAccount.id(account.getId());
        outAccount.cur(account.getCurrency().getCurrencyCode());
        outAccount.balance(account.getBalance().getNetAmount().toPlainString());
        return outAccount;
    };

    private final RuntimeAccountStorage storage = new RuntimeAccountStorage();
    private final MoneyTransfer moneyTransfer = new MoneyTransfer();

    @Override
    public ResultCheck createAccountsWithListInput(List<Account> notConvertedAccounts) {
        List<IAccount> accounts = notConvertedAccounts.stream().map(accountInConverter).collect(Collectors.toList());
        moneyTransfer.init(accounts);
        storage.store(accounts);
        return new ResultCheck();
    }

    @Override
    public List<Account> getAccounts() {
        return storage.getAll().stream().map(accountOutConverter).collect(Collectors.toList());
    }

    @Override
    public ResultCheck transferMoney(TransferInfo transferInfo) {
        IAccount from = storage.findById(transferInfo.getFrom());
        if (from == null) {
            return new ResultCheck("Unknown account: " + transferInfo.getFrom());
        }
        IAccount to = storage.findById(transferInfo.getTo());
        if (to == null) {
            return new ResultCheck("Unknown account: " + transferInfo.getTo());
        }
        return moneyTransfer.transfer(from, to, new Balance(Currency.getInstance(transferInfo.getCur()), new BigDecimal(transferInfo.getAmount())));
    }
}
