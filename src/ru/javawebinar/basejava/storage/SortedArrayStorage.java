package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {



    @Override
    protected void add(Resume r, int index) {
        int actualIndex = Math.abs(index + 1);
        System.arraycopy(storage, actualIndex, storage, actualIndex + 1, size - actualIndex);
        storage[actualIndex] = r;
        size++;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
