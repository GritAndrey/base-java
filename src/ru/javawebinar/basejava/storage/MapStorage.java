package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
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
}
