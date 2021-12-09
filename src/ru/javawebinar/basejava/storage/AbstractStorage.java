package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Objects;

public abstract class AbstractStorage<T> implements Storage {

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        T key = getKeyErrorIfNotExist(r.getUuid());
        makeUpdate(key, r);
    }

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        T key = getKeyErrorIfExist(r.getUuid());
        makeSave(key, r);
    }

    @Override
    public Resume get(String uuid) {
        T key = getKeyErrorIfNotExist(uuid);
        return makeGet(key);
    }

    @Override
    public void delete(String uuid) {
        T key = getKeyErrorIfNotExist(uuid);
        makeDelete(key);
    }

    private T getKeyErrorIfExist(String uuid) {
        Objects.requireNonNull(uuid);
        T key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private T getKeyErrorIfNotExist(String uuid) {
        Objects.requireNonNull(uuid);
        T key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract boolean isExist(T key);

    protected abstract T getKey(String uuid);

    protected abstract void makeUpdate(T key, Resume r);

    protected abstract void makeSave(T key, Resume r);

    protected abstract Resume makeGet(T key);

    protected abstract void makeDelete(T key);
}
