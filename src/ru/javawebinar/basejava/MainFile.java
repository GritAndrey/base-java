package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File rootDir = new File(".");
        printAllFileNames(rootDir);
    }

    private static void printAllFileNames(File startDir) {
        Objects.requireNonNull(startDir);
        if (!startDir.exists()) {
            return;
        }
        if (startDir.isDirectory()) {
            for (File elem : Objects.requireNonNull(startDir.listFiles())) {
                if (elem.isFile()) {
                    System.out.println(elem);
                } else {
                    printAllFileNames(elem);
                }
            }
        } else {
            System.out.println(startDir);
        }
    }

}
