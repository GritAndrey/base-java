package ru.javawebinar.basejava;

public class MainDeadlock {
    private static final Object RESOURCE = new Object(){
        @Override
        public String toString() {
            return "Resource";
        }
    };
    private static final Object SECOND_RESOURCE = new Object(){
        @Override
        public String toString() {
            return "second Resource";
        }
    };

    public static void main(String[] args) {


        new Thread(() -> makeDeadLock(RESOURCE, SECOND_RESOURCE)).start();

        new Thread(() -> makeDeadLock(SECOND_RESOURCE, RESOURCE)).start();
    }

    private static void makeDeadLock(Object o, Object o1) {
        System.out.println(Thread.currentThread().getName() + " is running");

        synchronized (o) {
            System.out.println("Thread" + Thread.currentThread().getName() + " hold " + o);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": need " + o1);
            synchronized (o1) {
                System.out.println(Thread.currentThread().getName() + " hold ALL");
            }
        }
    }
}
