package org.mycompany.economics.model;

import org.mycompany.economics.checks.ITransferCheck;
import org.mycompany.economics.checks.ResultCheck;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public abstract class Account implements IAccount {
    private final String id;
    private final Currency currency;
    private final List<ITransferCheck> checks;
    private IBalance balance;

    Account(String id, Currency currency, List<ITransferCheck> checks) {
        this.id = id;
        this.currency = currency;
        this.balance = new Balance(currency, BigDecimal.ZERO);
        this.checks = checks;
    }

    Account(String id, Currency currency, IBalance balance, List<ITransferCheck> checks) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.checks = checks;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public IBalance getBalance() {
        return balance;
    }

    @Override
    public ResultCheck isUpdateBalanceAvailable(IAccount account, IBalance delta, Operation operation) {
        return isUpdateBalanceAvailable(account, delta, checks, operation);
    }

    @Override
    public ResultCheck updateBalanceSafety(IBalance delta, Operation operation) {
        ResultCheck resultCheck = isUpdateBalanceAvailable(this, delta, checks, operation);
        if (resultCheck.ok) {
            balance.updateBalance(delta, operation);
        }
        return resultCheck;
    }

    private ResultCheck isUpdateBalanceAvailable(IAccount account, IBalance delta, List<ITransferCheck> checks, Operation operation) {
        return checks.stream()
                .map(transferCheck -> transferCheck.check(account, delta, operation))
                .filter(resultCheck -> !resultCheck.ok)
                .findFirst().orElse(new ResultCheck());
    }

}
