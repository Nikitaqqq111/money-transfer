package org.mycompany.economics.core;

import org.mycompany.economics.checks.ResultCheck;
import org.mycompany.economics.model.IAccount;
import org.mycompany.economics.model.IBalance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static org.mycompany.economics.model.Operation.DECREASE;
import static org.mycompany.economics.model.Operation.INCREASE;

public class MoneyTransfer {

    private final Map<IAccount, ReentrantLock> locksByAccount = new ConcurrentHashMap<>();

    public void init(List<IAccount> accounts) {
        accounts.forEach(account -> locksByAccount.put(account, new ReentrantLock()));
    }

    public ResultCheck transfer(IAccount from, IAccount to, IBalance delta) {
        final ReentrantLock lockFrom = locksByAccount.get(from);
        final ReentrantLock lockTo = locksByAccount.get(to);
        if (lockFrom == null) {
            return new ResultCheck("Unknown From account: " + from.getId());
        }
        if (lockTo == null) {
            return new ResultCheck("Unknown To account: " + to.getId());
        }

        //avoiding a deadlock
        if (from.getId().compareTo(to.getId()) < 0) {
            try {
                lockFrom.lock();
                try {
                    lockTo.lock();
                    return safeTransferUnderLocks(from, to, delta);
                } finally {
                    lockTo.unlock();
                }
            } finally {
                lockFrom.unlock();
            }
        } else {
            try {
                lockTo.lock();
                try {
                    lockFrom.lock();
                    return safeTransferUnderLocks(from, to, delta);
                } finally {
                    lockFrom.unlock();
                }
            } finally {
                lockTo.unlock();
            }
        }
    }

    private ResultCheck safeTransferUnderLocks(IAccount from, IAccount to, IBalance delta) {
        ResultCheck resultCheck = from.updateBalanceSafety(delta, DECREASE);
        if (resultCheck.ok) {
            //increasing is a safe operation, we don't need some rollback here
            to.updateBalanceSafety(delta, INCREASE);
        }
        return resultCheck;
    }
}
