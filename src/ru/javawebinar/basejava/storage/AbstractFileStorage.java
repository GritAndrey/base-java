package ru.javawebinar.basejava.storage;

import java.io.File;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File storageDir;

    protected AbstractFileStorage(File storageDir) {
        Objects.requireNonNull(storageDir, "Directory must not be null");
        if (!storageDir.isDirectory()) {
            throw new IllegalArgumentException(storageDir + " not a Directory ");
        }
        if (storageDir.canWrite() || storageDir.canRead()) {
            throw new IllegalArgumentException("can`t read\\write to " + storageDir);
        }
        this.storageDir = storageDir;
    }

    @Override
    protected boolean isExist(File key) {
        return key.exists();
    }

    @Override
    protected File getKey(String uuid) {
        return new File(storageDir,uuid);
    }
}
