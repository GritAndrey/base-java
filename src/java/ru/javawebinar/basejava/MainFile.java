package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainFile {
    public static void main(String[] args) {
        File rootDir = new File(".");
        printDirectoryDeeply(rootDir, "");
    }

    public static void printDirectoryDeeply(File dir, String indent) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(indent + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(indent + "- " + file.getName());
                    printDirectoryDeeply(file, " " + indent);
                }
            }
        }
    }

    public static Collection<File> getFiles(File rootDir) {
        Set<File> fileSet = new HashSet<>();
        if (rootDir == null || rootDir.listFiles() == null) {
            return fileSet;
        }
        for (File entry : Objects.requireNonNull(rootDir.listFiles())) {
            if (entry.isFile()) {
                fileSet.add(entry);
            } else {
                fileSet.addAll(getFiles(entry));
            }
        }
        return fileSet;
    }

    private static void filesWalk(String rootDir) throws IOException {
        Files.walk(Paths.get("."))
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .forEach(System.out::println);
    }

    private static void filesFind(String rootDir) throws IOException {
        Files.find(Paths.get("."), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile())
                .map(Path::getFileName)
                .forEach(System.out::println);
    }

}
