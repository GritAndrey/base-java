package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    @Override
    public abstract void clear();

    @Override
    public void update(Resume r) {
        if (r == null || r.getUuid() == null) return;
        T key = getKey(r.getUuid());
        if (isExist(key)) {
            makeUpdate(key, r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (r == null || r.getUuid() == null) return;
        T key = getKey(r.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            makeSave(r, key);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (uuid == null) return null;
        T key = getKey(uuid);
        if (isExist(key)) {
            return makeGet(key);
        }
        throw new NotExistStorageException(uuid);

    }

    @Override
    public void delete(String uuid) {
        if (uuid == null) return;
        T key = getKey(uuid);
        if (isExist(key)) {
            makeDelete(uuid, key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();


    protected abstract boolean isExist(T key);

    protected abstract T getKey(String uuid);

    protected abstract void makeUpdate(T key, Resume r);

    protected abstract void makeSave(Resume r, T key);

    protected abstract Resume makeGet(T key);

    protected abstract void makeDelete(String uuid, T key);
}
