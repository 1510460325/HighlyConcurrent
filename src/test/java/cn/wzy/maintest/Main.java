package cn.wzy.maintest;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant(10);
        Thread[] hosts = new Thread[50];
        Thread[] guests = new Thread[50];
        for (int i = 0; i < 50; ++i) {
            hosts[i] = new Thread(() -> {
                restaurant.create();
            });
            hosts[i].setName("host(" + i + ")");
            hosts[i].start();
        }
        for (int i = 0; i < 50; ++i) {
            guests[i] = new Thread(() -> {
                restaurant.receive();
            });
            guests[i].setName("guest(" + i + ")");
            guests[i].start();
        }
    }
}
