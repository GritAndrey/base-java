package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test(expected = IllegalArgumentException.class)
    public  void positiveIndex() {
        ((SortedArrayStorage) storage).add(new Resume(),1);
    }
}