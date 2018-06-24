package cn.wzy.maintest;

import cn.wzy.entity.User;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        Restaurant restaurant = new Restaurant(10);
//        Thread[] hosts = new Thread[50];
//        Thread[] guests = new Thread[50];
//        for (int i = 0; i < 50; ++i) {
//            hosts[i] = new Thread(() -> {
//                restaurant.create();
//            });
//            hosts[i].setName("host(" + i + ")");
//            hosts[i].start();
//        }
//        for (int i = 0; i < 50; ++i) {
//            guests[i] = new Thread(() -> {
//                restaurant.receive();
//            });
//            guests[i].setName("guest(" + i + ")");
//            guests[i].start();
//        }
        Class<User> clazz = User.class;
        User user = new User().setAge(12);
        Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.set(user,123);
        System.out.println(user.getAge());
    }
}
