package ru.javawebinar.basejava.exception;

import java.io.IOException;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String msg, String uuid) {
        super(msg + uuid);
        this.uuid = uuid;
    }

    public StorageException(String msg, String uuid, IOException e) {
        super(msg + uuid, e);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
