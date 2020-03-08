package org.mycompany.economics.storage;

import org.junit.Before;
import org.junit.Test;
import org.mycompany.economics.model.Balance;
import org.mycompany.economics.model.IAccount;
import org.mycompany.economics.model.ZeroOverdraftAccount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

import static org.junit.Assert.*;

public class RuntimeAccountStorageTest {
    private RuntimeAccountStorage runtimeAccountStorage;
    private RuntimeAccountStorage emptyRuntimeStorage;

    @Before
    public void setUp() {
        Balance accountBalance = new Balance(Currency.getInstance("RUR"), new BigDecimal("50"));
        IAccount account = new ZeroOverdraftAccount("1", Currency.getInstance("RUR"), accountBalance);

        Balance accountBalance2 = new Balance(Currency.getInstance("EUR"), new BigDecimal("100"));
        IAccount account2 = new ZeroOverdraftAccount("2", Currency.getInstance("EUR"), accountBalance2);

        Balance accountBalance3 = new Balance(Currency.getInstance("EUR"), new BigDecimal("200"));
        IAccount account3 = new ZeroOverdraftAccount("3", Currency.getInstance("EUR"), accountBalance3);

        Balance accountBalance4 = new Balance(Currency.getInstance("RUR"), new BigDecimal("300"));
        IAccount account4 = new ZeroOverdraftAccount("4", Currency.getInstance("RUR"), accountBalance4);

        runtimeAccountStorage = new RuntimeAccountStorage(Arrays.asList(account, account2, account3, account4));

        emptyRuntimeStorage = new RuntimeAccountStorage();
    }

    @Test
    public void findById() {
        IAccount account = runtimeAccountStorage.findById("2");
        assertEquals(100, account.getBalance().getNetAmount().doubleValue(), 0d);
        account = runtimeAccountStorage.findById("unknown");
        assertNull(account);
    }

    @Test
    public void store() {
        Balance accountBalance5 = new Balance(Currency.getInstance("RUR"), new BigDecimal("600"));
        IAccount account5 = new ZeroOverdraftAccount("5", Currency.getInstance("RUR"), accountBalance5);

        Balance accountBalance6 = new Balance(Currency.getInstance("RUR"), new BigDecimal("700"));
        IAccount account6 = new ZeroOverdraftAccount("6", Currency.getInstance("RUR"), accountBalance6);

        runtimeAccountStorage.store(Arrays.asList(account5, account6));
        assertEquals(6, runtimeAccountStorage.getAll().size());
    }

    @Test
    public void getAll() {
        assertEquals(4, runtimeAccountStorage.getAll().size());
        assertEquals(0, emptyRuntimeStorage.getAll().size());
    }
}