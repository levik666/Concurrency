package com.levik.concurrency.serivce.impl;

import com.levik.concurrency.exception.AccountLockedException;
import com.levik.concurrency.exception.InsufficientFundsException;
import com.levik.concurrency.model.Account;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Transfer implements Callable<Boolean>{

    private static final int WAIT_SEC = 60;

    private Account firstAccount;
    private Account secondAccount;
    private int amount;

    public Transfer(Account firstAccount, Account secondAccount, int amount) {
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.amount = amount;
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("Transfer start from " + firstAccount + " to " + secondAccount + ". Thread name " + Thread.currentThread().getName());
        if (firstAccount.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
            try {
                if (firstAccount.getBalance() < amount) {
                    throw new InsufficientFundsException();
                }

                System.out.println("Looked " + firstAccount + ". Thread name " + Thread.currentThread().getId());
                Thread.sleep(100l);

                if (secondAccount.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Looked " + secondAccount + ". Thread name " + Thread.currentThread().getId());
                        Thread.sleep(100l);
                        firstAccount.withdraw(amount);
                        secondAccount.deposit(amount);
                        System.out.println("Transfer from " + firstAccount + " to " + secondAccount + " completed" + ". Thread name " + Thread.currentThread().getId());
                    } finally {
                        secondAccount.getLock().unlock();
                    }
                } else {
                    System.err.println("Transfer from " + firstAccount + " to " + secondAccount + " failed" + ". Thread name " + Thread.currentThread().getId());
                    return false;
                }
            } finally {
                firstAccount.getLock().unlock();
            }
        } else {
            System.err.println("Transfer from " + firstAccount + " to " + secondAccount + " failed" + ". Thread name " + Thread.currentThread().getId());
            return false;
        }
        return true;
    }
}
