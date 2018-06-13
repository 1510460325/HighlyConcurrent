package cn.wzy.maintest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {


    private Semaphore hosts = new Semaphore(10);
    private Semaphore guests = new Semaphore(10);
    private Lock lock = new ReentrantLock();
    private Condition hostCondition = lock.newCondition();
    private Condition guestCondition = lock.newCondition();
    private Object[] cups;
    private int size;

    public Restaurant(int size) {
        this.size = size;
        cups = new Object[size];
        for (int i = 0; i < size; ++i)
            cups[i] = "a cup of water.";
    }

    private boolean IsFull() {
        for (int i = 0; i < size; ++i) {
            if (cups[i] == null) {
                return false;
            }
        }
        return true;
    }

    private boolean IsEmpty() {
        for (int i = 0; i < size; ++i) {
            if (cups[i] != null) {
                return false;
            }
        }
        return true;
    }

    public void create() {
        try {
            hosts.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        while (IsFull()) {
            System.out.println(Thread.currentThread().getName() + "wait......");
            try {
                hostCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < size; ++i) {
            if (cups[i] == null) {
                cups[i] = "a cup of water.";
                System.out.println(Thread.currentThread().getName() + "create one");
                break;
            }
        }
        guestCondition.signalAll();
        lock.unlock();
        hosts.release();
    }

    public void receive() {
        try {
            guests.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        while (IsEmpty()) {
            System.out.println(Thread.currentThread().getName() + "wait......");
            try {
                guestCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < size; ++i) {
            if (cups[i] != null) {
                cups[i] = null;
                System.out.println(Thread.currentThread().getName() + "receive one");
                break;
            }
        }
        hostCondition.signalAll();
        lock.unlock();
        guests.release();
    }
}
