package org.mycompany.economics.model;

import java.math.BigDecimal;
import java.util.Currency;

import static org.mycompany.economics.model.Operation.DECREASE;
import static org.mycompany.economics.model.Operation.INCREASE;

public class Balance implements IBalance {

    private final Currency currency;
    private BigDecimal netAmount;

    public Balance(Currency currency, BigDecimal netAmount) {
        this.currency = currency;
        this.netAmount = netAmount;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    @Override
    public void updateBalance(IBalance delta, Operation operation) {
        if (operation == INCREASE) {
            netAmount = netAmount.add(delta.getNetAmount());
            return;
        }
        if (operation == DECREASE) {
            netAmount = netAmount.add(delta.getNetAmount().negate());
        }
    }

}
