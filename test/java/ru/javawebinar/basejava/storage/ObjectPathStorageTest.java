package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.PathStorage;
import ru.javawebinar.basejava.storage.strategy.ObjectStreamStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStrategy()));
    }
}