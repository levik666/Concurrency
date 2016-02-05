package com.levik.concurrency.serivce.impl;

import com.levik.concurrency.exception.AccountLockedException;
import com.levik.concurrency.exception.InsufficientFundsException;
import com.levik.concurrency.model.Account;
import com.levik.concurrency.serivce.Operation;

import java.util.concurrent.TimeUnit;

public class LockOperation implements Operation{

    private static final int WAIT_SEC = 60;
    private static final long SLEEP = 100l;

    @Override
    public void transfer(Account firstAccount, Account secondAccount, int amount) throws InsufficientFundsException, InterruptedException, AccountLockedException {
        System.out.println("Transfer start from " + firstAccount + " to " + secondAccount + ". Thread name " + Thread.currentThread().getName());
        if (firstAccount.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
            try {
                if (firstAccount.getBalance() < amount) {
                    throw new InsufficientFundsException();
                }

                System.out.println("Looked " + firstAccount + ". Thread name " + Thread.currentThread().getId());
                Thread.sleep(SLEEP);

                if (secondAccount.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Looked " + secondAccount + ". Thread name " + Thread.currentThread().getId());
                        Thread.sleep(SLEEP);
                        firstAccount.withdraw(amount);
                        secondAccount.deposit(amount);
                        System.out.println("Transfer from " + firstAccount + " to " + secondAccount + " completed" + ". Thread name " + Thread.currentThread().getId());
                    } finally {
                        secondAccount.getLock().unlock();
                    }
                } else {
                    System.err.println("Transfer from " + firstAccount + " to " + secondAccount + " failed" + ". Thread name " + Thread.currentThread().getId());
                    throw new AccountLockedException("Can't make transfer due to " + secondAccount + " was locked.");
                }
            } finally {
                firstAccount.getLock().unlock();
            }
        } else {
            System.err.println("Transfer from " + firstAccount + " to " + secondAccount + " failed" + ". Thread name " + Thread.currentThread().getId());
            throw new AccountLockedException("Can't make transfer due to " + firstAccount + " was locked.");
        }
    }
}
