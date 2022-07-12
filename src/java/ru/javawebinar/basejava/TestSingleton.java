package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.SectionType;

import java.util.stream.Stream;

public class TestSingleton {
    private static TestSingleton instance;

    private TestSingleton() {
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    public static void main(String[] args) {

        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.name());
        System.out.println(instance.ordinal());
        Stream<SectionType> sectionTypeStream = Stream.of(SectionType.values());
        sectionTypeStream.map(SectionType::getTitle).forEach(System.out::println);
    }

    public enum Singleton {
        INSTANCE
    }
}
