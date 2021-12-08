package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ListStorage extends AbstractStorage<Integer> {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected Integer getKey(String uuid) {
        return getIndex(uuid);
    }

    @Override
    protected void makeUpdate(Integer key, Resume r) {
        storage.set(key, r);
    }

    @Override
    protected void makeSave(Integer key, Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume makeGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void makeDelete(Integer key) {
        storage.remove((int) key);
    }

    private int getIndex(String uuid) {
        return IntStream.range(0, storage.size())
                .filter(i -> storage.get(i).getUuid().equals(uuid))
                .findFirst().orElse(-1);
    }
}
