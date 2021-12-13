package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File(".gitignore");
        System.out.println(file.getCanonicalPath());
        File dir = new File("src/ru/javawebinar/basejava");
        System.out.println(dir.getCanonicalPath());
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            Arrays.stream(list).forEach(System.out::println);
        }
    }

}
