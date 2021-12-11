package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractStorage<T> implements Storage {

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        T key = getExistedKey(r.getUuid());
        makeUpdate(key, r);
    }

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        T key = getNotExistedKey(r.getUuid());
        makeSave(key, r);
    }

    @Override
    public Resume get(String uuid) {
        T key = getExistedKey(uuid);
        return makeGet(key);
    }

    @Override
    public void delete(String uuid) {
        T key = getExistedKey(uuid);
        makeDelete(key);
    }

    private T getNotExistedKey(String uuid) {
        Objects.requireNonNull(uuid);
        T key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private T getExistedKey(String uuid) {
        Objects.requireNonNull(uuid);
        T key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        return getStorageStream()
                .sorted()
                .toList();
    }

    protected abstract Stream<Resume> getStorageStream();

    protected abstract boolean isExist(T key);

    protected abstract T getKey(String uuid);

    protected abstract void makeUpdate(T key, Resume r);

    protected abstract void makeSave(T key, Resume r);

    protected abstract Resume makeGet(T key);

    protected abstract void makeDelete(T key);
}
