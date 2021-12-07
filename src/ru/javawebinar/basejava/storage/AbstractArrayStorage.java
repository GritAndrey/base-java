package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
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

    private boolean checkCapacity() {
        return size < STORAGE_LIMIT;
    }

    @Override
    protected boolean isExist(Object key) {

        return (int) key >= 0;
    }

    @Override
    protected Object getKey(String uuid) {
        return getIndex(uuid);
    }

    @Override
    protected void update(Object key, Resume r) {
        storage[(int) key] = r;
    }

    @Override
    protected void save(Resume r, Object key) {
        if (!checkCapacity()) {
            throw new StorageException(ERROR_STORAGE_SIZE_EXCEEDED, r.getUuid());
        }
        add(r, (int) key);
        size++;

    }

    @Override
    protected void delete(String uuid, Object key) {
        int index = (int) key;
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
    }

    @Override
    protected Resume get(Object key) {
        int index = (int) key;
        return storage[index];
    }

    protected abstract void add(Resume r, int index);

    protected abstract int getIndex(String uuid);
}
