package com.levik.concurrency.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private Lock lock;

    private int balance;
    private String id;

    public Account(String id, int balance) {
        this.balance = balance;
        this.id = id;

        lock = new ReentrantLock();
    }

    public void withdraw(int amount){
        balance -= amount;
    }

    public void deposit(int amount){
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance + ", id = " + id +
                '}';
    }
}
