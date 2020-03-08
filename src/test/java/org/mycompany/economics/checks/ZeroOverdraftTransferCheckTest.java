package org.mycompany.economics.checks;

import org.junit.Before;
import org.junit.Test;
import org.mycompany.economics.model.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ZeroOverdraftTransferCheckTest {

    private ITransferCheck check;
    private IAccount account;
    private IBalance delta;

    @Before
    public void setUp() {
        check = new ZeroOverdraftTransferCheck();
        IBalance accountBalance = new Balance(Currency.getInstance("RUR"), new BigDecimal("10"));
        account = new ZeroOverdraftAccount("1", Currency.getInstance("RUR"), accountBalance);
        delta = new Balance(Currency.getInstance("RUR"), new BigDecimal("100"));
    }

    @Test
    public void check() {
        ResultCheck resultCheck = check.check(account, delta, Operation.DECREASE);
        assertFalse(resultCheck.ok);
        assertFalse(StringUtils.isEmpty(resultCheck.rejectReason));

        resultCheck = check.check(account, delta, Operation.INCREASE);
        assertTrue(resultCheck.ok);
    }
}