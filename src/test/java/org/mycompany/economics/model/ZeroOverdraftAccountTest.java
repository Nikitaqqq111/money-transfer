package org.mycompany.economics.model;

import org.junit.Before;
import org.junit.Test;
import org.mycompany.economics.checks.ResultCheck;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ZeroOverdraftAccountTest {

    private ZeroOverdraftAccount zeroOverdraftAccount;
    private Balance delta;

    @Before
    public void setUp() {
        Balance accountBalance = new Balance(Currency.getInstance("RUR"), new BigDecimal("50"));
        delta = new Balance(Currency.getInstance("RUR"), new BigDecimal("100"));
        zeroOverdraftAccount = new ZeroOverdraftAccount("1", Currency.getInstance("RUR"), accountBalance);
    }

    @Test
    public void updateBalanceSafety() {
        ResultCheck check = zeroOverdraftAccount.updateBalanceSafety(delta, Operation.DECREASE);
        assertFalse(check.ok);
        assertEquals(50d, zeroOverdraftAccount.getBalance().getNetAmount().doubleValue(), 0d);

        check = zeroOverdraftAccount.updateBalanceSafety(delta, Operation.INCREASE);
        assertTrue(check.ok);
        assertEquals(150d, zeroOverdraftAccount.getBalance().getNetAmount().doubleValue(), 0d);
    }

}