package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path storageDir;
    private final SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        storageDir = Paths.get(dir);
        this.strategy = strategy;
        Objects.requireNonNull(storageDir, "Directory must not be null");
        if (!Files.isDirectory(storageDir) || !Files.isWritable(storageDir)) {
            throw new IllegalArgumentException(storageDir + " not a Directory ");
        }
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::makeDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    protected Stream<Resume> getStorageStream() {
        return getFilesList().map(this::makeGet);
    }

    @Override
    protected boolean isExist(Path key) {
        return Files.isRegularFile(key);
    }

    @Override
    protected Path getKey(String uuid) {
        return storageDir.resolve(uuid);
    }

    @Override
    protected void makeUpdate(Path key, Resume resume) {
        try {
            strategy.write(new BufferedOutputStream(Files.newOutputStream(key)), resume);
        } catch (IOException e) {
            throw new StorageException("File IOError", key.toString(), e);
        }
    }

    @Override
    protected void makeSave(Path key, Resume resume) {
        makeUpdate(key, resume);
    }

    @Override
    protected Resume makeGet(Path key) {
        try {
            return strategy.read(new BufferedInputStream(Files.newInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("File IOError", key.toString(), e);
        }
    }

    @Override
    protected void makeDelete(Path key) {
        try {
            Files.delete(key);
        } catch (IOException e) {
            throw new StorageException("Can`t delete file", key.getFileName().toString(), e);
        }
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(storageDir);
        } catch (IOException e) {
            throw new StorageException(" Storage directory I/O error", e);
        }
    }
}
