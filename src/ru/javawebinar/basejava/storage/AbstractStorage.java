package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public abstract void clear();

    @Override
    public void update(Resume r) {
        if (r == null || r.getUuid() == null) return;
        Object key = getKey(r.getUuid());
        if (isExist(key)) {
            update(key, r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (r == null || r.getUuid() == null) return;
        Object key = getKey(r.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            save(r, key);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (uuid == null) return null;
        Object key = getKey(uuid);
        if (isExist(key)) {
            return get(key);
        }
        throw new NotExistStorageException(uuid);

    }

    @Override
    public void delete(String uuid) {
        if (uuid == null) return;
        Object key = getKey(uuid);
        if (isExist(key)) {
            delete(uuid, key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    protected abstract boolean isExist(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract void update(Object key, Resume r);

    protected abstract <T> void save(Resume r, T key);

    protected abstract Resume get(Object key);

    protected abstract void delete(String uuid, Object key);
}
