package com.levik.concurrency.exception;

import java.util.concurrent.Executor;

public class AccountLockedException extends Exception {

    public AccountLockedException(String message) {
        super(message);
    }
}
