package ru.javawebinar.basejava.storage;

import static org.junit.Assert.*;

public class ObjectStreamStorageTest extends  AbstractStorageTest{

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}