package com.levik.concurrency;

import com.levik.concurrency.model.Account;
import com.levik.concurrency.serivce.Operation;
import com.levik.concurrency.serivce.impl.LockOperation;
import com.levik.concurrency.serivce.impl.Transfer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Operations {

    public static void main(String[] args) {
        final Account firstAccount = new Account("123", 1000);
        final Account secondAccount = new Account("456", 2000);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i <= 10; i++) {
            executorService.submit(new Transfer(firstAccount, secondAccount, 100));
        }

        executorService.shutdown();
    }
}
