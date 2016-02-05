package com.levik.concurrency.serivce;

import com.levik.concurrency.exception.AccountLockedException;
import com.levik.concurrency.exception.InsufficientFundsException;
import com.levik.concurrency.model.Account;

public interface Operation {

    void transfer(Account firstAccount, Account secondAccount, int amount) throws InsufficientFundsException, InterruptedException, AccountLockedException;
}
