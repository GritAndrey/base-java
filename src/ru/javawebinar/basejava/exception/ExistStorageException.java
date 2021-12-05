package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    private static final String RESUME_ALREADY_EXIST = "Resume already exist";

    public ExistStorageException(String uuid) {
        super(RESUME_ALREADY_EXIST, uuid);
    }
}
