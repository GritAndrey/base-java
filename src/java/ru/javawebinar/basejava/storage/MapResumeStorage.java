package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected Stream<Resume> getStorageStream() {
        return storage.values().stream();
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void makeUpdate(Resume key, Resume r) {
        makeSave(key, r);
    }

    @Override
    protected void makeSave(Resume key, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume makeGet(Resume key) {
        return key;
    }

    @Override
    protected void makeDelete(Resume key) {
        storage.remove(key.getUuid());
    }
}
