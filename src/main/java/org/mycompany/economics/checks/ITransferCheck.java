package org.mycompany.economics.checks;

import org.mycompany.economics.model.IAccount;
import org.mycompany.economics.model.IBalance;
import org.mycompany.economics.model.Operation;

public interface ITransferCheck {

    ResultCheck check(IAccount account, IBalance delta, Operation operation);

}
