package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path storageDir;
    private SerializationStrategy strategy;

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
        try {
            Files.list(storageDir).forEach(this::makeDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(storageDir).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
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
            strategy.write(new BufferedOutputStream(new FileOutputStream(key.toFile())), resume);
        } catch (IOException e) {
            throw new StorageException("File IOError", key.toString(), e);
        }
    }

    @Override
    protected void makeSave(Path key, Resume resume) {
        makeUpdate(key, resume);
    }

    @Override
    protected void makeDelete(Path key) {
        try {
            Files.delete(key);
        } catch (IOException e) {
            throw new StorageException("Can`t delete file", key.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume makeGet(Path key) {
        try {
            return strategy.read(new BufferedInputStream(new FileInputStream(key.toFile())));
        } catch (IOException e) {
            throw new StorageException("File IOError", key.toString(), e);
        }
    }

    @Override
    protected Stream<Resume> getStorageStream() {
        try {
            return Files.list(storageDir).map(this::makeGet);
        } catch (IOException e) {
            throw new StorageException(" Storage directory I/O error", null, e);
        }
    }
}
