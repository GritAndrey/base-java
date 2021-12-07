package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.stream.IntStream;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            int limit = AbstractArrayStorage.STORAGE_LIMIT;
            IntStream.range(storage.size(), limit)
                    .forEach(i -> storage.save(new Resume(String.valueOf(i))));
        } catch (StorageException e) {
            Assert.fail("Incorrect storage limit");
        }
        storage.save(new Resume("final"));
    }
}