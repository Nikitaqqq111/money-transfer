package org.mycompany.economics.checks;

import org.mycompany.economics.model.IAccount;
import org.mycompany.economics.model.IBalance;
import org.mycompany.economics.model.Operation;

import static org.mycompany.economics.model.Operation.INCREASE;

public class ZeroOverdraftTransferCheck implements ITransferCheck {
    @Override
    public ResultCheck check(IAccount account, IBalance delta, Operation operation) {
        if (operation == INCREASE) {
            return new ResultCheck();
        }
        if (account.getBalance().getNetAmount().compareTo(delta.getNetAmount()) >= 0) {
            return new ResultCheck();
        }
        return new ResultCheck(account.getId() + ": Not enough money");
    }
}
