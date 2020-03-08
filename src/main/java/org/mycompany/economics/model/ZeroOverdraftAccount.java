package org.mycompany.economics.model;

import org.mycompany.economics.checks.SameCurrencyTransferCheck;
import org.mycompany.economics.checks.ZeroOverdraftTransferCheck;

import java.util.Arrays;
import java.util.Currency;

public class ZeroOverdraftAccount extends Account {

    public ZeroOverdraftAccount(String id, Currency currency) {
        super(id, currency,
                Arrays.asList(new ZeroOverdraftTransferCheck(), new SameCurrencyTransferCheck()));
    }

    public ZeroOverdraftAccount(String id, Currency currency, Balance balance) {
        super(id, currency, balance,
                Arrays.asList(new ZeroOverdraftTransferCheck(), new SameCurrencyTransferCheck()));
    }
}
