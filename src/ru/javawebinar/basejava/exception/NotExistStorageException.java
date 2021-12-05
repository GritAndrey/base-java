package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    private static final String RESUME_NOT_EXIST = "Resume not exist: ";

    public NotExistStorageException(String uuid) {
        super(RESUME_NOT_EXIST, uuid);
    }
}
