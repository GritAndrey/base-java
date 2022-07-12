package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void update(Resume r) {
        log.info("Update " + r);
        Objects.requireNonNull(r);
        SK key = getExistedKey(r.getUuid());
        makeUpdate(key, r);
    }

    @Override
    public void save(Resume r) {
        log.info("Save " + r);
        Objects.requireNonNull(r);
        SK key = getNotExistedKey(r.getUuid());
        makeSave(key, r);
    }

    @Override
    public Resume get(String uuid) {
        log.info("Get " + uuid);
        SK key = getExistedKey(uuid);
        return makeGet(key);
    }

    @Override
    public void delete(String uuid) {
        log.info("Delete " + uuid);
        SK key = getExistedKey(uuid);
        makeDelete(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        log.info("getAllSorted");
        return getStorageStream()
                .sorted()
                .collect(Collectors.toList());
    }

    private SK getNotExistedKey(String uuid) {
        Objects.requireNonNull(uuid);
        SK key = getKey(uuid);
        if (isExist(key)) {
            ExistStorageException existStorageException = new ExistStorageException(uuid);
            log.warning(existStorageException.getMessage());
            throw existStorageException;
        }
        return key;
    }

    private SK getExistedKey(String uuid) {
        Objects.requireNonNull(uuid);
        SK key = getKey(uuid);
        if (!isExist(key)) {
            NotExistStorageException notExistStorageException = new NotExistStorageException(uuid);
            log.warning(notExistStorageException.getMessage());
            throw notExistStorageException;
        }
        return key;
    }

    protected abstract Stream<Resume> getStorageStream();

    protected abstract boolean isExist(SK key);

    protected abstract SK getKey(String uuid);

    protected abstract void makeUpdate(SK key, Resume r);

    protected abstract void makeSave(SK key, Resume r);

    protected abstract Resume makeGet(SK key);

    protected abstract void makeDelete(SK key);
}
