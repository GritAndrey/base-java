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
        if (!checkCapacity()) {
            System.out.println("Error: storage size exceeded");
            return;
        }
        int index = findIndex(r.getUuid());
        boolean notPresent = index == -1;
        if (notPresent) {
            storage[size++] = r;
        } else {
            System.out.println("This resume already present at position: " + (index + 1));
        }
    }

    public Resume get(String uuid) {
        if (uuid == null) return null;
        int index = findIndex(uuid);
        boolean isPresent = index != -1;
        if (isPresent) {
            return storage[index];
        }
        System.out.println("Error: can`t find resume with uuid: " + uuid);
        return null;
    }

    public void delete(String uuid) {
        if (uuid == null) return;
        int index = findIndex(uuid);
        boolean isPresent = index != -1;
        if (isPresent) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            size--;
        } else
            System.out.println("Error: can`t find resume with uuid: " + uuid);
    }

    public void update(Resume r) {
        if (r == null) return;
        int index = findIndex(r.getUuid());
        boolean isPresent = index != -1;
        if (isPresent) {
            storage[index] = r;
        } else {
            System.out.println("Error: can`t find resume: " + r);
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

    private int findIndex(String uuid) {
        return IntStream.range(0, size)
                .filter(i -> storage[i].getUuid().equals(uuid))
                .findFirst().orElse(-1);
    }
}
