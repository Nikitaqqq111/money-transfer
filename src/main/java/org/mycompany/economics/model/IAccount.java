package org.mycompany.economics.model;

import org.mycompany.economics.checks.ResultCheck;

import java.util.Currency;

public interface IAccount extends Comparable<IAccount> {
    String getId();

    Currency getCurrency();

    IBalance getBalance();

    ResultCheck isUpdateBalanceAvailable(IAccount account, IBalance delta, Operation operation);

    ResultCheck updateBalanceSafety(IBalance delta, Operation operation);
}
