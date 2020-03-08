package org.mycompany.economics.checks;

import org.mycompany.economics.model.IAccount;
import org.mycompany.economics.model.IBalance;
import org.mycompany.economics.model.Operation;

public class SameCurrencyTransferCheck implements ITransferCheck {
    @Override
    public ResultCheck check(IAccount account, IBalance delta, Operation operation) {
        if (account.getCurrency().getCurrencyCode()
                .equals(delta.getCurrency().getCurrencyCode())) {
            return new ResultCheck();
        } else {
            return new ResultCheck(account.getId() + ": Currencies are not equal");
        }
    }
}
