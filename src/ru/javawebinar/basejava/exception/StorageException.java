package ru.javawebinar.basejava.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String msg) {
        this(msg, null, null);
    }

    public StorageException(String msg, String uuid) {
        super(msg + uuid);
        this.uuid = uuid;
    }

    public StorageException(String msg, Exception e) {
        this(msg, null, e);
    }

    public StorageException(String msg, String uuid, Exception e) {
        super(msg + uuid, e);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
