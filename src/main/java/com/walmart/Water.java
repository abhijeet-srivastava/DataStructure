package com.walmart;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Water {

    private Semaphore mutex;
    private Integer oxygenCount;
    private Integer hydrogenCount;
    CyclicBarrier barrier;
    Semaphore oxyQueue;
    Semaphore hydroQueue;

    public Water() {
        this.mutex = new Semaphore(1);
        this.oxygenCount = 0;
        this.hydrogenCount = 0;
        this.barrier = new CyclicBarrier(3);
        this.oxyQueue = new Semaphore(0);
        this.hydroQueue = new Semaphore(0);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        this.mutex.acquire();
        this.oxygenCount += 1;
        if(this.hydrogenCount >= 2) {
            this.hydroQueue.release(2);
            this.hydrogenCount -= 2;
            this.oxyQueue.release();
            this.oxygenCount -= 1;
        } else {
            this.mutex.release();
        }
        this.oxyQueue.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();

        try {
            this.barrier.await();
        }catch (BrokenBarrierException ex) {
            ex.printStackTrace();
        }
        this.mutex.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        this.mutex.acquire();
        this.hydrogenCount += 1;
        if(this.hydrogenCount  >= 2 && this.oxygenCount >= 1) {
            this.hydroQueue.release(2);
            this.hydrogenCount -= 2;
            this.oxyQueue.release();
            this.oxygenCount -= 1;
        } else {
            this.mutex.release();
        }
        this.hydroQueue.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        try {
            this.barrier.await();
        }catch (BrokenBarrierException ex) {
            ex.printStackTrace();
        }
    }
}
