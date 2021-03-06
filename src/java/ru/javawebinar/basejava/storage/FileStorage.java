package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class FileStorage extends AbstractStorage<File> {
    private final File storageDir;
    private final SerializationStrategy strategy;

    protected FileStorage(File storageDir, SerializationStrategy strategy) {
        Objects.requireNonNull(storageDir, "Directory must not be null");
        this.strategy = strategy;
        if (!storageDir.isDirectory()) {
            throw new IllegalArgumentException(storageDir + " not a Directory ");
        }
        if (!storageDir.canWrite() || !storageDir.canRead()) {
            throw new IllegalArgumentException("can`t read\\write to " + storageDir);
        }
        this.storageDir = storageDir;
    }

    @Override
    public void clear() {
        Arrays.stream(getFilesList()).forEach(this::makeDelete);
    }

    @Override
    public int size() {
        return getFilesList().length;
    }

    @Override
    protected Stream<Resume> getStorageStream() {
        return Arrays.stream(getFilesList()).map(this::makeGet);

    }

    @Override
    protected boolean isExist(File key) {
        return key.exists();
    }

    @Override
    protected File getKey(String uuid) {
        return new File(storageDir, uuid);
    }

    @Override
    protected void makeUpdate(File key, Resume resume) {
        try {
            strategy.write(new BufferedOutputStream(new FileOutputStream(key)), resume);
        } catch (IOException e) {
            throw new StorageException("File IOError", key.toString(), e);
        }
    }

    @Override
    protected void makeSave(File key, Resume resume) {
        try {
            //noinspection ResultOfMethodCallIgnored
            key.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + key.getAbsolutePath(), key.getName(), e);
        }
        makeUpdate(key, resume);
    }

    @Override
    protected Resume makeGet(File key) {
        try {
            return strategy.read(new BufferedInputStream(new FileInputStream(key)));
        } catch (IOException e) {
            throw new StorageException("File IOError", key.toString(), e);
        }
    }

    @Override
    protected void makeDelete(File key) {
        if (!key.delete()) {
            throw new StorageException("Can`t delete file", key.getName());
        }
    }

    private File[] getFilesList() {
        File[] files = storageDir.listFiles();
        if (files == null) {
            throw new StorageException("empty dir");
        }
        return files;
    }
}
