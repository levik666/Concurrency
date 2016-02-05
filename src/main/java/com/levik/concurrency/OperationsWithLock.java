package com.levik.concurrency;

import com.levik.concurrency.model.Account;
import com.levik.concurrency.serivce.Operation;
import com.levik.concurrency.serivce.impl.LockOperation;

public class OperationsWithLock {

    public static void main(String[] args) {
        final Account firstAccount = new Account("123", 1000);
        final Account secondAccount = new Account("456", 2000);

        final Operation operation = new LockOperation();

        new Thread(new Runnable() {
            public void run() {
                try {
                    operation.transfer(firstAccount, secondAccount, 500);
                } catch (Exception exe) {
                    System.err.println(exe.getMessage());
                }
            }
        }).start();

        try {
            operation.transfer(secondAccount, firstAccount, 500);
            //transfer(firstAccount, secondAccount, 500);
        } catch (Exception exe) {
            System.err.println(exe.getMessage());
        }
    }

}
