package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("1");
        Method toString = r.getClass().getDeclaredMethod("toString");
        toString.setAccessible(true);
        System.out.println(toString.invoke(r));
    }
}
