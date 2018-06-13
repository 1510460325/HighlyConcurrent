package cn.wzy.maintest;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Random random = new Random();

    public void method() {
        lock.lock();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (random.nextBoolean()){
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("this is:" + Thread.currentThread().getName());
        condition.signalAll();
        lock.unlock();
    }
}
