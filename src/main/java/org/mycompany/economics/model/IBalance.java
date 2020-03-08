package org.mycompany.economics.model;

import java.math.BigDecimal;
import java.util.Currency;

public interface IBalance {
    Currency getCurrency();

    BigDecimal getNetAmount();

    void updateBalance(IBalance delta, Operation operation);
}
