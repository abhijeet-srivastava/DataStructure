package com.oracle.casb.leetcode;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private int n;
    private AtomicInteger integer = new AtomicInteger(1);
    Lock writeLock = new ReentrantLock();
    Condition condition = writeLock.newCondition();
    private AtomicBoolean writeZero = new AtomicBoolean(true);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    private boolean containsNonNumeric(char[] arr) {
        boolean res = false;
        for(char ch : arr) {
            if(!(Character.isDigit(ch) || ch == 'e' || ch == 'E')) {
                res = true;
                break;
            }
        }
        return res;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (integer.get() <= n) {
            this.writeLock.lock();
            try {
                if (writeZero.get()) {
                    printNumber.accept(0);
                    writeZero.set(false);
                    condition.signalAll();
                } else {
                    condition.await();
                }
            } finally {
                this.writeLock.unlock();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (integer.get() <= n) {
            this.writeLock.lock();
            try {
                int i = integer.get();
                if (!writeZero.get()  && (i % 2 == 0)) {
                    printNumber.accept(i);
                    integer.getAndIncrement();
                    writeZero.set(true);
                    condition.signalAll();
                } else {
                    condition.await();
                }
            } finally {
                this.writeLock.unlock();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (integer.get() <= n) {
            this.writeLock.lock();
            try {
                int i = integer.get();
                if (!writeZero.get()  && (i % 2 == 1)) {
                    printNumber.accept(i);
                    integer.getAndIncrement();
                    writeZero.set(true);
                    condition.signalAll();
                } else {
                    condition.await();
                }
            } finally {
                this.writeLock.unlock();
            }
        }
    }
}