package org.mycompany.economics.core;

import org.junit.Before;
import org.junit.Test;
import org.mycompany.economics.checks.ResultCheck;
import org.mycompany.economics.model.Balance;
import org.mycompany.economics.model.IAccount;
import org.mycompany.economics.model.IBalance;
import org.mycompany.economics.model.ZeroOverdraftAccount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

import static org.junit.Assert.*;

public class MoneyTransferTest {

    private MoneyTransfer moneyTransfer;


    @Before
    public void setUp() {
        moneyTransfer = new MoneyTransfer();
    }

    @Test
    public void transfer() {
        Balance accountBalance = new Balance(Currency.getInstance("RUR"), new BigDecimal("50"));
        IAccount account = new ZeroOverdraftAccount("1", Currency.getInstance("RUR"), accountBalance);

        Balance accountBalance2 = new Balance(Currency.getInstance("EUR"), new BigDecimal("500"));
        IAccount account2 = new ZeroOverdraftAccount("2", Currency.getInstance("EUR"), accountBalance2);

        Balance accountBalance3 = new Balance(Currency.getInstance("EUR"), new BigDecimal("200"));
        IAccount account3 = new ZeroOverdraftAccount("3", Currency.getInstance("EUR"), accountBalance3);

        IBalance deltaRUR = new Balance(Currency.getInstance("RUR"), new BigDecimal("400"));
        IBalance deltaEUR = new Balance(Currency.getInstance("EUR"), new BigDecimal("300"));

        moneyTransfer.init(Arrays.asList(account, account2, account3));

        //cannot transfer money from EUR to RUR accounts1
        ResultCheck resultCheck = moneyTransfer.transfer(account2, account, deltaRUR);
        assertFalse(resultCheck.ok);
        assertEquals(500d, account2.getBalance().getNetAmount().doubleValue(), 0d);
        assertEquals(50d, account.getBalance().getNetAmount().doubleValue(), 0d);
        assertTrue(resultCheck.rejectReason.contains("Currencies are not equal"));

        //cannot transfer money from EUR to EUR using RUR delta
        resultCheck = moneyTransfer.transfer(account2, account3, deltaRUR);
        assertFalse(resultCheck.ok);
        assertEquals(500d, account2.getBalance().getNetAmount().doubleValue(), 0d);
        assertEquals(200d, account3.getBalance().getNetAmount().doubleValue(), 0d);
        assertTrue(resultCheck.rejectReason.contains("Currencies are not equal"));

        //not enough money
        resultCheck = moneyTransfer.transfer(account3, account2, deltaEUR);
        assertFalse(resultCheck.ok);
        assertEquals(200d, account3.getBalance().getNetAmount().doubleValue(), 0d);
        assertEquals(500d, account2.getBalance().getNetAmount().doubleValue(), 0d);
        assertTrue(resultCheck.rejectReason.contains("Not enough money"));

        //successful transfer
        resultCheck = moneyTransfer.transfer(account2, account3, deltaEUR);
        assertTrue(resultCheck.ok);
        assertEquals(200d, account2.getBalance().getNetAmount().doubleValue(), 0d);
        assertEquals(500d, account3.getBalance().getNetAmount().doubleValue(), 0d);

    }
}