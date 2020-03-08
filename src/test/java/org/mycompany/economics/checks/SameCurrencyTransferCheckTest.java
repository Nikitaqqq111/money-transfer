package org.mycompany.economics.checks;

import org.junit.Before;
import org.junit.Test;
import org.mycompany.economics.model.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class SameCurrencyTransferCheckTest {

    private ITransferCheck check;
    private IAccount accountRUR;
    private IAccount accountEUR;
    private IBalance balanceRUR;
    private Operation operation;

    @Before
    public void setUp() throws Exception {
        check = new SameCurrencyTransferCheck();
        accountRUR = new ZeroOverdraftAccount("1", Currency.getInstance("RUR"));
        balanceRUR = new Balance(Currency.getInstance("RUR"), new BigDecimal("1.2"));
        accountEUR = new ZeroOverdraftAccount("2", Currency.getInstance("EUR"));
        operation = Operation.INCREASE;
    }

    @Test
    public void check() {
        ResultCheck resultCheck = check.check(accountRUR, balanceRUR, operation);
        assertTrue(resultCheck.ok);

        resultCheck = check.check(accountEUR, balanceRUR, operation);
        assertFalse(resultCheck.ok);
        assertFalse(StringUtils.isEmpty(resultCheck.rejectReason));
    }
}