package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {

    protected final Storage storage;

    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String UUID_4 = "uuid4";
    static final String DUMMY = "dummy";
    static final Resume r1 = ResumeTestData.generateResume(UUID_1, "Name1");
    static final Resume r2 = ResumeTestData.generateResume(UUID_2, "Name2");
    static final Resume r3 = ResumeTestData.generateResume(UUID_3, "Name3");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume updatedResume = ResumeTestData.generateResume(UUID_1, "New Name");
        storage.update(updatedResume);
        Assert.assertSame(updatedResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = ResumeTestData.generateResume("dummy", "dummy");
        storage.update(resume);
    }

    @Test
    public void get() {
        Assert.assertEquals(r1, storage.get(r1.getUuid()));
        Assert.assertEquals(r2, storage.get(r2.getUuid()));
        Assert.assertEquals(r3, storage.get(r3.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void save() {
        Resume r4 = ResumeTestData.generateResume(UUID_4, "Name4");
        storage.save(r4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(r4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExists() {
        storage.save(r1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        storage.get(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        Assert.assertEquals(3, actualResumes.size());
        Assert.assertEquals(Arrays.asList(r1, r2, r3), actualResumes);

    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}