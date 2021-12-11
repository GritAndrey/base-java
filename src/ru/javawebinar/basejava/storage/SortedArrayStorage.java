package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);
    @Override
    protected void add(Resume r, int index) {
        if (index >= 0) {
            throw new IllegalArgumentException("index must be < 0. Was:" + index);
        }
        int actualIndex = Math.abs(index + 1);
        System.arraycopy(storage, actualIndex, storage, actualIndex + 1, size - actualIndex);
        storage[actualIndex] = r;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid,"dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}
