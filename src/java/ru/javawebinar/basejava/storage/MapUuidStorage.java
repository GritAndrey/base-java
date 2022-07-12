package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void makeUpdate(String key, Resume r) {
        makeSave(key, r);
    }

    @Override
    protected void makeSave(String key, Resume r) {
        storage.put(key, r);
    }

    @Override
    protected Resume makeGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void makeDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected Stream<Resume> getStorageStream() {
        return storage.values().stream();
    }
}
