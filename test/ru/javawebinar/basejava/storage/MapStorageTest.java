package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() {
        List<Resume> actualResumes = Arrays.asList(storage.getAll());
        List<Resume> resumes = new ArrayList<>(Arrays.asList(r1, r2, r3));
        actualResumes.sort(Comparator.comparing(Resume::getUuid));
        resumes.sort(Comparator.comparing(Resume::getUuid));
        Assert.assertEquals(actualResumes, resumes);
        Assert.assertEquals(3, actualResumes.size());
    }
}
