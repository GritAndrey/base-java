package ru.javawebinar.basejava;

public class DeadLock {
    private static final Object RESOURCE = new Object();
    private static final Object SECOND_RESOURCE = new Object();

    public static void main(String[] args) {


        new Thread(() -> {
            System.out.println("Thread" + Thread.currentThread().getName() + " is running");
            try {
                synchronized (RESOURCE) {
                    System.out.println(Thread.currentThread().getName() + " hold RESOURCE");
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName() + ": Need SECOND_RESOURCE");
                    synchronized (SECOND_RESOURCE) {
                        System.out.println("Thread" + Thread.currentThread().getName() + " hold ALL");
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is running");
            try {
                synchronized (SECOND_RESOURCE) {
                    System.out.println("Thread" + Thread.currentThread().getName() + " hold SECOND_RESOURCE");
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName() + ": Need RESOURCE");
                    synchronized (RESOURCE) {
                        System.out.println(Thread.currentThread().getName() + " hold ALL");
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
