package ru.javawebinar.basejava.util;

public class MainConcurrency {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
            }
        }.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
    }
}
