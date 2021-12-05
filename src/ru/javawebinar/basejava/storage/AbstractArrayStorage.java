package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected static final String RESUME_ALREADY_EXIST_AT_POSITION = "Resume already exist at position: ";
    protected static final String ERROR_STORAGE_SIZE_EXCEEDED = "Error: storage size exceeded";
    protected static final String RESUME_NOT_EXIST = "Resume not exist: ";

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        if (r == null || r.getUuid() == null) return;
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println(RESUME_NOT_EXIST + r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    public Resume get(String uuid) {
        if (uuid == null) return null;
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(RESUME_NOT_EXIST + uuid);
            return null;
        }
        return storage[index];
    }

    @Override
    public void save(Resume r) {
        if (r == null || r.getUuid() == null) return;
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println(RESUME_ALREADY_EXIST_AT_POSITION + index + 1);
        } else if (!checkCapacity()) {
            System.out.println(ERROR_STORAGE_SIZE_EXCEEDED);
        } else {
            add(r, index);
            size++;
        }
    }

    public void delete(String uuid) {
        if (uuid == null) return;
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(RESUME_NOT_EXIST + uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return this.size;
    }

    protected boolean checkCapacity() {
        return size < STORAGE_LIMIT;
    }

    protected abstract void add(Resume r, int index);

    protected abstract int getIndex(String uuid);


}
