package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

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
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume updatedResume = ResumeTestData.generateResume(UUID_1, "New Name");
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = ResumeTestData.generateResume("dummy", "dummy");
        storage.update(resume);
    }

    @Test
    public void get() {
        assertEquals(r1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void save() {
        Resume r4 = ResumeTestData.generateResume(UUID_4, "Name4");
        storage.save(r4);
        assertEquals(4, storage.size());
        assertEquals(r4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExists() {
        storage.save(r1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        storage.get(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(3, actualResumes.size());
        assertEquals(Arrays.asList(r1, r2, r3), actualResumes);

    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}