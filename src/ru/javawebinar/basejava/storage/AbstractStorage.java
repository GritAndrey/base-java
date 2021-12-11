package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        SK key = getExistedKey(r.getUuid());
        makeUpdate(key, r);
    }

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        SK key = getNotExistedKey(r.getUuid());
        makeSave(key, r);
    }

    @Override
    public Resume get(String uuid) {
        SK key = getExistedKey(uuid);
        return makeGet(key);
    }

    @Override
    public void delete(String uuid) {
        SK key = getExistedKey(uuid);
        makeDelete(key);
    }

    private SK getNotExistedKey(String uuid) {
        Objects.requireNonNull(uuid);
        SK key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getExistedKey(String uuid) {
        Objects.requireNonNull(uuid);
        SK key = getKey(uuid);
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

    protected abstract boolean isExist(SK key);

    protected abstract SK getKey(String uuid);

    protected abstract void makeUpdate(SK key, Resume r);

    protected abstract void makeSave(SK key, Resume r);

    protected abstract Resume makeGet(SK key);

    protected abstract void makeDelete(SK key);
}
