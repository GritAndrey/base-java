package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int MAX_CAPACITY = 10000;
    private final Resume[] storage = new Resume[MAX_CAPACITY];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (r == null) return;
        if (getIndex(r.getUuid()) != -1) {
            System.out.println("Resume already exist at position: ");
        } else if (!checkCapacity()) {
            System.out.println("Error: storage size exceeded");
            return;
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        if (uuid == null) return null;
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error: can`t find resume with uuid: " + uuid);
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        if (uuid == null) return;
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error: can`t find resume with uuid: " + uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            size--;
        }
    }

    public void update(Resume r) {
        if (r == null) return;
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            storage[index] = r;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return this.size;
    }


    private boolean checkCapacity() {
        return size < MAX_CAPACITY;
    }

    private int getIndex(String uuid) {
        return IntStream.range(0, size)
                .filter(i -> storage[i].getUuid().equals(uuid))
                .findFirst().orElse(-1);
    }
}
