package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    private static final String ERROR_STORAGE_SIZE_EXCEEDED = "Error: storage size exceeded";
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return this.size;
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected Integer getKey(String uuid) {
        return getIndex(uuid);
    }

    @Override
    protected void makeUpdate(Integer key, Resume r) {
        storage[key] = r;
    }

    @Override
    protected void makeSave(Integer key, Resume r) {
        if (!checkCapacity()) {
            throw new StorageException(ERROR_STORAGE_SIZE_EXCEEDED, r.getUuid());
        }
        add(r, key);
        size++;
    }

    private boolean checkCapacity() {
        return size < STORAGE_LIMIT;
    }

    @Override
    protected void makeDelete(Integer key) {
        int index = key;
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
    }

    @Override
    protected Resume makeGet(Integer key) {
        return storage[key];
    }

    protected abstract void add(Resume r, int index);

    protected abstract int getIndex(String uuid);

    @Override
    protected Stream<Resume> getStorageStream() {
        return Arrays.stream(Arrays.copyOf(storage, size));
    }
}
