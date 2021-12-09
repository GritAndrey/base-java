package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);

    public static void main(String[] args) {
        List<Resume> collection = new ArrayList<>(Arrays.asList(r1, r2, r3));
        for (Resume r : collection) {
            System.out.println(r);
        }
        for (Resume resume : collection) {
            System.out.println(resume);
/*            if (Objects.equals(resume.getUuid(), UUID_1)) {
                iterator.remove();
            }*/
        }
        System.out.println(collection);

        Map<String, Resume> map = collection.stream().collect(Collectors.toMap(Resume::getUuid, Function.identity()));
        System.out.println(map.get(UUID_2).getClass().getName());
        System.out.println(map);
        List<Resume> resumes = Arrays.asList(r1, r2, r3);
        System.out.println(resumes);
    }
}
