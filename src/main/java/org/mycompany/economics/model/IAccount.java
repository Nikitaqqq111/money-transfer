package org.mycompany.economics.model;

import org.mycompany.economics.checks.ResultCheck;

import java.util.Currency;

public interface IAccount {
    String getId();

    Currency getCurrency();

    IBalance getBalance();

    ResultCheck updateBalanceSafety(IBalance delta, Operation operation);
}
