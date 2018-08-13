package cn.wzy.regextest;


import java.util.concurrent.CountDownLatch;

/**
 * Create by Wzy
 * on 2018/8/3 9:42
 * 不短不长八字刚好
 */
public class Main {//闭锁的学习

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch start = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程：" + Thread.currentThread().getId() + " ready..");
                        start.await();
                        System.out.println("线程：" + Thread.currentThread().getId() + " complete..");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println("我说开始才开始..  start");
        Thread.sleep(5000);
        System.out.println("start!");
        start.countDown();
    }
}
